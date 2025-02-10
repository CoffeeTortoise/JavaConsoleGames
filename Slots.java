import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;


public class Slots implements Game {

    private final String[] syms = {"🐍", "🦎", "🐊", "🐢", "🐉", "🐸", "🐌"};

    private final HashMap<String, Double> winTable = new HashMap<>();

    private final Scanner scanner = new Scanner(System.in);

    private final Random random = new Random();

    private String[][] table;

    private double balance, bet;

    public void game() {

        boolean running = true;

        this.showFormatted("Welcome to SLOTS!");
        this.showCombinations();
        this.getBalance();

        while (running) {
            this.getBet();
            this.balance -= this.bet;
            String combination = this.getCombination();
            this.showFormatted(combination);

            if (this.winTable.containsKey(combination)) {
                double k = this.winTable.get(combination);
                double gain = Math.round(this.bet * k * 100) / 100.;
                this.balance += gain;
                System.out.printf("Congratulations! You won %.3f points!\n", gain);
            }

            System.out.printf("Your current balance: %.3f\n", this.balance);

            System.out.print("Enter q if you want to quit: ");
            String sym = scanner.next().toLowerCase();

            if (sym.equals("q")) {
                System.out.println("Goodbye! We will be waiting for you again!");
                running = false;
            } else if (this.balance == 0) {
                System.out.println("Sorry, you balance is 0!");
                System.out.println("Goodbye! We will be waiting for you again!");
                running = false;
            }
            else {
                this.showWinTable();
            }

        }

        this.scanner.nextLine();
    }

    private void getBet() {

        boolean run = true;

        while (run) {
            System.out.println("Please enter your bet.");
            double bet = this.getNum();

            if (bet > this.balance) {
                System.out.println("The bet cannot exceed the balance!");
            }
            else {
                this.bet = bet;
                run = false;
            }

        }
    }

    private void getBalance() {
        System.out.println("Enter your balance, please.");
        this.balance = this.getNum();
    }

    private double getNum() {

        boolean run = true;
        double n = 1;

        while (run) {

            try {
                System.out.print("Enter the sum: ");
                n = this.scanner.nextDouble();

                if (n <= 0) {
                    System.out.println("Number cannot be less or equal to 0!");
                }
                else run = false;

            } catch (InputMismatchException e) {
                System.out.println("Invalid import!");
                this.scanner.nextLine();
            }

        }

        return n;
    }

    private void showWinTable() {
        for (String[] pair : this.table) {
            System.out.printf("Combination: %s. Gain: %s\n", pair[0], pair[1]);
        }
    }

    private void showCombinations() {

        double n = 10;

        if (!this.winTable.isEmpty()) {
            this.winTable.clear();
        }

        this.table = new String[this.syms.length + 1][2];
        int i = 0;

        for (String sym : this.syms) {
            StringBuilder builder = new StringBuilder(sym);
            String endl = ":" + n;
            builder.repeat(sym, 2);
            this.winTable.put(builder.toString(), n);
            this.table[i][0] = builder.toString();
            this.table[i][1] = Double.toString(n);
            i++;
            builder.append(endl);
            n = Math.round((n + 1.5) * 10) / 10.;
            this.showFormatted(builder.toString());
        }

        this.showFormatted("🐍🐸🐌: x100");
        this.winTable.put("🐍🐸🐌", 100.);
        this.table[this.table.length - 1][0] = "🐍🐸🐌";
        this.table[this.table.length - 1][1] = Double.toString(100);

    }

    private String getCombination() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            builder.append(this.getSymbol());
        }

        return builder.toString();
    }

    private String getSymbol() {
        int n = this.random.nextInt(this.syms.length);
        return this.syms[n];
    }

    private void showFormatted(String content) {
        System.out.println("***********************************************");
        System.out.println(content);
        System.out.println("***********************************************");
    }

    public String toString() {
        return "Slots";
    }
}