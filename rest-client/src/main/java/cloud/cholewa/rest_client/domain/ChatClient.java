package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.ui.Console;

public class ChatClient {

    private final ClientService service = new ClientService();

    public void start() {
        Console.clear();
        Console.showWelcomeMessage();
        service.processLogin();
        service.processLogout();
    }
}
