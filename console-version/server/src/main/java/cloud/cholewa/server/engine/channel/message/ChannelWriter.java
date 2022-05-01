package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.message.Message;
import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.User;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChannelWriter {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private ObjectOutputStream objectOutputStream;

    public ChannelWriter(Socket messageSocket) {

        try {
            objectOutputStream = new ObjectOutputStream(messageSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (Exception e) {
            log.error("Message sending problem");
        }
    }
}
