package Menus;

import DataModels.*;
import DataModels.Package;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Menu class for the warehouse user
 */
public class WarehouseMenu {

    /**
     * Move package menu selection
     */
    private static final int MOVE_PACKAGE = 1;

    /**
     * Mark package as delivered menu selection
     */
    private static final int MARK_PACKAGE_AS_DELIVERED = 2;

    /**
     * Get all the packages in a location menu selection
     */
    private static final int GET_PACKAGES_IN_LOCATION = 3;

    /**
     * Exit menu selection
     */
    private static final int EXIT = 4;

    /**
     * Gets and prints all the packages in a given location
     */
    private static void getAllPackagesInLocation() {
        boolean goodID;
        do {

            System.out.println("\nEnter a location ID: ");
            int locationID = 0;
            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID entered.");
            }

            try {
                Location l = new Location(locationID);
                ArrayList<Package> pkgs = l.getPackagesWithin();

                System.out.println("\nPackages contained in " + l.getName() + ": ");

                for (Package p : pkgs) {
                    System.out.println("\tPackage: " + p.getTrackingId());
                }


                goodID = true;
            } catch (Exception e) {
                System.out.println("\nLocation ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);
    }


    /**
     * Main menu for the warehouse users
     */
    public static void enterMainWarehouseMenu() {
        int menuSelection;
        do {
            // display the menu options
            System.out.print("\nWarehouse Menu:\n\t1. Move A Package To A New Location\n\t2. Mark Package As Delivered\n\t" +
                    "3. Get All Packages In A Locationn\n\t4. Log Out\n");

            // Make a menu selection
            menuSelection = Input.makeSelectionInRange(MOVE_PACKAGE, EXIT);

            switch (menuSelection) {
                case MOVE_PACKAGE:
                    movePackageToLocation();
                    break;
                case MARK_PACKAGE_AS_DELIVERED:
                    markPackageAsDelivered();
                    break;
                case GET_PACKAGES_IN_LOCATION:
                    getAllPackagesInLocation();
                    break;
                case EXIT:
                    System.out.println("Goodbye.");
                    return;
            }
        } while (menuSelection != EXIT);
    }

    /**
     * Move a package to a new location by tracking number and location ID
     */
    private static void movePackageToLocation() {

        boolean goodID;

        do {
            System.out.println("\nEnter a tracking ID: ");
            int trackingID = 0;
            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            System.out.println("\nEnter a location ID: ");
            int locationID = 0;
            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            TrackingEvent newLocationEvent = new TrackingEvent(
                    trackingID, locationID, new Timestamp(System.currentTimeMillis()), "Arrived");

            try {
                newLocationEvent.saveToDB();
                goodID = true;
                System.out.println("\nPackage moved to new location: " + newLocationEvent.getLocation().getName());
            } catch (Exception e) {
                System.out.println("\nTracking ID or Location ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);
    }

    /**
     * Mark a package as delivered by tracking number
     */
    private static void markPackageAsDelivered() {
        boolean goodID;

        do {
            System.out.println("\nEnter a tracking ID: ");
            int trackingID = 0;
            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            TrackingEvent deliveredEvent = new TrackingEvent(
                    trackingID, -1, new Timestamp(System.currentTimeMillis()), "Delivered");

            try {
                deliveredEvent.saveToDB();
                goodID = true;
                System.out.println("\nPackage marked sucessfully.");
            } catch (SQLException e) {
                System.out.println("\nTracking ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);

    }

}