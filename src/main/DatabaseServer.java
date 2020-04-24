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

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DatabaseServer {
    public DatabaseServer(int portNumber) throws IOException {
        try (
                // initialize server side Socket
                ServerSocket DatabaseServerSocket = new ServerSocket(portNumber);
                // accept client side connection
                Socket  DatabaseClientSocket = DatabaseServerSocket.accept();
                PrintWriter out = new PrintWriter(DatabaseClientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(DatabaseClientSocket.getInputStream()));
        ) {
            String input = "";
            while (in.readLine() != null) {
                try {
                    input = in.readLine();
                    out.println(input);
                } catch (IOException i) {
                    out.println(i);
                }
            }
        }
    }

}