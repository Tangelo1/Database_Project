package Menus;

import DataModels.Account;
import DataModels.Address;
import DataModels.TrackingEvent;

import java.sql.SQLException;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Comparator;

import static DataModels.TrackingEvent.*;

public class CustomerMenu {

    /** Track package menu selection */
    private static final int TRACK_PACKAGE = 1;

    /** Ship package menu selection*/
    private static final int SHIP_PACKAGE = 2;

    /** Edit account menu selection */
    private static final int EDIT_ACCOUNT = 3;

    /** Menu option for logging out. */
    private static final int LOG_OUT = 4;

    private static Account account;

    //TODO need a setAccount method

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

    static void trackPackage(){
        System.out.println("Please enter your tracking ID");
        String trackingID = Input.readStr();
        ArrayList<TrackingEvent> packages = null;
        int flag = 0;
        while(flag == 0) {
            try {
                packages = getEventsForPackage(Integer.parseInt(trackingID));
                if (packages == null) {
                    System.out.println("Invalid ID, please try again");
                } else {
                    flag = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(packages, new Comparator<TrackingEvent>() {
            @Override
            public int compare(TrackingEvent o1, TrackingEvent o2) {

                if(o1.getTime().after(o2.getTime())){
                    return 1;
                }else if(o1.getTime().before(o2.getTime())){
                    return -1;
                }else{
                    return 0;
                }
            }
        });

        for(TrackingEvent event : packages){
            try {
                System.out.println("At " + event.getTime() + "the package was at "+event.getLocation().getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return;
    }

    static void shipNewPackage(){
        //Need To first build a Address, both destAddress and srcAddress
        Address srcAddress;
        String srcStreet;
        String srcCity;
        String srcState;
        String srcPostal;
        String srcCountry;

        System.out.println("Starting up steps to ship new package");
        System.out.println("Enter the street of where it is being shipped from");
        int flag = 0;
        while(flag == 0) {
            srcStreet = Input.readStr();
            if (srcStreet.length() > 50) {
                System.out.println("Invalid street, please try again");
            } else {
                flag = 1;
            }
        }

        System.out.println("Enter the city of where it is being shipped from");
        flag = 0;
        while(flag == 0) {
            srcCity = Input.readStr();
            if (srcCity.length() > 50) {
                System.out.println("Invalid city, please try again");
            } else {
                flag = 1;
            }
        }

        System.out.println("Enter the state of where it is being shipped from");
        flag = 0;
        while(flag == 0) {
            srcState = Input.readStr();
            if (srcState.length() > 50) {
                System.out.println("Invalid state, please try again");
            } else {
                flag = 1;
            }
        }

        System.out.println("Enter the postal of where it is being shipped from");
        flag = 0;
        while(flag == 0) {
            srcPostal = Input.readStr();
            if (srcPostal.length() != 8) {
                System.out.println("Invalid postal, please try again");
            } else {
                flag = 1;
            }
        }

        System.out.println("Enter the country of where it is being shipped from");
        flag = 0;
        while(flag == 0) {
            srcCountry = Input.readStr();
            if (srcCountry.length() > 50) {
                System.out.println("Invalid country, please try again");
            } else {
                flag = 1;
            }
        }

        //TODO create the address

        return;
    }

    static void editAccountDetails(){
        System.out.println("This is a edit account details stub");
        return;
    }

}
