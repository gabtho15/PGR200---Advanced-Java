package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class responsible to read a given file. And put the result into an ArrayList, each input for each line.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Reader
{
    private static ArrayList<String> fileInfo;
    private static Scanner scanner;

    /**
     * Empty private constructor to prevent instances to be created.
     */
    private Reader()
    { }

    /**
     * Reads a given file and put data in an ArrayList
     * @param filename                      File to read/scan
     * @return ArrayList&lt;String&gt;      List of lines
     */
    public static ArrayList<String> readFile(String filename)
    {
        fileInfo = new ArrayList();

        try {
            scanner = new Scanner(new File(filename));
            scanner.useDelimiter("\n");

            while(scanner.hasNextLine())
            {
                fileInfo.add(scanner.nextLine());
            }
            return fileInfo;
        } catch (FileNotFoundException e) {
            Printer.printError("Failed to find file on given path");
            return null;
        } finally{
            close(scanner);
        }
    }

    /**
     * Close the scanner
     * @param scanner   Scanner to be closed
     */
    private static void close(Scanner scanner)
    {
        if (scanner != null)
            scanner.close();
    }
}