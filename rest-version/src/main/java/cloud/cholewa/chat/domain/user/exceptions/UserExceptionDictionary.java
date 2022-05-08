package cloud.cholewa.chat.domain.user.exceptions;

public class UserExceptionDictionary {

    public static String USER_EXISTS = "User already exists";
    public static String USER_NOT_FOUND = "User not found, please register";
    public static String USER_INVALID_CREDENTIALS = "Invalid password";
    public static String USER_INVALID_TOKEN = "User has no permissions";
    public static String USER_IS_NOT_CHANNEL_MEMBER = "User cant publish messages on channel";
}
