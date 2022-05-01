package cloud.cholewa.client.services.file;

import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileTransmit {

    @SneakyThrows
    public void receive(Socket socket, String fileName) {
        int bytes;

        try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

            long size = dataInputStream.readLong();

            byte[] buffer = new byte[4 * 1024];
            while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void send(Socket socket, String filePath) {
        int bytes;
        File file = new File(filePath);
        try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             FileInputStream fileInputStream = new FileInputStream(file)) {

            dataOutputStream.writeLong(file.length());

            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
