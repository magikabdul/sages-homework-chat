package cloud.cholewa.client.ui;

import cloud.cholewa.client.services.User;

public class Console {

    public final static String PROMPT = " #> ";

    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

    public static void writeSuccessMessage(boolean inNewLine, String body, boolean withPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(inNewLine ? "\n" : "");
        sb.append(ConsoleColor.CYAN_BACKGROUND)
                .append(body)
                .append(ConsoleColor.RESET);
        sb.append(withPrompt ? PROMPT : "");
        System.out.print(sb);
    }

    public static void writeInfoMessage(boolean inNewLine, String body, boolean withPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(inNewLine ? "\n" : "");
        sb.append(ConsoleColor.GREEN_BACKGROUND)
                .append(body)
                .append(ConsoleColor.RESET);
        sb.append(withPrompt ? PROMPT : "");
        System.out.print(sb);
    }

    public static void writeWarningMessage(boolean inNewLine, String body, boolean withPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(inNewLine ? "\n" : "");
        sb.append(ConsoleColor.YELLOW_BACKGROUND)
                .append(body)
                .append(ConsoleColor.RESET);
        sb.append(withPrompt ? PROMPT : "");
        System.out.print(sb);
    }

    public static void writeErrorMessage(boolean inNewLine, String body, boolean withPrompt) {
        StringBuilder sb = new StringBuilder();
        sb.append(inNewLine ? "\n" : "");
        sb.append(ConsoleColor.RED_BOLD)
                .append(body)
                .append(ConsoleColor.RESET);
        sb.append(withPrompt ? PROMPT : "");
        System.out.print(sb);
    }

    public static void writePromptMessage(boolean inNewLine, User user) {
        StringBuilder sb = new StringBuilder();
        sb.append(inNewLine ? "\n" : "");
        sb.append(ConsoleColor.YELLOW_BACKGROUND);
        sb.append(user.getChannel().length() > 0 ? user.getChannel() + " / " : "");
        sb.append(user.getName().length() > 0 ? user.getName() : "");
        sb.append(ConsoleColor.RESET);
        sb.append(PROMPT);
        System.out.print(sb);
    }

    public static void writeServerBroadcast(String user, String chanel, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append(ConsoleColor.MAGENTA_BACKGROUND)
                .append(chanel.toUpperCase())
                .append(chanel.length() > 0 ? " / " : "")
                .append(ConsoleColor.GREEN_BACKGROUND)
                .append(user)
                .append(ConsoleColor.RESET)
                .append(" - ")
                .append(body)
                .append("\n");
        System.out.println(sb);
    }

    public static void writeHistoryHeader(String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append(ConsoleColor.RED_BACKGROUND)
                .append(body)
                .append(ConsoleColor.RESET)
                .append("\n");
        System.out.println(sb);
    }

    public static void writeHistoryPosition(String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.WHITE_BOLD)
                .append(body)
                .append(ConsoleColor.RESET);
        System.out.println(sb);
    }

    public static void writeHistoryFooter(String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append(ConsoleColor.GREEN_BACKGROUND)
                .append(body)
                .append(ConsoleColor.RESET)
                .append("\n");
        System.out.println(sb);
    }
}
