package services.recorder;

import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatRecorder {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final String fileName;

    public ChatRecorder(String fileName) {
        this.fileName = getCurrentDate() + "-" + fileName;
    }

    public void record(String line) {
        lock.writeLock().lock();
        try (
                var fileWriter = new FileWriter(fileName, true);
                var printWriter = new PrintWriter(fileWriter)
        ) {
            printWriter.println(String.format("%s - %s", getCurrentTime(), line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }

    private String getCurrentTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    private String getCurrentDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    }
}
