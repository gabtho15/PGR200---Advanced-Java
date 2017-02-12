package DBHandler;

import Server.ServerOutput;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connector class who has the job to connect to database
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class MySQLProvider
{
    private final String propertyFileName = "config.properties";

    private String databaseUrl;
    private String user;
    private String password;

    /**
     * Constructor that creates a new instance of MySQLProvider. Reads database connection data
     * from config.properties. This method could be protected, but has to be public to make tests work :(
     */
    public MySQLProvider()
    {
        InputStream inputStream = null;
        Properties properties = new Properties();

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);

            if (inputStream != null) {
                properties.load(inputStream);

                databaseUrl = String.format("jdbc:mysql://%s:%s/%s",
                        properties.get("host"),
                        properties.get("port"),
                        properties.get("schema"));
                user = properties.get("user").toString();
                password = properties.get("password").toString();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeInputStream(inputStream);
        }
    }

    /**
     * Get the current connection to the database. If none, call createConnection to create a new
     * @return ConnectionSource     the connection to database
     */
    public ConnectionSource getConnection()
    {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl, user, password);
            ServerOutput.printInfo("Connection to database created");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connectionSource;
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