package Data;

import java.util.ArrayList;

/**
 * Holds data for entire table. Mainly stored in three ArrayList's
 *  'columnNames'    - Holds all the column names
 *  'columnTypes'    - Holds all the types like VARCHAR, INT
 *  'columnSizes'    - Also called DisplaySize by java.utils.mysql, holds the size of the column like (50) in VARCHAR(50)
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class TableData
{
    private ArrayList<String> columnNames;
    private ArrayList<String> columnTypes;
    private ArrayList<Integer> columnSizes;
    private ArrayList<ColumnData> columnData;


    /**
     * Constructor
     */
    public TableData()
    {
        columnNames = new ArrayList<String>();
        columnTypes = new ArrayList<String>();
        columnSizes = new ArrayList<Integer>();
        columnData = new ArrayList<ColumnData>();
    }

    //region Getters
    /**
     * Get name
     * @param index     Get name on given index
     * @return String   To be returned
     */
    public String getName(int index)
    {
        return columnNames.get(index);
    }

    /**
     * Get type
     * @param index     Get type on given index
     * @return String   The type
     */
    public String getType(int index)
    {
        return columnTypes.get(index);
    }

    /**
     * Get size
     * @param index     Get size on given index
     * @return Integer  The size
     */
    public Integer getSize(int index)
    {
        return columnSizes.get(index);
    }

    /**
     * Get ColumnData object
     * @param index         Get ColumnData on given index
     * @return ColumnData   The columnData
     */
    public ColumnData getData(int index)
    {
        return columnData.get(index);
    }

    /**
     * Gives back the total number of columns, defined by the first arrayList of names
     * @return int      The number of columns
     */
    public int getNumberOfColumns()
    {
        return columnNames.size();
    }

    /**
     * Gives back the total number of rows, defined by size of ColumnData list
     * @return int      The number of rows
     */
    public int getNumberOfRows()
    {
        return columnData.size();
    }
    //endregion

    //region Setters and "Adders"

    /**
     * Adds a new name to the list columnNames
     * @param columnName    Name to add
     */
    public void addName(String columnName)
    {
        this.columnNames.add(columnName);
    }

    /**
     * Adds a new type to the list columnTypes
     * @param columnType    Type to add
     */
    public void addType(String columnType)
    {
        this.columnTypes.add(columnType);
    }

    /**
     * Adds a new size to the list columnSizes
     * @param columnSize    Size to add
     */
    public void addSize(Integer columnSize)
    {
        this.columnSizes.add(columnSize);
    }

    /**
     * Adds a new columnData object to the list columnData
     * @param columnData    ColumnData object to added to list
     */
    public void addData(ColumnData columnData)
    {
        this.columnData.add(columnData);
    }
    //endregion

}
