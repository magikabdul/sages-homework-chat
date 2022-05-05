package cloud.cholewa.chat.domain.channel.port.in;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.model.Message;
import cloud.cholewa.chat.domain.user.model.User;

import java.util.List;

public interface ChannelServicePort {

    Channel createChannel(Channel channel, String token);

    boolean changeChannel(String name);

    Message publishMessage(String message, String token);

    boolean addMember(User user);

    void removeMember(User user);

    List<Message> getHistory();

    User getMemberByNick();

    List<User> getAllMembers();

    List<String> getActiveUsers();
}
