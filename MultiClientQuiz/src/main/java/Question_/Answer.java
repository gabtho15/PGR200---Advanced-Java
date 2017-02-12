package Question_;

/**
 * Holds an answer in string format. Also includes simple compareTo method to check if
 * answer is correct.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Answer
{
    private String answer;

    /**
     * Creates a new Answer.
     * @param answer    Sets this.answer to the sent in parameter answer
     */
    public Answer(String answer)
    {
        this.answer = answer;
    }

    /**
     * This method takes in a string to compare with the set answer.
     * The check if case-insencitive
     * @param answerString  this is the string to be compared with this.answer
     * @return boolean      returns the result of equalcheck between this.answer and answerString
     */
    public boolean compareAnswer(String answerString)
    {
        return(answer.equalsIgnoreCase(answerString));        // Would be really interesting to use some sort of compare strings algorithms, to be nicer on correct answer.
    }
}