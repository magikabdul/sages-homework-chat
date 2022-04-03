import cloud.cholewa.server.ChatServer;

public class ChatServerApplication {

    private static final int DEFAULT_PORT = 9500;

    public static void main(String[] args) {
        new ChatServer(getPort(args)).start();
    }

    private static int getPort(String[] args) {
        for (String s : args) {
            if (s.contains("port:")) {
                try {
                    return Integer.parseInt(s.replace("port:", ""));
                } catch (NumberFormatException e) {
                    return DEFAULT_PORT;
                }
            }
        }

        return DEFAULT_PORT;
    }
}
