package de.stk.console;

import lombok.Getter;

public class ColorUtil {

    public static String colorize(String input, Color... colors) {
        StringBuilder result = new StringBuilder();
        for (Color color : colors) {
            result.append(color.getColorCode());
        }
        return result.toString() + input + Color.RESET.getColorCode();
    }

    public enum Color {
        //This
        RESET("\033[0m"),

        //Normal Colours
        RED("\033[0;31m"),
        GREEN("\\u001B[32m"),
        YELLOW("\033[0;33m"),
        BLUE("\033[0;34m"),
        PURPLE("\033[0;35m"),
        CYAN("\033[0;36m"),
        WHITE("\033[0;37m"),

        //Light Colours
        Light_RED("\033[91m"),
        Light_GREEN("\033[92m"),
        Light_YELLOW("\033[93m"),
        Light_BLUE("\033[94m"),
        Light_PURPLE("\033[95m"),
        Light_CYAN("\033[96m"),

        //Bold
        BOLD("\033[1m"),

        //Underline and no Underline
        UNDERLINE("\033[4m"),
        NO_UNDERLINE("\033[24m"),

        //Default
        DEFAULT("\033[0m");



        @Getter
        private final String colorCode;

        Color(String colorCode) {
            this.colorCode = colorCode;
        }
    }
}
