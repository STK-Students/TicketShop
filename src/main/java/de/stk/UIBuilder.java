package de.stk;

import java.util.Arrays;

public class UIBuilder {

    public static final char STRIKETHROUGH_CHAR = '\u0336';

    public void buildUI(String inputText, String[] options, String... strikeThroughOptions) {
        //Print information
        System.out.println(inputText);

        //build Option Menu
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.length; i++) {
            sb.append("[").append(i).append("] ").append(options[i]).append("  ");
        }
        System.out.print(sb.toString());

        StringBuilder strikeThroughOptionsBuilder = new StringBuilder();
        for (String s : strikeThroughOptions) {
            strikeThroughOptionsBuilder.append(strikeThrough(s)).append("  ");
        }
        strikeThroughOptionsBuilder.append("\n");
        System.out.print(strikeThroughOptionsBuilder.toString());
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
}
