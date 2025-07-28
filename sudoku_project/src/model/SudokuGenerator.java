package model;

import java.util.Random;
import java.util.Arrays; 
import java.util.Collections;
import java.util.List; 

public class SudokuGenerator { 

    private static final Random random = new Random(); 

    /**
     * Generates a new Sudoku puzzle based on the specified difficulty.
     *
     * @param difficulty The desired difficulty level (EASY, MEDIUM, HARD).
     * @return A 9x9 integer array representing the generated Sudoku board.
     */
    public static int[][] generate(Difficulty difficulty) { 
       int[][] fullBoard = new int[9][9]; 
        if (!fillBoard(fullBoard, 0, 0)) {
            return new int[9][9]; // Return an empty board in case of unexpected failure
        }
        removeNumbers(fullBoard, difficulty);
        return fullBoard;
    }

    /**
     * Recursive backtracking algorithm to fill the Sudoku board with a valid solution.
     *
     * @param board The 9x9 board to be filled.
     * @param row   Current row index.
     * @param col   Current column index.
     * @return true if the board was successfully filled, false otherwise.
     */
    private static boolean fillBoard(int[][] board, int row, int col) { 
        // If column exceeds 8, move to the next row
        if (col == 9) {
            col = 0;
            row++;
            // If row exceeds 8, the board is complete
            if (row == 9) return true;
        }

        // If the cell is already filled (not 0), skip to the next cell
        if (board[row][col] != 0) {
            return fillBoard(board, row, col + 1);
        }

        // Try random numbers from 1 to 9
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // Shuffle the array to ensure different puzzles are generated
        shuffleArray(numbers); // Now uses Integer[]

        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (fillBoard(board, row, col + 1)) return true;
                // If filling the rest is not possible, undo and try another number
                board[row][col] = 0;
            }
        }
        return false; // No number works for this cell
    }

    /**
     * Checks if a number is valid at a given position on the board.
     * This method is intended for internal use during puzzle generation/solving.
     *
     * @param board The board.
     * @param row   Row index of the cell.
     * @param col   Column index of the cell.
     * @param num   Number to be checked.
     * @return true if the number is valid, false otherwise.
     */
    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int x = 0; x < 9; x++) {
            if (board[row][x] == num) {
                return false;
            }
        }

        // Check column
        for (int x = 0; x < 9; x++) {
            if (board[x][col] == num) {
                return false;
            }
        }

        // Check 3x3 block
        int startRow = (row / 3) * 3; // Renamed from 'linhaBase'
        int startCol = (col / 3) * 3; // Renamed from 'colunaBase'

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Removes numbers from the board to create the puzzle, based on the difficulty.
     *
     * @param board      The complete board solution.
     * @param difficulty The desired difficulty level.
     */
    private static void removeNumbers(int[][] board, Difficulty difficulty) {
        int cellsToRemove;
        switch (difficulty) {
            case EASY:
                cellsToRemove = 40; // Example: 40 empty cells
                break;
            case MEDIUM:
                cellsToRemove = 50; // Example: 50 empty cells
                break;
            case HARD:
                cellsToRemove = 60; // Example: 60 empty cells
                break;
            default:
                cellsToRemove = 45; // Default value
        }

        int count = 0;
        while (count < cellsToRemove) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            // Attempts to remove a number, and only counts if it's uniquely solvable
            if (removeNumberIfUnique(board, row, col)) {
                count++;
            }
        }
    }

    /**
     * Attempts to remove a number from the board and checks if the remaining puzzle
     * still has a unique solution.
     *
     * @param board The current board.
     * @param row   Row of the cell to remove.
     * @param col   Column of the cell to remove.
     * @return true if the number was successfully removed and solution remains unique, false otherwise.
     */
    public static boolean removeNumberIfUnique(int[][] board, int row, int col) {
        if (board[row][col] == 0) return false; // Cannot remove if already empty

        int temp = board[row][col]; // Temporarily store the value
        board[row][col] = 0;         // Remove the number

        // To check for unique solution, we try to solve the puzzle and count solutions.
        // If count[0] is > 1 after solving, it means multiple solutions exist.
        int[] count = {0}; // Renamed from 'conta' (single-element array to pass by reference)
        // 'limit' is set to 2 because we only care if it's 1 or >1. Finding 2 solutions is enough.
        solveAndCount(board, 0, 0, count, 2);

        if (count[0] != 1) { // If not a unique solution (0 or >1 solutions)
            board[row][col] = temp; // Restore the number
            return false;
        }

        return true; // Number successfully removed and solution is unique
    }

    /**
     * Recursive backtracking solver used to count the number of solutions for a given Sudoku board.
     * It stops searching if 'limit' solutions are found.
     *
     * @param board   The Sudoku board to solve.
     * @param row     Current row to start solving from.
     * @param col     Current column to start solving from.
     * @param count   An array holding the count of solutions found so far (passed by reference).
     * @param limit   The maximum number of solutions to count before stopping.
     * @return true if a solution was found and count is below limit, false otherwise (used internally by recursion).
     */
    public static boolean solveAndCount(int[][] board, int row, int col, int[] count, int limit) {
        if (count[0] >= limit) return false; // Stop if limit reached (e.g., already found 2 solutions)

        if (row == 9) { // Base case: If all rows are processed, a solution is found
            count[0]++; // Increment solution count
            return false; // Continue searching for more solutions by returning false
        }

        int nextRow = col == 8 ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        if (board[row][col] != 0) { // If cell is already filled, move to the next
            return solveAndCount(board, nextRow, nextCol, count, limit);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                solveAndCount(board, nextRow, nextCol, count, limit); // Recurse
                board[row][col] = 0; // Backtrack: undo the move
            }
        }
        return false; // No number works for this path
    }

    /**
     * Shuffles an array of Integers using the Fisher-Yates shuffle algorithm.
     * Used to randomize the order of number attempts in backtracking, ensuring different puzzles.
     *
     * @param array The Integer array to be shuffled.
     */
    private static void shuffleArray(Integer[] array) {
        // Convert to List for Collections.shuffle
        List<Integer> list = Arrays.asList(array);
        Collections.shuffle(list, random);
        // Convert back to array (if necessary, though for-each loop will work with List directly)
        // No need to convert back if the array is used as a List.
        // But for consistency with original signature, copy back:
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
    }
}