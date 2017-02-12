package Utils;

import Data.TableData;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests file to object parser.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class FileToObjectParserTest
{
    @Test
    public void testIfParserParsesAFileWithOnlyMetadata()
    {
        // Arrange
        List<String> listToBeParsed = getCorrectListWithOnlyMetadata();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(listToBeParsed);

        // Assert
        Assert.assertEquals(3, tableData.getNumberOfColumns());
        Assert.assertEquals("Alder", tableData.getName(2));
    }

    @Test
    public void testIfParserParsesAFileWithOnlyOneColumn()
    {
        // Arrange
        List<String> listToBeParsed = getCorrectListWithOnlyOneColumn();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(listToBeParsed);

        // Assert
        Assert.assertEquals(1, tableData.getNumberOfColumns());
        Assert.assertEquals("Dolly Duck", tableData.getData(1).getValue(0));
    }

    @Test
    public void testIfParserParsesAFileWithColumns()
    {
        // Arrange
        List<String> listToBeParsed = getCorrectListWithColumnData();
        TableData tableData = null;

        // Act
        tableData = FileToObjectParser.generateTableData(listToBeParsed);

        // Assert
        Assert.assertEquals(2, tableData.getNumberOfRows());
        Assert.assertEquals("Dolly Duck", tableData.getData(1).getValue(0));
    }

    @Test
    public void testIfParserReturnsNullIfEmptyArray()
    {
        // Arrange
        List<String> emptyArray = null;

        // Act
        TableData tableData = FileToObjectParser.generateTableData(emptyArray);

        //Assert
        Assert.assertTrue(null == tableData);
    }

    @Test
    public void testIfParserReturnsNullIfWrongPrecision()
    {
        // Arrange
        List<String> wrongPrecision = getWrongPrecision();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(wrongPrecision);

        // Assert
        Assert.assertTrue(null == tableData);
    }

    @Test
    public void testIfParserReturnsNullIfNotEnoghMeta()
    {
        // Arrange
        List<String> notEnoghMetadata = getNotEnoghMetaData();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(notEnoghMetadata);

        // Assert
        Assert.assertTrue(null == tableData);
    }

    @Test
    public void testIfParserReturnsNullIfUnevenColumnLengths()
    {
        // Arrange
        List<String> unevenColumnLengths = getUnevenColumnLengths();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(unevenColumnLengths);

        // Assert
        Assert.assertTrue(null == tableData);
    }

    @Test
    public void testIfParserReturnsNullIfEmptyFirstLine()
    {
        // Arrange
        List<String> emptyFirstLine = getEmptyFirstLine();

        // Act
        TableData tableData = FileToObjectParser.generateTableData(emptyFirstLine);

        // Assert
        Assert.assertTrue(null == tableData);
    }

    //region Helper methods
    private List<String> getCorrectListWithOnlyMetadata()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("Navn/Adresse/Alder");
        listToBeParsed.add("STRING/STRING/INT");
        listToBeParsed.add("128/128/3");

        return listToBeParsed;
    }

    private List<String> getCorrectListWithOnlyOneColumn()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("Navn");
        listToBeParsed.add("STRING");
        listToBeParsed.add("128");
        listToBeParsed.add("Donald Duck");
        listToBeParsed.add("Dolly Duck");

        return listToBeParsed;
    }

    private List<String> getCorrectListWithColumnData()
    {
        List<String> listToBeParsed = getCorrectListWithOnlyMetadata();
        listToBeParsed.add("Donald Duck/Uflaksveien 13/60");
        listToBeParsed.add("Dolly Duck/Andebygaarden 2/55");

        return listToBeParsed;
    }

    private List<String> getWrongPrecision()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("Navn/Adresse/Alder");
        listToBeParsed.add("STRING/STRING/INT");
        listToBeParsed.add("128/dsadsa/3");

        return listToBeParsed;
    }

    private List<String> getNotEnoghMetaData()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("Navn/Adresse/Alder");
        listToBeParsed.add("STRING/STRING/INT");

        return listToBeParsed;
    }

    private List<String> getUnevenColumnLengths()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("Navn/Adresse/Alder");
        listToBeParsed.add("STRING/STRING/INT");
        listToBeParsed.add("128/128/3");
        listToBeParsed.add("Donald Duck/Uflaksveien 13/60");
        listToBeParsed.add("Dolly Duck/Andebygaarden 2/55/extracolumn1/extracolumn2");

        return listToBeParsed;
    }

    private List<String> getEmptyFirstLine()
    {
        List<String> listToBeParsed = new ArrayList<String>();
        listToBeParsed.add("");
        listToBeParsed.add("STRING/STRING/INT");
        listToBeParsed.add("128/128/3");
        listToBeParsed.add("Dolly Duck/Andebygaarden 2/55");

        return listToBeParsed;
    }
    //endregion
}