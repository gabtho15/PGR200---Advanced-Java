package Client;

/**
 * Main method in Client program.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Program
{
    /**
     * Creates a new client and calls the client.run() method.
     * @param args  takes in program arguments. Not in use.
     */
    public static void main(String args[])
    {
        Client client = new Client();
        client.run();
    }
}
