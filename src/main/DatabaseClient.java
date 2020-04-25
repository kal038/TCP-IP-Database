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

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DatabaseClient {
    // initialize socket, output stream and input stream
    private Socket socket = null;
    private DataInputStream inConsole = null;
    private DataInputStream inServer = null;
    private DataOutputStream out = null;

    public DatabaseClient(String hostName, int portNumber) throws IOException {
       try {
           socket = new Socket(hostName, portNumber);
           System.out.println("Connection Established");
           // setup input from terminal
           inConsole = new DataInputStream(System.in);
           //setup input from server
           inServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
           // setup output to server
           out = new DataOutputStream(socket.getOutputStream());
       } catch (UnknownHostException u) {
           System.out.println(u);
       } catch (EOFException i) {
           System.out.println(i);
       }
       // variable to hold console input
       String lineOut = "";
       String lineIn = "";
       while(!lineOut.equalsIgnoreCase("Over") ) {
           try {
               lineOut = inConsole.readLine();
               out.writeUTF(lineOut);
               //lineIn = inServer.readUTF();
               //System.out.printf("Server: " +lineIn+"\n");

           } catch (IOException e) {
               e.printStackTrace();
               System.out.println(e);
           }
       }

       try {
           inServer.close();
           inConsole.close();
           out.close();
           socket.close();
       } catch (EOFException i) {
           i.printStackTrace();
           System.out.println(i);
       }

    }


}