package ServerTest;

import DBHandlerTest.H2Services;
import Server.ServerLogic;
import org.h2.tools.Server;
import org.junit.*;

import java.sql.*;

/**
 * Testing some of the ServerLogic actions
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ServerTest
{
    private H2Services h2services;
    private static Server server;
    private ServerLogic serverLogic;

    @BeforeClass
    public static void setUp() throws SQLException
    {
        System.out.println("Starting H2 server");
        server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();
    }

    @AfterClass
    public static void tearDown()
    {
        System.out.println("Stopping H2 server");
        server.stop();
    }

    @Before
    public void setup() throws SQLException
    {
        h2services = new H2Services();
        serverLogic = new ServerLogic();
        serverLogic.setServices(h2services);
    }

    @After
    public void after() throws SQLException
    {
        Statement statement = getConnection().createStatement();
        statement.execute("DROP TABLE IF EXISTS Music");
        statement.execute("DROP TABLE IF EXISTS Score");
    }

    @Test
    public void testIfScoreIsWrittenToDatabase() throws SQLException
    {
        // Arrange
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS score(id INT, name VARCHAR(55), score INT);");

        // Act
        serverLogic.saveScore("Thomas", 10);
        serverLogic.saveScore("Thomas", 15);
        serverLogic.saveScore("Thomas", 20);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM score");

        // Assert
        Assert.assertTrue(resultSet != null);
    }

    @Test
    public void testIfServerIsRunningWhenServerCanFindSongs() throws SQLException
    {
        // Arrange

        // Act
        serverLogic.loadMusic();

        // Assert
        Assert.assertEquals(3, serverLogic.getQuestions().size());  // As seen in H2Services, loadMusic. I added list with 3 music.
    }




    private Connection getConnection() throws SQLException
    {
        return h2services.h2Provider.getConnection();
    }
}
