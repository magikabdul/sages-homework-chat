package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.message.ChannelReader;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import cloud.cholewa.server.engine.channel.message.ClientMessageParser;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.CONTROL_COMMAND_CHANNEL_CHANGE;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.CONTROL_COMMAND_EMPTY_BODY;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.CONTROL_COMMAND_END_SESSION;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.HEADER_LOGIN;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.HEADER_LOGOUT;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_SYSTEM;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_CHANNEL;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_END_SESSION;


public class Worker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    @Getter
    private final User user = new User();
    private final ClientMessageParser clientMessageParser = new ClientMessageParser();

    private final Socket socket;
    private final List<ChatChannel> serverChannels;

    @Getter
    private ChannelWriter writer;

    public Worker(Socket socket, List<ChatChannel> serverChannels) {
        this.socket = socket;
        this.serverChannels = serverChannels;
    }

    @Override
    public void run() {
        log.debug("Running new chat worker thread");

        writer = new ChannelWriter(socket, user);

        try {
            new ChannelReader(socket, writer, this::processIncomingMessage).read();
        } catch (ConnectionLostException e) {
            removeWorkerFromServerChannels();
            log.error("Client connection lost");
        }
    }

    private void processIncomingMessage(String message) {
        log.debug("CLIENT MESSAGE: " + message);
        clientMessageParser.parseToMap(message);

        if (clientMessageParser.getMessageType().equals(MESSAGE_TYPE_SYSTEM)) {
            switch (clientMessageParser.getHeader()) {
                case HEADER_LOGIN:
                    registerNewUserLogin(clientMessageParser.getBody());
                    writer.send("", "");
                    break;
                case HEADER_LOGOUT:
                    writer.send("", "");
                    break;
            }
        } else {
            processClientMessageType(clientMessageParser.getBody());
        }
    }

    private void processClientMessageType(String message) {
        if (message.equals(CONTROL_COMMAND_EMPTY_BODY)) {
            writer.send("", "");
        } else if (message.equals(CONTROL_COMMAND_END_SESSION)) {
            writer.send(SERVER_COMMAND_END_SESSION, "Bye " + user.getName());
            log.debug(String.format("User \"%s\" left server", user.getName()));
            removeWorkerFromServerChannels();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.contains(CONTROL_COMMAND_CHANNEL_CHANGE)) {
            changeChannel(message.substring(2));    //removing \c
        } else {
            broadcastMessageToAllChannelUsers(clientMessageParser.getBody());
            writer.send("", "");
        }
    }

    private void changeChannel(String newName) {
        String newChannelName = newName.toUpperCase();

        if (serverChannels.stream().map(ChatChannel::getName).anyMatch(s -> s.equals(newChannelName))) {
            if (!newChannelName.isBlank()) {
                PrivateChatChannel channel = (PrivateChatChannel) serverChannels.stream().filter(chatChannel -> chatChannel.getName().equals(newChannelName)).findFirst().orElseThrow();
                if (channel.getAllMembers().contains(user.getName())) {
                    removeWorkerFromServerChannels();
                    channel.addWorker(this);
                    user.setChannel(newChannelName);
                    writer.send("", "");
                    log.debug(String.format("User %s has moved to channel %s", user.getName(), newChannelName));
                } else {
                    writer.send(SERVER_COMMAND_CHANNEL, String.format("User \"%s\" has no permission to switch to channel \"%s\"", user.getName(), newChannelName));
                    writer.send("", "");
                }
            } else {
                removeWorkerFromServerChannels();
                ChatChannel globalChannel = serverChannels.stream().filter(chatChannel -> chatChannel.getName().equals("")).findFirst().orElseThrow();
                globalChannel.addWorker(this);
                user.setChannel("");
                writer.send("", "");
                log.debug(String.format("User %s has moved to main channel", user.getName()));
            }

        } else {
            writer.send(SERVER_COMMAND_CHANNEL, String.format("Channel \"%s\" doesn't found on server channels list", newChannelName));
            writer.send("", "");
        }
    }

    private void broadcastMessageToAllChannelUsers(String message) {
        ChatChannel channel = serverChannels.stream()
                .filter(chatChannel -> chatChannel.getName().equals(user.getChannel()))
                .findFirst().orElseThrow();

        channel.broadcast(this, String.format("%s: %s", user.getName(), message));
    }

    private void removeWorkerFromServerChannels() {
        List<ChatChannel> channels = serverChannels.stream()
                .filter(chatChannel -> chatChannel.getAllWorkers().contains(this))
                .collect(Collectors.toList());

        channels.forEach(chatChannel -> chatChannel.removeWorker(this));


    }

    private void registerNewUserLogin(String messageBody) {
        user.setName(messageBody);
        log.debug(String.format("User \"%s\" join to server", user.getName()));
    }
}
