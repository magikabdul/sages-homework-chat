package cloud.cholewa.chat.domain.channel.port.in;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.model.HistoryMessage;
import cloud.cholewa.chat.domain.channel.model.Message;
import cloud.cholewa.chat.domain.user.model.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.File;
import java.util.List;

public interface ChannelServicePort {

    Channel createChannel(Channel channel, String token);

    void changeChannel(String name, String token);

    Message publishMessage(String message, String token);

    boolean addMember(User user);

    void removeMember(User user);

    List<HistoryMessage> getHistory(String token);

    HistoryMessage findMessageById(Long id, String token);

    HistoryMessage findLastPostedMessage(String token);

    void saveFile(MultipartFormDataInput input);

    File getFile(String fileName);

    User getMemberByNick();

    List<User> getAllMembers();

    List<String> getActiveUsers();
}
