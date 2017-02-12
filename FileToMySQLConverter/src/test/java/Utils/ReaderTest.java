package Utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests reader class
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ReaderTest
{
    @Test
    public void testIfReaderReturnsNullIfNoFileFound()
    {
        // Arrange
        ArrayList<String> listThatIsRead;

        // Act
        listThatIsRead = Reader.readFile("thisFileDoesNotExistsHopefullyUnlessSensorAddsTheFile.&%&%&");

        // Assert
        Assert.assertEquals(null, listThatIsRead);
    }

    /**
     * Sadly this test breaks unit test princip, since this test demands the file to present to work
     */
    @Test
    public void testIfReaderReturnsDataIfFileIsFound()
    {
        // Arrange
        ArrayList<String> listThatIsRead;

        // Act
        listThatIsRead = Reader.readFile("src/main/resources/1column_3rows.txt");

        // Assert
        Assert.assertEquals(3, listThatIsRead.size());
    }
}
