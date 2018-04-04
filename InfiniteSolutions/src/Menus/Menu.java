package Menus;

import DataModels.*;
import Driver.DBDriver;

import java.sql.SQLException;

/**
 * The main entry point for the front facing user interface of the application
 * This class displays the initial menu and set of menu options for the user to select when first
 * launching the application. On choosing to log in, the user will be redirected to the appropriate menu.
 */
public class Menu {

    public static final int NULL = 0;
    /**
     * Main entry point for the menu system.
     * @param args unused command line args.
     */
    public static void main(String[] args) {
        //Catch IllegalState Exception
        DBDriver driver = new DBDriver();


        // Welcome message.
        printWelcome();

        // Show the main menu options and wait for the user to make a selection
        enterMainMenu();

        driver.closeConnection();
    }

    /**
     * Prints some welcome message.
     */
    private static void printWelcome() {
        System.out.println("\n\n\tWelcome to the Infinite Solutions Package Management System\n");
    }

    /**
     * Displays the first main menu for the application. Presents the user with options to
     * login, track a package, or create an account.
     */
    private static void enterMainMenu() {
        // Stay in the main menu indefinitely.
        while (true) {
            int selectionFlag = -1;

            System.out.print("Main menu:\n\t1: Log in\n\t2: Create Account\n\t3: Track Package\n\t4: Exit\n");

            // Read user selection, sanitizing input values to numbers 1 2 3 or 4.
            selectionFlag = Input.makeSelectionInRange(1, 4);

            // Selection choice of 1 should have the user log in
            switch (selectionFlag) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    trackPackage();
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Called when the user selects to login. Will attempt to log the user in
     */
    private static void login() {
        int ID = 0;
        System.out.print("Please enter your account ID, otherwise -1 to go back to the menu " +
                "or -2 to go to the admin menu\n");
        try {
            ID = Input.readInt();
        } catch (Input.InputException e) {
            e.printStackTrace();
        }

        // Log into admin menu
        if (ID == -2) {
            AdminMenu.enterMainAdminMenu();
        }
        else if (ID == -1) {
            return;
        }
        else {
            //Get account
            // TODO: When we have querying set up correctly, actually load the account.
            // The way to do this will be create a new account
            // Then load it from the DB like this
            //Account userAccount = new Account(id).loadFromDB();
            //This has the same functionality as getAccountByNumber
            Account userAccount = null;
            try {
                userAccount = Account.getAccountByNumber(ID);
            } catch(SQLException e) {
                // Don't need to do anything here.
            }

            // TODO enable this check when the account can actually be queried.
            // If the user account doesn't exist, return to the previous menu.
            if (userAccount == null) {
                System.out.println("\nError: Unknown Account Number.");
                return;
            }
            else {
                CustomerMenu.setAccount(userAccount);
                CustomerMenu.enterCustomerMenu();
            }
        }
    }

    /**
     * Called when the user selects to go down the account creation path.
     * This guides the user through creating a personal account, as only the adminstrators
     * have permission to create corporate accounts. After creation, will be logged in
     */
    private static void createAccount() {
        Account newAccount;

        String name = "";
        String phone = "";
        int flag = 0;

        //Setting up account
        while(flag == 0){

            System.out.print("Please enter your name\n");
            name = Input.readStr();

            name = name.trim();
            if(name.length() > 50){
                System.out.println("Your name is too long, enter a shorter form\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your phone number\n");
            phone = Input.readStr();

            phone = phone.trim();
            if(phone.length() > 15){
                System.out.println("Invalid number, try again\n");
            }else{
                flag = 1;
            }
        }

        //TODO SAVE TO DB
        newAccount = new Account(NULL,'P',name,phone,NULL,NULL);

        //Setting up Credit card

        CreditCard card;
        String cardID = "";
        String ownerName = "";
        String expD = "";
        String cvv = "";

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your credit card ID\n");
            cardID = Input.readStr();
            if(cardID.length() < 16){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your name\n");
            ownerName = Input.readStr();
            if(ownerName.length() < 50){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your exp date\n");
            expD = Input.readStr();
            if(expD.length() < 8){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your cvv\n");
            cvv = Input.readStr();
            if(cvv.length() < 3){
                System.out.println("Invalid cvv, try again\n");
            }else{
                flag = 1;
            }
        }

        card = new CreditCard(NULL,name,cardID,expD,Integer.parseInt(cvv));
        //TODO need a way to get creditcardid and need to save to db

        newAccount.setCreditCardId(Integer.parseInt(cardID));


        //Address being setup
        Address newAddress;
        String street = "";
        String city = "";
        String state = "";
        String postal = "";
        String country = "";

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your street\n");
            street = Input.readStr();

            if(phone.length() > 50){
                System.out.println("Invalid street(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your city\n");
            city = Input.readStr();

            if(city.length() > 50){
                System.out.println("Invalid city(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your state\n");
            state = Input.readStr();

            if(state.length() > 50){
                System.out.println("Invalid state(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your postal\n");
            postal = Input.readStr();

            if(postal.length() > 8){
                System.out.println("Invalid postal(too long/short), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;


        while (flag == 0){

            System.out.print("Please enter your country\n");
            country = Input.readStr();

            if(country.length() > 50){
                System.out.println("Invalid country(too long/short), try again\n");
            }else{
                flag = 1;
            }
        }

        newAddress = new Address(NULL,street,city,state,postal,country);

        //TODO NEED TO SAVE TO DB

    }

    /**
     * Displays the package tracking menu to the user.
     */
    private static void trackPackage() {
        //


    }
}
