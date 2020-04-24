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

public class DatabaseClient {
    public static void main(String[] args) {
        // The program must take in 2 command-line arguments
        if (args.length != 2) {
            System.err.println("Usage: java DatabaseClient <hostname> <port>");
            System.exit(1);
        }
        // Record the hostname and port number
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        // try-with resource statement to connect to a Socket
    }
}