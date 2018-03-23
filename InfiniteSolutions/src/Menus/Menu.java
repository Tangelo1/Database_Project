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
     * Displays the first main menu for the application. Presents the user with options to
     * login, track a package, or create an account.
     */
    private static void enterMainMenu() {

        int selectionFlag = -1;

        System.out.print("Main menu:\n\t1: Log in\n\t2: Create Account\n\t3: Track Package\n\t4: Exit\n");

        // Read user selection, sanitizing input values to numbers 1 2 3 or 4.
        do {
            try {
                selectionFlag = Input.readInt();
            } catch (Input.InputException ie) {
                selectionFlag = -1;
            } finally {
                if (selectionFlag > 4 || selectionFlag < 1) {
                    System.out.print("Error: Selection must be 1, 2, 3 or 4.");
                }
            }
        } while (selectionFlag > 4 || selectionFlag < 1);

        // Selection choice of 1 should have the user log in
        switch(selectionFlag) {
            case 1:
                login();
                break;
            case 2:
                createAccount();
                break;
            case 3:
                trackPackage();
                break;
            case 4:
                System.out.println("Goodbye.");
                System.exit(0);
                break;
        }
    }

    /**
     * Called when the user selects to login. Will attempt to log the user in
     */
    private static void login() {
        int ID = 0;
        System.out.print("Please enter your account ID, or -1 to go back to the menu\n");
        try {
            ID = Input.readInt();
        } catch (Input.InputException e) {
            e.printStackTrace();
        }

        //Get account
        Account userAccount = Account.getAccount(ID);

        if(userAccount == null){

            enterMainMenu();
        }

        CustomerMenu.setAccount(userAccount);

    }

    /**
     * Called when the user selects to go down the account creation path.
     * This guides the user through creating a personal account, as only the adminstrators
     * have permission to create corporate accounts.
     */
    private static void createAccount() {
        // TODO
    }

    /**
     * Displays the package tracking menu to the user.
     */
    private static void trackPackage() {
        // TODO
    }
}
