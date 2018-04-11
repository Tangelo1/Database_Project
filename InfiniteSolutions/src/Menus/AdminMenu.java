package Menus;

import DataModels.*;
import DataModels.Package;
import Driver.DBDriver;

import javax.sound.midi.Track;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            System.out.print("\nAdministration Menu:\n\t1. Enter the SQL Console\n\t2. Create New Corporate Account\n\t3. Monitor Distribution Network\n\t4. View Package Details\n\t5. View Orders\n\t6. Charge Corporate Customers\n\t7. Log Out\n");


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
                	viewPackageDetails();
                	break;
				case ORDER_DETAILS:
                    viewOrders();
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


		System.out.println("\nLocation " + locId + ": " + location.getName() + " is a " + location.getTypeName());
		System.out.println(packages.size() + " packages are contained within.");

		if (packages.size() > 0) {
			System.out.println("\nWould you like to see the list of packages contained within?");
			if (Input.makeYesNoChoice()) {
				System.out.println("Tracking No.\tWeight\tType\tSpeed\n");
				for (Package p : packages) {
					System.out.printf("%d\t\t\t%.2f\t%s\t%s\n", p.getTrackingId(), p.getWeight(), p.getType(), p.getSpeed());
				}
			}
		}
    }

	/**
	 * Shows a menu for getting the details on a package.
	 */
	private static void viewPackageDetails() {

		// Need to get the package details.
		System.out.println("Please enter the tracking ID of the package");
		int trackingId = -1;
		do {
			// Get a valid tracking number
			try {
				trackingId = Input.readInt();
			} catch (Input.InputException ie) {
				System.out.println("Invalid number");
				continue;
			}

			// Make sure the package with that number exists
			if (!Package.exists(trackingId)) {
				System.out.println("Tracking number does not exist.");
				trackingId = -1;
			}

		} while (trackingId == -1);

		Package pack = null;
		ShippingOrder order = null;
		ArrayList<TrackingEvent> history = null;
		ArrayList<ManifestItem> manifest = null;
		Address origin = null;
		Address destination = null;
		try {
			pack = new Package(trackingId);
			order = pack.getOrder();
			history = pack.getHistory();
			manifest = pack.getManifest();
			origin = pack.getOrigin();
			destination = pack.getDestination();
		} catch(SQLException e) {
			System.out.println("An unexpected error occurred when loading the package information");
			return;
		}

		// Show general package information.
		System.out.println("Package " + pack.getTrackingId() + " information:\n");

		System.out.printf("Order created on %s by account number %d for $%.2f\n", order.getDateCreated(), order.getAccountId(), order.getCost());
		System.out.println(pack.isHazard() ? "Package contains hazardous material" : "Package does not contain hazardous material.");
		if (pack.isInternational()) {
			// Show customs information about the package, if it's international.
			System.out.println("Package will be shipped internationally. Customs information:");
			System.out.printf("\tValue = $%.2f\n", pack.getValue());
			System.out.println("\tManifest:");
			if (manifest.size() > 0) {
				for (ManifestItem item : manifest) {
					System.out.printf("\t\t%s\n", item.getName());
				}
			}
			else {
				System.out.println("\t\tNo Items listed in manifest.");
			}
		}
		else {
			System.out.println("Package will be shipped domestically.");
		}

		// Print the origin and destination addresses of the package
		System.out.println();
		System.out.println("Source Address:");
		System.out.println("---------------");
		System.out.println(origin.toString() + "\n");

		System.out.println("Destination Address:");
		System.out.println("--------------------");
		System.out.println(destination.toString() + "\n");

		// Print the tracking history.
		System.out.println("Tracking History:");
		System.out.println("-----------------");
		System.out.println();
		System.out.println("Date\tLocation\tEvent");
		System.out.println("-----------------------------");

		try {
			for (TrackingEvent event : history) {
				System.out.println(event.getTime() + "\t" + event.getLocation().getName() + "\t" + event.getStatus());
			}
		} catch (SQLException e) {
			System.out.println("An unexpected error occurred while getting the tracking history of the package.");
		}

		System.out.println();
	}

    /**
     * Sub menu for viewing orders for customers and date ranges.
     */
    private static void viewOrders() {
    	System.out.println("Please enter the account number to view orders for\nor enter -1 to return to the menu.");

    	// Get a valid account number from the user
    	int accountNumber = 0;
    	boolean accountValid = true;
    	do {
    		if (!accountValid) {
    			System.out.println("Invalid account number. Please try again.");
			}

			accountValid = true;

    		try {
    			accountNumber = Input.readInt();
			} catch (Input.InputException ie) {
    			accountValid = false;
    			continue;
			}

			// return when the user enters -1
			if (accountNumber == -1) return;

    		if (!Account.exists(accountNumber)) accountValid = false;

		} while (!accountValid);

    	// Get a reference to the user account.
    	Account account = null;
    	try {
			account = new Account(accountNumber);
		} catch (SQLException e) {
    		System.out.println("An unexpected error occurred while accessing the account information.");
    		return;
		}

		// Get a time frame from the user to read the dates from.
		System.out.println("Enter the starting date of the order range");
    	String dateStr = "";
    	Date startDate = null;
    	boolean dateValid = true;
    	do {
    		dateValid = true;
    		dateStr = Input.readStrWhileNotEmpty("YYYY-MM-DD");

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startDate = format.parse(dateStr);
				if (startDate.after(new Date())) {
					System.out.println("Start date cannot be in the future.");
					dateValid = false;
				}
			} catch(ParseException pe) {
				System.out.println("Invalid format; please enter the date in the format YYYY-MM-DD");
				dateValid = false;
			}
		} while (!dateValid);

    	// Get the ending date.
		System.out.println("Enter the ending date of the order range");
		Date endDate = null;
		do {
			dateValid = true;
			dateStr = Input.readStrWhileNotEmpty("YYYY-MM-DD");

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				endDate = format.parse(dateStr);
				if (endDate.before(startDate)) {
					System.out.println("End date cannot be before the starting date.");
					dateValid = false;
				}
			} catch(ParseException pe) {
				System.out.println("Invalid format; please enter the date in the format YYYY-MM-DD");
				dateValid = false;
			}
		} while (!dateValid);

		// Get the orders in the date range.
		ArrayList<ShippingOrder> orders = null;
		try {
			System.out.println("Test: "+ new Timestamp(startDate.getTime()));
			orders = ShippingOrder.getOrdersForAccount(account, new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		} catch(SQLException se) {
			System.out.println("An unexpected error occurred when loading the order information.");
		}

		// Print some info about the number of orders found.
		System.out.println("Found " + orders.size() + " orders for account " + accountNumber + " in date range " +
			startDate.toString() + " to " + endDate.toString() + "\n");

		// Print and format the details of each order
		System.out.println("Order\tPackage\tAccount\tDate\tTotal\n");
		for(ShippingOrder order : orders) {
			System.out.printf("%d\t%d\t%d\t%s\t$%.2f\n",
					order.getOrderId(), order.getTrackingId(), order.getAccountId(), order.getDateCreated(), order.getCost());
		}
    }


    /**
     * Sub menu for charging all customers.
     */
    private static void chargeCorporateCustomers() {
        // TODO
        System.out.println("Bill Corporate Customers Method Stub");
    }
}
