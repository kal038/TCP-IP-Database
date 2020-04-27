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
    // protocol stuff
    private static OptionMenuUtil options = new OptionMenuUtil();
    private static final int WAITING = 0;
    private static final int SENTMENU = 1;
    private static final int SENTQUERYFORMAT = 2;
    private static final int SENTCHATFORMAT = 3;
    private static final int SENTQUERYRESULTS = 4;
    private static final String END = "\nEND.";
    private int currState = WAITING;
    //database stuff
    private static String[] dbFiles = {"db1.csv","db2.csv","db3.csv"};
    private static String saveFile = "dbSave.csv";
    private static int index = 0;//Points to the most current database
    private static int undosLeft = 0;
    private static String fileName = "defDB.csv";
    private static Database db = new Database(fileName);
    private Object IllegalArgumentException;

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

        if (theInput == null && currState != WAITING) {
            throw new NullPointerException("Invalid Input, Type Something");
        }
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
                //inputs to the query function
                int numQueries;
                ArrayList<String> characteristics = new ArrayList<String>();
                String[] tokens = theInput.strip().split(",");
                numQueries = Integer.parseInt(tokens[0]);
                characteristics.add(tokens[1]);
                if (tokens.length == 3) {
                    characteristics.add(tokens[2]);
                } else
                if (tokens.length == 4) {
                    characteristics.add(tokens[3]);
                }
                ArrayList<OBAJWorker> answer = db.queryWorker(numQueries, characteristics);
                String answerInString = "";
                for (OBAJWorker w: answer){
                answerInString += (w.toString() + "\n");
                }
                theOutput = answerInString + "Want more queries? [Y|N]:  "+ END;
                currState = SENTQUERYRESULTS;
        } else if (currState == SENTCHATFORMAT) {
            //TODO: Enable chat mode by using threads?
        } else if (currState == SENTQUERYRESULTS) {
            if (theInput.equalsIgnoreCase("Y")) {
                theOutput = "Let's do it, input your query command:" + END;
                currState = SENTQUERYFORMAT;}
            else {
                theOutput = "Exiting Query Page, Back to menu? [Y|N]: " + END;
                currState = WAITING;
            }
        }
        return theOutput;
    }
}

