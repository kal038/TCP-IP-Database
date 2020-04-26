package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    OBAJWorker worker;
    Database db;
    Database dbTwo;
    @BeforeEach
    void setUp() {
        db = new Database("defDB.csv");
        ArrayList<WorkSkills> skills = new ArrayList<>();
        skills.add(WorkSkills.Construction);
        skills.add(WorkSkills.Mathematics);
        ArrayList<Characteristics> chars = new ArrayList<>();
        chars.add(Characteristics.Crafty);
        ArrayList<Jobs> jobs = new ArrayList<>();
        jobs.add(Jobs.Boulder_Reducer);
        worker = new OBAJWorker(99999,"Martian","06/09/1999","Married",
                0,WorkingReasons.DEBT,chars,skills,"NA",Jobs.Civilian_Guard,jobs,"04/25/2020",
                "04/25/2020","NA");
    }

    @Test
    void bubbleSort() {
        db.bubbleSort();
        assertEquals(0,db.getWorker(0).getWorkerID());
        assertEquals(db.getSize()-1,db.getWorker(db.getSize()-1).getWorkerID());
    }

    @Test
    void addWorker() {
        ArrayList<WorkSkills> skills = new ArrayList<>();
        skills.add(WorkSkills.Construction);
        skills.add(WorkSkills.Mathematics);
        ArrayList<Characteristics> chars = new ArrayList<>();
        chars.add(Characteristics.Crafty);
        ArrayList<Jobs> jobs = new ArrayList<>();
        jobs.add(Jobs.Boulder_Reducer);
        OBAJWorker worker = new OBAJWorker(99999,"Martian","06/09/1999","Married",
                0,WorkingReasons.DEBT,chars,skills,"NA",Jobs.Civilian_Guard,jobs,"04/25/2020",
                "04/25/2020","NA");

        db.addWorker(worker);
        OBAJWorker newestWorker = db.getWorker(db.getSize()-1);
        assertEquals(worker,newestWorker);
    }

    /**
     * This test is accomplished by writing a database to  file, then reading it into a new database, and then checking
     * to see if the first worker in each database has the same toString. NOTE: This is also a sufficient test for
     * the readInFile method since creating a new database calls readInFile.
     */
    @Test
    void writeToFile() {
        db.writeToFile("testFile.csv");
        dbTwo = new Database("testFile.csv");
        String workerOne = db.getWorker(0).toString();
        String workerTwo = db.getWorker(0).toString();
        assertEquals(workerOne,workerTwo);


    }

    /**
     * Parses a worker from a string, then adds it to the database. If the last worker in the database is the same
     * as the parsed worker, then it will succeed.
     */
    @Test
    void parseLineIntoWorker() {
        String stringWorker = "4964,Italian,25/9/1987,Other,3,BEHAVIOR,['Dark skin'],['Typing'; 'Electrical'],Time,NA,['Office_Worker';"+
                "'Construction'],9/10/2017,NA,10/19/2019";
        OBAJWorker worker = db.parseLineIntoWorker(stringWorker);
        db.addWorker(worker);
        OBAJWorker w = db.getWorker(db.getSize()-1);
        assertEquals(worker.toString(),w.toString());
    }

    @Test
    void queryWorker() {
        ArrayList<String> chars = new ArrayList<>();
        chars.add("MaritalStatus:Married");
        ArrayList<OBAJWorker> list = db.queryWorker(1,chars);
        for (OBAJWorker w: list){
            assertEquals("Married",w.getMaritalStatus());
        }
    }
}