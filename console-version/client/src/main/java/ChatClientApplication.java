import services.ChatClient;

public class ChatClientApplication {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 9500;


    public static void main(String[] args) {
        new ChatClient(DEFAULT_HOST, DEFAULT_PORT).start();
    }
}
