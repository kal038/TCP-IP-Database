/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 4/20/20
 * Time: 9:25 PM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: Database
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Database{
    ArrayList<OBAJWorker> list;
    String outputFileName;
    public Database(String fileName,String outputFileName) {
        list = readInFile(fileName);
        this.outputFileName = outputFileName;
    }

    public void writeToFile(){
        try{
            FileWriter writer = new FileWriter(outputFileName,false);
            writer.write("Unique ID,Nationality,DOB,Marital Status,Number of Children,Work Reason, Characteristics," +
                    "Work Skill,Reason for Leaving,Current Job,Previous Job,Start Date,Current Job Start Date,End Date");
            for (OBAJWorker w: list){
                writer.write(w.formatForCSV());
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e){

        }
    }

    /**
     * Reads in the CSV file and creates an array of workers
     * @param fileName the name of the csv file
     */
    public ArrayList<OBAJWorker> readInFile(String fileName){
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

    /**
     * Updates every wokrer in the database, to be used when the date is changed or if working reasons are updated.
     * @param inputArray
     * @param time
     * @return
     */
    public static ArrayList<OBAJWorker> updateWorkersJobs(ArrayList<OBAJWorker> inputArray, Date time){
        ArrayList<OBAJWorker> updatedArray = inputArray;
        for (OBAJWorker w: updatedArray) {
            if (w.getLeaveReason() != "NA" && w.getCurrJob() != Jobs.NA){
                ArrayList<Jobs> jobs = w.getPastJobs();
                jobs.add(w.getCurrJob());
                w.setPastJobs(jobs);
                w.setCurrJob(Jobs.NA);
                w.setStartCurrentDate(null);
                w.setEndDate(time);
            }
            if (w.getCurrJob() == Jobs.NA) {
                continue;
            }
            else{
            try {
                Date workerStartHireDate = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartWorkingDate());
                long diffInMilliesHireDate = Math.abs(time.getTime() - workerStartHireDate.getTime());
                long diffHireDate = TimeUnit.DAYS.convert(diffInMilliesHireDate, TimeUnit.MILLISECONDS);

                Date currJobStart = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartCurrentDate());
                long diffInMillies = Math.abs(time.getTime() - currJobStart.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                while (diff / 30 > w.getCurrJob().getEmployTime() && diffHireDate <= 547.5) {//Assuming 30 days in a month
                    ArrayList<Jobs> prevJobs = w.getPastJobs();
                    prevJobs.add(w.getCurrJob());
                    w.setPastJobs(prevJobs);
                    w.setCurrJob(null);
                    for (Jobs j : Jobs.values()) {//Finds a job that the worker hasn't done yet.
                        if (!w.getPastJobs().contains(j)) {
                            long newStartTime = (long) (currJobStart.getTime() + w.getCurrJob().getEmployTime()*2.628*Math.pow(10,9));
                            Date newStartDate = new Date(newStartTime);
                            w.setCurrJob(j);
                            w.setStartCurrentDate(newStartDate);
                            break;
                        }
                    }
                    //If there are no more jobs that they can do, then they are done working.
                    if (w.getCurrJob() == null) {
                        w.setCurrJob(Jobs.NA);
                        w.setStartCurrentDate(null);
                        break;
                    }
                    //Updates their new start time and updates the diff betweeen their start time and the current time
                    currJobStart = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartCurrentDate());
                    diffInMillies = Math.abs(time.getTime() - currJobStart.getTime());
                    diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                }

                //Goes through to see if the worker has been working for over a year and 6 months and then lets them go if they have.
                Date workerStart = new SimpleDateFormat("MM/dd/yyyy").parse(w.getStartWorkingDate());
                diffInMillies = Math.abs(time.getTime() - workerStart.getTime());
                diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 547.5) {//547.5 is 1 year and 6 months
                    long newTime = (long) (workerStart.getTime() + 4.7304 * Math.pow(10, 10));
                    Date endTime = new Date(newTime);
                    w.setEndDate(endTime);
                    ArrayList<Jobs> prevJobs = w.getPastJobs();
                    prevJobs.add(w.getCurrJob());
                    w.setPastJobs(prevJobs);
                    w.setCurrJob(Jobs.NA);
                }
                else {

                }
            } catch (ParseException p) {
                //TODO
            }
        }
        }
        return updatedArray;
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
        trimmedString = trimmedString.replace(" ","");
        trimmedString = trimmedString.trim();
        trimmedString = trimmedString.replace("-","_");
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