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

import java.io.DataOutputStream;
import java.util.Scanner;

/**
 * Class that is used to display the menu for the db
 */
public final class OptionMenuUtil {

    /**
     * Prints the menu that shows the user what functions they can do with the database
     * @return
     */
    public int printMenu() {
        System.out.println("Welcome to the database explorer!");
        /**
        System.out.println("Please enter the name of your database file, or press Enter to process default file (.csv extension included): ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        // a default option
        if (fileName.equals("")) {fileName = "defDB.csv";}
        // the option menu
        System.out.println("Processing file [" + fileName + "]");
        **/
        Scanner in = new Scanner(System.in);
        System.out.println("-----------------OPTION MENU--------------------");
        System.out.println("1. Query Database Data");
        System.out.println("2. Sort Database Data");
        System.out.println("3. Modify Worker");
        System.out.println("4. Undo Last Change");
        System.out.println("5: Save Changes");
        System.out.println("0: Exit Application");
        // user's choice
        System.out.printf("Please choose an option above: ");
        String usersChoice = in.nextLine();
        System.out.println("You have chosen option "+usersChoice);
        int ans;
        try {
            ans = Integer.parseInt(usersChoice);
            if (ans < 0 || ans > 5)
                return 0;
            else
                return ans;
        }
        catch (NumberFormatException n){
            return 0;
        }

    }
    public int printMenuClient() {
        System.out.println("------------------------------------------------");
        System.out.println("Welcome to the database explorer!");
        System.out.println("-----------------OPTION MENU--------------------");
        System.out.println("1. Query Database Data");
        System.out.println("2: Chat With Server");
        System.out.println("0: Exit Application");
        System.out.printf("Select Option: ");
        Scanner in = new Scanner(System.in);
        String usersChoice = in.nextLine();
        System.out.println("You have chosen option "+usersChoice);
        int ans;
        try {
            ans = Integer.parseInt(usersChoice);
            if (ans < 0 || ans > 2)
                return 0;
            else
                return ans;
        }
        catch (NumberFormatException n){
            return 0;
        }


    }
    
}