import java.util.Scanner;
import java.util.Random;


public class TicTacToe implements Game {

    private String
            player,
            computer;

    private final String
            cross = "X",
            zero = "O",
            filler = "*",
            separator = ";";

    private final String[][] field = new String[4][4];

    private final Random random = new Random();

    private final Scanner scanner = new Scanner(System.in);

    public TicTacToe() {

        for (int i = 0; i < this.field.length; i++) {

            for (int j = 0; j < this.field[i].length; j++) {

                if ((i == 0) &&
                        (j == 0)) {
                    this.field[i][j] = "R/C";
                } else if ((i > 0)
                            && (j == 0)) {
                    this.field[i][j] = Integer.toString(i);
                } else if ((i == 0)
                            && (j > 0)) {
                    this.field[i][j] = Integer.toString(j);
                }
                else {
                    this.field[i][j] = this.filler;
                }

            }
        }

    }

    public void game() {
        this.getPlayer();
        this.showField();
        boolean running = true;
        boolean comp_first = this.random.nextBoolean();

        if (comp_first) {
            System.out.println("That time the computer makes the first move!");
        }
        else {
            System.out.println("That time you make the first move!");
        }

        while (running) {

            if (comp_first) {
                this.computerPlay();
                boolean won = this.checkWin(this.computer);
                this.showField();
                comp_first = false;

                if (won) {
                    this.victoryMsg("Computer");
                    running = false;
                }

            }
            else {
                this.playerPlay();
                boolean won = this.checkWin(this.player);
                this.showField();
                comp_first = true;

                if (won) {
                    System.out.println("Congratulations!");
                    this.victoryMsg("You");
                    running = false;
                }

            }

        }

        this.scanner.nextLine();
    }

    private void victoryMsg(String winner) {
        System.out.printf("%s is the winner!\n", winner);
        System.out.println("Good game! Goodbye!");
    }

    private void getPlayer() {

        boolean playerCross = this.random.nextBoolean();

        if (playerCross) {
            this.player = this.cross;
            this.computer = this.zero;
        }
        else {
            this.player = this.zero;
            this.computer = this.cross;
        }

        System.out.printf("You are playing as %s!\n", this.player);
        System.out.printf("Computer playing as %s!\n", this.computer);

    }

    private void showField() {

        for (String[] row : this.field) {

            for (String col : row) {
                System.out.printf("| %s |", col);
            }

            System.out.println();
        }

    }

    private void playerPlay() {
        boolean run = true;
        System.out.println("Your move!");

        while (run) {
            System.out.printf("Enter the row and column via %s: ", this.separator);
            String user = this.scanner.next();

            if (user.isEmpty()) {
                System.out.println("Invalid input! Empty!");
                continue;
            }

            if (!user.contains(this.separator)) {
                System.out.printf("Invalid input! %s is missed!\n", this.separator);
                continue;
            }

            String[] xy = user.replace(" ", "")
                    .replace("\n", "")
                    .split(this.separator);

            if (xy.length != 2) {
                System.out.println("Invalid input! Enter only row and column!");
                continue;
            }

            try {
                int row = Integer.parseInt(xy[0]);
                int col = Integer.parseInt(xy[1]);

                if (!this.isIndexValid(row, col)) {
                    System.out.println("Invalid indexes!");
                    continue;
                }

                if (this.isPlaceOccupied(row, col)) {
                    System.out.println("This place is already occupied!");
                    continue;
                }

                System.out.println("You made your move!");
                System.out.printf("%s placed at row %d and col %d!\n", this.player, row, col);
                this.field[row][col] = this.player;
                run = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format!");
            }
        }
    }

    private void computerPlay() {

        boolean run = true;
        System.out.println("Computer move!");

        while (run) {
            int row = this.random.nextInt(1, this.field.length);
            int col = this.random.nextInt(1, this.field[0].length);

            if (!this.isPlaceOccupied(row, col)) {
                this.field[row][col] = this.computer;
                System.out.println("Computer made it's move!");
                System.out.printf("%s placed at row %d and col %d!\n", this.computer, row, col);
                run = false;
            }
        }
    }

    private boolean isPlaceOccupied(
            int row,
            int col
    ) {

        return !this.field[row][col].equals(this.filler);

    }

    private boolean isIndexValid(
            int row,
            int col
    ) {

        return (((row > 0) && (row < this.field.length))
                && ((col > 0) && (col < this.field[0].length)));

    }

    private boolean checkWin(String sym) {

        for (int i = 1; i < this.field.length; i++) {

            if (this.checkRow(sym, i)) {
                return true;
            }

        }

        for (int i = 1; i < this.field[0].length; i++) {

            if (this.checkCol(sym, i)) {
                return true;
            }

        }

        return this.checkDiagonal(sym);

    }

    private boolean checkRow(
            String sym,
            int row
    ) {

        boolean equality = true;

        for (int i = 1; i < this.field[0].length; i++) {

            if (!this.field[row][i].equals(sym)) {
                equality = false;
                break;
            }
        }

        return equality;
    }

    private boolean checkCol(
            String sym,
            int col
    ) {

        boolean equality = true;

        for (int i = 1; i < this.field.length; i++) {

            if (!this.field[i][col].equals(sym)) {
                equality = false;
                break;
            }
        }

        return equality;
    }

    private boolean checkDiagonal(String sym) {

        boolean equality1 = true;
        boolean equality2 = true;

        int k = this.field.length - 1;

        for (int i = 1; i < this.field.length; i++) {

            for (int j = 1; j < this.field[0].length; j++) {

                if (i == j) {

                    if (equality1
                        && !this.field[i][j].equals(sym)) {
                        equality1 = false;
                    }

                    if (equality2
                        && !this.field[k][j].equals(sym)) {
                        equality2 = false;
                    }

                }
            }

            if (!equality1 && !equality2) {
                break;
            }

            k--;
        }

        return equality1 ? equality1 : equality2;

    }

    public String toString() {
        return "TicTacToe";
    }

}