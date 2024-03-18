package cpsc2150.extendedConnects;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.GameBoard;
import cpsc2150.extendedConnectX.models.IGameBoard;

import java.util.ArrayList;
import java.util.Scanner;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
    Noah  Fultz     (nfultz23)
    James Moore     (1091229)
    Reese Myers     (Rcat44)
    Bryan Pisciotti (bpisciotti)
 */

/**
 * This class is used to handle all front-end interactions with the user(s) in order to play a game of ConnectX.
 * It is used to control the flow of the game.
 */
public class GameScreen {

    public static void main(String[] args)
    {
        Scanner scnr = new Scanner(System.in);
        IGameBoard board;

        char player;

        do {
            int numPlayers, playerIndex, numRows, numCols, numToWin, selectedColumn;

            ArrayList<Character> playerTokens = new ArrayList<>();
            // Prompts user for # of players
            numPlayers = askForNumPlayers(scnr);
            // First index represents player 1
            playerIndex = 0;
            // Allows each player to select a token Character. Stored in playerTokens
            playerTokenSelection(scnr, playerTokens, numPlayers);

            // Prompts for # of rows and cols
            numRows = askForNumRows(scnr);
            numCols = askForNumColumns(scnr);

            // numToWin cannot be greater than the minimum value between numRows and numCols
            if (numRows <= numCols) {
                numToWin = askForNumToWin(scnr, numRows);
            }
            else {
                numToWin = askForNumToWin(scnr, numCols);
            }

            // Prompts user to choose fast or memory efficient game.
            // If isFastGame returns true, a fast game was selected, so a GameBoard object is constructed.
            // Else, a GameBoardMem object is constructed.
            if (isFastGame(scnr)) {
                board = new GameBoard(numRows, numCols, numToWin);
            }
            else {
                board = new GameBoardMem(numRows, numCols, numToWin);
            }

            // new GameBoard is created and initialized to prepare for a new game
            // Player is set to Player 1's char
            player = playerTokens.get(playerIndex);

            boolean currentGameRunning = true;

            do {
                // board is printed and player is prompted for a column
                printBoard(board);
                selectedColumn = askPlayerForColumn(scnr, player, board);
                // Selected token is dropped into board
                board.dropToken(player, selectedColumn);

                // If a win has occurred, the winning board and the winning player are printed.
                // Else if a tie has occurred, the full board is printed, and user is informed of tie.
                // Else, the game moves on to the next turn.
                if (board.checkForWin(selectedColumn)) {
                    printBoard(board);
                    printWinner(player);
                    // current game ends
                    currentGameRunning = false;
                }
                else if (board.checkTie()) {
                    printBoard(board);
                    printTie();
                    // current game ends
                    currentGameRunning = false;
                }
                else {
                    // Causes turn to change
                    playerIndex = (playerIndex + 1) % numPlayers;
                    player = playerTokens.get(playerIndex);
                }
            } while (currentGameRunning);
            // A new game begins if player indicates they wish to play again
        } while (playAgain(scnr));
        scnr.close();
    }

    /**
     * Function prompts user for a column to drop their token in and returns a column number only after a
     * valid column number is entered.
     *
     * @param scnr Scanner object to access user input
     * @param player char representing the player whose turn it is
     * @param board Object of static type IGameBoard which has a specific number of columns of which some may be full
     *
     * @return int representing a valid user-selected column to drop a token in
     *
     * @pre None
     * 
     * @post askPlayerForColumn = [a valid column selected by the user]
     */
    private static int askPlayerForColumn(Scanner scnr, char player, IGameBoard board) {
        int selectedColumn;
        boolean isValidColumn;

        do {
            // Player is prompted for a column
            System.out.println("Player " + player + ", what column do you want to place your marker in?");
            selectedColumn = askForNumber(scnr);
            // Determines if column is valid
            isValidColumn = checkColumnValidity(selectedColumn, board);
            // If column is not valid, player is reprompted
        } while (!isValidColumn);

        return selectedColumn;
    }

    /**
     * Function asks user how many players there will be
     *
     * @param scnr Scanner object to access user input
     *
     * @return int value representing number of players
     *
     * @pre None
     *
     * @post askForNumPlayers = [value between IGameBoard.MIN_NUM_PLAYERS and IGameBoard.MAX_NUM_PLAYERS]
     */
    private static int askForNumPlayers(Scanner scnr) {
        int numPlayers;
        boolean needValidNumPlayers = true;

        do {
            // Player is prompted for a # of players
            System.out.println("How many players?");
            numPlayers = askForNumber(scnr);

            // Determines whether selected # of players was too low or too high
            if (numPlayers < IGameBoard.MIN_NUM_PLAYERS) {
                System.out.println("Must be at least " + IGameBoard.MIN_NUM_PLAYERS + " players");
            }
            else if (numPlayers > IGameBoard.MAX_NUM_PLAYERS) {
                System.out.println("Must be " + IGameBoard.MAX_NUM_PLAYERS + " players or fewer");
            }
            else {
                needValidNumPlayers = false;
            }
            // If player provided an invalid # of players, player is re-prompted
        } while (needValidNumPlayers);

        return numPlayers;
    }

