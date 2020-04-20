/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 3/2/20
 * Time: 8:35 AM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: OBAJWorker
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OBAJWorker {
    /** A unique ID number */
    private int workerID;
    /** Nationality */
    private String nationality;
    /** DOB */
    private String DOB;
    /** Marital Status */
    private String maritalStatus;
    /** Number of children */
    private int numberChildren;
    /** Reason for working (an enum called WorkingReasons) */
    private WorkingReasons workReason;
    /** Physical or Mental Characteristics (a list of enums, specifically a list of Characteristics enums) */
    private ArrayList<Characteristics> workerCharacteristics;
    /** Work Skill (an Enum called Skills) */
    private ArrayList<WorkSkills> workerSkill;
    /** Reason for leaving position */
    private String leaveReason;
    /** Current Job */
    private Jobs currJob;
    /** Previous Jobs (An array list)*/
    private ArrayList<Jobs> pastJobs;
    /** Date started working (I currently have it as a String but we could have a DateUtil just like in the lab to turn it into an object) */
    private String startWorkingDate;
    /** Date began current job */
    private String startCurrentDate;
    /** Date ended working */
    private String endDate;

    public OBAJWorker(int workerID, String nationality, String DOB, String maritalStatus, int numberChildren, WorkingReasons workReason, ArrayList<Characteristics> workerCharacteristics, ArrayList<WorkSkills> workerSkill, String leaveReason, Jobs currJob, ArrayList<Jobs> pastJobs, String startWorkingDate, String startCurrentDate, String endDate) {
        this.workerID = workerID;
        this.nationality = nationality;
        this.DOB = DOB;
        this.maritalStatus = maritalStatus;
        this.numberChildren = numberChildren;
        this.workReason = workReason;
        this.workerCharacteristics = workerCharacteristics;
        this.workerSkill = workerSkill;
        this.leaveReason = leaveReason;
        this.currJob = currJob;
        this.pastJobs = pastJobs;
        this.startWorkingDate = startWorkingDate;
        this.startCurrentDate = startCurrentDate;
        this.endDate = endDate;
    }

    public int getWorkerID() {
        return workerID;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDOB() {
        return DOB;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public int getNumberChildren() {
        return numberChildren;
    }

    public WorkingReasons getWorkReason() {
        return workReason;
    }

    public ArrayList<Characteristics> getWorkerCharacteristics() {
        return workerCharacteristics;
    }

    public ArrayList<WorkSkills> getWorkerSkill() {
        return workerSkill;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public Jobs getCurrJob() {
        return currJob;
    }

    public ArrayList<Jobs> getPastJobs() {
        return pastJobs;
    }

    public String getStartWorkingDate() {
        return startWorkingDate;
    }

    public String getStartCurrentDate() {
        return startCurrentDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String formatForCSV(){
        String format = Integer.toString(getWorkerID()) + "," + getNationality() + "," + getDOB() + "," + getMaritalStatus() + "," +
                Integer.toString(getNumberChildren()) + "," + getWorkReason().toString() + "," + formatCharacteristics() + "," +
                formatWorkSkills() + "," + getLeaveReason() + "," + getCurrJob().toString() + "," + formatPreviousJobs() + "," +
                getStartCurrentDate() + "," + getStartCurrentDate() + "," + getEndDate();
        return format;
    }

    private String formatCharacteristics(){
        String value = "";
        for (Characteristics c: workerCharacteristics){
            value = c.toString() + ";";
        }
        return value.substring(0,value.length()-1);
    }

    private String formatWorkSkills(){
        String value = "";
        for (WorkSkills w: workerSkill){
            value = w.toString() + ";";
        }
        return value.substring(0,value.length()-1);
    }

    private String formatPreviousJobs(){
        String value = "";
        for (Jobs w: pastJobs){
            value = w.toString() + ";";
        }
        return value.substring(0,value.length()-1);
    }


    @Override
    public String toString() {
        return "OBAJWorker{" +
                "workerID=" + workerID +
                ", nationality='" + nationality + '\'' +
                ", DOB='" + DOB + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", numberChildren=" + numberChildren +
                ", workReason=" + workReason +
                ", workerCharacteristics=" + workerCharacteristics +
                ", workerSkill=" + workerSkill +
                ", leaveReason='" + leaveReason + '\'' +
                ", currJob='" + currJob + '\'' +
                ", pastJobs=" + pastJobs +
                ", startWorkingDate='" + startWorkingDate + '\'' +
                ", startCurrentDate='" + startCurrentDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
    public void setEndDate(Date newDate){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        endDate = formatter.format(newDate);
    }
    public void setCurrJob(Jobs input){
        currJob = input;
    }
    public void setPastJobs(ArrayList<Jobs> newJobs){
        pastJobs = newJobs;
    }
    public void setStartCurrentDate(Date date){
        if (date == null){
            startCurrentDate = "NA";
        }
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            startCurrentDate = formatter.format(date);
        }
    }
}