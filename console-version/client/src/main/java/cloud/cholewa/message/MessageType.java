package cloud.cholewa.message;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public enum MessageType implements Serializable {

    REQUEST_FOR_LOGIN("LOGIN_REQUEST"),
    RESPONSE_FOR_LOGIN("LOGIN_RESPONSE"),
    SERVER_OK("SERVER_OK"),

    REQUEST_END_SESSION("END"),

    CLIENT_CHAT("CLIENT_CHAT"),
    SERVER_CHAT("SERVER_CHAT"),

    REQUEST_CHANNEL_CHANGE("REQUEST_CHANNEL_CHANGE"),
    RESPONSE_CHANNEL_CHANGE_ERROR("RESPONSE_CHANNEL_CHANGE_ERROR"),
    RESPONSE_CHANNEL_CHANGE("RESPONSE_CHANNEL_CHANGE"),




    SERVER_COMMAND_HISTORY("HISTORY"),
    SERVER_COMMAND_FILE_TRANSFER("TRANSFER");

    private final String name;
}
