package com.example;

import javax.swing.*;
import java.awt.*;

/**
 * VictoryScreen is a specialized panel that displays a congratulatory message
 * and action buttons when a user successfully completes a Sudoku puzzle.
 */
public class VictoryScreen extends JPanel {

    private final Color PRIMARY_COLOR = new Color(108, 92, 231); // Soft Purple
    private final Color BG_COLOR = new Color(241, 242, 246);    // Very Light Gray
    private final Color BUTTON_HOVER = new Color(162, 155, 254);

    /**
     * Constructs the victory screen with callbacks for user actions.
     * 
     * @param onPlayAgain Callback to execute when the "Play Again" button is clicked.
     * @param onGoToMenu  Callback to execute when the "Back to Menu" button is clicked.
     */
    public VictoryScreen(Runnable onPlayAgain, Runnable onGoToMenu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BG_COLOR);

        // Add some vertical spacing at the top
        add(Box.createVerticalGlue());

        // Victory Icon (Emoji)
        JLabel icon = new JLabel("🏆");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel("¡VICTORIA!");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 64));
        title.setForeground(PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("¡Has completado el Sudoku con éxito!");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton btnPlayAgain = createStyledButton("JUGAR DE NUEVO");
        btnPlayAgain.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnPlayAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPlayAgain.addActionListener(e -> onPlayAgain.run());

        JButton btnMenu = createStyledButton("VOLVER AL MENÚ");
        btnMenu.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnMenu.setBackground(Color.GRAY);
        btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMenu.addActionListener(e -> onGoToMenu.run());

        // Add components to the panel with spacing
        add(icon);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(subtitle);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(btnPlayAgain);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnMenu);
        
        // Add vertical spacing at the bottom
        add(Box.createVerticalGlue());
    }

    /**
     * Helper method to create styled buttons consistent with the app's aesthetic.
     * 
     * @param text The button label.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != Color.GRAY) {
                    btn.setBackground(BUTTON_HOVER);
                } else {
                    btn.setBackground(Color.DARK_GRAY);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != Color.DARK_GRAY) {
                    btn.setBackground(PRIMARY_COLOR);
                } else {
                    btn.setBackground(Color.GRAY);
                }
            }
        });

        return btn;
    }
}
