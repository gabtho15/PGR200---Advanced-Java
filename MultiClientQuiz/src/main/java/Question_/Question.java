package Question_;

/**
 * Holds a question in string format and an answer as object Answer
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Question
{
    private String question;
    private Answer answer;          // Use of composition (Question has an Answer)

    /**
     * Generates new Question
     * @param question  is used to save the question in string question
     * @param answer    is used to generate a new Answer which is saved as answer
     */
    public Question(String question, String answer)
    {
        this.question = question;
        this.answer = new Answer(answer);
    }

    /**
     * Gets question
     * @return String     the question
     */
    public String getQuestion()
    {
        return question;
    }

    /**
     * Compares the answer of the question with a sent in string.
     * @param answerString  used to compare the answer with this question.answer
     * @return boolean      returns the true/false if the answer is correct
     */
    public boolean compareAnswer(String answerString)
    {
        return answer.compareAnswer(answerString);
    }
}
