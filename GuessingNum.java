import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;


public class GuessingNum implements Game{

    private final Scanner scanner = new Scanner(System.in);

    private final Random random = new Random();

    private int max, min;

    public void game() {

        boolean running = true;

        this.setBounds();

        int n = this.random.nextInt(this.min, this.max + 1);
        int attempts = 0;

        while (running) {
            attempts++;

            try {
                System.out.print("Guess your number: ");
                int num = this.scanner.nextInt();

                if (num == n) {
                    System.out.printf("You win! You needed %d attempts.\n", attempts);
                    running = false;
                } else if (num > n) {
                    System.out.println("Too big!");
                }
                else System.out.println("Too small!");

            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                this.scanner.nextLine();
            }

        }

        this.scanner.nextLine();
    }

    private void setBounds() {

        boolean run = true;

        while (run) {

            try {
                System.out.print("Enter maximum number: ");
                this.max = this.scanner.nextInt();

                System.out.print("Enter minimum number: ");
                this.min = this.scanner.nextInt();

                run = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                this.scanner.nextLine();
            }

        }

        int max = this.max;
        this.max = Math.max(max, this.min);
        this.min = Math.min(this.min, max);

    }

    public String toString() {
        return "GuessingNum";
    }
}