package Server;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Class responsible to print different kind of messages to the console. Together with the Jdbc messages, there
 * is three types: debug, error and info.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ServerOutput
{
    /**
     * Empty private constructor to prevent instances to be created.
     */
    private ServerOutput()
    { }

    /**
     * Prepare errormessage printing. Adding extra formatting to message.
     * @param print     string to be printed
     */
    public static void printError(String print)
    {
        printLine(String.format("[ERROR] %s", print));
    }

    /**
     * Prepare infomessage printing. Adding extra formatting to message.
     * @param print     string to be printed
     */
    public static void printInfo(String print)
    {
        printLine(String.format("[INFO] %s", print));
    }

    /**
     * Prints the given message
     * @param print     string to be printed
     */
    private static void printLine(String print)
    {
        Date date = new Date(System.currentTimeMillis());
        System.out.print(new Timestamp(date.getTime()));
        System.out.println(" " + print);
    }
}