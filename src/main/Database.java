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

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Class that represents a database which holds all the workers and manipulates them.
 */
public class Database{
    private final String defaultFile = "defDB.csv";
    private ArrayList<OBAJWorker> list;
    private boolean fileNotFound = false;

    public Database(String fileName) {
        readInFile(fileName);
        if (fileNotFound){
            readInFile(defaultFile);
        }
    }

    /**
     * Sets the worker at the index to be the input worker
     * @param index index in the list
     * @param w  new worker to put into list
     */
    private void setWorker(int index,OBAJWorker w){
        list.set(index,w);
    }

    /**
     * Sorts the database by ID number with 0 at the top and the largest ID at the bottom.
     * Uses a bubblesort method
     */
    public void bubbleSort(){
        boolean swap = true;
        OBAJWorker temp;
        while (swap){
            swap = false;
            for (int i=0;i<list.size()-1;i++){
                if (getWorker(i).getWorkerID()>getWorker(i+1).getWorkerID()){
                    temp = getWorker(i);
                    setWorker(i,getWorker(i+1));
                    setWorker(i+1,temp);
                    swap = true;
                }
            }
        }

    }

    /**
     * Adds a worker to the end of the workers list
     * @param w new worker to add
     */
    public void addWorker(OBAJWorker w){
        list.add(w);
    }

    /**
     * Prompts the user to enter a new worker and then, if they enter a valid worker, adds it to the database.
     * An invalid worker will result in no worker being added.
     */
    public void promptForNewWorker(){
        System.out.println("Please enter the information for your new worker in the following format:");
        System.out.println("\"Unique ID,Nationality,DOB,Marital Status,Number of Children,Work Reason,Characteristics," +
                                    "Work Skill,Reason for Leaving,Current Job,Previous Job,Start Date,Current Job Start Date,End Date");
        System.out.println("Example: 4964,Italian,25/9/1987,Other,3,BEHAVIOR,['Dark skin'],['Typing'; 'Electrical'],Time,NA,['Office_Worker';"+
                "'Construction'],9/10/2017,NA,10/19/2019");
        Scanner in = new Scanner(System.in);
        String newWorker = in.nextLine();
        OBAJWorker newWorkerObj = parseLineIntoWorker(newWorker);
        if (newWorkerObj == null){
            System.out.println("Sorry, we couldn't convert that input into a worker.");
        }
        else{
            addWorker(newWorkerObj);
            System.out.println("The new worker has been added to the database.");
        }
    }

    /**
     * Gets the worker at the specified index in the list
     * @param index the spot in the list to get the worker from
     * @return the worker at the index spot
     */
    public OBAJWorker getWorker(int index){
        return list.get(index);
    }

