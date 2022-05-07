package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.ui.Console;

public class ChatClient {

    private final ClientServicePort servicePort = new ClientService();

    public void start() {
        Console.clear();
        Console.showWelcomeMessage();
        servicePort.processLogin();
        servicePort.processChat();
        servicePort.processLogout();
    }
}
