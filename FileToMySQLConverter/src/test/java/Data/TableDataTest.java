package Data;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests tableData actions
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class TableDataTest
{
    /**
     * I was abit unsure if i really needed to test this, since its barely have any logic.
     * But a small test couldn't hurt was my conclusion.
     * I also don't see the point in testing different scenarious here like trying to put String
     * in int field etc. This because there is no logic here to prevent it. That is in other layers.
     */
    @Test
    public void testIfColumnValueIsReachAbleAfterCreatingATableDataObject()
    {
        // Arrange
        TableData tableDataInTest = new TableData();
        ColumnData columnDataInTest;

        // Act
        // Adding metadata
        tableDataInTest.addName("Name");
        tableDataInTest.addType("VARCHAR");
        tableDataInTest.addSize(55);

        // Adding first column
        columnDataInTest = new ColumnData();
        columnDataInTest.addData("Ole");
        tableDataInTest.addData(columnDataInTest);

        // Adding second column
        columnDataInTest = new ColumnData();
        columnDataInTest.addData("Dole");
        tableDataInTest.addData(columnDataInTest);

        // Adding third column
        columnDataInTest = new ColumnData();
        columnDataInTest.addData("Doffen");
        tableDataInTest.addData(columnDataInTest);

        // Assert
        Assert.assertEquals(1, tableDataInTest.getNumberOfColumns());
        Assert.assertEquals("Doffen", tableDataInTest.getData(2).getValue(0));
        Assert.assertEquals(3, tableDataInTest.getNumberOfRows());
    }
}