package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.dto.UserDto;
import cloud.cholewa.rest_client.domain.ui.Console;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import lombok.SneakyThrows;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientService {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final String SERVER_URL = "http://localhost:8080/chat/";

    @SneakyThrows
    public String getConsole() {
        return es.submit(new ConsoleReader()).get();
    }

    public void processLogin() {
        boolean done = false;

        while (!done) {
            String command = getConsole();

            if (command.equals("1")) {
                executeRegister();
                done = executeLogin();
            } else if (command.equals("2")) {
                done = executeLogin();
            } else {
                Console.errorMessage("Invalid option");
            }
        }
    }

    private void executeRegister() {
        Console.infoMessage("NICK: ", false);
        String nick = getConsole();
        Console.infoMessage("PASSWORD: ", false);
        String password = getConsole();


        var restClient = new ResteasyClientBuilderImpl().build();

        var response =  restClient.target(SERVER_URL + "users/register")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(
                        UserDto.builder()
                                .nick(nick)
                                .password(password)
                                .build(),
                        MediaType.APPLICATION_JSON));

        Console.infoMessage("Status: " + response.getStatus(), true);
        Console.successMessage("SUCCESS - please login", false);
    }

    private boolean executeLogin() {
        Console.infoMessage("NICK: ", false);
        String nick = getConsole();
        Console.infoMessage("PASSWORD: ", false);
        String password = getConsole();
        return true;
    }

    public void processLogout() {
        Console.infoMessage("\n Bye !!! ", false);
        es.shutdown();
    }
}
