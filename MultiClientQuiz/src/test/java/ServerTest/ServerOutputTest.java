package ServerTest;

import Server.ServerOutput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests if message using ServerOutput get correct formatting
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ServerOutputTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;

    @Before
    public void setUpStreams()
    {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams()
    {
        System.setOut(oldOut);      // Thanks to 'Luke Usherwood', in the same page as linked above.
    }

    @Test
    public void testIfMessagesWithErrorPrintIsPrintedCorrectly()
    {
        // Arrange
        String messageToBePrinterWithError = ("msg");

        // Act
        ServerOutput.printError(messageToBePrinterWithError);

        // Assert
        // Using contains due its difficult to compare cause printer adds timestamp
        Assert.assertTrue(outContent.toString().trim().contains("[ERROR] msg"));
    }

    @Test
    public void testInfoPrintPrintingCorrectly()
    {
        // Arrange
        String messageToBePrinterWithInfo = ("msg");

        // Act
        ServerOutput.printInfo(messageToBePrinterWithInfo);

        // Assert
        // Using contains due its difficult to compare cause printer adds timestamp
        Assert.assertTrue(outContent.toString().trim().contains("[INFO] msg"));
    }
}
