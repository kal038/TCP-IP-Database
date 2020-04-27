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

import java.util.ArrayList;

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
    private static OptionMenuUtil options = new OptionMenuUtil();
    private static final int WAITING = 0;
    private static final int SENTMENU = 1;
    private static final int SENTQUERYFORMAT = 2;
    private static final int SENTCHATFORMAT = 3;
    private static final int SENTQUERYRESULTS = 4;
    private static final String END = "\nEND.";


    private int currState = WAITING;

//    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
//    private String[] answers = { "Turnip the heat, it's cold in here!",
//            "I didn't know you could yodel!",
//            "Bless you!",
//            "Is there an owl in here?",
//            "Is there an echo in here?" };

    public String processInput(String theInput) {
        String theOutput = null;
//        if (state == WAITING) {
//            theOutput = "Knock! Knock!";
//            state = SENTKNOCKKNOCK;
//        } else if (state == SENTKNOCKKNOCK) {
//            if (theInput.equalsIgnoreCase("Who's there?")) {
//                theOutput = clues[currentJoke];
//                state = SENTCLUE;
//            } else {
//                theOutput = "You're supposed to say \"Who's there?\"! " +
//                        "Try again. Knock! Knock!";
//            }
//        } else if (state == SENTCLUE) {
//            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
//                theOutput = answers[currentJoke] + " Want another? (y/n)";
//                state = ANOTHER;
//            } else {
//                theOutput = "You're supposed to say \"" +
//                        clues[currentJoke] +
//                        " who?\"" +
//                        "! Try again. Knock! Knock!";
//                state = SENTKNOCKKNOCK;
//            }
//        } else if (state == ANOTHER) {
//            if (theInput.equalsIgnoreCase("y")) {
//                theOutput = "Knock! Knock!";
//                if (currentJoke == (NUMJOKES - 1))
//                    currentJoke = 0;
//                else
//                    currentJoke++;
//                state = SENTKNOCKKNOCK;
//            } else {
//                theOutput = "Bye.";
//                state = WAITING;
//            }
//        }


        if (currState == WAITING) {
            theOutput = "MENU:\n" + options.getMenu() + END;
            currState = SENTMENU;

        } else if (currState == SENTMENU) {
            if (theInput.equals("1")) {
                theOutput = "-----QUERY: Choose criteria for query in this specific format\n-----" +
                        "Format : [NUMRESULTS], [CHARACTERISTICS]\n" +
                        "in which [CHARACTERISTICS] is [FIELD:FIELDRESULTS] and NUMRESULTS <= 100\n" +
                        "Example: 50,MaritalStatus:Married or 75,Nationality:Italian,MarriedStatus:Married " + END;
                currState = SENTQUERYFORMAT;
            } else if (theInput.equals("2")) {
                theOutput = "CHATMODE" + END;
                currState = SENTCHATFORMAT;
            }

        } else if (currState == SENTQUERYFORMAT) {
                ArrayList<String> characteristics = new ArrayList<String>();
                String[] tokens = theInput.strip().split(",");
                int numQueries = Integer.parseInt(tokens[0]);
                characteristics.add(tokens[1]);
                if (tokens.length == 3) {
                    characteristics.add(tokens[2]);
                } else
                if (tokens.length == 4) {
                    characteristics.add(tokens[3]);
                }
                theOutput = Integer.toString(numQueries) + END;
                currState = SENTQUERYRESULTS;



            // Enable the query function and change state to SENT QUERY RESULTS

        }
        return theOutput;
    }
}

