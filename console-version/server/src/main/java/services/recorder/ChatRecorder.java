package services.recorder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static helpers.DateTimeService.getCurrentDate;
import static helpers.DateTimeService.getCurrentTime;

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
}
