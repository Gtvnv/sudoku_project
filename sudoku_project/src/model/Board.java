package model;

import java.io.*;

public class Board {
    private Cell[][] cells = new Cell[9][9];
    private int[][] initialFixedValues = new int[9][9];

    public Board() { // Renamed from Tabuleiro()
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(0, false);
                initialFixedValues[i][j] = 0;
            }
        }
    }

    /**
     * Sets the initial Sudoku puzzle on the board.
     * Non-zero values in the 'puzzle' are considered fixed cells.
     *
     * @param puzzle A 9x9 integer array representing the puzzle.
     * 0 for empty cells, 1-9 for fixed numbers.
     */
    public void setInitialPuzzle(int[][] puzzle) {
        if (puzzle == null || puzzle.length != 9 || puzzle[0].length != 9) {
            throw new IllegalArgumentException("The puzzle must be a 9x9 matrix.");
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = puzzle[i][j];
                boolean isFixed = (value != 0);

                this.initialFixedValues[i][j] = value; // Stores the original fixed value
                this.cells[i][j] = new Cell(value, isFixed); // Creates the cell with fixed status
            }
        }
    }

    public int getCellValue(int row, int col) { 
        return cells[row][col].getValue();
    }

    public void setCellValue(int row, int col, int value) {
        if (!cells[row][col].isFixed()) {
            cells[row][col].setValue(value);
        }
    }

    public boolean isCellFixed(int row, int col) {
        // A cell is fixed if its original value was not zero
        return initialFixedValues[row][col] != 0;
    }

    /**
     * Checks if a given value is valid at a specific position on the board,
     * considering Sudoku rules (row, column, 3x3 block).
     *
     * @param row   Row index of the cell to check.
     * @param col   Column index of the cell to check.
     * @param value The value to be checked.
     * @return true if the value is valid at the position, false otherwise.
     */
    public boolean isValidMove(int row, int col, int value) {
        if (value == 0) return true; // A 0 (empty cell) is always valid for placement

        // Check row
        for (int i = 0; i < 9; i++) {
            // Skip the cell being checked itself
            if (i != col && cells[row][i].getValue() == value) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            // Skip the cell being checked itself
            if (i != row && cells[i][col].getValue() == value) {
                return false;
            }
        }

        // Check 3x3 block
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                // Skip the cell being checked itself
                if ((i != row || j != col) && cells[i][j].getValue() == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the board is completely filled and all cells are valid,
     * indicating a victory.
     * @return true if the game is won, false otherwise.
     */
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = getCellValue(i, j);
                if (value == 0) {
                    return false;
                }
                // The isValidMove method already correctly excludes the cell itself from checking.
                if (!isValidMove(i, j, value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Saves the current board state (user values) and the initial fixed puzzle.
     * @param path File path to save to.
     * @throws IOException If an I/O error occurs.
     */
    public void saveGame(String path) throws IOException { // Renamed from salvarPartida
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Save the initial fixed values matrix
            for (int i = 0; i < 9; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 9; j++) {
                    line.append(initialFixedValues[i][j]);
                    if (j < 8) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
            writer.newLine(); // Add a blank line to separate the blocks

            // Save the current cells matrix (current game state)
            for (int i = 0; i < 9; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 9; j++) {
                    line.append(cells[i][j].getValue()); // Get the numeric value
                    if (j < 8) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Loads a previous game, restoring fixed values and user moves.
     * @param path File path to load from.
     * @throws IOException If an I/O error occurs or the file is corrupted.
     */
    public void loadGame(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // --- Load initial fixed values ---
            for (int i = 0; i < 9; i++) {
                String line = reader.readLine();
                if (line == null) throw new IOException("Save file incomplete (initial values).");
                String[] valuesStr = line.split(",");
                if (valuesStr.length != 9) throw new IOException("Invalid line format (initial values) on line " + (i + 1));
                for (int j = 0; j < 9; j++) {
                    try {
                        initialFixedValues[i][j] = Integer.parseInt(valuesStr[j].trim());
                    } catch (NumberFormatException e) {
                        throw new IOException("Invalid value in initial values on line " + (i + 1) + ", column " + (j + 1), e);
                    }
                }
            }

            reader.readLine(); // Read and discard the blank separation line

            // --- Load current state ---
            for (int i = 0; i < 9; i++) {
                String line = reader.readLine();
                if (line == null) throw new IOException("Save file incomplete (current values).");
                String[] valuesStr = line.split(",");
                if (valuesStr.length != 9) throw new IOException("Invalid line format (current values) on line " + (i + 1));
                for (int j = 0; j < 9; j++) {
                    try {
                        int currentCellValue = Integer.parseInt(valuesStr[j].trim());
                        boolean isFixed = (initialFixedValues[i][j] != 0); // Use the just-loaded initial fixed values
                        this.cells[i][j] = new Cell(currentCellValue, isFixed); // Create a new Cell with current value and correct fixed status
                    } catch (NumberFormatException e) {
                        throw new IOException("Invalid value in current values on line " + (i + 1) + ", column " + (j + 1), e);
                    }
                }
            }
        }
    }
}
