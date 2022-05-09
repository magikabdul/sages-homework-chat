package cloud.cholewa.rest_client.domain.ui;

import cloud.cholewa.rest_client.domain.dto.MessageHistoryDto;

import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("StringBufferReplaceableByString")
public class Console {

    public final static String PROMPT = " #> ";

    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

    public static void showWelcomeMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.YELLOW_BOLD)
                .append("""
                         ,-----. ,--.                 ,--.
                        '  .--./ |  ,---.   ,--,--. ,-'  '-.
                        |  |     |  .-.  | ' ,-.  | '-.  .-'
                        '  '--'\\ |  | |  | \\ '-'  |   |  |
                         `-----' `--' `--'  `--`--'   `--'

                        """);
        System.out.println(sb);
        showMenu("Select below option", List.of("Login", "Register", "Exit"));
    }

    public static void infoMessage(String message, boolean showPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.CYAN_BACKGROUND)
                .append(message)
                .append(ConsoleColor.RESET)
                .append(" ");
        System.out.print(sb);

        if (showPrompt) {
            showPrompt();
        }
    }

    public static void errorMessage(String message, boolean showPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.RED_BACKGROUND)
                .append(message)
                .append("\n")
                .append(ConsoleColor.RESET);
        System.out.print(sb);

        if (showPrompt) {
            showPrompt();
        }
    }

    public static void successMessage(String message, boolean showPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.GREEN_BACKGROUND)
                .append(message)
                .append("\n")
                .append(ConsoleColor.RESET);
        System.out.print(sb);

        if (showPrompt) {
            showPrompt();
        }
    }

    public static void advancedPrompt(String channel, String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.BLACK_BOLD)
                .append(ConsoleColor.YELLOW_BACKGROUND)
                .append(" ")
                .append(channel.toUpperCase())
                .append(" / ")
                .append(userName)
                .append(" #>")
                .append(ConsoleColor.RESET)
                .append(" ");
        System.out.print(sb);
    }

    private static void showPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.WHITE_BOLD)
                .append(ConsoleColor.GREEN_BACKGROUND)
                .append(PROMPT)
                .append(ConsoleColor.RESET)
                .append("\s");
        System.out.print(sb);
    }

    public static void showHistoryStart() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.GREEN_BACKGROUND)
                .append("------------------------- BEGIN -------------------------")
                .append(ConsoleColor.RESET)
                .append("\n");
        System.out.print(sb);
    }

    public static void showHistoryPosition(List<MessageHistoryDto> list) {
        list.forEach(h -> {
            StringBuilder sb = new StringBuilder();
            sb.append(h.getCreatedAtDate()).append(" ").append(h.getCreatedAtTime()).append(" ")
                    .append("[ ").append(h.getNick()).append(" ]")
                    .append(" - ").append(h.getBody())
                    .append("\n");
            System.out.print(sb);
        });
    }

    public static void showHistoryEnd() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.RED_BACKGROUND)
                .append("-------------------------- END --------------------------")
                .append(ConsoleColor.RESET)
                .append("\n");
        System.out.print(sb);
    }

    public static void chatMessage(MessageHistoryDto messageHistoryDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append(ConsoleColor.MAGENTA_BACKGROUND)
                .append("[ ")
                .append(messageHistoryDto.getNick())
                .append(" ]")
                .append(" - ")
                .append(messageHistoryDto.getBody())
                .append(ConsoleColor.RESET)
                .append("\n");
        System.out.print(sb);
    }

    public static void showMenu(String menuTitle, List<String> menuPositions) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.WHITE_BOLD)
                .append(menuTitle)
                .append("\n");

        ListIterator<String> iterator = menuPositions.listIterator();

        while (iterator.hasNext()) {
            sb.append(iterator.nextIndex() + 1).append(". ").append(iterator.next()).append("\n");
        }

        sb.append(ConsoleColor.RESET);
        System.out.println(sb);
        showPrompt();
    }
}
