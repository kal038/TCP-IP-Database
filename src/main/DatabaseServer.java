/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 4/23/20
 * Time: 10:02 PM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: DatabaseServer
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DatabaseServer {

    private boolean isTurnToWrite;

    /**
     * Consulted GeeksforGeeks and Baeldung and Oracle
     * Link to GeeksforGeeks: https://www.geeksforgeeks.org/socket-programming-in-java/
     * Link to Baeldung: https://www.baeldung.com/a-guide-to-java-sockets?fbclid=IwAR1OyghWj0F5rnp7E_oPvtDuOplj8jn0lgWN6n5zZOl8uz03lHpOLJJrZ-o
     * Link to Oracle: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
     * @param portNumber
     * @throws IOException
     */
    public DatabaseServer(int portNumber) throws IOException {

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket socket = serverSocket.accept();
                PrintWriter writer =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine, outputLine;

            // Initiate conversation with client
            DatabaseProtocol dbp = new DatabaseProtocol();
            // message sent
            outputLine = dbp.processInput(null);
            writer.println(outputLine);

            while ((inputLine = reader.readLine()) != null) {
                outputLine = dbp.processInput(inputLine);
                writer.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
        }
            System.out.println("The client has dropped, closing server");


            } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }




//        try {
//            ServerSocket server = new ServerSocket(4000);
//
//            Socket client = server.accept();
//
//            PrintWriter writer = new PrintWriter(client.getOutputStream());
//            InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
//            BufferedReader reader = new BufferedReader(streamReader);
//
//            while (!client.isClosed()) {
//
//                String text = reader.readLine();
//                if (text == null) {break;}
//                System.out.println("Recevied: " + text);
//
//            }
//        } catch (Exception ex) {
//            System.err.println(ex);
//        }




//    private void depreciatedProcessing(int portNumber) throws IOException {
//        String text = reader.readLine();
//        if (text == null) {
//            System.out.println("Client Dropped, Awaiting new Client");
//            initializeSocketStreams(portNumber);
//        }
//        if (text != null) {
//            // This is where we process data
//            System.out.println(text);
//            if (text.equalsIgnoreCase("1")) {
//                //System.out.println("query");
//                //writer.println("query results: ");
//                //writer.flush();
//            } else if (text.equalsIgnoreCase("2")) {
//                //System.out.println("chat");
//                //writer.println("chat function");
//                //writer.flush();
//            }
//        }
//    }


//    private void chat() {
//        // read message until over is sent
//        String lineIn = "";
//        System.out.println("Getting message from client...");
//        while (!serverSocket.isClosed() ) {
//            try {
//                 receiveAndPrint(lineIn);
//            }catch (NullPointerException n) {
//                System.out.println(n);
//            }
//        }
//    }

//    private void disconnect() throws IOException {
//        System.out.println("Closing Connection.");
//        socket.close();
//        serverSocket.close();
//        in.close();
//    }

//    private void receiveAndPrint(String lineIn) {
//        try {
//            lineIn = reader.readLine();
//            System.out.printf("Client: " +lineIn+"\n");
//            //lineOut = inConsole.readLine();
//            //out.writeUTF(lineOut);
//        } catch (IOException i) {
//            i.printStackTrace();
//            System.out.println(i);
//        }
//        // tell the server that it is its turn now
//        isTurnToWrite = true;
//
//    }

//    private void initializeSocketStreams (int portNumber) {
//        try {
//            // accepting client
//            socket = serverSocket.accept();
//            System.out.println("New client accepted on port: " + portNumber);
//            // takes input (Socket In))
//            in = new InputStreamReader(socket.getInputStream());
//            // wrap input to a reader
//            reader = new BufferedReader(in);
//            // initialize writer (Socket out)
//            writer = new PrintWriter(socket.getOutputStream());
//        } catch (IOException u) {
//            System.out.println(u);
//        }
//    }


}

