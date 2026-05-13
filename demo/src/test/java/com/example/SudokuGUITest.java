package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.GraphicsEnvironment;

public class SudokuGUITest {

    @Test
    public void testGUIInitialization() {
        // Skip test in headless environments (like some CI/CD) to prevent HeadlessException
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        
        assertDoesNotThrow(() -> {
            SudokuGUI gui = new SudokuGUI();
            // We do not call startGUI() to avoid popping up a window during maven tests, 
            // but we ensure the object can be created without exceptions.
            assertNotNull(gui);
        });
    }
}
