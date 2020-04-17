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

import com.sun.source.tree.ParenthesizedTree;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Main{
    public static void main(String[] args) {

        readInFile("defDB.csv");
        try {
            String inputDate = getDate();
            System.out.println(inputDate);
        }
        catch (NullPointerException e){
            System.out.println("Goodbye");
        }

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
                line = line.replace("-","_");
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
                Jobs currJob = Jobs.valueOf(separatedValues[9]);
                ArrayList<Jobs> pastJobs = getPastJobs(separatedValues[10]);
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
        //System.out.println(workers.get(0));
        return workers;
    }
    public static ArrayList<OBAJWorker> updateWorkersJobs(ArrayList<OBAJWorker> inputArray, Date time){
        ArrayList<OBAJWorker> updatedArray = inputArray;
        for (OBAJWorker w: updatedArray){
            try {
                Date currJobStart = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartWorkingDate());
                long diffInMillies = Math.abs(time.getTime() - currJobStart.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff/30 > w.getCurrJob().getEmployTime()){//Assuming 30 days in a month
                    ArrayList<Jobs> prevJobs = w.getPastJobs();
                    prevJobs.add(w.getCurrJob());
                    w.setPastJobs(prevJobs);
                    w.setCurrJob(null);
                    for (Jobs j: Jobs.values()){
                        if (!w.getPastJobs().contains(j)){
                            w.setCurrJob(j);
                            w.setStartCurrentDate(time);//TODO may need to fix this
                            break;
                        }
                    }
                    if (w.getCurrJob() == null){
                        w.setCurrJob(Jobs.NA);
                        w.setStartCurrentDate(null);
                    }
                }

                Date workerStart = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartWorkingDate());
                diffInMillies = Math.abs(time.getTime() - workerStart.getTime());
                diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 547.5){//547.5 is 1 year and 6 months
                    long newTime = (long) (workerStart.getTime() + 4.7304*Math.pow(10,10));
                    Date endTime = new Date(newTime);
                    w.setEndDate(endTime);
                    ArrayList<Jobs> prevJobs = w.getPastJobs();
                    prevJobs.add(w.getCurrJob());
                    w.setPastJobs(prevJobs);
                    w.setCurrJob(Jobs.NA);
                }
                else{

                }
            }
            catch (ParseException p){
                //TODO
            }

        }
    }

    public static ArrayList<Characteristics> getCharacteristics(String input){
        String trimmedString = input.trim();
        trimmedString = trimmedString.replace("[","");
        trimmedString = trimmedString.replace("]","");
        trimmedString = trimmedString.replace("'","");

        //trimmedString = trimmedString.replace(" ","_");
        String[] characteristics = trimmedString.split(";");
        //System.out.println(trimmedString);
        ArrayList<Characteristics> chars = new ArrayList<Characteristics>();
        for (int i =0;i < characteristics.length;i++){
            String temp = characteristics[i];
            temp = temp.trim();
            temp = temp.replace(" ","_");
            chars.add(Characteristics.valueOf(temp));
        }
        return chars;
    }

    public static ArrayList<Jobs> getPastJobs(String input){
        String trimmedString = input.replace("[","");
        trimmedString = trimmedString.replace("]","");
        trimmedString = trimmedString.replace("'","");
        trimmedString = trimmedString.trim();
        trimmedString = trimmedString.replace(" ","_");
        String[] jobList = trimmedString.split(";");
        ArrayList<Jobs> jobs = new ArrayList<Jobs>();
        for (int i =0;i < jobList.length;i++){
            jobs.add(Jobs.valueOf(jobList[i]));
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