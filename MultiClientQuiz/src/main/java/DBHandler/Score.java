package DBHandler;

import com.j256.ormlite.field.DatabaseField;

/**
 * Score object which holds table information from the Score table
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Score
{
    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String SCORE_FIELD = "score";

    @DatabaseField(columnName = ID_FIELD, generatedId = true)
    private int id;

    @DatabaseField(columnName = NAME_FIELD, canBeNull = false)
    private String name;

    @DatabaseField(columnName = SCORE_FIELD, canBeNull = false)
    private int score;

    /**
     * Empty constructor
     */
    public Score()
    {
        this("", 0);
    }

    /**
     * Construtor for Score
     * @param name      defines the name of the user in the score MySQL table
     * @param score     defines the highscore of the user in the score MySQL table
     */
    public Score(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the id
     * @return int  contains id of the table column
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the name
     * @return String  contains name of the table column
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the score
     * @return int  contains score of the table column
     */
    public int getScore()
    {
        return score;
    }
}
