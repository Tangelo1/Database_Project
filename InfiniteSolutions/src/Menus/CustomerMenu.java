package Menus;

import DataModels.*;
import DataModels.Package;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import DataModels.ShippingCostMultiplier;


public class CustomerMenu {
    private static HashMap<String, Double> costList;

    /**
     * Track package menu selection
     */
    private static final int TRACK_PACKAGE = 1;

    /**
     * Ship package menu selection
     */
    private static final int SHIP_PACKAGE = 2;

    /**
     * Edit account menu selection
     */
    private static final int EDIT_ACCOUNT = 3;

    /**
     * Menu option for logging out.
     */
    private static final int LOG_OUT = 4;

    /**
     * Current account that is logged in.
     */
    private static Account account;

    public static void setAccount(Account account) {
        CustomerMenu.account = account;
    }

    ;

    public static void enterCustomerMenu() {

        try {
            costList = ShippingCostMultiplier.getCostList();
        } catch (SQLException e) {
            System.out.println("Cannot get cost list");
        }

        System.out.println("\n~~ Welcome " + account.getName() + "! ~~\n");

        int menuSelection = -1;
        do {
            // display the menu options
            System.out.println("Customer Menu:\n\t1. Track Package\n\t2. Ship New Package\n\t3. " +
                    "Edit Account Details\n\t4. Log out\n");

            // Stay in the admin menu until the user chooses to exit the admin menu.


            // Make a menu selection
            menuSelection = Input.makeSelectionInRange(TRACK_PACKAGE, LOG_OUT);

            switch (menuSelection) {
                case TRACK_PACKAGE:
                    trackPackage();
                    break;
                case SHIP_PACKAGE:
                    shipNewPackage();
                    break;
                case EDIT_ACCOUNT:
                    editAccountDetails();
                    break;
                case LOG_OUT:
                    System.out.println("Goodbye.");
                    break;
            }

        } while (menuSelection != LOG_OUT);
    }

    private static void trackPackage() {
        // Already implemented in Menu, no need to reimplement
        Menu.trackPackage();
    }

    private static void shipNewPackage() {
        //Need To first build a Address, both destAddress and srcAddress
        Address srcAddress;
        String srcStreet = "";
        String srcCity = "";
        String srcState = "";
        String srcPostal = "";
        String srcCountry = "";

        System.out.println("\nSource Address: ");

        srcStreet = Input.readStrWhileNotEmpty("\tStreet", 50);
        srcCity = Input.readStrWhileNotEmpty("\tCity", 50);
        srcState = Input.readStrWhileNotEmpty("\tState", 50);
        srcPostal = Input.readStrWhileNotEmpty("\tPostal Code", 8);
        srcCountry = Input.readStrWhileNotEmpty("\tCountry", 50);

        srcAddress = new Address(-1, srcStreet, srcCity, srcState, srcPostal, srcCountry);

        try {
            srcAddress.saveToDB();
        } catch (SQLException e) {
            System.out.println("Source Address could not be saved.");
        }

        Address dscAddress;
        String dscStreet = "";
        String dscCity = "";
        String dscState = "";
        String dscPostal = "";
        String dscCountry = "";

        System.out.println("\nDestination Address: ");

        dscStreet = Input.readStrWhileNotEmpty("\tStreet", 50);
        dscCity = Input.readStrWhileNotEmpty("\tCity", 50);
        dscState = Input.readStrWhileNotEmpty("\tState", 50);
        dscPostal = Input.readStrWhileNotEmpty("\tPostal Code", 8);
        dscCountry = Input.readStrWhileNotEmpty("\tCountry", 50);

        dscAddress = new Address(-1, dscStreet, dscCity, dscState, dscPostal, dscCountry);
        try {
            dscAddress.saveToDB();
        } catch (SQLException e) {
            System.out.println("Destination Address could not be saved.");
        }

        boolean isDouble;
        double weight = 0;
        do {
            try {
                System.out.println("\nWeight");
                weight = Input.readInt();
                isDouble = true;
            } catch (Exception e) {
                System.out.println("Must be a number.");
                isDouble = false;
            }
        }
        while (!isDouble);


        System.out.println("\nType? (1 - Letter, 2 - Small, 3 - Medium, 4 - Large)");
        int type = Input.makeSelectionInRange(1, 4);

        String pkgType = null;
        switch (type) {
            case 1:
                pkgType = "Letter";
                break;
            case 2:
                pkgType = "Small";
                break;
            case 3:
                pkgType = "Medium";
                break;
            case 4:
                pkgType = "Large";
                break;
        }

        System.out.println("\nSpeed? (1 - No Rush, 2 - Standard, 3 - Expedited, 4 - Overnight)");
        int speed = Input.makeSelectionInRange(1, 4);

        String pkgSpeed = null;
        switch (speed) {
            case 1:
                pkgSpeed = "NoRush";
                break;
            case 2:
                pkgSpeed = "Standard";
                break;
            case 3:
                pkgSpeed = "Expedited";
                break;
            case 4:
                pkgSpeed = "Overnight";
                break;
        }

        int value = 0;
        boolean isInternational = false;
        String items = null;

        // If the package is international
        if (!dscCountry.toUpperCase().equals(srcCountry.toUpperCase())) {
            isInternational = true;

            boolean isInt;
            do {
                try {
                    System.out.println("\nDeclared Value");
                    value = Input.readInt();
                    isInt = true;
                } catch (Exception e) {
                    System.out.println("Must be a number.");
                    isInt = false;
                }
            }
            while (!isInt);

            items = Input.readStrWhileNotEmpty("\nDescription of Items", 75);
        }

        System.out.println("\nContains Hazardous Materials?");
        boolean isHazardous = Input.makeYesNoChoice();

        Package p = new Package(-1, weight, pkgType, pkgSpeed, value,
                dscAddress.getId(), srcAddress.getId(), isHazardous, isInternational);

        try {
            p.saveToDB();
        } catch (SQLException e) {
            System.out.println("Could not save package.");
            return;
        }

        if (isInternational) {
            ManifestItem m = new ManifestItem(p.getTrackingId(), items);
            try {
                m.saveToDB();
            } catch (SQLException e) {
                System.out.println("Could not save manifest item.");
                return;
            }
        }

        // Create the shipping order
        double cost = 0.0;
        try {
            ShippingOrder order = Package.createPackageOrder(p, account);
            cost = order.getCost();
        } catch(SQLException e) {
            System.out.println("An error occurred while creating the shipping order.");
        }


        System.out.println("\nTracking No.\tWeight\tType\tSpeed");
        System.out.printf("%d\t\t\t%.2f\t%s\t%s\n", p.getTrackingId(), p.getWeight(), p.getType(), p.getSpeed());
        System.out.println("\nShipping from: \n" + srcAddress);
        System.out.println("\nShipping to: \n" + dscAddress);
        System.out.println("\nHazardous: " + isHazardous);
        System.out.println("\nYour total is $" + cost);

        System.out.println("\nPackage order created successfully!\n");

    }

