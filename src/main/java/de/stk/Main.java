package de.stk;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("reg add HKEY_CURRENT_USER\\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f");
            runtime.exec("cmd.exe /c start java -cp STK-TicketShop.jar de.stk.TicketShop");
        } catch (IOException e) {
            System.out.println("Die Windows Command Line konnte nicht gestartet werden.");
            e.printStackTrace();
        }
    }
}
