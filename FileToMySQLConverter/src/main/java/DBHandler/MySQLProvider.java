package DBHandler;

import Utils.Printer;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provide connection to a database using information put in MySQL.properties
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class MySQLProvider implements ConnectionProvider
{
    private MysqlDataSource dataSource;
    private final String propertyFileName = "MySQL.properties";

    private String user;
    private String password;
    private String host;
    private String db;

    public MySQLProvider()
    {
        dataSource = new MysqlDataSource();
        InputStream inputStream = null;
        Properties properties = new Properties();

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);

            if (inputStream != null) {
                properties.load(inputStream);

                user = properties.get("user").toString();
                password = properties.get("password").toString();
                host = properties.get("host").toString();
                db = properties.get("db").toString();
            }

        } catch (FileNotFoundException e) {
            Printer.printError("Config file not found on given path.");
        } catch (IOException e) {
            Printer.printError("Couldn't read from config. Check it's format.");
        } finally {
            closeInputStream(inputStream);
        }

        dataSource.setDatabaseName(db);
        dataSource.setServerName(host);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }

    /**
     * Method that creates a new connection with help from the dataSource object
     * @return Connection   connection to be returned
     */
    public Connection getConnection()
    {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            Printer.printError("Something wen't wrong with connection to database. Check config and database.");
        } finally {
            return connection;
        }
    }

    /**
     * Safe closing the inputStream
     * @param inputStream   inputStream to be closed
     */
    private void closeInputStream(InputStream inputStream)
    {
        if(inputStream != null)
        {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}