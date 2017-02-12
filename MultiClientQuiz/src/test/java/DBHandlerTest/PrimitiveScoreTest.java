package DBHandlerTest;

import DBHandler.Score;
import org.junit.Assert;
import org.junit.Test;

/**
 * Including simple Score class tests.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class PrimitiveScoreTest
{
    @Test
    public void testIfICanCreateNewScoreObject() {
        // Arrange
        Score score = new Score("Thomas", 13);

        // Act
        int getScore = score.getScore();
        String getName = score.getName();

        // Assert
        Assert.assertEquals(13, getScore);
        Assert.assertEquals("Thomas", getName);
    }

    @Test
    public void testIfEmptyNewScoreCreatesADefaultScoreObject()
    {
        // Arrange
        Score score = new Score();

        // Act
        int getScore = score.getScore();
        String getName = score.getName();

        // Assert
        Assert.assertEquals(0, getScore);
        Assert.assertEquals("", getName);
    }
}
