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

public class Main{
    public static void main(String[] args) {
        System.out.println("Hello World");

    }

    /**
     * Reads in the CSV file and creates an array of workers
     * @param fileName the name of the csv file
     */
    public void readInFile(String fileName){
        final String separator = ",";
        BufferedReader b = null;
        try {
            b = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = b.readLine()) != null) {
                String[] separatedValues = line.split(separator);


            }
        }
        catch (FileNotFoundException f){
            //TODO print error message
        }
        catch (IOException e){
            //TODO print error message
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
}