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
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream inClient = null;
    private DataInputStream inConsole = null;
    private DataOutputStream out = null;

    public DatabaseServer(int portNumber) throws IOException {
        // start the server and wait for a connection from a client
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started, waiting for client...");

            socket = serverSocket.accept();
            System.out.println("Client accepted on port: " + portNumber);

            // takes input from the client socket
            inClient = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            // read message until over is sent
            String lineIn = "";
            String lineOut = "";
            System.out.println("Getting message from client...");
            while (!lineIn.equalsIgnoreCase("Over") ) {
                try {
                    lineIn = inClient.readUTF();
                    System.out.printf("Client: " +lineIn+"\n");
                    //lineOut = inConsole.readLine();
                    //out.writeUTF(lineOut);
                } catch (IOException i) {
                    i.printStackTrace();
                    System.out.println(i);
                }
            }
            //close connection
            System.out.println("Closing Connection.");
            socket.close();
            inClient.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}