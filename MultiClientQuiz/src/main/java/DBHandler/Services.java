package DBHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for services class, all has to implement following methods.
 */
public interface Services
{
    /**
     * Method that is giving you a list of Music objects
     * @return List&lt;Music&gt;      List of raw music data
     * @throws SQLException     SQLExceptions should be thrown and not catched
     */
    List<Music> getMusic() throws SQLException;

    /**
     * Writes a given score to the database
     * @param name              Name of the scoreholder in String format
     * @param userScore         Score the person achieved in int format
     * @throws SQLException     SQLExceptions should be thrown and not catched
     */
    void writeScore(String name, int userScore) throws SQLException;
}
