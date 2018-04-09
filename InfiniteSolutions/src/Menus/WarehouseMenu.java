package Menus;

import DataModels.*;

import java.sql.SQLException;
import java.sql.Timestamp;

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
     *
     */
    private static final int GET_PACKAGES_IN_LOCATION=3;

    /**
     * Exit menu selection
     */
    private static final int EXIT = 4;
    //private static final DataModels.Location Location = ;


    private static void getAllPackagesInLocation() {
        boolean goodID = false;

        do {

            System.out.println("\nEnter a location ID: ");
            int locationID = 0;
            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
            }

            try {
                Location l = new Location(locationID);
                l.getPackagesWithin();
                goodID = true;
            }
            catch (Exception e) {
                System.out.println("\nLocation ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);
    }


    /**
     *
     */
    public static void enterMainWarehouseMenu () {
        int menuSelection;
        do {
            // display the menu options
            System.out.print("\nWarehouse Menu:\n\t1. Move A Package To A New Location\n\t2. Mark Package As Delivered\n\t" +
                    "3. Get All Packages In A Locationn\n\t4. Log Out\n");


            // Make a menu selection
            menuSelection = Input.makeSelectionInRange(MOVE_PACKAGE, EXIT);

            switch(menuSelection) {
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
     *
     *
     */
    private static void movePackageToLocation() {

        boolean goodID = false;

        do {
            System.out.println("\nEnter a tracking ID: ");
            int trackingID = 0;
            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            System.out.println("\nEnter a location ID: ");
            int locationID = 0;
            try {
                locationID = Input.readInt();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            TrackingEvent newLocationEvent = new TrackingEvent(
                    trackingID, locationID, new Timestamp(System.currentTimeMillis()), "Arrived");

            try {
                newLocationEvent.saveToDB();
                goodID = true;
            }
            catch (Exception e) {
                System.out.println("\nTracking ID or Location ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);
    }

    /**
     *
     */
    private static void markPackageAsDelivered() {
        boolean goodID = false;

        do {
            System.out.println("\nEnter a tracking ID: ");
            int trackingID = 0;
            try {
                trackingID = Input.readInt();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            TrackingEvent deliveredEvent = new TrackingEvent(
                    trackingID, -1, new Timestamp(System.currentTimeMillis()), "Delivered");


            try {
                deliveredEvent.saveToDB();
                goodID = true;
            } catch (SQLException e) {
                System.out.println("\nTracking ID cannot be found.");
                goodID = false;
            }
        } while (!goodID);


    }

}
