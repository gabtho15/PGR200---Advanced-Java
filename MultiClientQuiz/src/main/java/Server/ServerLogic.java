package Server;

import DBHandler.Music;
import DBHandler.Services;
import Question_.Question;
import Question_.QuestionGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/**
 * Serverlogic is holds the main server jobs. It does server actions, and accepting new client before it sends
 * them away in a new thread.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class ServerLogic implements Runnable
{
    private List<Question> questions;
    private Services services;
    private boolean serverIsRunning;

    private ServerSocket server = null;

    /**
     * Empty constructor
     */
    public ServerLogic()
    {
        this.services = null;
    }

    /**
     * Constructor for ServerLogic
     * @param services  Sets the services
     */
    public ServerLogic(Services services)
    {
        this.services = services;  // Casting service to DB service
    }

    /**
     * Implements run() from Runnable. This method is ran when this thread is started.
     */
    public void run()
    {

        try {
            server = new ServerSocket(993);
            ServerOutput.printInfo("ServerLogic started");

            serverIsRunning = true;
            loadMusic();
            checkIfNumberOfQuestionsFirstTime();
            if(checkIfNumberOfQuestionsFirstTime() == true)     // could drop '== true', but more readable
                listenToClients();      // Start listen to clientconnections

        } catch (IOException e) {
            ServerOutput.printInfo("ServerLogic couldn't be started");
            e.printStackTrace();
        }
    }

    /**
     * Checks if the list question has any data. Also does a countdown for servershutdown.
     * @return
     */
    private boolean checkIfNumberOfQuestionsFirstTime()
    {
        int countdown = 20;

        while(countdown > 0)
        {
            if(questions != null && questions.size() > 0)
                return true;
            else
            {
                ServerOutput.printError(
                        String.format("ServerLogic shutting down in %d seconds", countdown));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    ServerOutput.printError("Failed to do countdown. Shutting down server");
                    stop();
                }
                countdown = countdown - 2;
            }
        }

        return false;
    }

    // Create new question list
    public void loadMusic()
    {
        List<Music> musicList = null;
        try {
            musicList = services.getMusic();

            if(musicList != null && musicList.size() > 0)
            {
                questions = QuestionGenerator.generateQuestions(musicList);
                ServerOutput.printInfo(String.format("Loaded %d questions", questions.size()));
            } else
                ServerOutput.printError("No questions created, check database and try 'load' again.");

        } catch (SQLException e) {
            ServerOutput.printError("Failed to load music");
        }
    }


    /**
     * Listen to new clients. Once new client is accepted, it socket will be sent away in a new thread. And
     * server will continue listen for new clients.
     */
    private void listenToClients()
    {
        while(serverIsRunning)        // Listen to new clients if server is running and there is questions. No questions = no quiz.
        {
            ServerOutput.printInfo("Listen to new clients");
            Socket clientSocket = null;

            //listen to clients
            try {
                clientSocket = server.accept();

                new Thread(
                        new ClientCommunicator(clientSocket, questions, this)).start(); // Each client is given a copy of list of questions

                ServerOutput.printInfo("New client joined quiz");

            } catch (IOException e) {
                ServerOutput.printError("Stopped listening for clients");
            }
        }
        System.out.println("ops");
    }

    /**
     * Shut down server: This will cause serverthread/clientthreads to stop and after cause ServerController thread to stop = program quits.
     */
     public void stop()
    {
        try {
            server.close();
            ServerOutput.printInfo("ServerLogic closed");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverIsRunning = false;
        }
    }

    /**
     * Save a given score
     * @param name      Name that holds the score
     * @param score     The score achieved
     */
    public void saveScore(String name, int score)
    {
        try {
            services.writeScore(name, score);
            ServerOutput.printInfo(String.format("Saving score: %s : %d", name, score));
        } catch (SQLException e) {
            ServerOutput.printError("Failed to save a clients score");
        }
    }

    /**
     * Get the status if server is running or not
     * @return boolean  True/False if server runs
     */
    public boolean getServerIsRunning()
    {
        return serverIsRunning;
    }

    /**
     * Sets the services to another services. Used for testing
     * @param services  Service to be set as services
     */
    public void setServices(Services services)
    {
        this.services = services;
    }

    /**
     * Gets the current services
     * @return Services     Services to be returned
     */
    public Services getService()
    {
        return services;
    }

    /**
     * Returns the list of questions. Mainly for debugging :(
     * @return List&lt;Question&gt;   a list with questions
     */
    public List<Question> getQuestions()
    {
        return questions;
    }
}