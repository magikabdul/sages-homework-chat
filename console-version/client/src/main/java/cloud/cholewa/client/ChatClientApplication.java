package cloud.cholewa.client;

import cloud.cholewa.client.services.ChatClient;

public class ChatClientApplication {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 9500;


    public static void main(String[] args) {
        new ChatClient(getDefaultHost(args), getDefaultPort(args)).start();
    }

    private static String getDefaultHost(String[] args) {
        for (String s : args) {
            if (s.contains("host:")) {
                return s.replace("host:", "");
            }
        }

        return DEFAULT_HOST;
    }

    private static int getDefaultPort(String[] args) {
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
