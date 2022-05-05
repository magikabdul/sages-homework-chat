package cloud.cholewa.chat.domain.channel.port.out;

import cloud.cholewa.chat.domain.channel.model.Message;

public interface MessageRepositoryPort {

    Message save(Message message);
}
