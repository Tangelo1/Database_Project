package Menus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Class designed to make it easy to read input. Additionally provides various function
 * to sanitize user input, such as isNumeric functions or isDate functions.
 */
public class Input
{
    /** Standard input scanner object */
    private static Scanner stdin = new Scanner(System.in);

    /**
     * Reads an integer from stdin
     * @return the integer read
     * @throws InputException If the user did not input something we were expecting.
     */
    public static int readInt() throws InputException {
        System.out.print("> ");
        String line = stdin.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException nfe) {
            throw new InputException("Unexpected Input \'" + line + "\' received when expecting a number.");
        }
    }

    /**
     * Reads an string from stdin. Trims the string. Does not perform any error checking on the string.
     * @return the string read
     */
    public static String readStr() {
        System.out.print("> ");
        String line = stdin.nextLine().trim();

        return line;
    }

    /**
     * Reads an input string with the given prompt.
     * ensures that the user enters SOMETHING.
     * @param prompt The prompty for input. E.g. "Account Name"
     * @return The non-empty input string.
     */
    public static String readStrWhileNotEmpty(String prompt) {
        String input;
        do {
            System.out.print(prompt + " ");
            input = readStr();
        } while (input.length() == 0);

        return input;
    }

    /**
     * Reads an input string with a given prompt.
     * Ensures that the user enters a non-empty string that
     * does not exceed the given max length.
     * @param prompt The prompt to give.
     * @param maxLength The maximum length that the input string may be. Must be greater than 0.
     * @return The string read from the user.
     * @throws IllegalArgumentException unchecked exception thrown when maxLength is less than 0.
     */
    public static String readStrWhileNotEmpty(String prompt, int maxLength) {

        if (maxLength < 1) {
            throw new IllegalArgumentException("Max Length Parameter must be greater than 0");
        }

        String input;
        do {
            System.out.print(prompt + " ");
            input = readStr();
        } while (input.length() > 0 && input.length() <= maxLength);
        return input;
    }


    /**
     * Gets an integer in some range from the user.
     * @param min The minimum number
     * @param max The maximum number
     * @return The number input by the user.
     */
    public static int makeSelectionInRange(int min, int max)
    {
        // Stay in the admin menu until the user chooses to exit the admin menu.
        int menuSelection = -1;
        while (menuSelection < min || menuSelection > max) {
            try {
                menuSelection = Input.readInt();
            } catch (Input.InputException ie) {
                System.out.println("Invalid menu selection.");
                menuSelection = -1;
            }
        }

        return menuSelection;
    }

    /**
     * Determines if a given string is a number or not. This will perform this check
     * by attempting to convert the string to a Long int.
     * @param str The string which may contain a number.
     * @return true if str is a number, false if not.
     */
    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Determines if the given date string represents a valid date in the given date format.
     * @param date The string containing the date to be evaluated
     * @param format The format to evaluate the date with
     * @return true if date is a valid date, false otherwise.
     */
    public static boolean isDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            simpleDateFormat.parse(date);
        } catch(ParseException pe) {
            return false;
        }

        return true;
    }

    /**
     * Formats a phone number by removing all parenthesis, whitespace and dashes such that
     * it becomes a string of numbers. Note that this will NOT validate the phone number.
     * @param phone A phone number string to format down to pure numbers.
     * @return the formatted phone number string.
     */
    public static String formatPhoneNumber(String phone) {
        phone = phone.replaceAll("\\s+", "");
        phone = phone.replaceAll("\\(", "");
        phone = phone.replaceAll("\\)", "");
        phone = phone.replaceAll("-", "");
        return phone;
    }

    /**
     * Determines if the given phone number is a valid phone number or not.
     * This function is written such that if a phone number contains parenthesis, whitespace
     * or dashes, they will be removed. Note that this function will NOT format the phone number.
     * Enforces a hard limit of 15 digits in a phone number because that is what is allowed in the database.
     * @param phone the phone number string.
     * @return true if phone is valid, false otherwise.
     */
    public static boolean isPhone(String phone) {
        phone = formatPhoneNumber(phone);
        // Phone number should just be a string of 10 numbers now.
        return phone.length() <= 15 && isNumeric(phone);
    }

    /**
     * An exception class for input errors.
     */
    public static class InputException extends Exception
    {
        /**
         * Default constructor
         */
        public InputException() {
            super();
        }

        /**
         * Parameterizes the exception with a message.
         * @param message The exception message.
         */
        public InputException(String message) {
            super(message);
        }
    }
}
