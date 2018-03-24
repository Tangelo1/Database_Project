package Menus;

import DataModels.*;

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

        // Welcome message.
        printWelcome();

        // Show the main menu options and wait for the user to make a selection
        enterMainMenu();
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

        int selectionFlag = -1;

        System.out.print("Main menu:\n\t1: Log in\n\t2: Create Account\n\t3: Track Package\n\t4: Exit\n");

        // Read user selection, sanitizing input values to numbers 1 2 3 or 4.
        selectionFlag = Input.makeSelectionInRange(1, 4);

        // Selection choice of 1 should have the user log in
        switch(selectionFlag) {
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

    /**
     * Called when the user selects to login. Will attempt to log the user in
     */
    private static void login() {
        int ID = 0;
        System.out.print("Please enter your account ID, otherwise -1 to go back to the menu" +
                "or -2 to go to the admin menu\n");
        try {
            ID = Input.readInt();
        } catch (Input.InputException e) {
            e.printStackTrace();
        }

        //Get account
        Account userAccount = Account.getAccount(ID);

        if(ID == -1){

            enterMainMenu();
        }else if(ID == -2){
            AdminMenu.enterMainAdminMenu();
        }else{
            CustomerMenu.setAccount(userAccount);

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
            try {
                name = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

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
            try {
                phone = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

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

        while (flag == 0){

            System.out.print("Please enter your credit card ID\n");
            try {
                cardID = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }
            cardID = cardID.trim();
            if(cardID.length() < 16){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }


        while (flag == 0){

            System.out.print("Please enter your name\n");
            try {
                ownerName = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }
            ownerName = ownerName.trim();
            if(ownerName.length() < 50){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }

        while (flag == 0){

            System.out.print("Please enter your exp date\n");
            try {
                expD = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }
            expD = expD.trim();
            if(expD.length() < 8){
                System.out.println("Invalid credit card ID, try again\n");
            }else{
                flag = 1;
            }
        }

        while (flag == 0){

            System.out.print("Please enter your cvv\n");
            try {
                cvv = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }
            cvv = cvv.trim();
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
            try {
                street = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            street = street.trim();
            if(phone.length() > 50){
                System.out.println("Invalid street(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your city\n");
            try {
                city = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            city = city.trim();
            if(city.length() > 50){
                System.out.println("Invalid city(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your state\n");
            try {
                state = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            state = state.trim();
            if(state.length() > 50){
                System.out.println("Invalid state(too long), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;

        while (flag == 0){

            System.out.print("Please enter your postal\n");
            try {
                postal = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            postal = postal.trim();
            if(postal.length() > 8){
                System.out.println("Invalid postal(too long/short), try again\n");
            }else{
                flag = 1;
            }
        }

        flag = 0;


        while (flag == 0){

            System.out.print("Please enter your country\n");
            try {
                country = Input.readStr();
            } catch (Input.InputException e) {
                e.printStackTrace();
            }

            country = country.trim();
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
