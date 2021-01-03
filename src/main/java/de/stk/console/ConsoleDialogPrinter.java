package de.stk.console;

import de.stk.data.Activity;
import de.stk.shop.ShoppingCart;
import de.stk.shop.ShoppingCartItem;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import static de.stk.Main.getShop;
import static de.stk.console.ColorUtil.colorize;
import static de.stk.console.ColorUtil.strikeThrough;
import static de.stk.console.ConsoleDialogPrinter.WindowState.BUY_PRICE_CLASS;
import static de.stk.console.ConsoleDialogPrinter.WindowState.SHOP;
import static de.stk.console.DataFormattingUtils.getFormattedDates;

/**
 * Holds the entire state of a users shopping process.
 * Prints output based on the users input and modifies the {@link de.stk.shop.Shop}'s data accordingly.
 */
public class ConsoleDialogPrinter {

    /**
     * This Shopping cart obtains a ShoppingCartItem when Shopping Process is completed.
     */
    private ShoppingCart shoppingCart;
    /**
     * The item that is currently constructed in a shopping process. Can be null.
     */
    private ShoppingCartItem currentItem;

    private final Scanner consoleScanner = new Scanner(System.in);

    public ConsoleDialogPrinter() {
        initWindow(SHOP);
    }

    /**
     * This method is called at the end of each windows print method. It's the central logic to switch WindowsStates
     * while maintaining the information that the user entered in the last window.
     *
     * @param nextWindow A {@link WindowState} that determines which window will be printed next.
     */
    public void initWindow(WindowState nextWindow) {
        printWindowBorder();

        System.out.println(nextWindow.getIntroductionText());
        switch (nextWindow) {
            case SHOP -> printShopWindow();
            case DETAILED_VIEW -> printDetailedViewWindow();
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
        Activity currentActivity = runShopWindowDialog(allActivities);
        currentItem = new ShoppingCartItem(currentActivity);

        initWindow(WindowState.DETAILED_VIEW);
    }

    private void printDetailedViewWindow() {
        Activity activity = currentItem.getActivity();
        //Print info
        System.out.println(activity.getInformation());

        Pair<LocalDate, LocalTime> pair = runDetailedViewDialog(activity);
        if (pair == null) {
            initWindow(SHOP);
        } else {
            currentItem.setDay(pair.getLeft());
            currentItem.setTime(pair.getRight());

            initWindow(BUY_PRICE_CLASS);
        }
    }

    private void printBuyPriceClassWindow() {
        ArrayList<Float> pricing = currentItem.getActivity().getPricing().getPrices();

        ArrayList<String> formattedPricing = DataFormattingUtils.getFormattedPricing(pricing);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < formattedPricing.size(); i++) {
            result.append(formattedPricing.get(i)).append(" ").append("[").append(i).append("]").append(" ");
        }

        System.out.println(result.toString());
        printInputBorder();
        System.out.println(BUY_PRICE_CLASS.getInstructionText());
        printInputBorder();

        int input;
        while (true) {
            input = consoleScanner.nextInt();
            if (formattedPricing.get(input) == null) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Preisklasse.", ColorUtil.Color.RED));
                continue;
            }
            break;
        }

        currentItem.setPriceClass(input);
    }

    private void printBuyAmountWindow() {
    }

    private void printBuyDiscountWindow() {
    }

    private void printShoppingCartWindow() {
    }

    private void printBillWindow() {
    }


    private Activity runShopWindowDialog(Activity... activities) {
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

    private Pair<LocalDate, LocalTime> runDetailedViewDialog(Activity activity) {
        //Prepare index List
        ArrayList<Pair<LocalDate, LocalTime>> dateIndex = new ArrayList<>();

        HashMap<String, Pair<LocalDate, LocalTime>> formattedDates = getFormattedDates(activity.getActivityDates());
        int index = 0;
        for (Entry<String, Pair<LocalDate, LocalTime>> date : formattedDates.entrySet()) {
            if (date.getValue() == null) {
                System.out.println(date.getKey());
            } else {
                System.out.println(date.getKey() + " [" + index + "]");
                dateIndex.add(date.getValue());
                index++;
            }
        }

        //Print misc info
        printInputBorder();
        System.out.println(WindowState.DETAILED_VIEW.getInstructionText());
        printInputBorder();

        //Get input
        String rawInput;
        int intInput;
        while (true) {
            rawInput = consoleScanner.nextLine();

            try {
                intInput = Integer.parseInt(rawInput);
            } catch (NumberFormatException exception) {
                if (rawInput.equals("Übersicht")) {
                    intInput = -1;
                    break;
                } else {
                    System.out.println(colorize("Ungültige Antwort! Es gibt dieses Datum nicht.", ColorUtil.Color.RED));
                    continue;
                }
            }

            if ((intInput < dateIndex.size()) && dateIndex.get(intInput) != null) {
                break;
            } else {
                System.out.println(colorize("Ungültige Antwort! Es gibt dieses Datum nicht.", ColorUtil.Color.RED));
            }
        }
        if (intInput == -1) {
            return null;
        }
        return dateIndex.get(intInput);
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
        BUY_PRICE_CLASS("Eine höhere Preisklasse entspricht für gewöhnlich einem besserem Sitzplatz.\nVerfügbare Preisklassen:", "Geben Sie bitte die gewünschte Preisklasse an. "),
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
