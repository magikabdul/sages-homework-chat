package cloud.cholewa.client.helpers;

import org.apache.log4j.Logger;

public interface ClientFactory {

    Logger createLogger(Class<?> clazz);
}
