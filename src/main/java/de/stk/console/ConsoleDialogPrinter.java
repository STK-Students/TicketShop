package de.stk.console;

import de.stk.TicketShop;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;
import de.stk.data.ActivityPricing.PricingType;
import de.stk.shop.ShoppingCart;
import de.stk.shop.ShoppingCartItem;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import static de.stk.TicketShop.*;
import static de.stk.console.ColorUtil.Color;
import static de.stk.console.ColorUtil.colorize;
import static de.stk.console.ConsoleDialogPrinter.WindowState.*;
import static de.stk.console.ConsoleUtils.printEmptyLine;
import static de.stk.console.DataFormattingUtils.*;

/**
 * Holds the entire state of a users shopping process.
 * Prints output based on the users input and modifies the {@link de.stk.shop.Shop}'s data accordingly.
 */
public class ConsoleDialogPrinter {

    /**
     * The item that is currently constructed in a shopping process. Can be null.
     */
    private ShoppingCartItem currentItem;

    final Scanner consoleScanner = new Scanner(System.in);

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
        printEmptyLine();
        Activity[] allActivities = getShop().getAllActivities().toArray(new Activity[0]);
        Activity currentActivity = runShopWindowDialog(allActivities);
        if (currentActivity == null) {
            initWindow(SHOPPING_CART);
        } else {
            currentItem = new ShoppingCartItem(currentActivity);
            initWindow(DETAILED_VIEW);
        }
    }

    private void printDetailedViewWindow() {
        Activity activity = currentItem.getActivity();

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
        ArrayList<String> formattedPricing = getFormattedPricing(pricing);


        StringBuilder result = new StringBuilder();
        for (int i = 0; i < formattedPricing.size(); i++) {
            result.append(formattedPricing.get(i)).append(" ").append("[").append(i).append("]").append(" ");
        }

        System.out.println(result.toString());

        printInstruction(BUY_PRICE_CLASS);

        int input;
        while (true) {
            try {
                String rawInput = getUserInput();
                input = Integer.parseInt(rawInput);
            } catch (NumberFormatException exception) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Preisklasse.", Color.RED));
                continue;
            }
            if (formattedPricing.size() <= input || input < 0) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Preisklasse.", Color.RED));
                continue;
            }
            break;
        }

        currentItem.setPriceClass(input);

        initWindow(BUY_AMOUNT);
    }

    private void printBuyAmountWindow() {
        Activity activity = currentItem.getActivity();
        ActivityDates.DailySchedule dailySchedule = activity.getActivityDates().getTimeSlot(currentItem.getDay());

        int availableTickets = dailySchedule.getAvailableTickets(currentItem.getTime());
        System.out.println("Es sind noch " + availableTickets + " Tickets verfügbar.");

        printInstruction(BUY_AMOUNT);

        int input;
        while (true) {
            try {
                String rawInput = getUserInput();
                input = Integer.parseInt(rawInput);
                if (input < 1 || input > availableTickets) {
                    System.out.println(colorize("Ungültige Antwort! Sie können nicht mehr Tickets als überhaupt verfügbar kaufen.", Color.RED));
                    continue;
                }
                break;
            } catch (NumberFormatException | InputMismatchException exception) {
                System.out.println(colorize("Ungültige Antwort! Bitte geben Sie eine Zahl ein.", Color.RED));
            }
        }

        currentItem.setBoughtTickets(input);

        initWindow(BUY_DISCOUNT);
    }

    private void printBuyDiscountWindow() {
        ActivityPricing activityPricing = currentItem.getActivity().getPricing();
        ArrayList<String> prices = calcAllPrices(activityPricing, currentItem.getBoughtTickets(), currentItem.getPriceClass());

        for (int i = 0; i < prices.size(); i++) {
            System.out.println(prices.get(i) + " [" + i + "]");
        }

        printInstruction(BUY_DISCOUNT);

        int input;
        while (true) {
            try {
                String rawInput = getUserInput();
                input = Integer.parseInt(rawInput);
                if (input < 0 || input > (prices.size() - 1)) {
                    System.out.println(colorize("Ungültige Antwort! Sie können nicht mehr Tickets als überhaupt verfügbar kaufen.", Color.RED));
                    continue;
                }
                break;
            } catch (NumberFormatException | InputMismatchException exception) {
                System.out.println(colorize("Ungültige Antwort! Bitte geben Sie eine Zahl ein.", Color.RED));
            }
        }
        for (int i = 0; i <= PricingType.values().length; i++) {
            if (input == i) {
                PricingType[] allPricingArray = PricingType.values();
                currentItem.setPricingType(allPricingArray[i]);
                break;
            }
        }
        initWindow(SHOPPING_CART);
    }

    private void printShoppingCartWindow() {
        ShoppingCart shoppingCart = TicketShop.getShop().getShoppingCart();
        if (currentItem != null && currentItem.isComplete()) {
            boolean success = shoppingCart.addItem(currentItem);
            currentItem = null;
            if (!success) {
                System.out.println(colorize("Sie dürfen maximal drei verschiedene Rechnungsposten auf einmal im Warenkorb haben.", Color.RED));
                System.out.println(colorize("Es wurde kein weiterer Posten dazugelegt.", Color.RED));
            }
        }

        shoppingCart.printSummary();

        printInstruction(SHOPPING_CART);

        String input;
        while (true) {
            input = getUserInput();
            if (input.equalsIgnoreCase("Übersicht") || input.equalsIgnoreCase("Kaufen") ||
                    input.equalsIgnoreCase("Abbruch")) {
                break;
            }
            System.out.println(colorize("Keine gültige Eingabe.", Color.RED));
        }

        if (input.equalsIgnoreCase("Übersicht")) {
            initWindow(SHOP);
        } else if (input.equalsIgnoreCase("Kaufen")) {
            initWindow(BILL);
        } else {
            //Abbruch
            shoppingCart.clear();
            initWindow(SHOP);
        }
    }


    private void printBillWindow() {
        ShoppingCart shoppingCart = TicketShop.getShop().getShoppingCart();
        shoppingCart.printSummary();
    }

    private Activity runShopWindowDialog(Activity... activities) {
        //Assign a unique name to each Activity so we can ask the user to choose
        HashMap<String, Activity> activityIndex = new HashMap<>();
        for (Activity activity : activities) {
            String identifier = activity.getUniqueName().toLowerCase(Locale.ROOT);
            activityIndex.put(identifier, activity);
        }

        for (Activity activity : activities) {
            System.out.println(activity.getSummary() + colorize("[" + activity.getUniqueName() + "]", Color.PURPLE,Color.UNDERLINE));
            printEmptyLine();
        }

        printInstruction(SHOP);

        String input;
        while (true) {
            input = getUserInput();
            if (input.equalsIgnoreCase("Warenkorb")) {
                if (TicketShop.getShop().getShoppingCart().holdsItems()) {
                    return null;
                }
            }
            if (activityIndex.get(input) == null) {
                System.out.println(colorize("Ungültige Antwort! Es gibt keine solche Veranstaltung.", Color.RED));
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

        if (formattedDates.size() == 0 ) {
            System.out.println(colorize("Ausverkauft!", Color.BOLD, Color.RED));
        }

        printInstruction(DETAILED_VIEW);

        //Get input
        String rawInput = "";
        int intInput;
        while (true) {
            try {
                rawInput = getUserInput();
                intInput = Integer.parseInt(rawInput);
            } catch (NumberFormatException exception) {
                if (rawInput.equalsIgnoreCase("Übersicht")) {
                    return null;
                } else {
                    System.out.println(colorize("Ungültige Antwort! Bitte geben Sie eine der Zahlen aus den eckigen Klammern ein.", Color.RED));
                    continue;
                }
            }
            if ((intInput >= 0 && intInput < dateIndex.size()) && dateIndex.get(intInput) != null) {
                break;
            } else {
                System.out.println(colorize("Ungültige Antwort! Bitte geben Sie eine der Zahlen aus den eckigen Klammern ein.", Color.RED));
            }
        }
        return dateIndex.get(intInput);
    }

    private void printWindowBorder() {
        int i = 0;
        while (i < 15) {
            printEmptyLine();
            i++;
        }
    }

    private void printInstruction(WindowState state) {
        String inputBorder = colorize("-------------------------------------------------------------------------------------", Color.GREEN);
        System.out.println(inputBorder + "\n" + state.getInstructionText() + "\n" + inputBorder);
    }

    private String getUserInput() {
        String input = consoleScanner.nextLine();
        return input.toLowerCase(Locale.ROOT);
    }

    enum WindowState {

        SHOP(colorize("Willkommen im Ticket Shop der Stadt Köln!\nDies ist unsere Auswahl an Veranstaltungen:", Color.CYAN), "Geben Sie bitte den Titel der Veranstaltung ein für die Sie sich interessieren:"),
        DETAILED_VIEW("Detailansicht des ausgewählten Objekts:\n", "Geben Sie bitte die Nummer der Veranstaltung ein für die Sie sich interessieren.\n[Übersicht] bringt Sie zurück in den Shop:"),
        BUY_PRICE_CLASS("Eine höhere Preisklasse entspricht für gewöhnlich einem besserem Sitzplatz.\nAlle Preise ohne Mehrwertsteuer!\n\nVerfügbare Preisklassen:", "Geben Sie bitte die gewünschte Preisklasse an. "),
        BUY_AMOUNT("Verfügbare Tickets:", "Geben Sie bitte die gewünschte Menge an Karten an."),
        BUY_DISCOUNT("Verfügbare Rabatte:\n", "Geben Sie bitte den für Rabatt an für den Sie qualifiziert sind."),
        SHOPPING_CART("Diese Produkte befinden sich in ihrem Warenkorb:\n", "[Kaufen] zum Kaufen aller Inhalte des Warenkorbs.\n[Übersicht] Wenn Sie noch weitere Gegenstände in den Warenkorb legen wollen.\n[Abbruch] zum leeren des Warenkorbs."),
        BILL(colorize("Vielen Dank für ihren Einkauf! \nIhre Rechnung:", Color.GREEN), "Wählen Sie [Übersicht] um sich alle Angebote anzeigen zu lassen.");

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
