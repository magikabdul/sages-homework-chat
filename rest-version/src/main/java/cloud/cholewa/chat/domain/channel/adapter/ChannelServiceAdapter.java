package cloud.cholewa.chat.domain.channel.adapter;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.model.Message;
import cloud.cholewa.chat.domain.channel.port.in.ChannelServicePort;
import cloud.cholewa.chat.domain.channel.port.out.ChannelRepositoryPort;
import cloud.cholewa.chat.domain.channel.port.out.MessageRepositoryPort;
import cloud.cholewa.chat.domain.user.exceptions.ChannelException;
import cloud.cholewa.chat.domain.user.model.User;
import cloud.cholewa.chat.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static cloud.cholewa.chat.domain.channel.exceptions.ChannelExceptionDictionary.CHANNEL_EXIST;
import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_INVALID_TOKEN;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ChannelServiceAdapter implements ChannelServicePort {

    private final UserRepositoryPort userRepository;
    private final ChannelRepositoryPort channelRepository;
    private final MessageRepositoryPort messageRepository;

    @Override
    public Channel createChannel(Channel channel, String token) {
        verifyPermission(token);

        if (channelRepository.findByName(channel.getName()).isPresent()) {
            throw new ChannelException(CHANNEL_EXIST);
        }

        return channelRepository.save(channel);
    }

    @Override
    public Message publishMessage(String message, String token) {
        verifyPermission(token);

        User user = userRepository.findByToken(token).orElseThrow();
        var newMessage = Message.builder()
                .createdAtDate(LocalDate.now())
                .createdAtTime(LocalTime.now())
                .body(message)
                .author(user)
                .build();

        return messageRepository.save(newMessage);
    }

    @Override
    public boolean changeChannel(String name) {
        return false;
    }

    @Override
    public boolean addMember(User user) {
        return false;
    }

    @Override
    public void removeMember(User user) {

    }

    @Override
    public List<Message> getHistory() {
        return null;
    }

    @Override
    public User getMemberByNick() {
        return null;
    }

    @Override
    public List<User> getAllMembers() {
        return null;
    }

    @Override
    public List<String> getActiveUsers() {
        return null;
    }

    private void verifyPermission(String token) {
        if (userRepository.findByToken(token).isEmpty()) {
            throw new ChannelException(USER_INVALID_TOKEN);
        }
    }
}
