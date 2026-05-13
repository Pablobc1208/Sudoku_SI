package com.example;

import java.util.Scanner;

/**
 * Handles the console-based UI for playing Sudoku.
 */
public class SudokuConsoleGame {

    private Sudoku sudoku;

    /**
     * Initializes the console game with a new Sudoku instance.
     */
    public SudokuConsoleGame() {
        this.sudoku = new Sudoku();
    }

    /**
     * Starts the interactive game loop in the console.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Sudoku game!");
        System.out.println("Choose a difficulty (easy, medium, hard):");
        String difficulty = scanner.nextLine();

        sudoku.generateBoard(difficulty);

        while (!sudoku.isSolved()) {
            System.out.println("\nCurrent board state:");
            sudoku.printBoard();

            System.out.println("\nEnter row (0-8), column (0-8) and value (1-9), or 0 to clear.");
            System.out.println("Example: 2 4 7 (to place a 7 at row 2, column 4)");
            System.out.println("Enter '-1' to exit the game.");
            
            int row, col, value;
            try {
                String input = scanner.nextLine();
                if (input.trim().equals("-1")) {
                    System.out.println("Exiting the game!");
                    return;
                }
                
                String[] parts = input.trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Please, enter three numbers separated by spaces.");
                    continue;
                }
                
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
                value = Integer.parseInt(parts[2]);

                sudoku.placeNumber(row, col, value);

            } catch (Exception e) {
                System.out.println("Invalid input. Make sure you enter valid numbers.");
            }
        }

        System.out.println("\nCongratulations! You have solved the Sudoku.");
        sudoku.printBoard();
        scanner.close();
    }
}
