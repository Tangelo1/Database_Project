package Menus;

import DataModels.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main entry point for the front facing user interface of the application
 * This class displays the initial menu and set of menu options for the user to select when first
 * launching the application. On choosing to log in, the user will be redirected to the appropriate menu.
 */
public class Menu {

    int accountID;
    int trackingID;

    /**
     * Main entry point for the menu system.
     * @param args unused command line args.
     */
    public static void main(String[] args) {

        // Welcome message.
        printWelcome();

        // Show the main menu options and wait for the user to make a selection
        enterMainMenu();
    }

    /**
     * Prints some welcome message.
     */
    private static void printWelcome() {
        System.out.println("\n\n\tWelcome to the Infinite Solutions Package Management System\n");
    }

    /**
     *
     */
    private static void enterMainMenu() {

        int selectionFlag;
        int accountID = 0;
        int trackingID = 0;

        Scanner reader = new Scanner(System.in);

        System.out.print("Main menu:\n\t1: Log in\n\t2: Track Package\n\t3: Create Personal Account\n> ");

        selectionFlag = reader.nextInt();

        if(selectionFlag == 1){
            System.out.println("Enter your account ID, -1 if you are an admin");
            accountID = reader.nextInt();
        }

        else if(selectionFlag == 2){
            System.out.println("Enter your tracking ID");
            trackingID = reader.nextInt();
        }

        else if(selectionFlag == 3){

        }

    }

    private static Account newAccount(){
        System.out.println("Enter 1 for personal account or 2 for corporate account");
        //Account newACc = new Account(null,)

        return null;
    }

}
