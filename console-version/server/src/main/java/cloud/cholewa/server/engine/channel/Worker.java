package cloud.cholewa.server.engine.channel;

import cloud.cholewa.message.Message;
import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.file.FileTransmit;
import cloud.cholewa.server.engine.channel.message.ChannelReader;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import cloud.cholewa.server.engine.channel.message.ClientMessageParser;
import cloud.cholewa.server.engine.channel.storage.ChannelHistoryStorage;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cloud.cholewa.message.MessageType.REQUEST_FOR_LOGIN;
import static cloud.cholewa.message.MessageType.RESPONSE_CHANNEL_CHANGE;
import static cloud.cholewa.message.MessageType.RESPONSE_CHANNEL_CHANGE_ERROR;
import static cloud.cholewa.message.MessageType.SERVER_OK;


public class Worker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    @Getter
    private final User user = new User();
    private final ClientMessageParser clientMessageParser = new ClientMessageParser();
    private final ChannelHistoryStorage historyStorage = new ChannelHistoryStorage();
    private final FileTransmit fileTransmit = new FileTransmit();

    @Getter
    private final Socket messageSocket;
    private final Socket fileSocket;

    private final List<ChatChannel> serverChannels;

    @Getter
    private ChannelWriter messageWriter;

    public Worker(Socket messageSocket, Socket fileSocket, List<ChatChannel> serverChannels) {
        this.messageSocket = messageSocket;
        this.fileSocket = fileSocket;
        this.serverChannels = serverChannels;
    }

    @Override
    public void run() {
        log.debug("Running new chat worker thread");

        messageWriter = new ChannelWriter(messageSocket, user);
        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(REQUEST_FOR_LOGIN)
                .body("Please enter your name")
                .build());

        try {
            new ChannelReader(messageSocket, fileSocket, messageWriter, this::processIncomingMessage).read();
        } catch (ConnectionLostException e) {
            removeWorkerFromServerChannels();
        }
    }

    private void processIncomingMessage(Message message) {
        switch (message.getType()) {
            case RESPONSE_FOR_LOGIN:
                responseForLogin(message);
                break;
            case REQUEST_END_SESSION:
                executeEndSession();
                break;
            case CLIENT_CHAT:
                broadcastClientMessage(message);
                break;
            case REQUEST_CHANNEL_CHANGE:
                executeChannelChange(message);
                break;
            default:
                log.error("Unknown client message type");
        }
    }

    private void executeChannelChange(Message message) {
        if (message.getChannel().isBlank()) {
            switchToChannel(message.getChannel());
        } else {

            Optional<ChatChannel> channel = serverChannels.stream()
                    .filter(chatChannel -> chatChannel.getName().equals(message.getChannel()))
                    .findFirst();

            if (channel.isPresent()) {
                PrivateChatChannel privateChatChannel = (PrivateChatChannel) channel.get();

                Optional<String> anyMember = privateChatChannel.getAllMembers().stream()
                        .filter(m -> m.equals(message.getUser()))
                        .findAny();

                if (anyMember.isPresent()) {
                    switchToChannel(message.getChannel());
                } else {
                    messageWriter.send(Message.builder()
                            .user(getUser().getName())
                            .user(getUser().getChannel())
                            .type(RESPONSE_CHANNEL_CHANGE_ERROR)
                            .body("Error - No permission to join to channel: " + message.getChannel())
                            .build());
                }

            } else {
                messageWriter.send(Message.builder()
                        .user(getUser().getName())
                        .channel(getUser().getChannel())
                        .type(RESPONSE_CHANNEL_CHANGE_ERROR)
                        .body("Error - channel doesn't exists")
                        .build());
            }
        }
    }

    private void switchToChannel(String name) {
        ChatChannel channel = serverChannels.stream()
                .filter(chatChannel -> chatChannel.getName().equals(name))
                .findFirst()
                .orElseThrow();

        removeWorkerFromServerChannels();
        channel.getAllWorkers().add(this);
        user.setChannel(name);

        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(RESPONSE_CHANNEL_CHANGE)
                .build());
    }

    private void broadcastClientMessage(Message message) {
        user.setName(message.getUser());
        user.setChannel(message.getChannel());

        serverChannels.stream()
                .filter(chatChannel -> chatChannel.getAllWorkers().contains(this))
                .findAny().orElseThrow()
                .broadcast(this, message.getBody());

        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(SERVER_OK)
                .body("")
                .build());
    }

    @SneakyThrows
    private void executeEndSession() {
        removeWorkerFromServerChannels();
        messageSocket.close();
        fileSocket.close();
    }

    private void responseForLogin(Message message) {
        user.setName(message.getUser());
        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(SERVER_OK)
                .body("")
                .build());
    }

    private void removeWorkerFromServerChannels() {
        serverChannels.stream()
                .filter(chatChannel -> chatChannel.getAllWorkers().contains(this))
                .forEach(chatChannel -> chatChannel.removeWorker(this));
    }

