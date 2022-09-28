import java.util.Scanner;
import java.util.Random;

public class paymentMethod {

    public static boolean IfEnough(double amountTemp, double cashSumTemp) {
        double cashSum = cashSumTemp;
        double amount = amountTemp;
        if (cashSum > amount) {
            System.out.println("Platnosc zaakceptowana. Drukowanie paragonu. Reszta wynosi: " + Math.floor((cashSum-amount)*100)/100);
            return true;
        }
        else{
            System.out.println("Za malo gotowki. \nDoloz banknoty \nalbo wpisz X by zakonczyc transakcje\nalbo wpisz Z by zmienic sposob platnosci");
            Scanner scn = new Scanner(System.in);
            String decision = scn.next();
            switch (decision){
                case "X":
                    System.out.println("Transakcja zakonczona.");
                    scn.close();
                    break;
                case "Z":
                    Payment(amount, 0);
                    break;
                default:
                    String splitCash2[] = decision.split(";");
                    for (int i = 0; i < splitCash2.length; i++) {
                        cashSum += Integer.parseInt(splitCash2[i]);
                    }
                    System.out.println("Suma: " + cashSum);
                    IfEnough(amount, cashSum);
                    break;
            }
            scn.close();
        }
        return true;
    }

    public static void Payment(double amountTemp, double cashSumTemp) {
        double amount = amountTemp;
        double cashSum = cashSumTemp;
        System.out.println("Do zaplaty: " + amount + "\n" + "Wybierz sposob platnosci:\n1. Gotowka\n2. Karta\n3. Blik");
        Scanner scn = new Scanner(System.in);
        String paymentMethod = scn.next();
        
        switch (paymentMethod) {
            case "1":
                System.out.println("Podaj banknoty po srednikach: ");
                String cash = scn.next();
                String splitCash[] = cash.split(";");
                for (int i = 0; i < splitCash.length; i++) {
                    cashSum += Integer.parseInt(splitCash[i]);
                }
                System.out.println("Suma: " + cashSum);
                boolean isIfEnough = false;
                do {
                    isIfEnough = IfEnough(amount, cashSum);
                } while (isIfEnough != true);
                break;
            default:
                System.out.println("Nieprawidlowa wartosc. Wybierz ponownie.");
                Payment(amount, 0);
                break;
        }
        scn.close();
    }
    
    public static void main(String[] args) {
        Random rnd = new Random();
	    double amount = Math.floor(rnd.nextDouble() * 10000)/100;
        Payment(amount, 0);
    }
}
