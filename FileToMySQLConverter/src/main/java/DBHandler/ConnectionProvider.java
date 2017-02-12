package DBHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for provider of the connection. Based on Per's solution.
 *
 * @author Per Lauvås
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public interface ConnectionProvider
{
    Connection getConnection() throws SQLException;
}
