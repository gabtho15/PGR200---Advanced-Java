package Utils;

import Data.ColumnData;
import Data.TableData;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * Parser that parse database resultSet to TablaData object
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class DatabaseToObjectParser
{
    /**
     * Empty private constructor to prevent instances to be created.
     */
    private DatabaseToObjectParser()
    { }

    /**
     * Parses a resultSet into a TableData
     * @param resultSet         ResultSet to be parsed
     * @return TableData        TableData to be returned
     * @throws SQLException     Throws away
     */
    public static TableData generateTableData(ResultSet resultSet) throws SQLException
    {
        TableData tableData = new TableData();
        ColumnData columnData;

        ResultSetMetaData resultMetaData = resultSet.getMetaData();
        int columnCount = resultMetaData.getColumnCount();

        for(int i = 0; i < columnCount; i++)
        {
            tableData.addName(resultMetaData.getColumnName(i+1));
            tableData.addType(replaceVarchar(resultMetaData.getColumnTypeName(i+1)));
            tableData.addSize(resultMetaData.getColumnDisplaySize(i+1));
        }

        while (resultSet.next())
        {
            columnData = new ColumnData();
            for (int i = 0; i < columnCount; i++)
            {
                columnData.addData(resultSet.getString(i+1));      // Adding each column to array
            }

            tableData.addData(columnData);  // Adding each row of columns to array
        }

        Printer.printInfo("Database data successfully parsed");
        return tableData;
    }

    /**
     * Checks if String contains VARCHAR, if yes replace with STRING string.
     * @param string    String to check
     * @return String   String returned, either original or edited
     */
    private static String replaceVarchar(String string)
    {
        if(string.equals("VARCHAR"))
            return "STRING";

        return string;
    }
}