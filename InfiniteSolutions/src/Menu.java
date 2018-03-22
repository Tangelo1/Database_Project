import DataModels.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    int accountID;
    int trackingID;

    public void menuStarter(){

        int selectionFlag;

        Scanner reader = new Scanner(System.in);

        System.out.println("Enter 1 if you want to look at your account, 2 if" +
                " you want to track a package, or 3 if you want to create an account");


        selectionFlag = reader.nextInt();

        if(selectionFlag == 1){
            System.out.println("Enter your account ID, -1 if you are an admin");
            accountID = reader.nextInt();
        }

        else if(selectionFlag == 2){
            System.out.println("Enter your tracking ID");
            trackingID = reader.nextInt();
        }

        else if(selectionFlag == 3){

        }

    }

    private static Account newAccount(){
        System.out.println("Enter 1 for personal account or 2 for corporate account");
        Account newACc = new Account(null,)

    }

}
