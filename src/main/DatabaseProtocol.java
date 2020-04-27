/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 4/26/20
 * Time: 2:23 PM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: DatabaseProtocol
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

/**
 * Protocol to communicate between the client and the server sequentially
 * Refer to: Oracle Socket Tutorial
 * @link: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 * In this protocol:
 * 0. The initial state is WAITING
 * 1. The server initiates conversation with a MENU
 * 2. The server recieves an answer from the client and proceed to reply with RESULTS
 */
public class DatabaseProtocol {
    private static final int WAITING = 0;
    private static final int SENTMENU = 1;
    private static final int SENTQUERYFORMAT = 2;
    //private static final int SENTQUERYRESULTS = 3;
    private static final int SENTCHATFORMAT = 4;

    private int currState = WAITING;

    public String processInput (String theInput) {
        String theOutput = null;

        if (currState == WAITING) {
            theOutput =   "MENU:\nHAHA";
            currState = SENTMENU;
        } else if (currState == SENTMENU) {
            if (theInput.equals("1")) {
                theOutput = "QUERY";
                currState = SENTQUERYFORMAT;
            } else if (theInput.equals("2")) {
                theOutput = "CHATMODE:";
                currState = SENTCHATFORMAT;
            }

        } else if (currState == SENTQUERYFORMAT) {
            // Enable the query function and change state to SENT QUERY RESULTS

        } else if (currState == SENTCHATFORMAT) {
            // Enable the chat function
        }


        return theOutput;
    }


}