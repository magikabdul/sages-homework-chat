package cloud.cholewa.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {

    REQUEST_FOR_LOGIN("LOGIN_REQUEST"),
    RESPONSE_FOR_LOGIN("LOGIN_RESPONSE"),
    SERVER_OK("SERVER_OK"),

    REQUEST_END_SESSION("END"),

    CLIENT_CHAT("CLIENT_CHAT"),
    SERVER_CHAT("SERVER_CHAT"),





    SERVER_COMMAND_CHANNEL("CHANNEL"),
    SERVER_COMMAND_HISTORY("HISTORY"),
    SERVER_COMMAND_FILE_TRANSFER("TRANSFER");

    private final String name;
}
