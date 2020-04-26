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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseClient {
    // initialize socket, output stream and input stream
    private static OptionMenuUtil options = new OptionMenuUtil();
    private Socket socket ;
    private Scanner scanner;
    private InputStreamReader in;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isTurnToWrite;

    public DatabaseClient(String hostName, int portNumber) throws IOException, InterruptedException {
//        while (!socket.isClosed()) {
//            int action = options.printMenuClient();
//            if (action == 0) {
//                // exit case
//                closeConnection();
//            } else {
//
//                writer.writeUTF(Integer.toString(action));
//                //Thread.sleep(5000);
//                writer.flush();
//                Thread.sleep(5000);
//                isTurnToWrite = false;
//                }
//            }
        try {
            initializeStreams(hostName,portNumber);

            new Thread(() -> {
                try {
                    while (!socket.isClosed()) {
                        String text = reader.readLine();
                        System.out.println(text);
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }).start();

            new Thread(() -> {
                try {
                    while (!socket.isClosed()) {
                        options.printMenuClient();
                        String text = scanner.nextLine();
                        writer.println(text);
                        writer.flush();
                        //Thread.sleep(1000);
                        if (Integer.parseInt(text) == 0) {
                            // exit case
                            closeConnection();
                        }
                   }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }).start();

            while (!socket.isClosed()) {
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            System.out.println(ex);
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
            // scanner setup
            scanner = new Scanner(System.in);

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (EOFException i) {
            System.out.println(i);
        }
    }



    private void closeConnection() throws IOException {
        try {
            in.close();
            writer.close();
            socket.close();
        } catch (EOFException i) {
            System.out.println(i);
        }
    }


}