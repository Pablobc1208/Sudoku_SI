package com.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuConsoleGameTest {

    @Test
    public void testStartExitImmediately() {
        // Mock user input: "easy" for difficulty, then "-1" to exit immediately
        String simulatedInput = "easy\n-1\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            SudokuConsoleGame game = new SudokuConsoleGame();
            
            // This should return cleanly without infinite loop
            assertDoesNotThrow(() -> game.start(), "Game should exit cleanly when -1 is entered");
        } finally {
            System.setIn(originalIn);
        }
    }
    
    @Test
    public void testInvalidInputThenExit() {
        // Mock user input: "easy", then an invalid command "invalid", then "-1"
        String simulatedInput = "easy\ninvalid\n-1\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            SudokuConsoleGame game = new SudokuConsoleGame();
            
            assertDoesNotThrow(() -> game.start(), "Game should handle invalid input without crashing");
        } finally {
            System.setIn(originalIn);
        }
    }
}
