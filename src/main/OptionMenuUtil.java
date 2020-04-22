/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2020
 * Instructor: Prof. Chris Dancy
 *
 * Name: YOUR NAME
 * Section: YOUR SECTION
 * Date: 3/6/20
 * Time: 8:16 AM
 *
 * Project: csci205_hw1
 * Package: main
 * Class: OptionMenu
 *
 * Description:
 *
 * ****************************************
 */
package
        main;

import java.util.Scanner;

public final class OptionMenuUtil {


    public static void printMenu() {
        String option1 = "1. Query Database Data\n";
        String option2 = "2. Sort Database Data\n";
        String option3 = "3. Modify Date (Skip ahead in time)\n";
        String option4 = "4. Undo Last Change\n";
        String option5 = "5: Save Changes\n";
        System.out.println("Welcome to the database explorer!");
        System.out.println("Please enter the name of your database file, or press Enter to process default file (.csv extension included): ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        // a default option
        if (fileName.equals("")) {fileName = "defDB.csv";}
        // the option menu
        System.out.println("Processing file [" + fileName + "]");
        System.out.println("-----------------OPTION MENU--------------------");
        System.out.printf("%s", option1);
        System.out.printf("%s", option2);
        System.out.printf("%s", option3);
        System.out.printf("%s", option4);
        System.out.printf("%s", option5);
        System.out.println("0: Exit Application");
        // user's choice
        System.out.printf("Please choose an option above: ");
        String usersChoice = in.nextLine();
        System.out.println("You have chosen option "+ usersChoice);


    }

}