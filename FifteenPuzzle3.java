import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FifteenPuzzle3 {
    private static final int SIZE = 4;
    private int[][] board;
    private int blankRow, blankCol;

    public FifteenPuzzle3() {
        this.board = new int[SIZE][SIZE];
        generateSolvablePuzzle();
    }

    // Generate a solvable 15-puzzle matrix
    private void generateSolvablePuzzle() {
        ArrayList<Integer> tiles = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            tiles.add(i);
        }

        // Shuffle until we find a solvable configuration
        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles));

        // Fill the board with the shuffled tiles
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = tiles.get(i * SIZE + j);
                if (board[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    // Check if a configuration is solvable
    private boolean isSolvable(ArrayList<Integer> tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i) != 0 && tiles.get(j) != 0 && tiles.get(i) > tiles.get(j)) {
                    inversions++;
                }
            }
        }

        // Check the row of the blank tile (count from bottom)
        int blankRowFromBottom = SIZE - (tiles.indexOf(0) / SIZE);
        return (inversions % 2 == 0) == (blankRowFromBottom % 2 == 1);
    }

    // Display the current state of the puzzle
    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", board[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Move the blank tile (0) if possible
    public boolean move(int direction) {
        // 0 = up, 1 = down, 2 = left, 3 = right
        int newRow = blankRow;
        int newCol = blankCol;

        switch (direction) {
            case 0: newRow--; break; // Move up
            case 1: newRow++; break; // Move down
            case 2: newCol--; break; // Move left
            case 3: newCol++; break; // Move right
            default: return false;
        }

        if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
            // Swap blank tile with adjacent tile
            board[blankRow][blankCol] = board[newRow][newCol];
            board[newRow][newCol] = 0;
            blankRow = newRow;
            blankCol = newCol;
            return true;
        }
        return false;
    }

    // Check if the puzzle is solved
    public boolean isSolved() {
        int value = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == SIZE - 1 && j == SIZE - 1) {
                    return board[i][j] == 0;
                }
                if (board[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FifteenPuzzle3 puzzle = new FifteenPuzzle3();

        System.out.println("Welcome to the 15 Puzzle Game!");
        System.out.println("Use W (up), S (down), A (left), D (right) to move the blank tile.");
        
        while (!puzzle.isSolved()) {
            puzzle.displayBoard();
            System.out.print("Move (WASD): ");
            char move = scanner.next().toUpperCase().charAt(0);
            boolean moved = false;

            switch (move) {
                case 'W': moved = puzzle.move(0); break;
                case 'S': moved = puzzle.move(1); break;
                case 'A': moved = puzzle.move(2); break;
                case 'D': moved = puzzle.move(3); break;
                default: System.out.println("Invalid move! Use W, A, S, or D.");
            }

            if (!moved) {
                System.out.println("Move not possible!");
            }
        }

        System.out.println("Congratulations! You've solved the puzzle!");
        scanner.close();
    }
}
