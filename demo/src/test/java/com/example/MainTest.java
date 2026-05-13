package com.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.awt.GraphicsEnvironment;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {


    
    @Test
    public void testMainGUIOption() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // Skip in headless CI/CD
        }
        
        // Simulate user choosing option 2 (GUI Mode)
        String simulatedInput = "2\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            
            // Should launch GUI on EDT and not block/throw
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }
    
    @Test
    public void testMainInvalidOptionDefaultsToGUI() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // Skip in headless CI/CD
        }
        
        // Simulate user entering invalid option (e.g. "abc")
        String simulatedInput = "abc\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            
            // Should catch exception and default to GUI Mode safely
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }
}
