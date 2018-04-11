package Menus;

import DataModels.*;
import Driver.DBDriver;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The main entry point for the front facing user interface of the application
 * This class displays the initial menu and set of menu options for the user to select when first
 * launching the application. On choosing to log in, the user will be redirected to the appropriate menu.
 */
public class Menu {

    /**
     * Main entry point for the menu system.
     *
     * @param args unused command line args.
     */
    public static void main(String[] args) {
        //Catch IllegalState Exception
        DBDriver driver = new DBDriver();

        // Welcome message.
        printWelcome();

        // Show the main menu options and wait for the user to make a selection
        enterMainMenu();

        driver.closeConnection();
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
        // Stay in the main menu indefinitely.
        while (true) {
            int selectionFlag = -1;

            System.out.print("Main menu:\n\t1: Log in\n\t2: Create Account\n\t3: Track Package\n\t4: Exit\n");

            // Read user selection, sanitizing input values to numbers 1 2 3 or 4.
            selectionFlag = Input.makeSelectionInRange(1, 4);

            // Selection choice of 1 should have the user log in
            switch (selectionFlag) {
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
    }

    /**
     * Called when the user selects to login. Will attempt to log the user in
     */
    private static void login() {
        int ID = 0;
        System.out.print("Please enter your account ID, otherwise -1 to go back to the menu, " +
                "-2 to go to the admin menu, or -3 to go to the warehouse menu.\n");
        try {
            ID = Input.readInt();
        } catch (Input.InputException e) {
            e.printStackTrace();
        }

        // Log into admin menu
        if (ID == -2) {
            AdminMenu.enterMainAdminMenu();
        } else if (ID == -3) {
            WarehouseMenu.enterMainWarehouseMenu();
        } else if (ID == -1) {
            return;
        } else {
            Account userAccount = null;
            try {
                // Get account
                userAccount = new Account(ID);
            } catch (SQLException e) {
                // Don't need to do anything here.
            }

            // If the user account doesn't exist, return to the previous menu.
            if (userAccount == null) {
                System.out.println("\nError: Unknown Account Number.");
                return;
            } else {
                CustomerMenu.setAccount(userAccount);
                CustomerMenu.enterCustomerMenu();
            }
        }
    }

    /**
     * Called when the user selects to go down the account creation path.
     * This guides the user through creating a personal account, as only the adminstrators
     * have permission to create corporate accounts. After creation, will be logged in
     */
    private static void createAccount() {
        System.out.println("----- Basic Account Info -----");

        // Read the name string, ensuring they don't just enter nothing.
        String accountName = Input.readStrWhileNotEmpty("Name on Account", 50);

        // Get the phone number
        String phone;
        boolean phoneValid;
        do {
            phoneValid = true;
            phone = Input.readStrWhileNotEmpty("Account Phone (10 digits)");
            if (!Input.isPhone(phone)) {
                phoneValid = false;
                System.out.println("Error: \'" + phone + "\' is not a valid phone number.");
            } else {
                // Format the phone number into a string of 10 digits so that it can be stored in the database.
                phone = Input.formatPhoneNumber(phone);
            }
        } while (!phoneValid);

        // Get address information
        System.out.println("\n----- Billing Address Info -----");

        // Get address line 1, ex: 123 Main Street
        String street = Input.readStrWhileNotEmpty("Number and Street", 50);

        // Read city
        String city = Input.readStrWhileNotEmpty("City", 50);

        // Read state
        String state = Input.readStrWhileNotEmpty("State/Province", 50);

        // Read postal code
        String postalCode = Input.readStrWhileNotEmpty("Postal Code", 8);

        // Read country
        String country = Input.readStrWhileNotEmpty("Country", 50);

        // Now get details pertaining to the payment method of the account.
        System.out.println("\n----- Payment Info -----");

        String nameOnCard = Input.readStrWhileNotEmpty("Name on Card", 50);

        // Validate the card number
        String number;
        boolean numberValid;
        do {
            numberValid = true;
            number = Input.readStrWhileNotEmpty("Card Number");
            number = number.replaceAll("\\s+", "");
            if (number.length() > 16 || !Input.isNumeric(number)) {
                numberValid = false;
                System.out.println("Error: invalid credit card number.");
            }
        } while (!numberValid);

        // Read the expiration date. Ensure that it is the proper MM/YY format.
        String date;
        boolean dateValid;
        do {
            dateValid = true;
            date = Input.readStrWhileNotEmpty("Expiration Date (MM/YY)");
            if (!Input.isDate(date, "MM/YY")) {
                dateValid = false;
                System.out.println("Error: Please enter the date in the MM/YY format.");
            }
        } while (!dateValid);

        // Read and validate the cvv.
        int cvv = 0;
        boolean cvvValid;
        do {
            cvvValid = true;
            String cvvStr = Input.readStrWhileNotEmpty("CVV");
            if (cvvStr.length() > 4 || !Input.isNumeric(cvvStr)) {
                cvvValid = false;
                System.out.println("Error: CVV Must be a number with no more than 4 digits.");
            } else {
                // Won't need to catch exception because it is at this point guaranteed to be an integer
                cvv = Integer.parseInt(cvvStr);
            }
        } while (!cvvValid);

        System.out.println("\nCreating account...\n");

        try {
            Address address = new Address(-1, street, city, state, postalCode, country);
            CreditCard card = new CreditCard(-1, nameOnCard, number, date, cvv);
            Account.createPersonal(address, card, accountName, phone);
        } catch (SQLException e) {
            System.out.println("\nAn unexpected error occurred while creating the account. The account could not be created.\n");
            System.out.println("Technical information:\n");
            e.printStackTrace();
            return;
        }

        System.out.println("=========================================================");
        System.out.println("====           Account Created Successfully          ====");
        System.out.println("=========================================================\n");
    }

    /**
     * Displays the package tracking menu to the user.
     */
    public static void trackPackage() {
        DataModels.Package p = null;
        Integer trackingID;

        System.out.println("\nEnter your tracking number:");
        try {
            trackingID = Input.readInt();
        }
        catch (Exception e) {
            System.out.println("\nInvalid tracking number.\n");
            return;
        }

        try {
            p = new DataModels.Package(trackingID);
        } catch (Exception e) {
        }

        ArrayList<TrackingEvent> history = null;

        try {
            history = p.getHistory();
        } catch (Exception e) {
            System.out.println("Package doesn't have any tracking history.");
        }

        for (TrackingEvent e : history) {
            try {
                System.out.printf("\n%s -- %s, %s", e.getTime(), e.getLocation().getName(), e.getStatus());
            } catch (Exception ex) {
                System.out.println("Could not find tracking history event");
            }
        }

        System.out.println("\n");
    }
}
