package DBHandler;

import Server.ServerOutput;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles SQL queries against the database
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class DBServices implements Services
{
    private Dao<Music, String> musicDao;
    private Dao<Score, String> scoreDao;
    private MySQLProvider mySQLProvider;

    /**
     * Constructor for DBSercives
     */
    public DBServices()
    {
        mySQLProvider = new MySQLProvider();
    }

    /**
     * Uses a query to ask for all data in the music table
     * @return List&lt;Music&gt;  list including all the music data from table
     */
    public List<Music> getMusic() throws SQLException
    {
        ConnectionSource connectionSource = mySQLProvider.getConnection();
        musicDao = DaoManager.createDao(connectionSource, Music.class);

        List<Music> music = null;
        mySQLProvider = new MySQLProvider();

        try {
            music = musicDao.queryForAll();
        } catch (SQLException e) {
            ServerOutput.printError("Failed to get musicdata. Check connection with database, and check config file");
        } finally{
            closeConnection(connectionSource);
            return music;
        }
    }

    /**
     * Writes name and score to database. Id will autoincrement (MySQL fixes this)
     * @param name          name of the user who owns the highscore
     * @param userScore     the score the user achieved
     */
    public void writeScore(String name, int userScore) throws SQLException
    {
        ConnectionSource connectionSource = mySQLProvider.getConnection();
        scoreDao = DaoManager.createDao(connectionSource, Score.class);

        Score score = new Score(name, userScore);

        try {
            scoreDao.createIfNotExists(score);
            ServerOutput.printInfo("Score written successfully");
        } catch (SQLException e) {
            ServerOutput.printError("Failed to upload score");
        } finally{
            closeConnection(connectionSource);
        }
    }

    /**
     * Closing the connection
     * @param connectionSource      ConnectionSource to close
     */
    public void closeConnection(ConnectionSource connectionSource)
    {
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}