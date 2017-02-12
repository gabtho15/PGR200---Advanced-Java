package QuestionTest;

import DBHandler.Music;
import Question_.Question;
import Question_.QuestionGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test different actions in Question packaage, such as generate question, compare answer and create new questions
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class QuestionTest
{
    @Test
    public void testAnswerCorrect()
    {
        // Arrange
        Question questionWithSingleWordAnswer = new Question("Question", "Answer");
        Question questionWithSeveralWordsAnswer = new Question("Question", "Answer von Answer");
        Question questionSingleWordWrongCaseAnswer = new Question ("Question", "Answer");
        Question questionNullValueAnswer = new Question("Question", "Answer");
        Question questionWithCorrectStartAnswer = new Question("Question", "Answer");

        // Act
        Boolean questionWithSingleWordAnswerBool = questionWithSingleWordAnswer.compareAnswer("Answer");
        Boolean questionWithSeveralWordsAnswerBool = questionWithSeveralWordsAnswer.compareAnswer("Answer von Answer");
        Boolean questionSingleWordWrongCaseAnswerBool = questionSingleWordWrongCaseAnswer.compareAnswer("answer");
        Boolean questionNullValueAnswerBool = questionNullValueAnswer.compareAnswer(null);
        Boolean questionWithCorrectStartAnswerBool = questionWithCorrectStartAnswer.compareAnswer("Answerfilltexthere");

        // Assert
        assertTrue("Giving correct-case answer must be true", questionWithSingleWordAnswerBool);
        assertTrue("Giving correct-case answer with several words must be true", questionWithSeveralWordsAnswerBool);
        assertTrue("Giving correct but wrong-case answer with single word must be true", questionSingleWordWrongCaseAnswerBool);
        assertFalse("Giving null value must to a non-null value answer must be false", questionNullValueAnswerBool);
        assertFalse("Giving correct start answer, but total answer wrong must be false", questionWithCorrectStartAnswerBool);
    }

    @Test
    public void testNumbersOfQuestionGeneratedByTheQuestionGenerator()
    {
        // Arrange
        List<Question> questionLists = new ArrayList<Question>();

        ArrayList<Music> rawMusicListWithZeroObjects = new ArrayList<Music>();
        ArrayList<Music> rawMusicListWithFourObjects = new ArrayList<Music>();
        rawMusicListWithFourObjects.add(new Music(1, "1", "1", "1", "1", "1"));
        rawMusicListWithFourObjects.add(new Music(1, "2", "2", "2", "2", "2"));
        rawMusicListWithFourObjects.add(new Music(1, "3", "3", "3", "3", "3"));
        rawMusicListWithFourObjects.add(new Music(1, "3", "3", "3", "3", "3"));

        // Act
        int rawMusicListWithFourObjectsBool = QuestionGenerator.generateQuestions(rawMusicListWithFourObjects).size();
        int rawMusicListWithZeroObjectsBool = QuestionGenerator.generateQuestions(rawMusicListWithZeroObjects).size();

        // Assert
        assertEquals("When list of four raw music objects is sent, method must return list of four questions", 4, rawMusicListWithFourObjectsBool);
        assertEquals("When list of zero raw music objects is sent, method must return list of zero questions", 0, rawMusicListWithZeroObjectsBool);
    }

    @Test
    public void testGetterInQuestion()      // probally abit waste to test a getter, but it fixes the coverage!
    {
        // Arrange
        Question questionToBeTested = new Question("question", "answer");

        // Act
        String questionTextFromQuestion = questionToBeTested.getQuestion();

        // Arrange
        Assert.assertEquals("question", questionTextFromQuestion);
    }
}



