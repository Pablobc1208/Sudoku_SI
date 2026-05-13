package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        sudoku = new Sudoku();
    }

    @Test
    public void testGenerateBoard() {
        sudoku.generateBoard("easy");
        int[][] board = sudoku.getBoard();
        boolean[][] fixed = sudoku.getFixedCells();
        
        assertNotNull(board, "Board should not be null after generation.");
        assertNotNull(fixed, "Fixed cells array should not be null.");
        
        int filledCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    filledCount++;
                    assertTrue(fixed[i][j], "A generated cell must be marked as fixed.");
                } else {
                    assertFalse(fixed[i][j], "An empty cell must not be marked as fixed.");
                }
            }
        }
        
        assertTrue(filledCount > 0, "Board should have some filled cells initially.");
    }

    @Test
    public void testIsMovementValid() {
        sudoku.getBoard()[0][0] = 5;
        
        // Same row
        assertFalse(sudoku.isMovementValid(0, 1, 5), "Should be invalid to place 5 in the same row.");
        // Same column
        assertFalse(sudoku.isMovementValid(1, 0, 5), "Should be invalid to place 5 in the same column.");
        // Same block
        assertFalse(sudoku.isMovementValid(1, 1, 5), "Should be invalid to place 5 in the same 3x3 block.");
        
        // Valid
        assertTrue(sudoku.isMovementValid(3, 3, 5), "Should be valid in another block, row, and column.");
    }

    @Test
    public void testPlaceNumber() {
        // Place a valid number
        assertTrue(sudoku.placeNumber(0, 0, 5), "Should allow placing a valid number.");
        assertEquals(5, sudoku.getBoard()[0][0], "Board should contain the placed number.");
        
        // Clear cell
        assertTrue(sudoku.placeNumber(0, 0, 0), "Should allow clearing a cell.");
        assertEquals(0, sudoku.getBoard()[0][0], "Board cell should be 0.");
        
        // Invalid range
        assertFalse(sudoku.placeNumber(-1, 0, 5), "Should not allow out of bounds row.");
        assertFalse(sudoku.placeNumber(0, 0, 10), "Should not allow value out of range (1-9).");
    }

    @Test
    public void testFixedCellModification() {
        sudoku.generateBoard("easy");
        
        // Find a fixed cell
        int fixedRow = -1, fixedCol = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku.getFixedCells()[i][j]) {
                    fixedRow = i;
                    fixedCol = j;
                    break;
                }
            }
            if (fixedRow != -1) break;
        }
        
        assertFalse(sudoku.placeNumber(fixedRow, fixedCol, 1), "Should not allow modifying a fixed cell.");
    }

    @Test
    public void testIsSolved() {
        // Empty board is not solved
        assertFalse(sudoku.isSolved(), "Empty board should not be considered solved.");
        
        // Load solution into board
        sudoku.generateBoard("easy");
        int[][] solution = sudoku.getSolutionBoard();
        int[][] board = sudoku.getBoard();
        
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, board[i], 0, 9);
        }
        
        assertTrue(sudoku.isSolved(), "Board filled with its solution should be considered solved.");
        
        // Introduce an error
        int originalValue = board[0][0];
        board[0][0] = (originalValue % 9) + 1; // Change to a different valid number
        if (board[0][0] == originalValue) board[0][0] = (originalValue == 1) ? 2 : 1;
        
        assertFalse(sudoku.isSolved(), "Board with an incorrect number should not be solved.");
    }

    @Test
    public void testDifficultyLevels() {
        sudoku.generateBoard("easy");
        int easyEmpty = countEmptyCells();
        
        sudoku.generateBoard("medium");
        int mediumEmpty = countEmptyCells();
        
        sudoku.generateBoard("hard");
        int hardEmpty = countEmptyCells();
        
        assertTrue(easyEmpty < mediumEmpty, "Easy should have fewer empty cells than medium.");
        assertTrue(mediumEmpty < hardEmpty, "Medium should have fewer empty cells than hard.");
    }

    @Test
    public void testInvalidMovements() {
        assertFalse(sudoku.isMovementValid(0, 0, 10), "10 is not a valid Sudoku number.");
        assertFalse(sudoku.isMovementValid(0, 0, -1), "-1 is not a valid Sudoku number.");
        assertTrue(sudoku.isMovementValid(0, 0, 0), "0 (clear) is always valid.");
    }

    private int countEmptyCells() {
        int count = 0;
        int[][] board = sudoku.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) count++;
            }
        }
        return count;
    }
}
