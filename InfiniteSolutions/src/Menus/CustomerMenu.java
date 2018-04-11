package Menus;

import DataModels.Account;
import DataModels.Address;

import java.sql.SQLException;


public class CustomerMenu {

    /** Track package menu selection */
    private static final int TRACK_PACKAGE = 1;

    /** Ship package menu selection*/
    private static final int SHIP_PACKAGE = 2;

    /** Edit account menu selection */
    private static final int EDIT_ACCOUNT = 3;

    /** Menu option for logging out. */
    private static final int LOG_OUT = 4;

    /** Current account that is logged in. */
    private static Account account;

    public static void setAccount(Account account){
        CustomerMenu.account = account;
    };

    public static void enterCustomerMenu(){

        // display the menu options
        System.out.print("Customer Menu:\n\t1. Track Package\n\t2. Ship New Package\n\t3. " +
                "Edit Account Details\n\t4. Log out\n");

        // Stay in the admin menu until the user chooses to exit the admin menu.
        int menuSelection = -1;
        do {
            // Make a menu selection
            menuSelection = Input.makeSelectionInRange(TRACK_PACKAGE, LOG_OUT);

            switch(menuSelection) {
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
                    System.exit(0);
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

        System.out.println("Starting up steps to ship new package");
        System.out.println("Enter the street of where it is being shipped from");

        srcStreet = Input.readStrWhileNotEmpty("Enter the street of where it is being shipped from",50);

        srcCity = Input.readStrWhileNotEmpty("Enter the city of where it is being shipped from",50);

        srcState = Input.readStrWhileNotEmpty("Enter the state of where it is being shipped from",50);

        srcPostal = Input.readStrWhileNotEmpty("Enter the postal of where it is being shipped from",8);

        srcCountry = Input.readStrWhileNotEmpty("Enter the country of where it is being shipped from",50);


        srcAddress = new Address(-1,srcStreet,srcCity,srcState,srcPostal,srcCountry);

        try {
            srcAddress.saveToDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Address dscAddress;
        String dscStreet = "";
        String dscCity = "";
        String dscState = "";
        String dscPostal = "";
        String dscCountry = "";

        System.out.println("Enter the street of where it is being shipped to");

        dscStreet = Input.readStrWhileNotEmpty("Enter the street of where it is being shipped to",50);
        dscCity = Input.readStrWhileNotEmpty("Enter the city of where it is being shipped to",50);
        dscState = Input.readStrWhileNotEmpty("Enter the state of where it is being shipped to",50);
        dscPostal = Input.readStrWhileNotEmpty("Enter the postal of where it is being shipped to",8);
        dscCountry = Input.readStrWhileNotEmpty("Enter the country of where it is being shipped to",50);

        dscAddress = new Address(-1,dscStreet,dscCity,dscState,dscPostal,dscCountry);
        try {
            dscAddress.saveToDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double weight = Double.parseDouble(Input.readStrWhileNotEmpty("What's the weight of the package"));

        System.out.println("What's the type of the package? 1 - letter, 2 - small, 3 - medium, 4 - large");
        int type = Input.makeSelectionInRange(1,4);

        System.out.println("What's the speed of the package? 1 - letter, 2 - small, 3 - medium, 4 - large");


    }

    private static void editAccountDetails(){
        System.out.println("This is a edit account details stub");
    }

}
