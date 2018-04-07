package Menus;

import DataModels.Account;
import DataModels.Address;
import DataModels.CreditCard;
import DataModels.Location;
import DataModels.Package;
import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        System.out.println("===========================================================");
        System.out.println("===  You are now in the admin SQL console. Be careful.  ===");
        System.out.println("===  You may type 'EXIT' at any time to return to the   ===");
        System.out.println("===  admin menu.                                        ===");
        System.out.println("===========================================================");

        // Get databse connection
        Connection conn = DBDriver.getConnection();

        // The console will read in a SQL command from the user and print results to them continuously.
        String input = "";
        while (true) {

            // Read input and exit when necessary.
            input = Input.readStr();
            if (input.trim().toLowerCase().equals("exit")) break;

            System.out.println();

            // Actually execute the query
            try {
                Statement stmt = conn.createStatement();
                stmt.execute(input);

                // Print results, if any.
                ResultSet rs = stmt.getResultSet();
                if (rs != null)
				{
					int cols = rs.getMetaData().getColumnCount();
					while (rs.next())
					{
						for (int i = 1; i <= cols; i++)
						{
							System.out.print(rs.getString(i) + "\t\t");
						}
						System.out.println();
					}
				}

            } catch (SQLException sqle) {
                System.out.println("Error executing query:\n" + sqle.getMessage());
            }

            System.out.println();
        }
    }

    /**
     * The sub menu procedure for creating corporate accounts.
     */
    private static void createCorporateAccount() {
        // TODO
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
            }
            else {
                // Won't need to catch exception because it is at this point guaranteed to be an integer
                cvv = Integer.parseInt(cvvStr);
            }
        } while (!cvvValid);

        System.out.println("\nCreating account...\n");

        // TODO Actualy create the account tuples & stuff etc. here.
        boolean success = true; // Set to false if something goes wrong creating the accounts.

        try {
            Address address = new Address(-1, street, city, state, postalCode, country);
            CreditCard card = new CreditCard(-1, nameOnCard, number, date, cvv);
            Account account = Account.createCorporate(address, card, accountName, phone);
        } catch(SQLException e) {
            System.out.println("\nAn unexpected error occurred while creating the account. The account could not be created.\n");
            System.out.println("Technical information:\n");
            e.printStackTrace();
            return;
        }

        // If we get to this point the account should have been created successfully.
        System.out.println("=========================================================");
        System.out.println("====           Account Created Successfully          ====");
        System.out.println("=========================================================\n");
    }

    /**
     * Views locations within the network.
     */
    private static void viewLocation() {

    	// Get the location ID.
    	System.out.println("Please enter the ID of the location you'd like to monitor\nor -1 to return to the admin menu.");

    	int locId = 0;
    	boolean locValid = true;
    	do {
    		if (!locValid) {
    			System.out.println("Invalid location id. Please try again.");
			}

			locValid = true;

			try {
				locId = Input.readInt();
			} catch (Input.InputException ie) {
				locValid = false;
				continue;
			}

			// Return when user enters -1.
			if (locId == -1) return;

			// Attempt to find a location with the given ID, if none exists, reiterate.
			if (!Location.exists(locId)) locValid = false;

		} while (!locValid);

    	// Load the location
		Location location = null;
		try {
			location = new Location(locId);
		} catch (SQLException e) {
			System.out.println("An unexpected error occurred while accessing the location information.");
			return;
		}

		// Get all the packages at the location.
		ArrayList<Package> packages = null;
		try {
			packages = location.getPackagesWithin();
		} catch (SQLException e) {
			System.out.println("An unexpected error occurred while accessing the packages within the location.\n" + e.getMessage() + "\n");
			return;
		}

		System.out.println("\nLocation " + locId + ": " + location.getName() + " is a " + location.getType());
		System.out.println(packages.size() + " packages are contained within.");

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
