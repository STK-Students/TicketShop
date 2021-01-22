package de.stk;

import java.io.IOException;

/**
 * A wrapper class that open a new terminal session with the actual TicketShop.
 */
public class Main {

    /**
     * Enables color code processing using the Windows registry and then starts the actual programm in a new command line.
     * @param args any arguments that might be passed using the command line are ignored.
     */
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("reg add HKEY_CURRENT_USER\\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f");
            runtime.exec("cmd.exe /c start java -cp STK-TicketShop.jar de.stk.TicketShop");
        } catch (IOException e) {
            System.out.println("Die Windows Command Line konnte nicht gestartet werden." +
                    " Bitte starten Sie das Programm als Administrator.");
            e.printStackTrace();
        }
    }
}
