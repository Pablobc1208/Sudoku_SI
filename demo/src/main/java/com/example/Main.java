package com.example;

import javax.swing.SwingUtilities;
import java.util.Scanner;

/**
 * Main entry point for the Sudoku application.
 * Allows the user to select between Console Mode and GUI Mode.
 */
public class Main {
    /**
     * Main method.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecciona modo de juego:");
        System.out.println("1. Modo Consola");
        System.out.println("2. Modo Interfaz Gráfica (Swing)");
        System.out.print("Opción: ");

        int option = 2; // Default to GUI mode
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Opción no válida, usando Interfaz Gráfica por defecto.");
        }

        if (option == 1) {
            SudokuConsoleGame game = new SudokuConsoleGame();
            game.start();
        } else {
            SwingUtilities.invokeLater(() -> {
                SudokuGUI gui = new SudokuGUI();
                gui.startGUI();
            });
        }
        
        if (option != 1) {
             scanner.close();
        }
    }
}