    /**
     * Function allows each player to select a token to represent them in the board
     *
     * @param scnr Scanner object to access user input
     * @param playerTokens ArrayList of Characters representing player tokens
     * @param numPlayers int representing the number of players
     *
     * @pre IGameBoard.MIN_NUM_PLAYERS <= numPlayers <= IGameBoard.MAX_NUM_PLAYERS AND
     *          |playerTokens| == 0
     *
     * @post |playerTokens| = numPlayers AND
     *          [Each element in playerTokens is a unique user-selected uppercase character]
     */
    private static void playerTokenSelection(Scanner scnr, ArrayList<Character> playerTokens, int numPlayers) {
        // Loop will allow numPlayers number of tokens to be selected
        for (int i = 1; i <= numPlayers; i++) {
            char selectedToken;
            boolean repromptForToken;
            do {
                repromptForToken = false;

                // User is prompted to enter a token
                System.out.println("Enter the character to represent player " + i);
                // Selected character is converted to upperCase
                selectedToken = scnr.next().toUpperCase().charAt(0);
                // Token is an invalid selection if it has already been selected by another player
                for (char token : playerTokens) {
                    if (selectedToken == token) {
                        System.out.println(token + " is already taken as a player token!");
                        repromptForToken = true;
                        break;
                    }
                }
                // If a player selected an invalid token, player is re-prompted
            } while (repromptForToken);
            // Adds valid token Character to playerTokens arrayList
            playerTokens.add(selectedToken);
        }
        scnr.nextLine();
    }

    /**
     * Function asks user how many rows in the board there will be
     *
     * @param scnr Scanner object to access user input
     *
     * @return int value representing number of rows in board
     *
     * @pre None
     *
     * @post askForNumRows = [value between IGameBoard.MIN_ROWS and IGameBoard.MAX_ROWS]
     */
    private static int askForNumRows(Scanner scnr) {
        int numRows;
        boolean needValidNumRows = true;

        do {
            // Player is prompted for a # of rows
            System.out.println("How many rows should be on the board?");
            numRows = askForNumber(scnr);

            // Determines whether selected # of rows is too low or too high
            if (numRows < IGameBoard.MIN_ROWS) {
                System.out.println("Must be at least " + IGameBoard.MIN_ROWS + " rows");
            }
            else if (numRows > IGameBoard.MAX_ROWS) {
                System.out.println("Must be " + IGameBoard.MAX_ROWS + " rows or fewer");
            }
            else {
                needValidNumRows = false;
            }
            // If player provided an invalid # of rows, player is re-prompted
        } while (needValidNumRows);

        return numRows;
    }

    /**
     * Function asks user how many columns in the board there will be
     *
     * @param scnr Scanner object to access user input
     *
     * @return int value representing number of columns in board
     *
     * @pre None
     *
     * @post askForNumColumns = [value between IGameBoard.MIN_COLS and IGameBoard.MAX_COLS]
     */
    private static int askForNumColumns(Scanner scnr) {
        int numColumns;
        boolean needValidNumColumns = true;

        do {
            // Player is prompted for a # of columns
            System.out.println("How many columns should be on the board?");
            numColumns = askForNumber(scnr);

            // Determines whether selected # of columns was too low or too high
            if (numColumns < IGameBoard.MIN_COLS) {
                System.out.println("Must be at least " + IGameBoard.MIN_COLS + " columns");
            }
            else if (numColumns > IGameBoard.MAX_COLS) {
                System.out.println("Must be " + IGameBoard.MAX_COLS + " columns or fewer");
            }
            else {
                needValidNumColumns = false;
            }
            // If player provided an invalid # of columns, player is re-prompted
        } while (needValidNumColumns);

        return numColumns;
    }

    /**
     * Function asks user how many connected tokens will be required to win
     *
     * @param scnr Scanner object to access user input
     * @param max int representing the maximum value for the number to win based on the minimum of the number of
     *           rows and columns
     *
     * @return int value representing number of tokens in a row required to win
     *
     * @pre None
     *
     * @post askForNumToWin = [value between IGameBoard.MIN_TO_WIN and 
     *          the calculated minimum between max and IGameBoard.MAX_NUM_TO_WIN]
     */
    private static int askForNumToWin(Scanner scnr, int max) {
        int numToWin;
        boolean needValidNumToWin = true;

        // If the max to win calculated by finding the minimum of the # of rows and the # of cols is greater than
        // IGameBoard.MAX_NUM_TO_WIN, then max is set to IGameBoard.MAX_NUM_TO_WIN
        if (max > IGameBoard.MAX_NUM_TO_WIN) {
            max = IGameBoard.MAX_NUM_TO_WIN;
        }

        do {
            // User is prompted to determine the # of tokens in a row to win
            System.out.println("How many in a row to win?");
            numToWin = askForNumber(scnr);

            // Determines whether selected # to win is too low or too high
            if (numToWin < IGameBoard.MIN_TO_WIN) {
                System.out.println("Must be at least " + IGameBoard.MIN_TO_WIN + " in a row");
            }
            else if (numToWin > max) {
                System.out.println("Must be " + max + " in a row or fewer");
            }
            else {
                needValidNumToWin = false;
            }
            // If player provided an invalid # of tokens to win, the player is reprompted
        } while (needValidNumToWin);

        return numToWin;
    }

