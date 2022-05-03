package cloud.cholewa.rest_client.domain.ui;

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

                        """)
                .append(ConsoleColor.WHITE_BOLD)
                .append("""
                        Select below option
                        1. Login
                        2. Register
                        3. Exit
                        """);
        System.out.print(sb);
        showPrompt();
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
}
