/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 4/23/20
 * Time: 9:50 PM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: DatabaseClient
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DatabaseClient {
    // initialize socket, output stream and input stream
    private static OptionMenuUtil options = new OptionMenuUtil();
    private Socket socket ;
    private Scanner inConsole = new Scanner(System.in);
    private InputStreamReader in;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isTurn;

    public DatabaseClient(String hostName, int portNumber) throws IOException {
        // Initialize Client sockets and DataStreams
        initializeStreams(hostName,portNumber);
        // Print the menu of choices
        while (!socket.isClosed()) {
            int action = options.printMenuClient();

            if (action == 0) {
                // exit case
                closeConnection();
            } else {
                // the user did not choose to exit
                //TODO: Proceed to send user choice to server
                writer.println(action);
                writer.flush();
            }
            // simple action


        }
    }

    private void initializeStreams(String hostName, int portNumber) throws IOException {
        try {
            socket = new Socket(hostName, portNumber);
            System.out.println("Connection Established on port "+portNumber);
            //setup input from server (socket in)
            in = new InputStreamReader(socket.getInputStream());
            // wrap that input inside a reader
            reader = new BufferedReader(in);
            // setup output to server (socket out)
            writer = new PrintWriter(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (EOFException i) {
            System.out.println(i);
        }
    }

    private String sendString(String lineOut) {
        lineOut = inConsole.nextLine();
        writer.println(lineOut);

        return lineOut;
    }

    private void closeConnection() throws IOException {
        try {
            in.close();
            writer.close();
            socket.close();
        } catch (EOFException i) {
            i.printStackTrace();
            System.out.println(i);
        }
    }


}