package sc2002.fcsi.grp3;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Dummy test for the Main class to verify console output.
 */
public class MainTest {

    @Test
    public void testMainPrintsHelloWorld() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call main
        Main.main(new String[]{});

        // Reset System.out
        System.setOut(originalOut);

        // Assert output
        assertEquals("Hello world!", outContent.toString().trim());
    }
}
