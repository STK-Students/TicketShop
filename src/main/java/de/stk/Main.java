package de.stk;

import javax.swing.*;
import java.io.IOException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * A wrapper class that open a new terminal session with the actual TicketShop.
 */
public class Main {

    /**
     * Enables color code processing using the Windows registry and then starts the actual programm in a new command line.
     *
     * @param args any arguments that might be passed using the command line are ignored.
     */
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("reg add HKEY_CURRENT_USER\\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f");
            runtime.exec("cmd /c start cmd.exe /K \"chcp 1252 && java -cp STK-TicketShop.jar de.stk.TicketShop\"");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Die Windows Command Line konnte nicht gestartet werden.\n" +
                    "Bitte starten Sie das Programm als Administrator.", "Fehler!", ERROR_MESSAGE);
        }
    }
}
