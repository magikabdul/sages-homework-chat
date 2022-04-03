package cloud.cholewa.server.helpers;

import cloud.cholewa.server.engine.channel.ChatChannel;
import cloud.cholewa.server.engine.channel.PrivateChatChannel;

import java.util.List;

public class ConfigurationFiller {

    public static void initialize(List<ChatChannel> serverChannels) {
        PrivateChatChannel privateChatChannel;

        //create channel games and some members
        privateChatChannel = new PrivateChatChannel("GAMES");
        privateChatChannel.addMember("kris");
        privateChatChannel.addMember("tom");
        privateChatChannel.addMember("alice");
        serverChannels.add(privateChatChannel);

        //create channel movies and some members
        privateChatChannel = new PrivateChatChannel("MOVIES");
        privateChatChannel.addMember("kris");
        privateChatChannel.addMember("ed");
        privateChatChannel.addMember("thomas");
        privateChatChannel.addMember("liv");
        serverChannels.add(privateChatChannel);
    }
}
