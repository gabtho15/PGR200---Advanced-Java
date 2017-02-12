package DBHandlerTest;

import DBHandler.Music;
import DBHandler.Services;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * H2 services class. Used in in memory database tests instead of the original DBServices.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class H2Services implements Services
{
    public H2Provider h2Provider;

    public H2Services()
    {
        h2Provider = new H2Provider();
    }

    /**
     * For easier unit testing, only return finished array. Not actually connection to H2 database.
     */
    public List<Music> getMusic() throws SQLException
    {
        ArrayList<Music> listOfMusic = new ArrayList();

        listOfMusic.add(new Music(1, "1", "1", "1", "1", "1"));
        listOfMusic.add(new Music(2, "2", "2", "2", "2", "2"));
        listOfMusic.add(new Music(3, "3", "3", "3", "3", "3"));

        return listOfMusic;
    }

    public void writeScore(String name, int userScore)
    {

        Statement statement = null;
        try {
            statement = h2Provider.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insertString = String.format("INSERT INTO score VALUES (99, '%s', %d);", name, userScore);
        System.out.println("insertString = " + insertString);
        try {
            statement.executeUpdate(insertString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
