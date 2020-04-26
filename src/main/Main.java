/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 3/2/20
 * Time: 8:10 AM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: Main
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Class for running the db program
 */

// TODO: Anything that you type in is on the client side to be sent to the server side. Results that was ouputted to the console now needs to be outputted across your network through TCP/IP
public class Main{
    private static OptionMenuUtil options = new OptionMenuUtil();
    private static String[] dbFiles = {"db1.csv","db2.csv","db3.csv"};
    private static String saveFile = "dbSave.csv";
    private static Database db;
    private static int index = 0;//Points to the most current database
    private static int undosLeft = 0;
    /**
     * Main method for the database, continually runs until the user decides to quit.
     * Users can query a few attributes.
     * Users can sort the list by ID number.
     * Users can add or modify workers.
     * Users can undo the last three changes
     * Users can save changes to a new csv called dbSave.csv
     */
    public static void main(String[] args) throws IOException {
        DatabaseServer server = null;
        DatabaseClient client = null;
        System.out.println("Do you want to run the program in Server/Client mode [Y|N]: ");
        Scanner in = new Scanner(System.in);
        String answer = in.next().strip();
        if (answer.equalsIgnoreCase("N")) {
            runNormal();
        } else {

            System.out.println("Are you running as Server or Client? [S|C]: ");
            String serverOrClient = in.next().strip();
            String hostName;
            System.out.println("Please specify port number: ");
            int portNumber = Integer.parseInt(in.next().strip());
            if (serverOrClient.equalsIgnoreCase("S")) {
                //Running program in Server Mode
                System.out.println("Running the program as Server");

                try {
                    server = new DatabaseServer(portNumber);
                } catch (IOException b) {
                    System.out.println(b);;
                }
            } else {
                // Running the program in Client Mode
                System.out.println("Running the program as Client");
                System.out.println("Do you want to run on localhost? [Y|N]: ");
                String localHostAns = in.next().strip();
                if (localHostAns.equalsIgnoreCase("Y")) {
                    hostName = "127.0.0.1";
                } else {
                    System.out.println("Provide host IP Address: ");
                    hostName = in.next().strip();
                }
                // Start the Client
                try {
                    System.out.println("Establishing Connection to Server");
                    client = new DatabaseClient(hostName,portNumber);
                    System.out.println("Connection closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        }




    private static void runNormal() {
        String fileName = getFile();
        db = new Database(fileName);
        try {
            String inputDate = getDate();
            Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(inputDate);
            db.updateWorkersJobs(date1);
            db.writeToFile(dbFiles[0]);
            db.writeToFile(dbFiles[1]);
            db.writeToFile(dbFiles[2]);
            int action = options.printMenu();
            while (action != 0){
                switch (action){
                    case 1:
                        ArrayList<String> chars = Database.queryInputHelper();
                        int numberOfWorkers = Database.queryNumberHelper();
                        ArrayList<OBAJWorker> answer = db.queryWorker(numberOfWorkers,chars);
                        System.out.println(answer.size());
                        for (OBAJWorker w: answer){
                            System.out.println(w.toString());
                        }
                        break;
                    case 2:
                        saveDatabase(dbFiles[index]);
                        incrementIndex();
                        db.bubbleSort();
                        incrementUndos();
                        break;
                    case 3:
                        int modOrAdd = db.modifyOrAddWorker();
                        if (modOrAdd == 0){
                            break;
                        }
                        if (modOrAdd == 1){
                            saveDatabase(dbFiles[index]);
                            incrementIndex();
                            db.promptModifyWorker();
                            incrementUndos();
                        }
                        else {
                            saveDatabase(dbFiles[index]);
                            incrementIndex();
                            db.promptForNewWorker();//Adds a new worker to the database if the input is valid
                            incrementUndos();
                        }
                        break;
                    case 4:
                        undoChange();
                        break;
                    case 5:
                        saveDatabaseToSaveFile();
                        break;
                }
                action = options.printMenu();
            }
        }
        catch (NullPointerException | ParseException e){
            System.out.println("Goodbye");
        }
    }

    /**
     * Asks the user for the name of the db file and returns it
     */
    private static String getFile(){
        System.out.println("Please enter a filename or the default file will be used:");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        if (fileName.equals("")) {return "defDB.csv";}
        return fileName;
    }

    /**
     *Saves the database to the save file, used for the save option.
     */
    private static void saveDatabaseToSaveFile(){
        db.writeToFile(saveFile);
    }


    /**
     * Increments the index for the csv save files and does mod 3 to keep it in range
     */
    private static void incrementIndex(){
        index++;
        index = index%3;
    }

    /**
     * Increments the number of undos correctly, never exceeding 3
     */
    private static void incrementUndos(){
        if (undosLeft < 3){
            undosLeft++;
        }
    }

    /**
     * Saves the database to the csv file, used to keep track of changes and undo when necessary
     * @param fileName the name of the file to save to
     */
    private static void saveDatabase(String fileName){
        db.writeToFile(fileName);
    }

    /**
     * Undoes the most recent change by loading the file behind the current index into the db object.
     */
    private static void undoChange(){
        if (undosLeft <= 0){
            System.out.println("You cannot undo anymore.");
        }
        else{
            System.out.println("Undoing last change");
            index--;
            index = index%3;
            undosLeft--;
            db.readInFile(dbFiles[index]);
        }
    }


    /**
     * Prompts the user to enter a new date for the system or keep the current date.
     */
    private static String getDate(){
        Object[] options = {"Use system time","Input future time"};
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date sysTime = new Date();
        int n = JOptionPane.showOptionDialog(null,
                "Would you like to use the system time or enter a future time?",
                "System Time Setup",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,options[0]);
        if (n == 0){//Use system time
            return formatter.format(sysTime);
        }
        else {
            String input = JOptionPane.showInputDialog("Please enter the date in this format: MM/dd/yyyy");
            try{
                Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(input);
                if (date1.compareTo(sysTime) < 0)
                    return formatter.format(sysTime);
                else
                    return formatter.format(date1);
            }
            catch (ParseException e) {
                JOptionPane.showMessageDialog(null,"Invalid date, using current time");
                return formatter.format(sysTime);
            }
        }
    }
}