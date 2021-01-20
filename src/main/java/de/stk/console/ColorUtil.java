package de.stk.console;

import lombok.Getter;

public class ColorUtil {

    private static final char STRIKETHROUGH_CHAR = '\u0336';

    public static String colorize(String input, Color color) {
        StringBuilder colours = new StringBuilder();

        colours.append(color.getColorCode());
        colours.append(input);
        colours.append(Color.RESET.getColorCode());

        return colours.toString();
    }

    public static String strikeThrough(String input) {
        StringBuilder output = new StringBuilder(String.valueOf(input.charAt(0)));
        for (int i = 1; i < input.length() + 1; i++) {
            output.append(STRIKETHROUGH_CHAR);
            if (i != input.length()) {
                output.append(input.charAt(i));
            }
        }
        return output.toString();
    }

    public enum Color {
        //This
        RESET("\033[0m"),

        //Normal Colours
        RED("\033[0;31m"),
        GREEN("\033[0;32m"),
        YELLOW("\033[0;33m"),
        BLUE("\033[0;34m"),
        PURPLE("\033[0;35m"),
        CYAN("\033[0;36m"),
        WHITE("\033[0;37m"),

        //Bold Colours
        BOLD_RED("\033[1;31m"),
        BOLD_GREEN("\033[1;32m"),
        BOLD_YELLOW("\033[1;33m"),
        BOLD_BLUE("\033[1;34m"),
        BOLD_PURPLE("\033[1;35m"),
        BOLD_CYAN("\033[1;36m"),
        BOLD_WHITE("\033[1;37m"),

        //Underlined Colours
        UNDERLINE_RED("\033[4;31m"),
        UNDERLINE_GREEN("\033[4;32m"),
        UNDERLINE_YELLOW("\033[4;33m"),
        UNDERLINE_BLUE("\033[4;34m"),
        UNDERLINE_PURPLE("\033[4;35m"),
        UNDERLINE_CYAN("\033[4;36m"),
        UNDERLINE_WHITE("\033[4;37m"),

        //Background
        BACKGROUND_RED("\033[41m"),
        BACKGROUND_GREEN("\033[42m"),
        BACKGROUND_YELLOW("\033[43m"),
        BACKGROUND_BLUE("\033[44m"),
        BACKGROUND_PURPLE("\033[45m"),
        BACKGROUND_CYAN("\033[46m"),
        BACKGROUND_WHITE("\033[47m"),

        //High Intensity
        HIGH_INTENSE_RED("\033[0;91m"),
        HIGH_INTENSE_GREEN("\033[0;92m"),
        HIGH_INTENSE_YELLOW("\033[0;93m"),
        HIGH_INTENSE_BLUE("\033[0;94m"),
        HIGH_INTENSE_PURPLE("\033[0;95m"),
        HIGH_INTENSE_CYAN("\033[0;96m"),
        HIGH_INTENSE_WHITE("\033[0;97m"),

        //Bold High Intensity
        BOLD_HIGH_INTENSE_RED("\033[1;91m"),
        BOLD_HIGH_INTENSE_GREEN("\033[1;92m"),
        BOLD_HIGH_INTENSE_YELLOW("\033[1;93m"),
        BOLD_HIGH_INTENSE_BLUE("\033[1;94m"),
        BOLD_HIGH_INTENSE_PURPLE("\033[1;95m"),
        BOLD_HIGH_INTENSE_CYAN("\033[1;96m"),
        BOLD_HIGH_INTENSE_WHITE("\033[1;97m"),

        //High Intensity Backgrounds
        INTENSE_BACKGROUND_RED("\033[0;101m"),
        INTENSE_BACKGROUND_GREEN("\033[0;102m"),
        INTENSE_BACKGROUND_YELLOW("\033[0;103m"),
        INTENSE_BACKGROUND_BLUE("\033[0;104"),
        INTENSE_BACKGROUND_PURPLE("\033[0;105m"),
        INTENSE_BACKGROUND_CYAN("\033[0;106m"),
        INTENSE_BACKGROUND_WHITE("\033[0;107m");

        @Getter
        private final String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }
    }
}
