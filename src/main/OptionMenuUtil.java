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

public class OptionMenuUtil {

    public static void printMenu() {
        System.out.println("Welcome to the database explorer!");
        System.out.println("Please enter the name of your database file (.csv extension included): ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        System.out.println("-----------------OPTION MENU--------------------");



    }
}