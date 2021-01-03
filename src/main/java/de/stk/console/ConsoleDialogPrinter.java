package de.stk.console;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import static de.stk.Main.getShop;
import static de.stk.console.ColorUtil.colorize;
import static de.stk.console.ColorUtil.strikeThrough;
import static de.stk.console.ConsoleDialogPrinter.WindowState.SHOP;
import static de.stk.console.DataFormattingUtils.*;

/**
 * Holds the entire state of a users shopping process.
 * Prints output based on the users input and modifies the {@link de.stk.shop.Shop}'s data accordingly.
 */
public class ConsoleDialogPrinter {

    private final Scanner consoleScanner = new Scanner(System.in);

    public ConsoleDialogPrinter() {
        initWindow(SHOP, null);
    }

    /**
     * This method is called at the end of each windows print method. It's the central logic to switch WindowsStates
     * while maintaining the information that the user entered in the last window.
     *
     * @param nextWindow A {@link WindowState} that determines which window will be printed next.
     * @param activity   Used to transfer information from one window to another.
     */
    public void initWindow(WindowState nextWindow, Activity activity) {
        printWindowBorder();

        System.out.println(nextWindow.getIntroductionText());
        switch (nextWindow) {
            case SHOP -> printShopWindow();
            case DETAILED_VIEW -> printDetailedViewWindow(activity);
            case BUY_PRICE_CLASS -> printBuyPriceClassWindow();
            case BUY_AMOUNT -> printBuyAmountWindow();
            case BUY_DISCOUNT -> printBuyDiscountWindow();
            case SHOPPING_CART -> printShoppingCartWindow();
            case BILL -> printBillWindow();
        }
    }

    private void printShopWindow() {
        System.out.println();
        Activity[] allActivities = getShop().getAllActivities().toArray(new Activity[0]);
        Activity selectedActivity = runShopWindowDialog(allActivities);

        initWindow(WindowState.DETAILED_VIEW, selectedActivity);
    }

    private void printDetailedViewWindow(Activity activity) {
        HashMap<String, Pair<LocalDate, LocalTime>> formattedDates = getFormattedDates(activity.getActivityDates());

        System.out.println(activity.getInformation());

        ArrayList<Pair<LocalDate,LocalTime>> dateIndex = new ArrayList<>();

        int index = 0;
        for (Entry<String, Pair<LocalDate, LocalTime>> date : formattedDates.entrySet()) {
            if (date.getValue() == null) {
                System.out.println(date.getKey());
            } else {
                System.out.println( date.getKey() + " [" + index + "]");
                index++;
            }
        }

        printInputBorder();
        System.out.println(WindowState.DETAILED_VIEW.getInstructionText());
        printInputBorder();

        int input;
        while (true) {
            try {
                input = consoleScanner.nextInt();
                
            } catch (InputMismatchException exception) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Preisklasse.", ColorUtil.Color.RED));
            }
            break;
        }
    }

    private void printBuyPriceClassWindow() {
    }

    private void printBuyAmountWindow() {
    }

    private void printBuyDiscountWindow() {
    }

    private void printShoppingCartWindow() {
    }

    private void printBillWindow() {
    }


    public Activity runShopWindowDialog(Activity... activities) {
        //Assign a unique name to each Activity so we can ask the user to choose
        HashMap<String, Activity> activityIndex = new HashMap<>();
        for (Activity value : activities) {
            activityIndex.put(value.getUniqueName(), value);
        }

        for (Activity activity : activities) {
            System.out.println(activity.getSummary() + "[" + activity.getUniqueName() + "]");
            System.out.println();
        }

        printInputBorder();
        System.out.println(WindowState.SHOP.getInstructionText());
        printInputBorder();

        String input;
        while (true) {
            input = consoleScanner.nextLine();
            if (activityIndex.get(input) == null) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Veranstaltung.", ColorUtil.Color.RED));
                continue;
            }
            break;
        }
        return activityIndex.get(input);
    }
    //HashMap<String, Integer> textOptions
    //Verfügbare Preisklassen: [60€] [40€] [30€]
    //2021-01-01 10:00 Übrige Tickets: 20 [1]
    //2021-01-01 13:00 Übrige Tickets: 20 [2]
    //2021-01-01 16:00 Übrige Tickets: 20 [3]

    private String buildOptions(String[] options, String... strikeThroughOptions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.length; i++) {
            sb.append("[").append(i).append("] ").append(options[i]).append("  ");
        }

        for (String currentOption : strikeThroughOptions) {
            sb.append(strikeThrough(currentOption)).append("  ");
        }
        sb.append("\n");
        return sb.toString();
    }

    private void printWindowBorder() {
        int i = 0;
        while (i < 15) {
            System.out.println();
            i++;
        }
    }

    private void printInputBorder() {
        System.out.println(colorize("-------------------------------------------------------------------------------------", ColorUtil.Color.GREEN));
    }

    enum WindowState {

        SHOP("Willkommen im Ticket Shop der Stadt Köln!\nDies ist unsere Auswahl an Veranstaltungen:", "Geben Sie bitte den Titel der Veranstaltung ein für die Sie sich interessieren:"),
        DETAILED_VIEW("Detailansicht des ausgewählten Objekts:\n", "Geben Sie bitte die Nummer der Veranstaltung ein für die Sie sich interessieren.\n[Übersicht] bringt Sie zurück in den Shop:"),
        BUY_PRICE_CLASS("Eine höhere Preisklasse entspricht für gewöhnlich einem besserem Sitzplatz.", "Geben Sie bitte die gewünschte Preisklasse an. "),
        BUY_AMOUNT("Verfügbare Tickets:", "Geben Sie bitte die gewünschte Menge an Karten an."),
        BUY_DISCOUNT("Verfügbare Rabatte:\n", "Geben Sie bitte den für Rabatt an für den Sie qualifiziert sind."),
        SHOPPING_CART("Diese Produkte befinden sich in ihrem Warenkorb:\n", "[Kaufen] zum Kaufen aller Inhalte des Warenkorbs.\n[Übersicht] Wenn Sie noch weitere Gegenstände in den Warenkorb legen wollen.\n[Abbruch] zum leeren des Warenkorbs."),
        BILL("Vielen Dank für ihren Einkauf! \nIhre Rechnung:", "Wählen Sie [Übersicht] um sich alle Angebote anzeigen zu lassen.");

        /**
         * A short informative text about the current Windows that is displayed to the user.
         */
        private final String introductionText;
        /**
         * A short instruction about what the users needs to input to continue their shopping process.
         */
        private final String instructionText;

        WindowState(String introductionText, String instructionText) {
            this.introductionText = introductionText;
            this.instructionText = instructionText;
        }


        public String getIntroductionText() {
            return introductionText;
        }

        public String getInstructionText() {
            return instructionText;
        }
    }
}
