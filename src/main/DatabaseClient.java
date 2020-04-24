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

public class DatabaseClient {


    public DatabaseClient(String hostName, int portNumber) throws IOException {
        try(
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
                ) {

        }
    }


}