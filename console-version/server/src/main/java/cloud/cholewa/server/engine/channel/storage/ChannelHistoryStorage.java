package cloud.cholewa.server.engine.channel.storage;

import cloud.cholewa.server.builders.BasicServerFactory;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChannelHistoryStorage {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ChannelHistoryStorage() {

    }

    public void save(String channelName, String message) {
        if (channelName.isBlank()) {
            channelName = "GLOBAL";
        }

        lock.writeLock().lock();

        try (
                FileWriter fileWriter = new FileWriter(channelName + ".txt", true);
                PrintWriter printWriter = new PrintWriter(fileWriter)
        ) {
            printWriter.println(message);
        } catch (IOException e) {
            log.error(String.format("Problem with create/access to file %s", channelName));
        }
        lock.writeLock().unlock();
    }

    public void getHistory(String channelName) {
        lock.readLock().lock();

        lock.readLock().unlock();
    }

//
//        public List<String> getHistory(String fileName) {
//            List<String> history = new ArrayList<>();
//
//            lock.writeLock().lock();
//
//            try (
//                    var fileReader = new FileReader(fileName);
//                    var bufferedReader = new BufferedReader(fileReader)
//            ) {
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    history.add(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            lock.writeLock().unlock();
//
//            return history;
//        }
//    }
//
}
