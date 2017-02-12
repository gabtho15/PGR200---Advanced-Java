import DBHandler.DBServices;
import Utils.Printer;

/**
 * Inizitate the program. Also includes definition for tableName and Filename.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Program
{
    public static void main(String[] args)
    {
        String tableName = "Andeby";
        String fileName = "src/main/resources/normalFile.txt";
        //String fileName = "src/main/resources/hugeFile.txt";
        //String fileName = "src/main/resources/weirdFile.txt";
        //String fileName = "src/main/resources/metaOnly.txt";

        DBServices services = new DBServices();

        services.copyFile(fileName, tableName);
        Printer.printDatabase(services.getTable(tableName));
    }
}
