package Question_;

import DBHandler.Music;
import java.util.List;
import java.util.Random;

/**
 * Includes only a simple method that generates a list of questions in format List&lt;Question&gt;.
 * Then returns this list.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class QuestionGenerator
{
    static Random random;

    private QuestionGenerator()
    { }

    /**
     * Takes a List&lt;Music&gt; and converts it into a List&lt;Question&gt;
     * @param list              the list of music objects which is sent in to be converted/parsed
     * @return List of Question   the result of the question generation. Includes a list of ready questions
     */
    public static List<Question> generateQuestions(List<Music> list)
    {
        random = new Random();
        String string;

        Questions questions = new Questions();

        for(Music m : list)
        {
            int randomNumber = random.nextInt(4);

            switch (randomNumber)
            {
                case 0:
                    string = String.format("Hvem ga ut albumet %s i %s", m.getAlbum(), m.getYear());
                    questions.add(new Question(string, m.getArtist()));
                    break;
                case 1:
                    string = String.format("Når ble albumet %s av %s gitt ut?", m.getAlbum(), m.getArtist());
                    questions.add(new Question(string, m.getYear()));
                    break;
                case 2:
                    string = String.format("Which country does %s come from?", m.getArtist());  //Thanks to Per's pdf for question suggestion
                    questions.add(new Question(string, m.getCountry()));
                    break;
                case 3:
                    string = String.format("Which artist wrote %s from the album %s ?", m.getSong(), m.getAlbum());  //Thanks to Per's pdf for question suggestion
                    questions.add(new Question(string, m.getArtist()));
                    break;
            }
        }

        return questions.getList();
    }
}