    /**
     * Function asks user to choose a fast or memory efficient game
     *
     * @param scnr Scanner object to access user input
     *
     * @return boolean value representing whether user chose a fast game.
     *
     * @pre None
     *
     * @post askForNumRows = true IFF [user selects 'f' or 'F']
     * ELSE askForNUMRows = false IFF [user eventually selects 'm' or 'M']
     */
    private static boolean isFastGame(Scanner scnr) {
        char userInput;
        boolean needValidInput = true;

        do {
            // Player is prompted to choose a fast or memory efficient game
            System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
            // Input is converted to lowerCase
            userInput = scnr.next().toLowerCase().charAt(0);

            // Checks whether user correctly selected a character representing the fast or memory efficient game
            if (userInput == 'f' || userInput == 'm') {
                needValidInput = false;
            }
            else {
                System.out.println("Please enter F or M");
            }
        } while (needValidInput);
        scnr.nextLine();

        return userInput == 'f';
    }

    /**
     * Function prints out current game board
     *
     * @param board Object of static type IGameBoard whose contents are to be printed to terminal
     *
     * @pre None
     *
     * @post [board.toString() is printed to the terminal with a newline at the end]
     */
    private static void printBoard(IGameBoard board) {
        System.out.println(board.toString());
    }

    /**
     * Function determines if a user would like to play again
     *
     * @param scnr Scanner object to access user input
     *
     * @return boolean value representing whether the user wishes to play again
     *
     * @pre None
     *
     * @post playAgain = true IFF [user eventually inputs 'Y' or 'y' when prompted]
     * ELSE playAgain = false IFF [user eventually inputs 'N' or 'n' when prompted]
     */
    private static boolean playAgain(Scanner scnr) {
        char playAgain;
        boolean needValidInput = true;

        do {
            // Player is asked if they wish to play again
            System.out.println("Would you like to play again? Y/N");
            playAgain = scnr.next().toLowerCase().charAt(0);

            // If user selected 'Y', 'y', 'N', or 'n', their input is valid
            if (playAgain == 'y' || playAgain == 'n'){
                needValidInput = false;
            }
            // User is re-prompted if input was invalid
        } while (needValidInput);
        scnr.nextLine();

        // If user selected 'Y' or 'y', true is returned.
        // Otherwise, they must have selected 'N' or 'n', so false is returned
        return playAgain == 'y';
    }

    /**
     * Function checks if a provided column value is valid to drop a token in for the current board
     *
     * @param col int representing column user wishes to drop a token in
     * @param board Object of static type IGameBoard which has a specific number of columns of which some may be full
     *
     * @return boolean value representing whether the provided column is valid
     *
     * @pre None
     *
     * @post checkColumnValidity = true IFF 0 <= col < board.getNumColumns() AND board.checkIfFree(col) == true, false OW AND
     *          [An error message is printed to tell the user if selected col was too low or too high or a full col]
     */
    private static boolean checkColumnValidity(int col, IGameBoard board) {
        // If user selects a column that is too low, too high, or full, an appropriate message is printed telling
        // them of their mistake, and false is returned.
        // Otherwise, true is returned because the column is valid.
        if (col < 0) {
            System.out.println("Column cannot be less than 0");
            return false;
        }
        else if (col >= board.getNumColumns()) {
            System.out.println("Column cannot be greater than " + (board.getNumColumns() - 1));
            return false;
        }
        else if (!board.checkIfFree(col)) {
            System.out.println("Column is full");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Function prints the winner of the game
     *
     * @param player char representing the player who won
     *
     * @pre [checkForWin() == true for the board being used for the current game] AND
     *          [player is an uppercase character representing the winning player token]
     *
     * @post [User is informed of the correct winner via a message printed to the terminal]
     */
    private static void printWinner(char player) {
        System.out.println("Player " + player + " Won!");
    }

    /**
     * Function prints a tie message
     *
     * @pre [checkForWin() == false for the board being used for the current game] AND [checkTie() == true for the same]
     * 
     * @post [User is informed that a Tie has occurred via a message printed to the terminal]
     */
    private static void printTie() {
        System.out.println("Tie!");
    }

    /**
     * Function to ask the user for integer input.
     * Input validation avoids a NumberFormatException by only parsing known good data.
     * 
     * @param scnr the Scanner object to use to ask for data
     * 
     * @pre None
     * 
     * @post askForNumber = [the integer value given by the user]
     * 
     * @return the integer input by the user
     */
    private static int askForNumber(Scanner scnr) {
        // loop while the input from the player can't be read as an integer
        while(!scnr.hasNextInt()) {
            System.out.println("Enter a valid number");
            // drop the rest of the line with the bad value
            scnr.nextLine();
        }

        // once we exit the loop we know the buffer has a parsable value
        int num = scnr.nextInt();
        // clean the rest of the input on this line from the buffer
        scnr.nextLine();

        return num;
    }
}