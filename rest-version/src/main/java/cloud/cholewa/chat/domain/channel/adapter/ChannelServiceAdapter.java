package cloud.cholewa.chat.domain.channel.adapter;

import cloud.cholewa.chat.domain.channel.exceptions.ChannelException;
import cloud.cholewa.chat.domain.channel.exceptions.MessageException;
import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.model.HistoryMessage;
import cloud.cholewa.chat.domain.channel.model.Message;
import cloud.cholewa.chat.domain.channel.port.in.ChannelServicePort;
import cloud.cholewa.chat.domain.channel.port.out.ChannelRepositoryPort;
import cloud.cholewa.chat.domain.channel.port.out.MessageRepositoryPort;
import cloud.cholewa.chat.domain.chat.port.in.ChatServicePort;
import cloud.cholewa.chat.domain.user.exceptions.UserException;
import cloud.cholewa.chat.domain.user.model.User;
import cloud.cholewa.chat.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static cloud.cholewa.chat.domain.channel.exceptions.ChannelExceptionDictionary.CHANNEL_EXIST;
import static cloud.cholewa.chat.domain.channel.exceptions.ChannelExceptionDictionary.CHANNEL_NOT_FOUND;
import static cloud.cholewa.chat.domain.channel.exceptions.MessageExceptionDictionary.MESSAGE_NOT_FOUND;
import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_INVALID_TOKEN;
import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_IS_NOT_CHANNEL_MEMBER;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ChannelServiceAdapter implements ChannelServicePort {

    private final UserRepositoryPort userRepository;
    private final ChannelRepositoryPort channelRepository;
    private final MessageRepositoryPort messageRepository;
    private final ChatServicePort chatService;

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

        var user = userRepository.findByToken(token).orElseThrow();

        var channel = chatService.getAllServerChannels().stream()
                .filter(ch -> ch.getActiveUsers().contains(user.getNick()))
                .findFirst()
                .orElseThrow(() -> new UserException(USER_IS_NOT_CHANNEL_MEMBER));

        var newMessage = Message.builder()
                .createdAtDate(LocalDate.now())
                .createdAtTime(LocalTime.now())
                .body(message)
                .author(user)
                .channel(channel)
                .build();

        Message savedMessage = messageRepository.save(newMessage);
        channel.setLastPostedMessageId(savedMessage.getId());

        return savedMessage;
    }

    @Override
    public HistoryMessage findMessageById(Long id, String token) {
        verifyPermission(token);

        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageException(MESSAGE_NOT_FOUND));
    }

    @Override
    public HistoryMessage findLastPostedMessage(String token) {
        verifyPermission(token);

        var user = userRepository.findByToken(token).orElseThrow();

        var channel = chatService.getAllServerChannels().stream()
                .filter(ch -> ch.getActiveUsers().contains(user.getNick()))
                .findFirst()
                .orElseThrow(() -> new UserException(USER_IS_NOT_CHANNEL_MEMBER));

        Long lastPostedMessageId = channel.getLastPostedMessageId();

        if (lastPostedMessageId > 0) {
            return messageRepository.findById(lastPostedMessageId)
                    .orElseThrow(() -> new MessageException(MESSAGE_NOT_FOUND));
        } else {
            throw new MessageException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void changeChannel(String name, String token) {
        var user = verifyPermission(token);
        var channel = getChannelIfExists(name);

        enableChanelOnServer(channel);
        moveUserToNewChannel(user, channel);
    }

    @Override
    public boolean addMember(User user) {
        return false;
    }

    @Override
    public void removeMember(User user) {

    }

    @Override
    public List<HistoryMessage> getHistory(String token) {
        var user = verifyPermission(token);

        var channel = chatService.getAllServerChannels().stream()
                .filter(ch -> ch.getActiveUsers().contains(user.getNick()))
                .findFirst()
                .orElseThrow();

        return messageRepository.getChannelHistory(channel.getName());
    }

    @SneakyThrows
    @Override
    public void saveFile(MultipartFormDataInput input) {
        InputStream inputStream = input.getFormDataPart("file", InputStream.class, null);
        String fileName = input.getFormDataPart("fileName", String.class, null);

        int read;
        byte[] bytes = new byte[1024];

        var outputStream = new FileOutputStream("d://" + fileName);
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        outputStream.flush();
        outputStream.close();
    }

    @Override
    public File getFile(String fileName) {
        File file = new File("d:\\" + fileName);

        return file;
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

    private User verifyPermission(String token) {
        Optional<User> user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new UserException(USER_INVALID_TOKEN);
        }

        return user.get();
    }

    private Channel getChannelIfExists(String name) {
        Optional<Channel> channel = channelRepository.findByName(name);

        if (channel.isEmpty()) {
            throw new ChannelException(CHANNEL_NOT_FOUND);
        }

        return channel.get();
    }

    private void enableChanelOnServer(Channel channel) {
        if (chatService.getAllServerChannels().stream().noneMatch(ch -> ch.getName().equals(channel.getName()))) {
            chatService.addChannel(channel);
        }
    }

    private void moveUserToNewChannel(User user, Channel channel) {
        chatService.getAllServerChannels().forEach(ch -> ch.getActiveUsers().remove(user.getNick()));

        Set<String> activeUsers = chatService.getAllServerChannels().stream()
                .filter(ch -> ch.getName().equals(channel.getName()))
                .map(Channel::getActiveUsers)
                .findFirst().orElseThrow();

        activeUsers.add(user.getNick());
    }
}
