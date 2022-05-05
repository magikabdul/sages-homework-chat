package cloud.cholewa.chat.domain.channel.port.out;

import cloud.cholewa.chat.domain.channel.model.HistoryMessage;
import cloud.cholewa.chat.domain.channel.model.Message;

import java.util.List;

public interface MessageRepositoryPort {

    Message save(Message message);

    List<HistoryMessage> getChannelHistory(String channelName);
}
