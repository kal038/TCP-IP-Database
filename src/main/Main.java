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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        System.out.println("Hello World");
        readInFile("defDB.csv");
    }

    /**
     * Reads in the CSV file and creates an array of workers
     * @param fileName the name of the csv file
     */
    public static void readInFile(String fileName){
        final String separator = ",";
        BufferedReader b = null;
        try {
            b = new BufferedReader(new FileReader(fileName));
            String line;
            String[] separatedValues;
            ArrayList<String> enumChars = new ArrayList<>();
            b.readLine();
            while ((line = b.readLine()) != null) {
                //line = line.replaceAll("^\\s+", "");
                line = line.replaceAll("-","_");
                line = line.replaceAll(" ","_");
                separatedValues = line.split(separator);
                int id = Integer.parseInt(separatedValues[0]);
                String nat = separatedValues[1];
                String dob = separatedValues[2];
                String marit = separatedValues[3];
                int numChild = Integer.parseInt(separatedValues[4]);
                WorkingReasons work = WorkingReasons.valueOf(separatedValues[5]);
                ArrayList<Characteristics> chars = getCharacteristics(separatedValues[6]);
                WorkSkills workSkills = WorkSkills.valueOf(separatedValues[7]);
                String leaveReason = separatedValues[8];
                String currJob = separatedValues[9];
                ArrayList<String> pastJobs = getPastJobs(separatedValues[10]);


            }
            System.out.println(enumChars);
        }
        catch (FileNotFoundException f){
            System.out.println("File not found");
        }
        catch (IOException e){
            System.out.println("IO exception");
        }

        finally{
            if (b != null){
                try {
                    b.close();
                } catch (IOException io) {
                    //TODO print message
                }
            }
        }

        //return array;

        //TODO Store the worker objects in an array and return that array
    }

    public static ArrayList<Characteristics> getCharacteristics(String input){
        return null;
    }
    public static ArrayList<String> getPastJobs(String input){
        return null;
    }
}