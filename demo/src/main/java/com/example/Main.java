package com.example;

import javax.swing.SwingUtilities;

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
        SwingUtilities.invokeLater(() -> {
            SudokuGUI gui = new SudokuGUI();
            gui.startGUI();
        });
    }
}
