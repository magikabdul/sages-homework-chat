package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.ui.Console;

public class ChatClient {

    private final ClientService service = new ClientService();

    public void start() {
        Console.clear();

        new Thread(() -> new JmsListener().listen()).start();

        Console.showWelcomeMessage();
        service.processLogin();
        service.processLogout();
    }
}
