package DBHandler;

import Data.TableData;
import Utils.DatabaseToObjectParser;
import Utils.FileToObjectParser;
import Utils.Printer;
import Utils.Reader;

import java.sql.*;
import java.util.ArrayList;

/**
 * DBService class execute different actions towards the database.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class DBServices
{
    private ConnectionProvider connectionProvider;
    private Connection connection;
    private int numberOfColumns;

    /**
     * Constructor
     */
    public DBServices()
    {
        connectionProvider = new MySQLProvider();
    }

    /**
     * Copy file from a given file to database to a given tableName
     * @param fileName      File to read
     * @param tableName     Tablename to save to
     */
    public void copyFile(String fileName, String tableName)
    {
        TableData tableData;
        ArrayList<String> rawFileData;

        rawFileData = Reader.readFile(fileName);
        if(rawFileData == null)
        {
            return; // Stopping process if no data from file
        }

        tableData = FileToObjectParser.generateTableData((rawFileData));
        if(tableData == null)
        {
            return; // Stopping process if no data from parser (corrupt data)
        }

        numberOfColumns = tableData.getNumberOfColumns();

        writeToDatabase(tableName, tableData);
    }

    /**
     * Execute different writing commands toward the database
     * @param tableName     Tablename to save to
     * @param tableData     Data to be writed
     */
    private void writeToDatabase(String tableName, TableData tableData)
    {
        try {
            // Drop table
            dropTable(tableName);

            // Generate table
            generateCreateTableStatement(tableName, tableData);

            // Insert data
            generateInsertIntoTableStatement(tableName, tableData);

        } catch (NullPointerException e) {
            Printer.printError("Tablename is most likely pointing to a non-existence table. Or you have no connection to DB");
        }
    }

    /**
     * Drops the given table.
     * @param tableName     Table to drop
     */
    private void dropTable(String tableName)
    {
        String dropSql = String.format("DROP TABLE IF EXISTS %s", tableName);
        Statement statement = null;

        try {
            connection = connectionProvider.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(dropSql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    /**
     * Generates a 'Create table' String.
     * @param tableName     Tablename to save to
     * @param tableData     Data to be writed
     * @return String       The built Create Table sql sentence
     */
    private String generateCreateString(String tableName, TableData tableData)
    {
        StringBuilder strBuilder = new StringBuilder("CREATE TABLE " + tableName + " (");

        for(int i = 0; i < numberOfColumns; i++)
        {
            strBuilder.append(tableData.getName(i) + " " + tableData.getType(i) + "(" + tableData.getSize(i) + ")");

            if(i != numberOfColumns - 1)      // adding ',' if not last line
                strBuilder.append(",");
        }

        strBuilder.append(");");

        return strBuilder.toString();
    }

    /**
     * Create a given table in the database-
     * @param tableName     Tablename to save to
     * @param tableData     Data to be writed
     */
    // https://www.mkyong.com/jdbc/jdbc-preparestatement-example-create-a-table/
    private void generateCreateTableStatement(String tableName, TableData tableData)
    {
        PreparedStatement preparedStatement = null;
        String queryStatement = generateCreateString(tableName, tableData);

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(queryStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Printer.printError("Failed to create new table");
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection();
        }
    }

    /**
     * Generates a 'Insert into' String.
     * @param tableName     Tablename to save to
     * @return String       The built Create Table sql sentence
     */
    private String generateInsertString(String tableName)
    {
        StringBuilder strBuilder = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for(int i = 0; i < numberOfColumns; i++)
        {
            if(i == numberOfColumns-1)
                strBuilder.append("?);");
            else
                strBuilder.append("?, ");
        }

        return strBuilder.toString();
    }

    /**
     * Feed data from the given tablaData into prepared statement who feed it into the database
     * @param tableName     Tablename to save to
     * @param tableData     Data to be feed
     */
    private void generateInsertIntoTableStatement(String tableName, TableData tableData)
    {
        PreparedStatement preparedStatement = null;
        String queryStatement = generateInsertString(tableName);

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(queryStatement);

            for(int i = 0; i < tableData.getNumberOfRows(); i++)      // Iterates each row
            {
                for(int j = 0; j < numberOfColumns; j++)    // Iterates each column
                {
                    String columnData =  tableData.getData(i).getValue(j);

                    if(tableData.getType(j).equals("VARCHAR"))
                        preparedStatement.setString(j+1, columnData);
                    else
                        preparedStatement.setInt(j+1, Integer.parseInt(columnData));
                }

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            Printer.printError("Insert data into database failed");
        } finally{
            closePreparedStatement(preparedStatement);
            closeConnection();
        }
    }

    /**
     * Get table information, sends the resultSet given to the DatabaseToObjectParser.
     * @param tableName     TableName to get data from
     * @return TableData    A TableData object holding all information
     */
    public TableData getTable(String tableName)
    {
        TableData tableData = new TableData();
        Statement statement = null;
        ResultSet resultSet = null;

        try
        {
            connection = connectionProvider.getConnection();
            statement = connection.createStatement();
            resultSet = (statement.executeQuery("SELECT * FROM " + tableName));

            if(resultSet != null)
                tableData = DatabaseToObjectParser.generateTableData(resultSet);
        }
        catch (NullPointerException e) {
            Printer.printError("Sadly no tabledata can be found due no connection is set");
        }
        catch (SQLException e) {
            Printer.printError("Failed to read data from table");
        } finally {
            closeConnection();
            closeStatement(statement);
            closeResultSet(resultSet);
        }

        return tableData;
    }

    /**
     * Sets the connectionProvider. Code from Per's session 10
     * @param connectionProvider    Used to set the current connectionProvider
     */
    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }
    /**
     * Gets the connectionProvider. Code from Per's session 10
     * @return  connectionProvider      ConenctionProvider to be returned
     */
    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    //region Close methods

    /**
     * Safe close connection
     */
    private void closeConnection()
    {
        if(connection != null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                Printer.printError("Failed to close connection");
            }
    }

    /**
     * Safe close a statement
     * @param statement     Statement to be closed
     */
    private void closeStatement(Statement statement)
    {
        if(statement != null)
            try {
                statement.close();
            } catch (SQLException e) {

            }
    }

    /**
     * Safe close a preparedStatement
     * @param preparedStatement     PreparedStatement to be closed
     */
    private void closePreparedStatement(PreparedStatement preparedStatement)
    {
        if(preparedStatement != null)
            try {
                preparedStatement.close();
            } catch (SQLException e) {

            }
    }

    /**
     * Safe close a resultSet
     * @param resultSet     ResultSet to be closed
     */
    private void closeResultSet(ResultSet resultSet)
    {
        if(resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    //endregion

    /**
     * Prints out data from the given resultSet
     * @param resultSet     which resultSet to print data from
     * @deprecated since 09.12.2016
     */
    private static void printTable(ResultSet resultSet)
    {
        try {
            ResultSetMetaData resultMeta = resultSet.getMetaData();
            int columnCount = resultMeta.getColumnCount();

            for (int i = 0; i < columnCount; i++)
                System.out.print(resultMeta.getColumnName(i+1) + "\t");

            while (resultSet.next()) {
                System.out.println("");
                for (int i = 0; i < columnCount; i++)
                    System.out.print(resultSet.getString(i+1) + "\t");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}