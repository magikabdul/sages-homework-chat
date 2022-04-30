package cloud.cholewa.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {

    REQUEST_FOR_LOGIN("LOGIN_REQUEST"),
    RESPONSE_FOR_LOGIN("LOGIN_RESPONSE"),


    SERVER_COMMAND_LOGIN("LOGIN"),
    SERVER_COMMAND_OK("OK"),
    SERVER_COMMAND_CHAT("CHAT"),
    SERVER_COMMAND_CHANNEL("CHANNEL"),
    SERVER_COMMAND_END_SESSION("END"),
    SERVER_COMMAND_HISTORY("HISTORY"),
    SERVER_COMMAND_FILE_TRANSFER("TRANSFER");

    private final String name;
}
