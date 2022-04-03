package services.recorder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

    public List<String> getHistory(String fileName) {
        List<String> history = new ArrayList<>();

        lock.writeLock().lock();

        try (
                var fileReader = new FileReader(fileName);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lock.writeLock().unlock();

        return history;
    }
}
