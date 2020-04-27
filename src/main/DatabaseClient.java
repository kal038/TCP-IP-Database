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


/**
 * Client for the Database, with skeleton code from Oracle Socket Tutorial
 * @link : https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 */
public class DatabaseClient {
    // initialize socket, output stream and input stream
    private Socket socket ;
    private Scanner scanner;
    private InputStreamReader in;
    private BufferedReader reader;
    private PrintWriter writer;


    public DatabaseClient(String hostName, int portNumber) throws IOException, InterruptedException {
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            //socket.setSoTimeout(2000);
            System.out.println("Connection Established with Server on Port " + portNumber);

            while (!socket.isClosed()) {
                do {
                    //System.out.println("pls don't");
                    fromServer = reader.readLine();
                    if (fromServer.equals("END.")) {break;}
                    System.out.println(fromServer);

                } while (fromServer != null);
//                fromServer = reader.readLine();
//                System.out.println(fromServer);
//                    if (fromServer.equalsIgnoreCase("MENU:")) {
//                        System.out.println(options.getMenu());
//                    } else if (fromServer.equalsIgnoreCase("QUERY")) {
//                        String instructions = " Choose criteria for query in this specific format, the maximum number for NUMRESULTS is 100\n" +
//                                "[NUMRESULTS], [CHARACTERISTICS] " +
//                                "in which [CHARACTERISTICS] is [FIELD:FIELDRESULTS]\n" +
//                                "Example: [MaritalStatus:Married] or [Nationality:Italian]. ";
//                        System.out.println(instructions);
//                    }

               // System.out.println("Choose Option: ");
                fromUser = inConsole.readLine();
                if (fromUser != null) {
                    if (fromUser.equals("0")) {
                        System.out.println("Closing Connection... Goodbye!");
                        //break;
                        socket.close();
                        writer.close();
                        reader.close();
                    }
                    //System.out.println("Client: " + fromUser);
                    writer.println(fromUser);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
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

    //depreciated stuff hidden here (shhhhh!)
//        try {
//            initializeStreams(hostName,portNumber);

//            new Thread(() -> {
//                try {
//                    while (true) {
//                        if(reader.readLine() == null) {
//                            System.out.println("Go to hell");
//                            break;
//                        }
//                        String text = reader.readLine();
//                        System.out.println(text);
//                    }
//
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }).start();

//            new Thread(() -> {
//                try {
//                    while (!socket.isClosed()) {
//                        if(reader.readLine() == null) {
//                            System.out.println("Loss server");
//                            break;}
//                        String text = scanner.nextLine();
//                        writer.println(text);
//                        writer.flush();
//                        //Thread.sleep(1000);
//                        if (Integer.parseInt(text) == 0) {
//                            // exit case
//                            closeConnection();
//                        }
//                   }
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }).start();
//
//            while (!socket.isClosed()) {
//
//                Thread.sleep(1000);
//            }
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }


}