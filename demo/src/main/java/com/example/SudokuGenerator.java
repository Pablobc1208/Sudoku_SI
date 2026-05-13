package com.example;

import java.util.Random;

/**
 * Utility class to generate valid Sudoku boards.
 */
public class SudokuGenerator {

    private int[][] board;
    private Random random;

    /**
     * Initializes the generator with an empty board and a random number generator.
     */
    public SudokuGenerator() {
        board = new int[9][9];
        random = new Random();
    }

    /**
     * Generates a valid Sudoku board and removes a specified number of cells.
     * 
     * @param emptyCells The number of cells to clear for the puzzle.
     * @return The 9x9 integer matrix representing the generated board.
     */
    public int[][] generateInitialBoard(int emptyCells) {
        // 1. Clear the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }

        // 2. Fill the 3 diagonal blocks (they are independent of each other)
        fillDiagonalBlock(0, 0);
        fillDiagonalBlock(3, 3);
        fillDiagonalBlock(6, 6);

        // 3. Solve the rest of the Sudoku using backtracking
        solveSudoku(board);

        // 4. Remove cells randomly according to difficulty
        removeCells(emptyCells);

        return board;
    }

    /**
     * Fills a 3x3 block on the main diagonal with valid random numbers.
     * 
     * @param startRow The starting row of the block.
     * @param startCol The starting column of the block.
     */
    private void fillDiagonalBlock(int startRow, int startCol) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(9) + 1;
                } while (!isSafeInBlock(startRow, startCol, num));
                board[startRow + i][startCol + j] = num;
            }
        }
    }

    /**
     * Checks if a number can be safely placed in a 3x3 block.
     * 
     * @param startRow The starting row of the block.
     * @param startCol The starting column of the block.
     * @param num The number to check.
     * @return true if the number is not present in the block, false otherwise.
     */
    private boolean isSafeInBlock(int startRow, int startCol, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates if placing a number at a specific position complies with Sudoku rules.
     * 
     * @param tab The board matrix.
     * @param row The row index.
     * @param col The column index.
     * @param num The number to validate.
     * @return true if the placement is valid, false otherwise.
     */
    private boolean isValid(int[][] tab, int row, int col, int num) {
        // Check row
        for (int j = 0; j < 9; j++) {
            if (tab[row][j] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (tab[i][col] == num) {
                return false;
            }
        }

        // Check block
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tab[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the Sudoku board using a backtracking algorithm.
     * 
     * @param tab The board matrix to solve.
     * @return true if a solution is found, false otherwise.
     */
    private boolean solveSudoku(int[][] tab) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (tab[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(tab, row, col, num)) {
                            tab[row][col] = num;
                            if (solveSudoku(tab)) {
                                return true;
                            } else {
                                tab[row][col] = 0; // backtrack
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Randomly clears a specified number of cells to create the puzzle.
     * 
     * @param count The number of cells to clear.
     */
    private void removeCells(int count) {
        while (count != 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (board[row][col] != 0) {
                board[row][col] = 0;
                count--;
            }
        }
    }
}
