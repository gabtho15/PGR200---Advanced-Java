package DBHandlerTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Generates a H2 server and return its connection.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class H2Provider
{
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Unable to find H2 driver");
        }
        return DriverManager.getConnection("jdbc:h2:~/things", "sa", "");
    }
}
