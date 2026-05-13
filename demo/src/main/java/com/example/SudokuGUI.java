package com.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Provides a fun Java Swing Graphical User Interface for the Sudoku game.
 */
public class SudokuGUI extends JFrame {

    private Sudoku sudoku;
    private JTextField[][] guiCells;

    // Layout and Panels
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private JPanel gamePanel;

    // Colors
    private final Color PRIMARY_COLOR = new Color(108, 92, 231); // Soft Purple
    private final Color BG_COLOR = new Color(241, 242, 246); // Very Light Gray
    private final Color BUTTON_HOVER = new Color(162, 155, 254);

    private String currentDifficulty = "medium";

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
        setTitle("✨ Fun Sudoku ✨");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Build the two main screens
        mainContainer.add(createStartScreen(), "StartScreen");
        mainContainer.add(createGameScreen(), "GameScreen");

        add(mainContainer);

        // Show start screen first
        cardLayout.show(mainContainer, "StartScreen");

        setVisible(true);
    }

    /**
     * Creates the welcome screen with the start button.
     * 
     * @return The start panel.
     */
    private JPanel createStartScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);

        panel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("SUDOKU");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 72)); // Fun font
        title.setForeground(PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Ready for a brain challenge?");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnStart = createStyledButton("▶ START GAME");
        btnStart.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStart.addActionListener(e -> {
            generateNewGame("medium"); // Default to medium on start
            cardLayout.show(mainContainer, "GameScreen");
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));
        panel.add(btnStart);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Creates the main game board screen.
     * 
     * @return The game panel.
     */
    private JPanel createGameScreen() {
        gamePanel = new JPanel(new BorderLayout(10, 10));
        gamePanel.setBackground(BG_COLOR);
        gamePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Menu
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        menuPanel.setBackground(BG_COLOR);

        JButton btnEasy = createStyledButton("Easy");
        JButton btnMedium = createStyledButton("Medium");
        JButton btnHard = createStyledButton("Hard");
        JButton btnValidate = createStyledButton("✔ Validate");
        JButton btnBack = createStyledButton("🔙 Back");

        menuPanel.add(btnBack);
        menuPanel.add(new JLabel(" | "));
        menuPanel.add(btnEasy);
        menuPanel.add(btnMedium);
        menuPanel.add(btnHard);
        menuPanel.add(new JLabel(" | "));
        menuPanel.add(btnValidate);

        gamePanel.add(menuPanel, BorderLayout.NORTH);

        // Board Panel
        JPanel boardPanel = new JPanel(new GridLayout(9, 9));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        Font font = new Font("SansSerif", Font.BOLD, 28);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField txtCell = new JTextField();
                txtCell.setHorizontalAlignment(JTextField.CENTER);
                txtCell.setFont(font);

                // Borders to visually separate 3x3 blocks with thicker lines
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == 8) ? 3 : 1;
                int right = (j == 8) ? 3 : 1;

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

        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Action Listeners
        btnEasy.addActionListener(e -> {
            currentDifficulty = "easy";
            generateNewGame(currentDifficulty);
        });
        btnMedium.addActionListener(e -> {
            currentDifficulty = "medium";
            generateNewGame(currentDifficulty);
        });
        btnHard.addActionListener(e -> {
            currentDifficulty = "hard";
            generateNewGame(currentDifficulty);
        });
        btnBack.addActionListener(e -> cardLayout.show(mainContainer, "StartScreen"));

        btnValidate.addActionListener(e -> {
            menuPanel.requestFocus(); // Force focus loss
            if (sudoku.isSolved()) {
                JOptionPane.showMessageDialog(this, " CONGRATULATIONS! \nYou are a Sudoku Master!", "Victory",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        " Oh no! There are mistakes or the board is incomplete.\nStarting over... Try again! 💪",
                        "Validation Failed", JOptionPane.ERROR_MESSAGE);
                generateNewGame(currentDifficulty);
            }
        });

        return gamePanel;
    }

    /**
     * Helper method to create modern-looking buttons.
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Simple hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });

        return btn;
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
                        guiCells[i][j].setBackground(new Color(223, 230, 233)); // Softer gray/blue
                        guiCells[i][j].setForeground(new Color(45, 52, 54));
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
     * @param row  The row of the modified cell.
     * @param col  The column of the modified cell.
     * @param text The string value entered by the user.
     */
    private void handleUserInput(int row, int col, String text) {
        if (sudoku.getFixedCells()[row][col]) {
            return;
        }

        if (text == null || text.trim().isEmpty()) {
            sudoku.placeNumber(row, col, 0);
            guiCells[row][col].setForeground(Color.BLACK);
            return;
        }

        try {
            int value = Integer.parseInt(text.trim());

            if (value < 1 || value > 9) {
                guiCells[row][col].setForeground(Color.RED);
                return;
            }

            boolean success = sudoku.placeNumber(row, col, value);
            if (!success) {
                guiCells[row][col].setForeground(Color.RED); // Red for errors
            } else {
                guiCells[row][col].setForeground(new Color(9, 132, 227)); // Nice Blue for correct inputs
            }

        } catch (NumberFormatException e) {
            guiCells[row][col].setForeground(Color.RED);
        }
    }
}
