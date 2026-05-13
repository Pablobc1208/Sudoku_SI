package com.example;

/**
 * Represents the core logic of the Sudoku game.
 * It manages the board state, rules, and validates movements.
 */
public class Sudoku {
    private int[][] board;
    private boolean[][] fixedCells;

    /**
     * Initializes an empty 9x9 Sudoku board and its fixed cells matrix.
     */
    public Sudoku() {
        this.board = new int[9][9];
        this.fixedCells = new boolean[9][9];
    }

    /**
     * Generates a new Sudoku board based on the specified difficulty level.
     * 
     * @param difficulty The difficulty level ("easy", "medium", "hard").
     */
    public void generateBoard(String difficulty) {
        SudokuGenerator generator = new SudokuGenerator();
        int emptyCells;
        
        switch (difficulty.toLowerCase()) {
            case "easy":
                emptyCells = 30; // Fewer empty cells for easy mode
                break;
            case "medium":
                emptyCells = 45; // Moderate number of empty cells
                break;
            case "hard":
                emptyCells = 60; // More empty cells for hard mode
                break;
            default:
                emptyCells = 45;
                System.out.println("Unknown difficulty, defaulting to 'medium'.");
        }
        
        this.board = generator.generateInitialBoard(emptyCells);
        
        // Mark the initial generated cells as fixed
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0) {
                    this.fixedCells[i][j] = true;
                } else {
                    this.fixedCells[i][j] = false;
                }
            }
        }
    }

    /**
     * Validates if a number can be placed at a specific position according to Sudoku rules.
     * 
     * @param row The row index (0-8).
     * @param col The column index (0-8).
     * @param value The number to be placed (1-9).
     * @return true if the movement is valid, false otherwise.
     */
    public boolean isMovementValid(int row, int col, int value) {
        // Rule 1: Number must not exist in the same row
        for (int j = 0; j < 9; j++) {
            if (j != col && board[row][j] == value) {
                return false;
            }
        }
        
        // Rule 2: Number must not exist in the same column
        for (int i = 0; i < 9; i++) {
            if (i != row && board[i][col] == value) {
                return false;
            }
        }
        
        // Rule 3: Number must not exist in the same 3x3 block
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (i != row && j != col && board[i][j] == value) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Attempts to place a number on the board.
     * 
     * @param row The row index (0-8).
     * @param col The column index (0-8).
     * @param value The number to place (1-9, or 0 to clear the cell).
     * @return true if successfully placed, false if invalid or cell is fixed.
     */
    public boolean placeNumber(int row, int col, int value) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            System.out.println("Position out of bounds.");
            return false;
        }
        if (fixedCells[row][col]) {
            System.out.println("Cannot modify a fixed initial cell.");
            return false;
        }
        if (value != 0 && (value < 1 || value > 9)) {
            System.out.println("Value must be between 1 and 9, or 0 to clear.");
            return false;
        }
        if (value == 0) {
            board[row][col] = 0;
            return true;
        }
        
        if (isMovementValid(row, col, value)) {
            board[row][col] = value;
            return true;
        } else {
            System.out.println("Invalid movement according to Sudoku rules.");
            return false;
        }
    }

    /**
     * Checks if the entire board is completely and correctly solved.
     * 
     * @return true if the Sudoku is solved, false otherwise.
     */
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false; // Found an empty cell
                }
                
                int temp = board[i][j];
                board[i][j] = 0;
                boolean valid = isMovementValid(i, j, temp);
                board[i][j] = temp;
                
                if (!valid) {
                    return false; // Found a rule violation
                }
            }
        }
        return true;
    }

    /**
     * Prints the current state of the board to the standard output.
     */
    public void printBoard() {
        System.out.println("   0 1 2   3 4 5   6 7 8 ");
        System.out.println(" +-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0) {
                System.out.println(" +-------+-------+-------+");
            }
        }
    }

    /**
     * Retrieves the current board matrix.
     * 
     * @return The 9x9 integer matrix representing the board.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Retrieves the matrix of fixed cells.
     * 
     * @return The 9x9 boolean matrix representing fixed cells.
     */
    public boolean[][] getFixedCells() {
        return fixedCells;
    }
}
