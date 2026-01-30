package kiki;

/**
 * Parser class for parsing user input into commands and arguments.
 */
public class Parser {

    /**
     * Parses the full user input string to determine the command type.
     *
     * @param fullCommand The full string entered by the user.
     * @return The Command enum corresponding to the user's input.
     * @throws KikiException If the command is invalid.
     */
    public static Command parseCommand(String fullCommand) throws KikiException {
        String[] inputLine = fullCommand.split(" ", 2);
        String commandString = inputLine[0];

        try {
            return Command.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new KikiException("I'm sorry, I don't understand that command.");
        }
    }

    /**
     * Extracts the arguments from the user input string.
     *
     * @param fullCommand The full string entered by the user.
     * @return The argument string.
     */
    public static String parseArguments(String fullCommand) {
        String[] inputLine = fullCommand.split(" ", 2);
        return inputLine.length > 1 ? inputLine[1] : "";
    }
}