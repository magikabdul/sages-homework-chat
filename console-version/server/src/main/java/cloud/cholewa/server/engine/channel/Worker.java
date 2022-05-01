package cloud.cholewa.server.engine.channel;

import cloud.cholewa.message.Message;
import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.file.FileTransmit;
import cloud.cholewa.server.engine.channel.message.ChannelReader;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import cloud.cholewa.server.engine.channel.storage.ChannelHistoryStorage;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.List;
import java.util.Optional;

import static cloud.cholewa.message.MessageType.FILE_RECEIVE_REQUEST;
import static cloud.cholewa.message.MessageType.FILE_TRANSFER_ERROR;
import static cloud.cholewa.message.MessageType.FILE_TRANSFER_REQUEST;
import static cloud.cholewa.message.MessageType.HISTORY_BEGIN;
import static cloud.cholewa.message.MessageType.HISTORY_END;
import static cloud.cholewa.message.MessageType.HISTORY_POSITION;
import static cloud.cholewa.message.MessageType.REQUEST_FOR_LOGIN;
import static cloud.cholewa.message.MessageType.RESPONSE_CHANNEL_CHANGE;
import static cloud.cholewa.message.MessageType.RESPONSE_CHANNEL_CHANGE_ERROR;
import static cloud.cholewa.message.MessageType.SERVER_OK;


public class Worker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    @Getter
    private final User user = new User();
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

        messageWriter = new ChannelWriter(messageSocket);
        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(REQUEST_FOR_LOGIN)
                .body("Please enter your name")
                .build());

        try {
            new ChannelReader(messageSocket, this::processIncomingMessage).read();
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
            case HISTORY_REQUEST:
                downloadHistory();
                break;
            case FILE_SENDING_NOTIFY:
                processFileSendingNotify(message);
                break;
            case FILE_RECEIVING_FROM_SERVER_READY:
                sendFileFromServer(message);
                break;
            default:
                log.error("Unknown client message type");
        }
    }

    private void sendFileFromServer(Message message) {
        String fileName = message.getBody().split("/")[0].split(":")[1];

        fileTransmit.send(fileSocket, "server-" + fileName);

        messageWriter.send(Message.builder()
                .user(user.getName())
                .channel(user.getChannel())
                .type(SERVER_OK)
                .body("")
                .build());
    }

    private void processFileSendingNotify(Message message) {
        String[] transferData = message.getBody().split("/");
        String fileName = transferData[0].split(":")[1];
        String targetUser = transferData[1].split(":")[1];


        ChatChannel channel = serverChannels.stream()
                .filter(chatChannel -> chatChannel.getName().equals(message.getChannel()))
                .findFirst()
                .orElseThrow();

        Optional<Worker> targetWorker = channel.getAllWorkers().stream()
                .filter(worker -> worker.getUser().getName().equals(targetUser))
                .findFirst();

        if (targetWorker.isPresent()) {
            messageWriter.send(Message.builder()
                    .type(FILE_TRANSFER_REQUEST)
                    .body(message.getBody())
                    .build());
            fileTransmit.receive(fileSocket, fileName);

            targetWorker.get().messageWriter.send(Message.builder()
                    .type(FILE_RECEIVE_REQUEST)
                    .body(message.getBody())
                    .build());

        } else {
            messageWriter.send(Message.builder()
                    .type(FILE_TRANSFER_ERROR)
                    .body("Target user not found on current channel")
                    .build());
        }
    }

    private void downloadHistory() {
        List<String> history = historyStorage.getHistory(user.getChannel());

        messageWriter.send(Message.builder()
                .type(HISTORY_BEGIN)
                .body("------------------- BEGIN -------------------")
                .build());

        history.forEach(s -> messageWriter.send(Message.builder()
                .type(HISTORY_POSITION)
                .body(s)
                .build()));

        messageWriter.send(Message.builder()
                .type(HISTORY_END)
                .body("-------------------- END --------------------")
                .build());
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

        historyStorage.save(message.getChannel(), message);

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
}
