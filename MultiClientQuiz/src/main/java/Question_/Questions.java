package Question_;

import java.util.ArrayList;

/**
 * Is an ArrayList which holds questions. Plan was to give this more features, but time didn't allow me.
 * So in theory this class is wasted as it, it doesnt give any more features than a single ArrayList&lt;Question&gt;
 * without own class would give.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Questions extends ArrayList<Question>
{
    /**
     * Empty constructor
     */
    public Questions()
    { }

    /**
     * Method that returns the list
     * @return ArrayList&lt;Question&gt;                returns this object
     */
    public ArrayList<Question> getList()
    {
        return this;
    }
}
