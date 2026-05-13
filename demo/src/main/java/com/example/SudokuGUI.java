package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Provides a Java Swing Graphical User Interface for the Sudoku game.
 */
public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private JTextField[][] guiCells;

    /**
     * Constructs the GUI components and initializes the game logic.
     */
    public SudokuGUI() {
        this.sudoku = new Sudoku();
        this.guiCells = new JTextField[9][9];
    }

    /**
     * Initializes and displays the main graphical interface.
     */
    public void startGUI() {
        setTitle("Sudoku Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        JButton btnEasy = new JButton("Easy");
        JButton btnMedium = new JButton("Medium");
        JButton btnHard = new JButton("Hard");
        JButton btnValidate = new JButton("Validate");

        menuPanel.add(new JLabel("Difficulty:"));
        menuPanel.add(btnEasy);
        menuPanel.add(btnMedium);
        menuPanel.add(btnHard);
        menuPanel.add(new JLabel(" | "));
        menuPanel.add(btnValidate);

        add(menuPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));
        
        Font font = new Font("SansSerif", Font.BOLD, 24);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField txtCell = new JTextField();
                txtCell.setHorizontalAlignment(JTextField.CENTER);
                txtCell.setFont(font);
                
                // Borders to visually separate 3x3 blocks
                int top = (i % 3 == 0) ? 2 : 1;
                int left = (j % 3 == 0) ? 2 : 1;
                int bottom = (i == 8) ? 2 : 1;
                int right = (j == 8) ? 2 : 1;
                
                txtCell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                final int row = i;
                final int col = j;

                txtCell.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        handleUserInput(row, col, txtCell.getText());
                    }
                });

                guiCells[i][j] = txtCell;
                boardPanel.add(txtCell);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        btnEasy.addActionListener(e -> generateNewGame("easy"));
        btnMedium.addActionListener(e -> generateNewGame("medium"));
        btnHard.addActionListener(e -> generateNewGame("hard"));
        
        btnValidate.addActionListener(e -> {
            // Force focus loss to validate the last edited cell
            menuPanel.requestFocus();
            
            if (sudoku.isSolved()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You successfully solved the Sudoku.");
            } else {
                JOptionPane.showMessageDialog(this, "The Sudoku is not solved yet or contains errors.", "Validation", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Generate default game
        generateNewGame("medium");

        setVisible(true);
    }

    /**
     * Triggers the generation of a new game based on the given difficulty.
     * 
     * @param difficulty The difficulty level.
     */
    private void generateNewGame(String difficulty) {
        sudoku.generateBoard(difficulty);
        updateBoardUI();
    }

    /**
     * Refreshes the graphical cells to match the underlying game state.
     */
    private void updateBoardUI() {
        int[][] board = sudoku.getBoard();
        boolean[][] fixed = sudoku.getFixedCells();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                guiCells[i][j].setEditable(true);
                guiCells[i][j].setBackground(Color.WHITE);
                guiCells[i][j].setForeground(Color.BLACK);

                if (board[i][j] != 0) {
                    guiCells[i][j].setText(String.valueOf(board[i][j]));
                    if (fixed[i][j]) {
                        guiCells[i][j].setEditable(false);
                        guiCells[i][j].setBackground(new Color(230, 230, 230)); // Light gray for fixed cells
                        guiCells[i][j].setForeground(Color.DARK_GRAY);
                    }
                } else {
                    guiCells[i][j].setText("");
                }
            }
        }
    }

    /**
     * Processes input provided by the user in the UI cells.
     * 
     * @param row The row of the modified cell.
     * @param col The column of the modified cell.
     * @param text The string value entered by the user.
     */
    private void handleUserInput(int row, int col, String text) {
        // Ignore if it's a fixed initial cell
        if (sudoku.getFixedCells()[row][col]) {
            return;
        }

        if (text == null || text.trim().isEmpty()) {
            sudoku.placeNumber(row, col, 0); // Clear
            guiCells[row][col].setForeground(Color.BLACK);
            return;
        }

        try {
            int value = Integer.parseInt(text.trim());
            
            // Validate range beforehand
            if (value < 1 || value > 9) {
                JOptionPane.showMessageDialog(this, "Value must be between 1 and 9", "Error", JOptionPane.ERROR_MESSAGE);
                SwingUtilities.invokeLater(() -> guiCells[row][col].setText(""));
                sudoku.placeNumber(row, col, 0);
                return;
            }

            // Attempt to place it via underlying logic
            boolean success = sudoku.placeNumber(row, col, value);
            if (!success) {
                guiCells[row][col].setForeground(Color.RED);
            } else {
                guiCells[row][col].setForeground(Color.BLUE); // Blue for correct user input
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Must enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(() -> guiCells[row][col].setText(""));
            sudoku.placeNumber(row, col, 0);
        }
    }
}
