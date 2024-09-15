import java.util.Random;
import java.util.Scanner;

public class FifteenPuzzle2 {
    private static final int DIM = 10;
    private static final int STARTINDEX= 0;
    private static final int ENDINDEX = 9;
    private static final int NUMTILES= 9;
    private int[][] board;
    private int blankX, blankY;

    public FifteenPuzzle2() {
        board = new int[DIM][DIM];
        initializeBoard();
        shuffleBoard();
    }

    private void initializeBoard() {
        int count = 1;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                board[i][j] = count++;
            }
        }
        board[DIM-1][DIM-1] = 0; // Blank tile
        blankX = DIM-1;
        blankY = DIM-1;
    }

    private void shuffleBoard() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int move = random.nextInt(4);
            switch (move) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveDown();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveRight();
                    break;
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (board[i][j] == 0) {
                    System.out.print("   "); // For the blank space
                } else {
                    System.out.printf("%3d ", board[i][j]);
                }
            }
            System.out.println();
        }
    }

    private boolean isSolved() {
        int count = 1;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (board[i][j] != count && count < NUMTILES) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    private void moveUp() {
        if (blankX > STARTINDEX) {
            board[blankX][blankY] = board[blankX - 1][blankY];
            board[blankX - 1][blankY] = 0;
            blankX--;
        }
    }

    private void moveDown() {
        if (blankX < ENDINDEX) {
            board[blankX][blankY] = board[blankX + 1][blankY];
            board[blankX + 1][blankY] = 0;
            blankX++;
        }
    }

    private void moveLeft() {
        if (blankY > STARTINDEX) {
            board[blankX][blankY] = board[blankX][blankY - 1];
            board[blankX][blankY - 1] = 0;
            blankY--;
        }
    }

    private void moveRight() {
        if (blankY < ENDINDEX) {
            board[blankX][blankY] = board[blankX][blankY + 1];
            board[blankX][blankY + 1] = 0;
            blankY++;
        }
    }

    public static void main(String[] args) {
        FifteenPuzzle2 game = new FifteenPuzzle2();

        Scanner scanner = new Scanner(System.in);
        while (!game.isSolved()) {//replay
            game.printBoard();
            System.out.print("Enter move (u = up, d = down, l = left, r = right): ");
            String move = scanner.nextLine();//check validity
            switch (move) {
                case "u":
                    game.moveUp();
                    break;
                case "d":
                    game.moveDown();
                    break;
                case "l":
                    game.moveLeft();
                    break;
                case "r":
                    game.moveRight();
                    break;
                default:
                    System.out.println("Invalid move! Use u, d, l, or r.");
            }
        }
        System.out.println("You solved it!");
        scanner.close();
    }
}
