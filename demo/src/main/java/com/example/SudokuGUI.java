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
    private final UserDAO userDAO = new UserDAO();

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
        setTitle("Sudoku");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Build the main screens
        mainContainer.add(createLoginScreen(), "LoginScreen");
        mainContainer.add(createStartScreen(), "StartScreen");
        mainContainer.add(createGameScreen(), "GameScreen");

        // Add Victory Screen with callbacks
        mainContainer.add(new VictoryScreen(
                () -> { // On Play Again
                    generateNewGame(currentDifficulty);
                    cardLayout.show(mainContainer, "GameScreen");
                },
                () -> cardLayout.show(mainContainer, "StartScreen") // On Go to Menu
        ), "WinScreen");

        add(mainContainer);

        // Show login screen first
        cardLayout.show(mainContainer, "LoginScreen");

        setVisible(true);
    }

    /**
     * Creates the login screen where the user enters credentials.
     * 
     * @return The login panel.
     */
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(30, 40, 30, 40)));

        JLabel title = new JLabel("BIENVENIDO");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Inicia sesión para jugar");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(250, 40));
        txtUser.setMaximumSize(new Dimension(250, 40));
        txtUser.setBorder(BorderFactory.createTitledBorder("Usuario"));

        JPasswordField txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(250, 40));
        txtPass.setMaximumSize(new Dimension(250, 40));
        txtPass.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        JButton btnLogin = createStyledButton("ENTRAR");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setMaximumSize(new Dimension(250, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos", "Atención",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (userDAO.authenticate(user, pass)) {
                cardLayout.show(mainContainer, "StartScreen");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de Acceso",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(txtUser);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(txtPass);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(btnLogin);

        panel.add(card);
        return panel;
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

        JLabel subtitle = new JLabel("¿Listo para un desafío mental?");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnStart = createStyledButton("INICIAR JUEGO");
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

        JButton btnEasy = createStyledButton("Fácil");
        JButton btnMedium = createStyledButton("Medio");
        JButton btnHard = createStyledButton("Difícil");
        JButton btnValidate = createStyledButton("Validar");
        JButton btnBack = createStyledButton("Volver");

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
                cardLayout.show(mainContainer, "WinScreen");
            } else {
                // In hard mode, lock correct numbers when validating
                if (currentDifficulty.equalsIgnoreCase("hard")) {
                    int[][] board = sudoku.getBoard();
                    int[][] solution = sudoku.getSolutionBoard();
                    boolean foundCorrect = false;

                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (!sudoku.getFixedCells()[i][j] && board[i][j] != 0 && board[i][j] == solution[i][j]) {
                                guiCells[i][j].setForeground(new Color(46, 204, 113)); // Green
                                guiCells[i][j].setEditable(false);
                                guiCells[i][j].setBackground(new Color(232, 248, 245));
                                sudoku.setFixed(i, j, true);
                                foundCorrect = true;
                            }
                        }
                    }

                    if (foundCorrect) {
                        JOptionPane.showMessageDialog(this, "Se han bloqueado los números correctos.", "Validación",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "No hay números correctos nuevos o el tablero tiene errores.", "Validación",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "¡Oh no! Hay errores o el tablero está incompleto.\nReiniciando... ¡Inténtalo de nuevo!",
                            "Validación Fallida", JOptionPane.ERROR_MESSAGE);
                    generateNewGame(currentDifficulty);
                }
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

        String trimmed = (text == null) ? "" : text.trim();
        if (trimmed.isEmpty()) {
            sudoku.placeNumber(row, col, 0);
            guiCells[row][col].setForeground(Color.BLACK);
            return;
        }

        try {
            int value = Integer.parseInt(trimmed);

            if (value == 0) {
                sudoku.placeNumber(row, col, 0);
                guiCells[row][col].setText("");
                guiCells[row][col].setForeground(Color.BLACK);
                return;
            }

            if (value < 1 || value > 9) {
                guiCells[row][col].setForeground(Color.RED);
                sudoku.placeNumber(row, col, 0); // Keep motor consistent
                return;
            }

            // Sync with motor logic
            boolean locallyValid = sudoku.isMovementValid(row, col, value);

            // Check against solution for easy/medium
            int solutionValue = sudoku.getSolutionBoard()[row][col];
            boolean isCorrect = (value == solutionValue);

            if (currentDifficulty.equalsIgnoreCase("easy") || currentDifficulty.equalsIgnoreCase("medium")) {
                if (isCorrect) {
                    guiCells[row][col].setText(String.valueOf(value));
                    guiCells[row][col].setForeground(new Color(46, 204, 113)); // Green
                    guiCells[row][col].setEditable(false);
                    guiCells[row][col].setBackground(new Color(232, 248, 245)); // Light green background
                    sudoku.getBoard()[row][col] = value;
                    sudoku.setFixed(row, col, true); // Lock it in the logic too
                } else {
                    sudoku.getBoard()[row][col] = value;
                    if (!locallyValid) {
                        guiCells[row][col].setForeground(Color.RED);
                    } else {
                        guiCells[row][col].setForeground(new Color(9, 132, 227));
                    }
                }
            } else {
                // Hard mode: just update board and show as normal input
                sudoku.getBoard()[row][col] = value;
                if (!locallyValid) {
                    guiCells[row][col].setForeground(Color.RED);
                } else {
                    guiCells[row][col].setForeground(new Color(9, 132, 227));
                }
            }

            // Automatic win check after user input
            if (sudoku.isSolved()) {
                cardLayout.show(mainContainer, "WinScreen");
            }

        } catch (NumberFormatException e) {
            guiCells[row][col].setForeground(Color.RED);
            sudoku.placeNumber(row, col, 0);
        }
    }
}
