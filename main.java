import java.util.Scanner;
import java.util.Random;

public class main {
    static Scanner scn = new Scanner(System.in);
    static double amount = new Random().nextInt(100000)/100.0-;
    public static void main(String[] args) {
        pay();
    }

    private static void pay() {
        int paymentMethod = getPaymentMethodFromKeyboard();
        switch (paymentMethod) {
            case 1: 
                if (payCash()) {
                    printReceipt();
                } else {
                    pay();
                }
                break;

            case 2:
                payBlik();
                break;

            case 3:
                payCard();
                break;

            default: 
                pay();
                break;
        }
    }

    private static int getPaymentMethodFromKeyboard() {
        int paymentMethodValue = 0;
        System.out.println("Do zaplaty: " + amount);
        System.out.println("Wybierz sposob platnosci: ");
        System.out.println("1: Gotowka\n2. BLIK\n3. Karta");

        String paymentMethod = scn.nextLine();
        try{
            paymentMethodValue = Integer.parseInt(paymentMethod);
        } catch (NumberFormatException e) {
            paymentMethodValue = getPaymentMethodFromKeyboard();
        }
        return paymentMethodValue;
    }

    private static boolean payCash() {
        while (amount > 0) {
            int banknotesAmount = getBanknotesAmount();
            if (banknotesAmount == 0) {
                return false;
            } else {
                amount -= banknotesAmount;
            }
        }
        return true;
    }

    private static int getBanknotesAmount() {
        int banknotesAmount = 0;
        System.out.println("Do zaplaty: " + amount);
        System.out.println("Podaj banknoty:");
        String decision = scn.nextLine();
        if (decision.equals("X")) {
            System.exit(0);
        } else if (decision.equals("Z")) {
            return 0;
        } else {
            String[] banknotesArray = decision.split(";");
            for (String banknote : banknotesArray) {
                banknotesAmount += Integer.parseInt(banknote);
            } 
        }
        return banknotesAmount;
    }

    private static boolean payBlik() {
        return true;
    }
    private static boolean payCard() {
        return true;
    }

    private static void printReceipt() {
        double residue = roundDouble(amount);
        System.out.println("Reszta wynosi: " + residue + "\nDrukowanie paragonu");
    }

    private static double roundDouble (double num) {
        return Math.round(num * 100) / 100.0;
    }
}
