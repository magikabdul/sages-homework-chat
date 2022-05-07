package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.dto.ErrorDto;
import cloud.cholewa.rest_client.domain.dto.TokenDto;
import cloud.cholewa.rest_client.domain.dto.UserDto;
import cloud.cholewa.rest_client.domain.ui.Console;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientService implements ClientServicePort {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final ApiGateway apiGateway = new ApiGateway();

    public static final String END_SESSION = "\\q";

    private String userName;
    private String token;
    private String channel;

    @SneakyThrows
    public String getConsole() {
        return es.submit(new ConsoleReader()).get();
    }

    @Override
    public void processLogin() {
        boolean done = false;

        while (!done) {
            String command = getConsole();

            switch (command) {
                case "1" -> done = executeLogin();
                case "2" -> {
                    executeRegister();
                    done = executeLogin();
                }
                case "3" -> done = true;
                default -> Console.errorMessage("Invalid option", true);
            }
        }
    }

    @Override
    public void processChat() {
        boolean done = false;

        while (!done) {
            String command = getConsole();

            switch (command) {
                case END_SESSION -> done = true;
                default -> Console.advancedPrompt(channel, userName);
            }
        }
    }

    private void executeRegister() {
        boolean isSuccess = false;

        do {
            Console.infoMessage("NICK: ", false);
            String nick = getConsole();
            Console.infoMessage("PASSWORD: ", false);
            String password = getConsole();

            var response = apiGateway.doResister(UserDto.builder()
                    .nick(nick)
                    .password(password)
                    .build());
            if (response.getStatus() == HttpStatus.SC_CREATED) {
                isSuccess = true;
            } else {
                Console.errorMessage(response.readEntity(ErrorDto.class).getDescription(), false);
            }
        } while (!isSuccess);

        Console.successMessage("SUCCESS - please login", false);
    }

    private boolean executeLogin() {
        boolean isSuccess = false;

        do {
            Console.infoMessage("NICK: ", false);
            String nick = getConsole();
            Console.infoMessage("PASSWORD: ", false);
            String password = getConsole();

            var response = apiGateway.doLogin(UserDto.builder()
                    .nick(nick)
                    .password(password)
                    .build());
            if (response.getStatus() == HttpStatus.SC_OK) {
                token = response.readEntity(TokenDto.class).getToken();
                userName = nick;
                channel = "general";
                isSuccess = true;
            } else {
                Console.errorMessage(response.readEntity(ErrorDto.class).getDescription(), false);
            }

        } while (!isSuccess);

        Console.successMessage("SUCCESSFULLY LOGGED IN", false);
        Console.advancedPrompt(channel, userName);
        return true;
    }

    @Override
    public void processLogout() {
        Console.infoMessage("\n Bye !!! ", false);
        es.shutdown();
    }
}
