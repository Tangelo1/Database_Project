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
        System.out.println("Please enter the following details of the corporate account");

        // Read the name string, ensuring they don't just enter nothing.
        String accountName;
        do {
            System.out.print("Name on Account ");
            accountName = Input.readStr();
        } while (accountName.length() == 0);

        // Get the phone number
        String phone;
        do {
            System.out.println("Account Phone Number ");
            phone = Input.readStr();
        } while (phone.length() == 0);

        System.out.println("Please enter the following information pertaining\n" +
                "to the account's billing address:");

        // Get address line 1, ex: 123 Main Street
        String street;
        do {
            System.out.print("Number and Street ");
            street = Input.readStr();
        } while (street.length() == 0);

        // Read city
        String city;
        do {
            System.out.print("City ");
            city = Input.readStr();
        } while (city.length() == 0);

        // Read state
        String state;
        do {
            System.out.println("State/Province ");
            state = Input.readStr();
        } while (state.length() == 0);

        // Read postal code
        String postalCode;
        do {
            System.out.println("Postal Code ");
            postalCode = Input.readStr();
        } while (postalCode.length() == 0);


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
