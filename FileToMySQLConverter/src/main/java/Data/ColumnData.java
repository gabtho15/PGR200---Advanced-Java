package Data;

import java.util.ArrayList;

/**
 * Holds data for one column. The columnValue.size() defines how many columns there is.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ColumnData
{
    private ArrayList<String> columnValues;

    /**
     * Constructor
     */
    public ColumnData()
    {
        columnValues = new ArrayList<String>();
    }

    /**
     * Gets a given value from the array
     * @param index     Index to object to pick out of array
     * @return String   String to be returned
     */
    public String getValue(int index)
    {
        return columnValues.get(index);
    }

    /**
     * Adding new data to the array.
     * @param columnValue   Data to add
     */
    public void addData(String columnValue)
    {
        this.columnValues.add(columnValue);
    }

    /**
     * Gets the total number of columns
     * @return int      Returns the columnValues size
     */
    public int getNumberOfColumns()
    {
        return columnValues.size();
    }
}
