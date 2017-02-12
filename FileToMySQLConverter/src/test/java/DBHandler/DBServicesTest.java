package DBHandler;

import Data.TableData;
import org.h2.tools.Server;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Tests DBServices.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class DBServicesTest
{
    private DBServices dbServices;
    private static Server server;

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
        dbServices = new DBServices();
        dbServices.setConnectionProvider(new H2ConnectionProvider());
    }

    @After
    public void after() throws SQLException
    {
        Statement statement = getConnection().createStatement();
        statement.execute("DROP TABLE IF EXISTS Andeby");
    }

    @Test
    public void testGetTableWithBothMetadataAndMultipleRows() throws SQLException
    {
        // Arrange
        TableData tableData;
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS andeby(navn VARCHAR(20), alder INT);");
        statement.execute("INSERT INTO ANDEBY VALUES ('Donald Duck', 23)");
        statement.execute("INSERT INTO ANDEBY VALUES ('Dolly Duck', 23)");
        statement.execute("INSERT INTO ANDEBY VALUES ('Onkel Skrue', 50)");

        // Act
        tableData = dbServices.getTable("Andeby");

        // Assert
        //Assert.assertEquals(3, tableData.getNumberOfRows());
        Assert.assertEquals("Onkel Skrue", tableData.getData(2).getValue(0));

        // After
        statement.close();
    }

    @Test
    public void testGetTableWithBothMetadataAndWithoutRows() throws SQLException
    {
        // Arrange
        TableData tableData;
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS andeby(navn VARCHAR(20), alder INT);");

        // Act
        tableData = dbServices.getTable("Andeby");

        // Assert
        //Assert.assertEquals(3, tableData.getNumberOfRows());
        Assert.assertEquals(0, tableData.getNumberOfRows());
        Assert.assertEquals(2, tableData.getNumberOfColumns());

        // After
        statement.close();
    }

    @Test
    public void testGetTableWithNullValues() throws SQLException
    {
        // Arrange
        TableData tableData;
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS andeby(navn VARCHAR(20), alder INT);");
        statement.execute("INSERT INTO ANDEBY VALUES ('null', 23)");
        statement.execute("INSERT INTO ANDEBY VALUES ('Dolly Duck', null)");
        statement.execute("INSERT INTO ANDEBY VALUES ('Onkel Skrue', 50)");

        // Act
        tableData = dbServices.getTable("Andeby");

        // Assert
        Assert.assertEquals("null", tableData.getData(0).getValue(0).toString());
        Assert.assertEquals(null, tableData.getData(1).getValue(1));

        // After
        statement.close();
    }

    @Test
    public void testIfDataWasLoadedFromFileToDatabase() throws SQLException
    {
        // Arrange
        String tableNameToTestWith = "Andeby";
        String fileNameToBeUsedWith = "src/main/resources/normalFile.txt";
        TableData tableData;

        // Act
        dbServices.copyFile(fileNameToBeUsedWith, tableNameToTestWith);
        tableData = dbServices.getTable(tableNameToTestWith);

        // Assert
        Assert.assertEquals(3, tableData.getNumberOfColumns());
    }

    @Test
    public void testIfThereIsNoTableToLoadIfFilepathIsWrong()
    {
        // Arrange
        String tableNameToTestWith = "Andeby";
        String fileNameToBeUsedWith = "src/main/resources/thisIsClearlyAWrongFilePath.txdasdsadsadasdassadt";
        TableData tableData;

        // Act
        dbServices.copyFile(fileNameToBeUsedWith, tableNameToTestWith);
        tableData = dbServices.getTable(tableNameToTestWith);

        // Assert
        Assert.assertEquals(0, tableData.getNumberOfColumns());
    }


    /**
     * Method that returns connection
     * @return Connection       a connection
     * @throws SQLException     throw this out in nowhere
     */
    private Connection getConnection() throws SQLException
    {
        return dbServices.getConnectionProvider().getConnection();
    }
}