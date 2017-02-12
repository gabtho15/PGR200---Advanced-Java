package Utils;

import Data.ColumnData;
import Data.TableData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Testing the Printer class. Alot of thanks to
 * http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 * which all my tests in this class is based on.
 *
 * Sadly i didn't found out how to actually test my printf methods, so that code is untested.
 * But again, its pretty clear due its a System.out and console that its working. The two tests
 * testIfFirstColumnIsPrintedWithCorrectSpacing and testIfPrinterPrintsErrorMessageWhenTryingToPrintNullTableData
 * also tests part of that method, but not the core logic sadly.
 * author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class PrinterTest
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
        Printer.printError(messageToBePrinterWithError);

        // Assert
        Assert.assertTrue(outContent.toString().trim().equals("[ERROR] msg"));
    }

    @Test
    public void testInfoPrintPrintingCorrectly()
    {
        // Arrange
        String messageToBePrinterWithInfo = ("msg");

        // Act
        Printer.printInfo(messageToBePrinterWithInfo);

        // Assert
        Assert.assertTrue(outContent.toString().trim().equals("[INFO] msg"));
    }

    @Test
    public void testIfThePrinterPassesTableDataToPrintStage()
    {
        // Arrange
        TableData tableDataToBeTestedWith = getCorrectWorkingTableDataObject();

        // Act
        Printer.printDatabase(tableDataToBeTestedWith);
        String stringThatWasPrinted = outContent.toString();

        // Assert
        Assert.assertTrue(stringThatWasPrinted.startsWith("[INFO] Printing tabledata"));
    }

    @Test
    public void testIfPrinterPrintsErrorMessageWhenTryingToPrintNullTableData()
    {
        // Arrange
        TableData tableDataToBeTestedWith = null;

        // Act
        Printer.printDatabase(tableDataToBeTestedWith);
        String stringThatWasPrinted = outContent.toString();

        // Assert
        Assert.assertTrue(stringThatWasPrinted.startsWith("[ERROR] Table is empty"));
    }

    private TableData getCorrectWorkingTableDataObject()
    {
        TableData tableData = new TableData();

        tableData.addName("Kolonne1");
        tableData.addName("Kolonne2");
        tableData.addName("Kolonne3");

        tableData.addType("VARCHAR");
        tableData.addType("VARCHAR");
        tableData.addType("VARCHAR");

        tableData.addSize(50);
        tableData.addSize(50);
        tableData.addSize(50);

        ColumnData columnData = new ColumnData();
        columnData.addData("thisIs17CharsLong");
        columnData.addData("thisIsActually25CharsLong");
        columnData.addData("whoCares.");

        tableData.addData(columnData);

        return tableData;
    }
}
