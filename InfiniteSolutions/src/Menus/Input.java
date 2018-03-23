package Menus;

import java.util.Scanner;

/**
 * Class designed to make it easy to read input.
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
     * Reads an string from stdin
     * @return the string read
     */
    public static String readStr() throws InputException{
        System.out.print("> ");
        String line = stdin.nextLine().trim();

        return line;
    }


    /**
     * Gets an integer in some range from the user.
     * @param min The minimum number
     * @param max The maximum number
     * @return The number input by the user.
     */
    public static int makeSelectionInrange(int min, int max)
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
