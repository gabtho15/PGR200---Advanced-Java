package Server;

import Question_.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles all interaction between serverLogic and client once the client has been accepted by the serverLogic
 * This is a runnable and is ran as threads started by serverLogic.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ClientCommunicator implements Runnable
{
    private int score;
    private String name;
    private boolean quizIsRunning;
    private List<Question> questions;
    private Socket clientSocket;
    private ServerLogic serverLogic;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Constructor for a new ClientCommunicator
     * @param clientSocket      holds the client socket
     * @param questions         list of questions
     * @param serverLogic            the serverLogic itself(used to call saveScore)
     */
    public ClientCommunicator(Socket clientSocket, List<Question> questions, ServerLogic serverLogic)
    {
        score = 0;
        name = null;
        this.questions = questions;
        this.clientSocket = clientSocket;
        this.serverLogic = serverLogic;
    }

    /**
     * Runnable implements the method Run(), which starts the thread
     */
    public void run()
    {
        Scanner in = new Scanner(System.in);
        quizIsRunning = true;

        System.out.println("Client accepted");

        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            output.writeObject("Welcome to Quiz. Type your name before we continue:");

            name = (String) input.readObject();

            output.writeObject(String.format("Hi %s! Starting quiz. Type 'quit' at any time to quit.", name));

            while(serverIsRunning() && quizIsRunning)
            {
                Question question = getRandomQuestion();
                output.writeObject(question.getQuestion());

                handleInput(question);
            }

        } catch (IOException e) {
            ServerOutput.printError("Lost connection between serverLogic and client");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            in.close();
            closeClientConnection();
        }
    }

    /**
     * Handles input from user using readObject. Comparing the answer to the question.answer.
     * @param q                         question the client should answer
     * @throws IOException              gets handled elsewhere
     * @throws ClassNotFoundException   gets handled elsewhere
     */
    private void handleInput(Question q) throws IOException, ClassNotFoundException
    {
        String message = null;

        message = (String) input.readObject();
        if(message.equals("quit"))
        {
            quizIsRunning = false;
            serverLogic.saveScore(name, score);
            output.writeObject(String.format("You scored total %d points!", score));
        }
        else if(q.compareAnswer(message))
        {
            score++;
            output.writeObject("Correct answer!");
        }
        else
            output.writeObject("Wrong answer.");
    }

    /**
     * Returns a random question from the list
     * @return Question     a question
     */
    private Question getRandomQuestion()
    {
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    /**
     * Finds out of the serverLogic is running or not
     * @return boolean  if serverLogic is running or not
     */
    private boolean serverIsRunning()
    {
        return serverLogic.getServerIsRunning();
    }

    /**
     * Close the client connection
     */
    private void closeClientConnection()
    {
        if(output != null)
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if(input != null)
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}