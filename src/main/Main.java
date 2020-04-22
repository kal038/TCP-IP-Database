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
import javax.swing.text.html.Option;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main{
    static OptionMenuUtil options = new OptionMenuUtil();
    static Database[] dbs = new Database[3];//Hold three databases so we can undo changes.
    static int index = 1;//Points to which database should be modified
    static int undosLeft = 3;
    public static void main(String[] args) {

        dbs[0] = new Database("defDB.csv","newDB.csv");
        dbs[1] = new Database("defDB.csv","newDB.csv");
        dbs[2] = new Database("defDB.csv","newDB.csv");
        try {
            String inputDate = getDate();
            Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(inputDate);
            dbs[index].updateWorkersJobs(date1);
            //System.out.println(dbs[index].getWorker(51).toString());
            int action = options.printMenu();
            while (action != 0){
                switch (action){
                    case 1:
                        ArrayList<String> chars = Database.queryInputHelper();
                        System.out.println(chars);
                        ArrayList<OBAJWorker> answer = dbs[index].queryWorker(chars);
                        System.out.println(answer);
                        for (OBAJWorker w: answer){
                            System.out.println(w.toString());
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        undoChange();
                        break;
                    case 5:
                        saveDatabase();
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
     * Prompts the user to enter a new worker to be added to the database
     * @return
     */
    private static String promptForNewWorker(){
        String input = JOptionPane.showInputDialog("Please enter the new worker's information in this format:"+
                "Unique ID,Nationality,DOB,Marital Status,Number of Children,Work Reason,Characteristics," +
                "Work Skill,Reason for Leaving,Current Job,Previous Job,Start Date,Current Job Start Date,End Date");
        return input;
    }

    /**
     * Adds a worker to the current database
     * @param input
     */
    private static void addWorkerToDB(String input){
        OBAJWorker newWorker = Database.parseLineIntoWorker(input);
        dbs[index].addWorker(newWorker);
        incrementIndex();
        incrementUndos();
    }

    private static void incrementIndex(){
        index++;
        index = index%3;
    }

    private static void incrementUndos(){
        if (undosLeft == 3){
            //Do nothing
        }
        else{
            undosLeft++;
        }
    }

    public static void saveDatabase(){
        dbs[index].writeToFile();
        //index++;
        //index = index%3;
    }

    private static void undoChange(){
        if (undosLeft <= 0){
            //TODO tell the user they can't go back any further
        }
        else{
            index--;
            index = index%3;
            undosLeft--;
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