import DBHandler.DBServices;
import Server.ServerController;

/**
 * Mainclass whos only job is to start the server controller
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Program
{
    /**
     * main mathod that starts a server controller
     * @param args  args not used
     */
    public static void main(String[] args)
    {
        DBServices dbServices = new DBServices();

        ServerController serverController = new ServerController(dbServices);
        serverController.runServer();
    }
}