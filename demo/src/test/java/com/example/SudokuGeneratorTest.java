package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuGeneratorTest {

    private SudokuGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new SudokuGenerator();
    }

    @Test
    public void testGenerateInitialBoard() {
        int emptyCells = 30;
        int[][] board = generator.generateInitialBoard(emptyCells);
        
        assertNotNull(board, "Generated board should not be null");
        assertEquals(9, board.length, "Board should have 9 rows");
        assertEquals(9, board[0].length, "Board should have 9 columns");
        
        int actualEmptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    actualEmptyCells++;
                } else {
                    assertTrue(board[i][j] >= 1 && board[i][j] <= 9, "Filled cells must be between 1 and 9");
                }
            }
        }
        
        // Since random generation might overlap cell removal, the actual empty cells might be slightly less
        // or exactly equal. We just ensure it's greater than 0 and less than or equal to the requested amount.
        assertTrue(actualEmptyCells > 0, "Board should have empty cells");
        assertTrue(actualEmptyCells <= emptyCells, "Board should not have more empty cells than requested");
    }

    @Test
    public void testSolutionValidity() {
        generator.generateInitialBoard(30); // Ensure a board is generated first
        int[][] solution = generator.getSolution();
        assertNotNull(solution, "Solution should not be null after generation");
        
        // Verify it's a valid Sudoku solution
        Sudoku validator = new Sudoku();
        // Manually fill the board to bypass fixed cells check if any
        int[][] board = validator.getBoard();
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, board[i], 0, 9);
        }
        
        assertTrue(validator.isSolved(), "The generated solution must be a valid solved Sudoku board");
    }
}
