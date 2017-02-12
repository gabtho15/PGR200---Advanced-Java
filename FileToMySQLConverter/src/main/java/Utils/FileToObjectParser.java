package Utils;

import Data.ColumnData;
import Data.TableData;

import java.util.Arrays;
import java.util.List;

/**
 * Parser that parses file data to TableData
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class FileToObjectParser
{
    /**
     * Empty private constructor to prevent instances to be created.
     */
    private FileToObjectParser()
    { }

    /**
     * Parses a list into a TableData object
     * @param list              List to be parsed
     * @return TableData        TableData to be returned
     */
    public static TableData generateTableData(List<String> list)
    {
        TableData tableData = new TableData();
        int numberOfColumns = -1;               // Saving this locally for easier access
        ColumnData columnData;

        if(list == null || list.size() < 3)     // If list isn't atleast 3 lines, then there will be impossible to make create table sql
        {
            Printer.printError("File is in corrupt format. Please check and try again");
            return null;
        }

        for(int i = 0; i < list.size(); i++)
        {
            List<String> splitList = splitStrings(list.get(i));
            columnData = new ColumnData();

            for(String splitString : splitList)
            {
                /**
                 * splitList.size() != numberOfColumns:     Checks if this new column (ex: '3/3/12' is same length as first
                 * requires numberOfColumns to not be -1
                 * splitString.length() checks if the word itself has length (ex '3//12', the middle word is 0 length.
                 */
                if((splitList.size() != numberOfColumns && numberOfColumns != -1) || splitString.length() == 0)
                {
                    Printer.printError("File is in corrupt format. Please check and try again");
                    return null;
                }

                if(i == 0)
                {
                    tableData.addName(splitString);
                    numberOfColumns = splitList.size();
                }
                else if(i == 1)
                {
                    tableData.addType(replaceString(splitString));
                }
                else if (i == 2)
                {
                    Integer parsedInteger = tryParseInt(splitString);
                    if(parsedInteger == null)
                    {
                        Printer.printError("Invalid precision number, please check file");
                        return null;        // invalid precision
                    }

                    tableData.addSize(parsedInteger);
                }
                else
                {
                    columnData.addData(splitString);                    // Adding each column to array
                }
            }
            if(columnData.getNumberOfColumns() > 0)
            {
                tableData.addData(columnData);      // Adding each row of columns to array
            }
        }

        Printer.printInfo("Filedata successfully parsed");
        return tableData;
    }

    /**
     * Checks if String contains STRING, if yes replace with VARCHAR string.
     * @param string    String to check
     * @return String   String returned, either original or edited
     */
    private static String replaceString(String string)
    {
        if(string.equals("STRING"))
            return "VARCHAR";

        return string;
    }

    /**
     * Trying to parse a String into int
     * Thanks to: http://stackoverflow.com/questions/1486077/java-good-way-to-encapsulate-integer-parseint
     * @param stringToParse     String to parse
     * @return Integer          Result of the parse
     */
    private static Integer tryParseInt(String stringToParse)
    {
        try {
            return Integer.parseInt(stringToParse);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Splits the given string on '/' symbols
     * @param string            String to split
     * @return List<String>     String splitted into list
     */
    private static List<String> splitStrings(String string)
    {
        return (Arrays.asList(string.split("/")));
    }
}