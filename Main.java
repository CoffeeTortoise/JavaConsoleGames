import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Game[] games = {
                new Slots(),
                new GuessingNum(),
                new TicTacToe()
        };

        boolean running = true;

        while (running) {
            System.out.println("Available games:");

            for (int i = 0; i < games.length; i++) {
                System.out.printf("Index: %d. Game: %s\n", i, games[i]);
            }

            try {
                System.out.print("Choose the game by index or enter -1 to quit: ");
                int j = scanner.nextInt();

                if (j == -1) {
                    running = false;
                } else if ((j < 0) || (j >= games.length)) {
                    System.out.println("Invalid index!");
                }
                else {
                    scanner.nextLine();
                    games[j].game();
                }

            } catch (InputMismatchException e) {
                System.out.println("Incorrect input!");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}