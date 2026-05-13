package com.example;

import org.junit.jupiter.api.Test;
import java.awt.GraphicsEnvironment;
import static org.junit.jupiter.api.Assertions.*;

public class VictoryScreenTest {

    @Test
    public void testVictoryScreenInitialization() {
        // Skip in headless environments
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        assertDoesNotThrow(() -> {
            VictoryScreen screen = new VictoryScreen(() -> {}, () -> {});
            assertNotNull(screen, "VictoryScreen should be initialized successfully");
        });
    }
}
