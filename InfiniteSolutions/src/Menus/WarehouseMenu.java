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
        int locationID = -1;
        do {
            System.out.println("\nEnter a location ID: ");

            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            if (!Package.exists(locationID))
                System.out.println("\nLocation not found.");
        }
        while (!Location.exists(locationID));

        try {
            Location l = new Location(locationID);
            ArrayList<Package> pkgs = l.getPackagesWithin();

            System.out.println("\nPackages contained in " + l.getName() + ": ");

            for (Package p : pkgs) {
                System.out.println("\tPackage: " + p.getTrackingId());
            }


        } catch (Exception e) {
            System.out.println("\nCould not load packages");
        }
    }


    /**
     * Main menu for the warehouse users
     */
    public static void enterMainWarehouseMenu() {
        int menuSelection;
        do {
            // display the menu options
            System.out.print("\nWarehouse Menu:\n\t1. Move A Package To A New Location\n\t2. Mark Package As Delivered\n\t" +
                    "3. Get All Packages In A Location\n\t4. Log Out\n");

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


        int trackingID = -1;
        do {
            System.out.println("\nEnter a tracking ID: ");

            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            if (!Package.exists(trackingID))
                System.out.println("\nPackage not found.");
        }
        while (!Package.exists(trackingID));

        try {
            TrackingEvent lastEvent = TrackingEvent.getLastEventForPackage(trackingID);
            if(lastEvent.getStatus().equals("Delivered")) {
                System.out.println("\nThis package has already been delivered.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Cannot load last event for package.");
        }


        int locationID = -1;
        do {
            System.out.println("\nEnter a location ID: ");

            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            if (!Package.exists(locationID))
                System.out.println("\nLocation not found.");
        }
        while (!Location.exists(locationID));


        TrackingEvent newLocationEvent = new TrackingEvent(
                trackingID, locationID, new Timestamp(System.currentTimeMillis()), "Arrived");

        try {
            newLocationEvent.saveToDB();
            System.out.println("\nPackage moved to new location: " + newLocationEvent.getLocation().getName());
        } catch (Exception e) {
            System.out.println("\nCould not move package.");
        }
    }

    /**
     * Mark a package as delivered by tracking number
     */
    private static void markPackageAsDelivered() {

        int trackingID = -1;
        do {
            System.out.println("\nEnter a tracking ID: ");

            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                System.out.println("Invalid ID input");
            }

            if (!Package.exists(trackingID))
                System.out.println("\nPackage not found.");
        }
        while (!Package.exists(trackingID));

        try {
            TrackingEvent lastEvent = TrackingEvent.getLastEventForPackage(trackingID);
            if(lastEvent.getStatus().equals("Delivered")) {
                System.out.println("\nThis package has already been delivered.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Cannot load last event for package.");
        }


        TrackingEvent deliveredEvent = new TrackingEvent(
                trackingID, -1, new Timestamp(System.currentTimeMillis()), "Delivered");

        try {
            deliveredEvent.saveToDB();
            System.out.println("\nPackage marked sucessfully.");
        } catch (SQLException e) {
            System.out.println("\nCould not mark package.");
        }

    }

}