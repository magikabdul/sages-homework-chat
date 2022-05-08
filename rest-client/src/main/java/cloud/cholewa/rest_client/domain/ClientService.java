package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.dto.ErrorDto;
import cloud.cholewa.rest_client.domain.dto.MessageHistoryDto;
import cloud.cholewa.rest_client.domain.dto.TokenDto;
import cloud.cholewa.rest_client.domain.dto.UserDto;
import cloud.cholewa.rest_client.domain.ui.Console;
import jakarta.ws.rs.core.GenericType;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientService implements ClientServicePort {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final ApiGateway apiGateway = new ApiGateway();

    public static final String END_SESSION = "\\q";
    public static final String GET_HISTORY = "\\h";

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
        Thread thread = new Thread(this::startMessagePulling);
        thread.setDaemon(true);
        thread.start();

        boolean done = false;

        while (!done) {
            String command = getConsole();

            switch (command) {
                case END_SESSION -> done = true;
                case GET_HISTORY -> showHistory();
                default -> Console.advancedPrompt(channel, userName);
            }
        }
    }

    @SneakyThrows
    private void startMessagePulling() {
        long lastMessageId = 0;

        while (true) {
            var response = apiGateway.getLastChannelMessage(token)
                    .readEntity(MessageHistoryDto.class);

            if (lastMessageId != response.getId()) {
                lastMessageId = response.getId();
                Console.chatMessage(response);
                Console.advancedPrompt(channel, userName);
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }

    private void showHistory() {

        var response = apiGateway.getHistory(token);

        if (response.getStatus() == HttpStatus.SC_FORBIDDEN) {
            Console.errorMessage(response.readEntity(ErrorDto.class).getDescription(), false);
        } else {
            List<MessageHistoryDto> list = response.readEntity(new GenericType<>() {
            });
            Console.showHistoryStart();
            Console.showHistoryPosition(list);
            Console.showHistoryEnd();
            Console.advancedPrompt(channel, userName);
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
