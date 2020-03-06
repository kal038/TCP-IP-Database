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
    public static ArrayList<OBAJWorker> readInFile(String fileName){
        final String separator = ",";
        BufferedReader b = null;
        ArrayList<OBAJWorker> workers = new ArrayList<OBAJWorker>();
        try {
            b = new BufferedReader(new FileReader(fileName));
            String line;
            String[] separatedValues;
            b.readLine();
            while ((line = b.readLine()) != null) {
                //line = line.replaceAll("^\\s+", "");
                line = line.replace("-","_");
                //line = line.replace(" ","_");
                separatedValues = line.split(separator);

                int id = Integer.parseInt(separatedValues[0]);
                String nat = separatedValues[1];
                String dob = separatedValues[2];
                String marit = separatedValues[3];
                int numChild = Integer.parseInt(separatedValues[4]);
                WorkingReasons work = WorkingReasons.valueOf(separatedValues[5]);
                ArrayList<Characteristics> chars = getCharacteristics(separatedValues[6]);
                ArrayList<WorkSkills> workSkills = getWorkSkills(separatedValues[7]);
                String leaveReason = separatedValues[8];
                String currJob = separatedValues[9];
                ArrayList<String> pastJobs = getPastJobs(separatedValues[10]);
                String startDate = separatedValues[11];
                String startCurrentDate = separatedValues[12];
                String endDate = separatedValues[13];
                workers.add(new OBAJWorker(id,nat,dob,marit,numChild,work,chars,workSkills,leaveReason,currJob,pastJobs,startDate,startCurrentDate,endDate));
            }

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

        return workers;
    }

    public static ArrayList<Characteristics> getCharacteristics(String input){
        String trimmedString = input.replace("[","");
        trimmedString = trimmedString.replace("]","");
        trimmedString = trimmedString.replace("'","");
        trimmedString = trimmedString.trim();
        trimmedString = trimmedString.replace(" ","_");
        String[] characteristics = trimmedString.split(";");
        ArrayList<Characteristics> chars = new ArrayList<Characteristics>();
        for (int i =0;i < characteristics.length;i++){
            chars.add(Characteristics.valueOf(characteristics[i]));
        }
        return chars;
    }

    public static ArrayList<String> getPastJobs(String input){
        String trimmedString = input.replace("[","");
        trimmedString = trimmedString.replace("]","");
        trimmedString = trimmedString.replace("'","");
        trimmedString = trimmedString.trim();
        trimmedString = trimmedString.replace(" ","_");
        String[] jobList = trimmedString.split(";");
        ArrayList<String> jobs = new ArrayList<String>();
        for (int i =0;i < jobList.length;i++){
            jobs.add(jobList[i]);
        }
        return jobs;

    }

    public static ArrayList<WorkSkills> getWorkSkills(String input){
        String trimmedString = input.replace("[","");
        trimmedString = trimmedString.replace("]","");
        trimmedString = trimmedString.replace("'","");
        trimmedString = trimmedString.replace(" ","");
        String[] skills = trimmedString.split(";");
        ArrayList<WorkSkills> skillArray = new ArrayList<WorkSkills>();
        for (int i =0;i < skills.length;i++){
            skillArray.add(WorkSkills.valueOf(skills[i]));
        }
        return skillArray;
    }
}