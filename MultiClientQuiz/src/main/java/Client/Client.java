package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Handles communication from client to server. Code very much based on Per's code.
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Client
{
    Scanner in;
    ObjectOutputStream output;
    ObjectInputStream input;
    Socket serverSocket;

    /**
     * Gameloop of client. Connects to server, then communicates via ObjectStreams.
     * The gameloop once started includes four mainsteps:
     *  1. Read and print message from server. First time welcome message, after the question
     *  2. Reads input from client and sends to server. First time write name, after the answer
     *  3. Reads and print message from server. First time quiz start message, after feedback on answer
     *  4. Primitive check if server wants to close the connection. This should probally be done more robust.
     */
    public void run()
    {
        try {
            boolean run = true;
            Object message = null;
            in = new Scanner(System.in);
            String s = null;

            System.out.println("Trying to connect...");
            serverSocket = new Socket("127.0.0.1", 993);
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            input = new ObjectInputStream(serverSocket.getInputStream());
            System.out.println("Connected");

            while(run)
            {
                s = (String) input.readObject();        // Ouputs: Welcome message / Question
                System.out.println(s);

                message = in.nextLine();
                output.writeObject(message);            // Ouputs: Write name / Answer

                s = (String) input.readObject();        // Ouputs: Quiz starts / Feedback
                System.out.println(s);

                if(s.startsWith("You scored total"))
                    break;                              // closing client if server stops quizzing you
            }
        } catch (EOFException eofException) {
            System.out.println("ServerLogic closed");
        } catch (IOException ioException) {
            System.out.println("Problems with the connection");
        } catch (ClassNotFoundException e) {
            System.out.println("Ops something bad happend. Please restart program");
        } finally {
            in.close();
            closeClient();
        }
    }

    /**
     * Safe close of streams and socket
     */
    private void closeClient()
    {
        if(output != null)
            try {
                output.close();
            } catch (IOException e) {
                System.out.println("Output already closed");
            }

        if(input != null)
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Input already closed");
            }

        if(serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}