    private static void editAccountDetails() {
        System.out.println("Which detail do you want to change: ");
        System.out.println("\n\t1. Name\n\t2. Phone Number\n\t3. Credit Card\n\t4. Address\n\t5. Exit");

        int choice = -1;
        do {
            choice = Input.makeSelectionInRange(1, 5);

            switch (choice) {
                case 1:
                    editName();
                    break;
                case 2:
                    editPhone();
                    break;
                case 3:
                    editCreditCard();
                    break;
                case 4:
                    editAddress();
                    break;
                case 5:
                    return;
            }
        } while (choice != 5);

    }

    private static void editAddress() {

        System.out.println("\nEnter your new information: ");

        // Get address line 1, ex: 123 Main Street
        String street = Input.readStrWhileNotEmpty("\tNumber and Street", 50);

        // Read city
        String city = Input.readStrWhileNotEmpty("\tCity", 50);

        // Read state
        String state = Input.readStrWhileNotEmpty("\tState/Province", 50);

        // Read postal code
        String postalCode = Input.readStrWhileNotEmpty("\tPostal Code", 8);

        // Read country
        String country = Input.readStrWhileNotEmpty("\tCountry", 50);

        account.updateBillingAddress(new Address(account.getBillingAddressId(), street, city, state, postalCode, country));

        System.out.println("\nUpdate sucessful.\n");
    }

    private static void editCreditCard() {
        System.out.println("\nEnter your new information: ");

        String nameOnCard = Input.readStrWhileNotEmpty("\tName on Card", 50);

        // Validate the card number
        String number;
        boolean numberValid;
        do {
            numberValid = true;
            number = Input.readStrWhileNotEmpty("\tCard Number");
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
            date = Input.readStrWhileNotEmpty("\tExpiration Date (MM/YY)");
            if (!Input.isDate(date, "MM/YY")) {
                dateValid = false;
                System.out.println("\tError: Please enter the date in the MM/YY format.");
            }
        } while (!dateValid);

        // Read and validate the cvv.
        int cvv = 0;
        boolean cvvValid;
        do {
            cvvValid = true;
            String cvvStr = Input.readStrWhileNotEmpty("\tCVV");
            if (cvvStr.length() > 4 || !Input.isNumeric(cvvStr)) {
                cvvValid = false;
                System.out.println("Error: CVV Must be a number with no more than 4 digits.");
            } else {
                // Won't need to catch exception because it is at this point guaranteed to be an integer
                cvv = Integer.parseInt(cvvStr);
            }
        } while (!cvvValid);

        account.updateCreditCard(new CreditCard(account.getCreditCardId(), nameOnCard, number, date, cvv));

        System.out.println("\nUpdate sucessful.\n");
    }

    private static void editPhone() {
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

        account.updatePhone(phone);

        System.out.println("\nUpdated Sucessfully.\n");
    }

    private static void editName() {
        String accountName = Input.readStrWhileNotEmpty("Name on Account", 50);
        account.updateName(accountName);

        System.out.println("\nUpdated Sucessfully.\n");
    }

}
