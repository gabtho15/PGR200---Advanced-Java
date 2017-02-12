package DBHandlerTest;

import DBHandler.MySQLProvider;
import DBHandler.DBServices;
import DBHandler.Music;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Tests DBHandler actions
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class DBHandlerTest
{
    private ConnectionSource connectionSource;
    private MySQLProvider mySQLProvider;

    @Before
    public void before()
    {
        mySQLProvider = new MySQLProvider();
        connectionSource = null;
    }

    @Test
    public void testIfConnectionWasCreated() throws IOException {
        Assert.assertTrue("Confirming that connection is not created yet", connectionSource == null);

        connectionSource = mySQLProvider.getConnection();
        Assert.assertTrue("Confirming that connection is created and returned", connectionSource != null);

        connectionSource.close();
    }

    @Test
    public void testIfConnectionIsClosed() throws IOException
    {
        ConnectionSource connectionSource = mySQLProvider.getConnection();     //getting the connection, creating new if null
        Assert.assertTrue("Confirming that connection is still active", connectionSource != null);

        connectionSource.close();    //closing connection
        Assert.assertFalse(connectionSource.isOpen("music"));
    }

    @Test      // This test demands data and connection and therefor not a unit test, since it demands entire system
    public void connectionCreatedWhenServicesCreated() throws SQLException {
        // Arrange
        DBServices dbServices = new DBServices();
        List<Music> musicList;

        // Act
        musicList = dbServices.getMusic();

        // Assert
        Assert.assertTrue(musicList.size() > 0);
    }
}
