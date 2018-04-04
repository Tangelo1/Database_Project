package Menus;

/**
 * Menu System for the administrator.
 */
public class AdminMenu {

    /** Menu choice to enter the sql console. */
    private static final int SQL_CONSOLE = 1;

    /** Corporate account menu selection */
    private static final int CORPORATE_ACCOUNT = 2;

    /** Monitor network locations menu selection */
    private static final int MONITOR_NETWORK = 3;

    /** View package details menu selection. */
    private static final int PACKAGE_DETAILS = 4;

    /** The order details menu selection */
    private static final int ORDER_DETAILS = 5;

    /** The bill customers menu selection. */
    private static final int BILL_CUSTOMERS = 6;

    /** Menu option for loging out. */
    private static final int LOG_OUT = 7;

    /**
     * Displays and operates over the main admin menu.
     */
    public static void enterMainAdminMenu() {
        // Stay in the admin menu until the user chooses to exit the admin menu.
        int menuSelection = -1;
        do {
            // display the menu options
            System.out.print("Administration Menu:\n\t1. Enter the SQL Console\n\t2. Create New Corporate Account\n\t3. Monitor Distribution Network\n\t4. View Package Details\n\t5. View Orders\n\t6. Charge Corporate Customers\n\t7. Log Out\n");


            // Make a menu selection
            menuSelection = Input.makeSelectionInRange(SQL_CONSOLE, LOG_OUT);

            switch(menuSelection) {
                case SQL_CONSOLE:
                    enterSQLConsole();
                    break;
                case CORPORATE_ACCOUNT:
                    createCorporateAccount();
                    break;
                case MONITOR_NETWORK:
                    viewLocation();
                    break;
                case PACKAGE_DETAILS:
                    viewOrders();
                    break;
                case ORDER_DETAILS:
                    chargeCorporateCustomers();
                    break;
                case BILL_CUSTOMERS:
                    chargeCorporateCustomers();
                    break;
                case LOG_OUT:
                    System.out.println("Goodbye.");
                    return;
            }

        } while (menuSelection != LOG_OUT);
    }

    /**
     * Delivers user to the SQL console.
     */
    private static void enterSQLConsole() {
        // TODO
        System.out.println("SQL Console Method Stub");
    }

    /**
     * The sub menu procedure for creating corporate accounts.
     */
    private static void createCorporateAccount() {
        // TODO
        System.out.println("----- Basic Account Info -----");

        // Read the name string, ensuring they don't just enter nothing.
        boolean accountNameValid;
        String accountName;
        do {
            accountNameValid = true;
            accountName = Input.readStrWhileNotEmpty("Name on Account");
            if (accountName.length() > 50) {
                accountNameValid = false;
                System.out.println("Error: Name on account may not exceed 50 characters.");
            }
        } while (!accountNameValid);

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
        String street = Input.readStrWhileNotEmpty("Number and Street");

        // Read city
        String city = Input.readStrWhileNotEmpty("City");

        // Read state
        String state = Input.readStrWhileNotEmpty("State/Province");

        // Read postal code
        String postalCode = Input.readStrWhileNotEmpty("Postal Code");

        // Read country
        String country = Input.readStrWhileNotEmpty("Country");

        // Now get details pertaining to the payment method of the account.
        System.out.println("\n----- Payment Info -----");

        String nameOnCard = Input.readStrWhileNotEmpty("Name on Card");

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
        String cvv;
        boolean cvvValid;
        do {
            cvvValid = true;
            cvv = Input.readStrWhileNotEmpty("CVV");
            if (cvv.length() > 4 || !Input.isNumeric(cvv)) {
                cvvValid = false;
                System.out.println("Error: CVV Must be a number with no more than 4 digits.");
            }
        } while (!cvvValid);

        System.out.println("\nCreating account...\n");

        // TODO Actualy create the account tuples & stuff etc. here.
        boolean success = true; // Set to false if something goes wrong creating the accounts.

        if (!success) {
            System.out.println("An unexpected error was encountered while creating the account.");
            System.out.println("The account has not been created.");
            return;
        }

        // If we get to this point the account should have been created successfully.
        System.out.println("=========================================================");
        System.out.println("====          Account Created Successfully           ====");
        System.out.println("=========================================================\n");
    }

    /**
     * Views locations within the network.
     */
    private static void viewLocation() {
        // TODO
        System.out.println("View Location Method Stub");
    }

    /**
     * Sub menu for viewing orders for customers and date ranges.
     */
    private static void viewOrders() {
        // TODO
        System.out.println("View Orders Method Stub");
    }


    /**
     * Sub menu for charging all customers.
     */
    private static void chargeCorporateCustomers() {
        // TODO
        System.out.println("Bill Corporate Customers Method Stub");
    }
}
