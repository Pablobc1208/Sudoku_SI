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
        System.out.println("¡Bienvenido al juego de Sudoku!");
        System.out.println("Elige una dificultad (easy, medium, hard):");
        String difficulty = scanner.nextLine();

        sudoku.generateBoard(difficulty);

        while (!sudoku.isSolved()) {
            System.out.println("\nEstado actual del tablero:");
            sudoku.printBoard();

            System.out.println("\nIntroduce fila (0-8), columna (0-8) y valor (1-9), o 0 para limpiar.");
            System.out.println("Ejemplo: 2 4 7 (para poner un 7 en la fila 2, columna 4)");
            System.out.println("Introduce '-1' para salir del juego.");
            
            int row, col, value;
            try {
                String input = scanner.nextLine();
                if (input.trim().equals("-1")) {
                    System.out.println("¡Saliendo del juego!");
                    return;
                }
                
                String[] parts = input.trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Por favor, introduce tres números separados por espacios.");
                    continue;
                }
                
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
                value = Integer.parseInt(parts[2]);

                sudoku.placeNumber(row, col, value);

            } catch (Exception e) {
                System.out.println("Entrada no válida. Asegúrate de introducir números válidos.");
            }
        }

        System.out.println("\n¡Enhorabuena! Has resuelto el Sudoku.");
        sudoku.printBoard();
        scanner.close();
    }
}
