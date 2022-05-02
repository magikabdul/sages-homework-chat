package cloud.cholewa.rest_client;

import cloud.cholewa.rest_client.domain.ChatClient;

public class ClientRestApplication {

    public static void main(String[] args) {
        new ChatClient().start();
    }
}
