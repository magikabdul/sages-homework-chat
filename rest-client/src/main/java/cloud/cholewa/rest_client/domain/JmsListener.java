package cloud.cholewa.rest_client.domain;

import lombok.SneakyThrows;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Topic;

public class JmsListener {

    private static final String CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";

    private static MessageListener onMessage = message -> {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    };

    @SneakyThrows
    public void listen() {
        System.out.println("--------------------------------------------------listener starts working");
        var proxyFactory = new ProxyFactory();

        ConnectionFactory connectionFactory = proxyFactory.createProxy(CONNECTION_FACTORY_JNDI_NAME);
        Topic topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);

        while (true) {
            try (JMSContext jmsContext = connectionFactory.createContext()) {
                var consumer = jmsContext.createConsumer(topic);
                consumer.setMessageListener(onMessage);
            }
        }
//        System.out.println("--------------------------------------------------listener ends working");
    }
}
