package cloud.cholewa.chat.infrastructure.application.jms;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Messages")
})
public class MessagingService implements MessageListener {

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        var text = message.getBody(String.class);
        System.out.println("-------------------------------" + text);
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println(getClass().getSimpleName() + ": postConstruct");
    }
}
