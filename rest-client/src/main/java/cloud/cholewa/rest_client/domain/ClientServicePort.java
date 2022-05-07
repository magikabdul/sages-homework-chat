package cloud.cholewa.rest_client.domain;

public interface ClientServicePort {
    void processLogin();

    void processLogout();

    void processChat();
}
