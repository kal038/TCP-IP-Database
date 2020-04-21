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
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main{
    Database[] dbs = new Database[3];//Hold three databases so we can undo changes.
    int index = 1;//Points to which database should be modified and where the next save occurs
    public void main(String[] args) {

        dbs[0] = new Database("defDB.csv","database0.csv");
        dbs[1] = new Database("defDB.csv","database1.csv");
        dbs[2] = new Database("defDB.csv","database2.csv");
        try {
            String inputDate = getDate();
            System.out.println(inputDate);
        }
        catch (NullPointerException e){
            System.out.println("Goodbye");
        }

    }

    public void saveDatabase(){
        dbs[index].writeToFile();
        index++;
        index = index%3;
    }


    /**
     * Prompts the user to enter a new date for the system or keep the current date.
     */
    public static String getDate(){
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