//
//    }
//
//    private void executeFileTransfer(String targetUser) {
//        //TODO check if user on online in current channel
//        Worker targetWorker = serverChannels.stream()
//                .filter(channel -> channel.getName().equals(user.getChannel()))
//                .map(ChatChannel::getAllWorkers)
//                .flatMap(Collection::stream)
//                .filter(worker -> worker.getUser().getName().equals(targetUser))
//                .findFirst().orElse(this);
//
//        if (targetWorker.getUser().getName().equals(getUser().getName())) {
//            writer.send(SERVER_COMMAND_FILE_TRANSFER, "Target user not found on this channel");
//            writer.send("", "");
//        } else {
//            log.debug("Starting file transfer to user: " + targetUser);
//            //TODO transfer logic
//            targetWorker.getWriter().send(SERVER_COMMAND_FILE_TRANSFER, "U are receiving file from user: " + getUser().getName());
////            targetWorker.getWriter().send("", "");
//
//            new Thread(() -> fileTransmit.receive(targetWorker.getMessageSocket(), "ubuntu")).start();
//            new Thread(() -> fileTransmit.send(messageSocket, "F:\\iso\\multipass-1.4.0+win-win64.exe")).start();
//            targetWorker.getWriter().send(SERVER_COMMAND_FILE_TRANSFER, "Transfer finished !!!");
//            targetWorker.getWriter().send("", "");
//
//
//            writer.send(SERVER_COMMAND_FILE_TRANSFER, "here should be a file");
//            log.debug("File transfer done");
//            writer.send("", "");
//        }
//    }
//
//    private void downloadChannelHistory() {
//        List<String> history = historyStorage.getHistory(user.getChannel());
//
//        history.forEach(s -> writer.send(SERVER_COMMAND_HISTORY, s));
//        writer.send("", "");
//    }
//
//    private void changeChannel(String newName) {
//        String newChannelName = newName.toUpperCase();
//
//        if (serverChannels.stream().map(ChatChannel::getName).anyMatch(s -> s.equals(newChannelName))) {
//            if (!newChannelName.isBlank()) {
//                PrivateChatChannel channel = (PrivateChatChannel) serverChannels.stream().filter(chatChannel -> chatChannel.getName().equals(newChannelName)).findFirst().orElseThrow();
//                if (channel.getAllMembers().contains(user.getName())) {
//                    removeWorkerFromServerChannels();
//                    channel.addWorker(this);
//                    user.setChannel(newChannelName);
//                    writer.send("", "");
//                    log.debug(String.format("User %s has moved to channel %s", user.getName(), newChannelName));
//                } else {
//                    writer.send(SERVER_COMMAND_CHANNEL, String.format("User \"%s\" has no permission to switch to channel \"%s\"", user.getName(), newChannelName));
//                    writer.send("", "");
//                }
//            } else {
//                removeWorkerFromServerChannels();
//                ChatChannel globalChannel = serverChannels.stream().filter(chatChannel -> chatChannel.getName().equals("")).findFirst().orElseThrow();
//                globalChannel.addWorker(this);
//                user.setChannel("");
//                writer.send("", "");
//                log.debug(String.format("User %s has moved to main channel", user.getName()));
//            }
//
//        } else {
//            writer.send(SERVER_COMMAND_CHANNEL, String.format("Channel \"%s\" doesn't found on server channels list", newChannelName));
//            writer.send("", "");
//        }
//    }
//
//    private void broadcastMessageToAllChannelUsers(String message) {
//        ChatChannel channel = serverChannels.stream()
//                .filter(chatChannel -> chatChannel.getName().equals(user.getChannel()))
//                .findFirst().orElseThrow();
//
//        channel.broadcast(this, String.format("%s: %s", user.getName(), message));
//    }
}
