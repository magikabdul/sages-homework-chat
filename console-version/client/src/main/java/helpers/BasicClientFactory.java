package helpers;

import org.apache.log4j.Logger;

public class BasicClientFactory implements ClientFactory {

    @Override
    public Logger createLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }
}
