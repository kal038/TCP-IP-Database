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

public class DatabaseServer {
    // initialize needed resources
    private Socket socket ;
    private ServerSocket serverSocket ;
    private InputStreamReader in;
    private BufferedReader reader;
    private PrintWriter writer ;
    private boolean isTurnToWrite;

    /**
     * Consulted GeeksforGeeks and Baeldung
     * Link to GeeksforGeeks: https://www.geeksforgeeks.org/socket-programming-in-java/
     * Link to Baeldung: https://www.baeldung.com/a-guide-to-java-sockets?fbclid=IwAR1OyghWj0F5rnp7E_oPvtDuOplj8jn0lgWN6n5zZOl8uz03lHpOLJJrZ-o
     * @param portNumber
     * @throws IOException
     */
    public DatabaseServer(int portNumber) throws IOException {
        // start the server and wait for a connection from a client
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started, waiting for client...");
        initializeSocketStreams(portNumber);



        while(!socket.isClosed() ) {
            //check for client drops
            if (reader.readLine() == null) {
                System.out.println("The client has disconnected, waiting for new client to join...");
                //waiting for new client
                initializeSocketStreams(portNumber);
            } else {
                // a connection is maintained
                String text = reader.readLine();
                System.out.println("Received " + text);
                isTurnToWrite = true;
                //writer.println("Hello" + text);
                //writer.flush();
            }
        }




    }

    private void chat() {
        // read message until over is sent
        String lineIn = "";
        System.out.println("Getting message from client...");
        while (!serverSocket.isClosed() ) {
            try {
                lineIn = receiveAndPrint(lineIn);
            }catch (NullPointerException n) {
                System.out.println(n);
            }
        }
    }

    private void disconnect() throws IOException {
        System.out.println("Closing Connection.");
        socket.close();
        serverSocket.close();
        in.close();
    }

    private String receiveAndPrint(String lineIn) {
        try {
            lineIn = reader.readLine();
            System.out.printf("Client: " +lineIn+"\n");
            //lineOut = inConsole.readLine();
            //out.writeUTF(lineOut);
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println(i);
        }
        // tell the server that it is its turn now
        isTurnToWrite = true;
        return lineIn;
    }

    private void initializeSocketStreams (int portNumber) {
        try {
            // accepting client
            socket = serverSocket.accept();
            System.out.println("New client accepted on port: " + portNumber);
            // takes input (Socket In))
            in = new InputStreamReader(socket.getInputStream());
            // wrap input to a reader
            reader = new BufferedReader(in);
            // initialize writer (Socket out)
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException u) {
            System.out.println(u);
        }
    }


}