    /**
     * Converts every worker in the database into csv format and then writes each one to the output file
     * @param outputFileName the destination filename
     */
    public void writeToFile(String outputFileName){
        try{
            FileWriter writer = new FileWriter(outputFileName,false);
            writer.write("Unique ID,Nationality,DOB,Marital Status,Number of Children,Work Reason,Characteristics," +
                    "Work Skill,Reason for Leaving,Current Job,Previous Job,Start Date,Current Job Start Date,End Date\n");
            for (OBAJWorker w: list){
                writer.write(w.formatForCSV());
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("Error, can't output to this file");
        }
    }

    /**
     * Reads in the CSV file and creates an array of workers
     * @param fileName the name of the csv file
     */
    public void readInFile(String fileName){
        BufferedReader b = null;
        ArrayList<OBAJWorker> workers = new ArrayList<OBAJWorker>();
        try {
            b = new BufferedReader(new FileReader(fileName));
            String line;
            b.readLine();
            while ((line = b.readLine()) != null) {
                workers.add(parseLineIntoWorker(line));
            }

        }
        catch (FileNotFoundException f){
            System.out.println("File not found");
            fileNotFound = true;
        }
        catch (IOException e){
            System.out.println("IO exception");
        }
        finally{
            if (b != null){
                try {
                    b.close();
                } catch (IOException io) {
                    System.out.println("Error, could not close file");
                }
            }
        }
        list = workers;
    }

    /**
     * Takes a line from a csv file and converts it into a worker object.
     * @param input a line from a csv representing a worker
     * @return the parse worker object
     */
    public static OBAJWorker parseLineIntoWorker(String input){
        try {
            final String separator = ",";
            String separatedValues[];
            String line = input.replace("-", "_");
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
            return new OBAJWorker(id, nat, dob, marit, numChild, work, chars, workSkills, leaveReason, currJob, pastJobs, startDate, startCurrentDate, endDate);
        }
        catch (NumberFormatException e){
            return null;
        }
    }

    /**
     * Updates every worker in the database, to be used when the date is changed or if working reasons are updated.
     * @param time
     * @return
     */
    public ArrayList<OBAJWorker> updateWorkersJobs(Date time){
        for (OBAJWorker w: list) {
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
            } catch (ParseException p) {
                System.out.println("There was an error parsing the time.");
            }
        }
        }
        return list;
    }

    /**
     * Gets the characteristics of a worker from the csv and converts them into an arraylist
     * @param input the unparsed string of characteristics
     * @return the parsed arraylist of characteristics
     */
    private static ArrayList<Characteristics> getCharacteristics(String input){
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

    /**
     * Gets the past jobs of a worker from the csv and converts them into an arraylist
     * @param input the unparsed string of jobs
     * @return the parsed arraylist of jobs
     *
     */
    private static ArrayList<Jobs> getPastJobs(String input){
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

    /**
     *Gets the work skills of a worker from the csv and converts them into an arraylist
     * @param input the unparsed string of skills
     * @return the parsed arraylist of work skills
     */
    private static ArrayList<WorkSkills> getWorkSkills(String input){
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

    /**
     * This is a query function that takes in an ArrayList of Workers and an ArrayList of characteristics that we have to query for
     * I loop through the ArrayList of workers and match them with the input criteria
     * @param queries (int),  characteristics (ArrayList<String></String>, comma-separated, requires a helper function to parse the input)
     * @return  queryResults an ArrayList of Worker objects that matches the query characteristics
     */
    public ArrayList<OBAJWorker> queryWorker( int queries, ArrayList<String> characteristics ) {
        ArrayList<OBAJWorker> queryResults = new ArrayList<OBAJWorker>(); // this is the resulting ArrayList to be returned containing workers that matches the criteria
        int queriesLeft = queries;
            // Now loop through the list of workers

            for (OBAJWorker worker : list) {
                if (queriesLeft <= 0)
                    break;
                    boolean failure = false;

                    for (int i = 0; i < characteristics.size(); i++) {
                        String[] tmp = characteristics.get(i).strip().split(":");
                        String field = tmp[0];
                        String fieldResult = tmp[1];
                        String resultToBeCompared = new String();
                        if (field.equalsIgnoreCase("MaritalStatus")) {
                            resultToBeCompared = worker.getMaritalStatus();
                        } else if (field.equalsIgnoreCase("Nationality")) {
                            resultToBeCompared = worker.getNationality();
                        } else if (field.equalsIgnoreCase("DOB")) {
                            resultToBeCompared = worker.getDOB();
                        } else if (field.equalsIgnoreCase("StartDate")) {
                            resultToBeCompared = worker.getStartWorkingDate();
                        }
                        if (resultToBeCompared.equalsIgnoreCase(fieldResult)) {
                            //Do nothing
                        } else
                            failure = true;

                    }

                    if (!failure) {
                        queriesLeft--;
                        queryResults.add(worker);
                    }

                }



        return queryResults;
    }


    /**
     * Asks the user whether they would like to add a worker or modify a worker and then returns their choice.
     * @return an int representing the users choice
     */
    public int modifyOrAddWorker(){
        System.out.println("Would you like to modify an existing worker or add a worker?");
        System.out.println("1. Modify");
        System.out.println("2. Add");
        System.out.println("0. Cancel");
        Scanner in = new Scanner(System.in);
        try{
            int num = Integer.parseInt(in.next().strip());
            if (num >= 0 && num <= 2)
                return num;
            else
                return 0;
        }
        catch (NumberFormatException p){
            return 0;
        }
    }

    /**
     * @return the input to query worker, well parsed to fit the format that the function needs. Example : ["Marital Status: Married","Nationality: German"]
     */
    public static ArrayList<String> queryInputHelper () {
        ArrayList<String> inputToQuery = new ArrayList<String>();
        try {
            // Prompt for the number of input from the user
            System.out.printf("How many criteria do you want to query. If you choose more than one\n" + "" +
                    "you will be prompted to enter each criteria one by one. Choose 1, 2, or 3: ");
            Scanner in = new Scanner(System.in);
            int numCriteria = Integer.parseInt(in.next().strip());
            for (int i = 0; i < numCriteria; i++) {
                System.out.printf("Specify the criteria that you want to query for(Nationality,MaritalStatus,DOB,StartDate): ");
                String field = in.next();
                System.out.println("Specify the result of that criteria(i.e. Italian,Married,06/09/1999,04/22/2020): ");
                String fieldResult = in.next();
                String inputString = String.format(field + ":" + fieldResult);
                inputToQuery.add(inputString);
            }
            return inputToQuery;
        }
        catch (NumberFormatException e){
            inputToQuery.add("MaritalStatus:Married");
            return inputToQuery;
        }
    }

    /**
     * Helper method to determine the number of results to  see
     * @return the number of results to display to the user
     */
    public static int queryNumberHelper(){
        System.out.println("How many results would you like to see, the max number is 100 and the default is 50");
        Scanner in = new Scanner(System.in);
        try {
            int num = Integer.parseInt(in.next().strip());
            if (num > 0 && num <= 100)
                return num;
            else
                return 50;
        }
        catch (NumberFormatException e){
            return 50;
        }

    }

    /**
     * Searches for worker based on their ID number, returns null if the worker doesn't exist. Returns the worker and
     * their spot in the list of workers as an array.
     * @param input the Id of the worker
     * @return an array containing the worker and its index in the list
     */
    private Object[] getWorkerByID(int input){
        Object[] ans = new Object[2];
        OBAJWorker w;
        for (int i=0;i<list.size();i++){
            w = getWorker(i);
            if (w.getWorkerID() == input){
                ans[0] = w;
                ans[1] = i;
                return ans;
            }
        }
        return null;
    }

    /**
     * Asks the user for the ID of the worker to modify, if the worker is valid and the modification is valid, then
     * the worker's informaation will be updated.
     */
    public void promptModifyWorker(){
        System.out.println("Please enter the ID number of the worker to modify");
        Scanner in = new Scanner(System.in);
        try{
            int id = Integer.parseInt(in.next().strip());
            Object[] workerAndIndex = getWorkerByID(id);
            OBAJWorker worker = (OBAJWorker) workerAndIndex[0];
            int index = (int) workerAndIndex[1];
            if (worker == null){
                System.out.println("Sorry, that worker is not in this database.");
            }
            else{
                System.out.println("What would you like to modify:");
                System.out.println("1.Marital Status\n2.Number of Children\n3.Reason for leaving\n4.Date began working\n5.Date ended working\n0.Cancel");
                int choice = Integer.parseInt(in.next().strip());
                System.out.println("Please enter the new value for that attribute:");
                switch (choice){
                    case 1:
                        String input = in.next().strip();
                        worker.setMaritalStatus(input);
                        list.set(index,worker);
                        break;
                    case 2:
                        int numChildren = Integer.parseInt(in.next().strip());
                        worker.setNumberChildren(numChildren);
                        list.set(index,worker);
                        break;
                    case 3:
                        String reason = in.next().strip();
                        worker.setLeaveReason(reason);
                        list.set(index,worker);
                        break;
                    case 4:
                        String newDate = in.next().strip();
                        worker.setStartCurrentDateWithString(newDate);
                        list.set(index,worker);
                        break;
                    case 5:
                        String endDate = in.next().strip();
                        worker.setEndDateWithString(endDate);
                        list.set(index,worker);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (NumberFormatException e){
            System.out.println("Error, invalid input");
        }
    }

    /**
     * Gets the number of workers in the database, used for testing
     * @return Number of workers in the database
     */
    public int getSize(){
        return list.size();
    }
}