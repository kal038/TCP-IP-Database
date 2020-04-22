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
        System.out.println("Welcome to the database explorer!");
        System.out.println("Please enter the name of your database file, or press Enter to process default file (.csv extension included): ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        // a default option
        if (fileName.equals("")) {fileName = "defDB.csv";}
        // the option menu
        System.out.println("Processing file [" + fileName + "]");
        System.out.println("-----------------OPTION MENU--------------------");
        System.out.println("1. Query Database Data");
        System.out.println("2. Sort Database Data");
        System.out.println("3. Modify Date (Skip ahead in time)");
        System.out.println("4. Undo Last Change");
        System.out.println("5: Save Changes");
        System.out.println("0: Exit Application");
        // user's choice
        System.out.printf("Please choose an option above: ");
        String usersChoice = in.nextLine();
        System.out.println("You have chosen option "+usersChoice);


    }

    public static void main(String[] args) {
        printMenu();
    }
}