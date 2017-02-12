package Utils;

import Data.TableData;


import java.util.ArrayList;

/**
 * Class responsible to print different kind of messages to the console. Together with the Jdbc messages, there
 * is three types: debug, error and info. This code is based on my same code form other delivery. The class called
 * Server.ServerOutput, only with small changes.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Printer
{
    /**
     * Empty private constructor to prevent instances to be created.
     */
    private Printer()
    { }

    /**
     * Prepare errormessage printing. Adding extra formatting to message.
     * @param print     string to be printed
     */
    public static void printError(String print)
    {
        printLine(String.format("[ERROR] %s", print));
    }

    /**
     * Prepare infomessage printing. Adding extra formatting to message.
     * @param print     string to be printed
     */
    public static void printInfo(String print)
    {
        printLine(String.format("[INFO] %s", print));
    }

    /**
     * Takes in a TableData object with database information to be printed.
     * @param tableData     database data to be printed
     */
    public static void printDatabase(TableData tableData)
    {
        if(tableData == null || tableData.getNumberOfColumns() == 0)   // checks if table has data OR/AND metadata
        {
            printError("Table is empty, nothing to print");
            return;
        }

        ArrayList<String> fieldSizes = calculateColumnPrint(tableData);

        Printer.printInfo("Printing tabledata...\n");
        for(int i = -1; i < tableData.getNumberOfRows(); i++)   //iterates rows
        {
            for(int j = 0; j < tableData.getNumberOfColumns(); j++)     // iterates columns
            {
                if(i == -1)     // prints the names
                {
                    System.out.printf(fieldSizes.get(j), tableData.getName(j));
                } else            // prints all datavalues, starting at 0
                {
                    System.out.printf(fieldSizes.get(j), tableData.getData(i).getValue(j));
                }
            }
            System.out.println("");     // new line
        }
    }

    /**
     * Calculates the the size needed for each column to show properly.
     * Thanks to: http://stackoverflow.com/questions/2550123/java-printf-using-variable-field-size
     * @param tableData             To iterate over
     * @return ArrayList<String>    Max columnsizes
     */
    private static ArrayList<String> calculateColumnPrint(TableData tableData)
    {
        ArrayList<String> fieldSizes = new ArrayList();

        for(int i = 0; i < tableData.getNumberOfColumns(); i++)
        {
            fieldSizes.add("%-" + findLongestValueOnEachColumn(tableData, i) + "S");   //Adding placement as current x value + size of column
        }

        return fieldSizes;
    }

    /**
     * Finds the largest value in a given column
     * @param tableData     data to search in
     * @param index         given index defines column number
     * @return int          returns the largest size
     */
    private static int findLongestValueOnEachColumn(TableData tableData, int index)
    {
        int extraSpaces = 2;        // Edit this to decide extra spaces. Should probally be in properties file.
        int longestRealColumnValue = tableData.getName(index).length();     // Setting columnname to initial value

        for(int j = 0; j < tableData.getNumberOfRows(); j++)
        {
            String string = tableData.getData(j).getValue(index);
            if(string != null)
            {
                int columnRealLength = string.length();
                if (columnRealLength > longestRealColumnValue)
                    longestRealColumnValue = columnRealLength;      // column.. column.. column.. i love columns!
            }
        }
        return longestRealColumnValue + extraSpaces;
    }

    /**
     * Prints the given message
     * @param print     string to be printed
     */
    private static void printLine(String print)
    {
        System.out.println(print);
    }
}