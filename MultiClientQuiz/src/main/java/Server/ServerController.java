package Server;

import DBHandler.DBServices;
import java.util.Scanner;

/**
 * Creates a new serverLogic in own thread, and manage input from user (serveradmin)
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ServerController
{
    private ServerLogic serverLogic;
    private Scanner scanner;
    private DBServices dbServices;

    public ServerController(DBServices musicDBServices)
    {
        this.dbServices = musicDBServices;
    }

    /**
     * ServerLogic mainloop. Stars the serverLogic logic thread, and listen for input from user.
     */
    public void runServer()
    {
        scanner = new Scanner(System.in);

        // Starting new thread with serverLogic and keeping controller as own thread (dual thread here)
        new Thread(
                serverLogic = new ServerLogic(dbServices)).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(serverLogic.getServerIsRunning())          // Mainserver loop
        {
            getKey();
        }
    }

    /**
     * Handles input from user, and doing certain actions on given commands
     */
    private void getKey()
    {
        String input = (String) scanner.next();

        if(input.equals("quit"))
        {
            ServerOutput.printInfo("Shutting down serverLogic");
            serverLogic.stop();
        }
        else if(input.equals("load"))
        {
            ServerOutput.printInfo("Reloading music");
            serverLogic.loadMusic();
        }
        else if(input.equals("?"))
            ServerOutput.printInfo("Commands: ?, quit, load");
        else
            ServerOutput.printInfo("Unknown command");
    }
}