package cloud.cholewa.client.services.message;

import cloud.cholewa.client.helpers.BasicClientFactory;
import cloud.cholewa.client.services.ChatClient;
import cloud.cholewa.client.services.file.FileTransmit;
import cloud.cholewa.client.ui.Console;
import cloud.cholewa.message.Message;
import lombok.SneakyThrows;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static cloud.cholewa.message.MessageType.FILE_RECEIVING_FROM_SERVER_READY;

public class ServerMessageReader {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());
    private final FileTransmit fileTransmit = new FileTransmit();

    private final ObjectInputStream objectInputStream;
    private final ChatClient chatClient;


    @SneakyThrows
    public ServerMessageReader(ChatClient chatClient) {
        this.chatClient = chatClient;
        objectInputStream = new ObjectInputStream(chatClient.getMessageSocket().getInputStream());
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    public void read() {
        log.setLevel(Level.OFF);

        try {
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                chatClient.setLastServerMessage(message);
                processInputMessage(message);
            }
        } catch (EOFException e) {
            Console.writeSuccessMessage(true, "Bye. User correctly logged out\n", false);
        }
    }

    public void processInputMessage(Message message) {
        switch (message.getType()) {
            case REQUEST_FOR_LOGIN:
                requestForLogin(message);
                break;
            case SERVER_CHAT:
                processServerBroadcast(message);
                break;
            case RESPONSE_CHANNEL_CHANGE:
                updateChannelData(message);
                break;
            case RESPONSE_CHANNEL_CHANGE_ERROR:
                handleChannelChangeError(message);
                break;
            case HISTORY_BEGIN:
                Console.writeHistoryHeader(message.getBody());
                break;
            case HISTORY_POSITION:
                Console.writeHistoryPosition(message.getBody());
                break;
            case HISTORY_END:
                Console.writeHistoryFooter(message.getBody());
                showPrompt();
                break;
            case FILE_TRANSFER_ERROR:
                handleFileTransferError(message);
                break;
            case FILE_TRANSFER_REQUEST:
                handleFileSend(message);
                break;
            case FILE_RECEIVE_REQUEST:
                handleFileReceive(message);
                break;
            default:
                showPrompt();
        }
    }

    @SneakyThrows
    private void handleFileReceive(Message message) {
        Console.writeInfoMessage(true, "Receiving file type \\r and hit ENTER", false);
        chatClient.setLastServerMessage(message);
        //String fileName = message.getBody().split("/")[0].split(":")[1];

//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(chatClient.getMessageSocket().getOutputStream());
//        objectOutputStream.writeObject(Message.builder()
//                .type(FILE_RECEIVING_FROM_SERVER_READY)
//                .body(message.getBody())
//                .build());
//        System.out.println("fff");
//        fileTransmit.receive(chatClient.getFileSocket(), chatClient.getUser().getName() + "-" + fileName);
//
//        Console.writeInfoMessage(true, "Finished file receiving", false);
//        showPrompt();
    }

    private void handleFileSend(Message message) {
        Console.writeInfoMessage(true, "File transfer started", false);
        String fileName = message.getBody().split("/")[0].split(":")[1];

        fileTransmit.send(chatClient.getFileSocket(), fileName);
        showPrompt();
    }

    private void handleFileTransferError(Message message) {
        Console.writeErrorMessage(true, message.getBody(), false);
        showPrompt();
    }

    private void handleChannelChangeError(Message message) {
        Console.writeErrorMessage(true, message.getBody(), false);
        showPrompt();
    }


    private void updateChannelData(Message message) {
        chatClient.getUser().setChannel(message.getChannel());
        showPrompt();
    }

    private void processServerBroadcast(Message message) {
        Console.writeServerBroadcast(message.getUser(), message.getChannel(), message.getBody());
        showPrompt();
    }

    private void showPrompt() {
        Console.writePromptMessage(true, chatClient.getUser());
    }

    public void requestForLogin(Message message) {
        Console.writeInfoMessage(true, message.getBody(), true);
    }
}
