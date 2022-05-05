package cloud.cholewa.rest_client.domain;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleReader implements Callable<String> {


    @SneakyThrows
    @Override
    public String call() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String command = reader.readLine();
            if (command != null) {
                return command;
            }
        }
    }
}
