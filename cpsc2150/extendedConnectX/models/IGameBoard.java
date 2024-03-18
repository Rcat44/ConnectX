package cpsc2150.extendedConnectX.models;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
    Noah  Fultz     (nfultz23)
    James Moore     (1091229)
    Reese Myers     (Rcat44)
    Bryan Pisciotti (bpisciotti)
 */

/**
 * IGameBoard abstractly represents a 2-dimensional ConnectX board with characters.
 *          Indexing starts at 0 (the bottom-left of the board).
 * <p>
 * Initialization Ensures: [a new board with NUMBER_OF_ROWS rows and NUMBER_OF_COLUMNS columns is created to
 *                              initially be empty, where NUMBER_TO_WIN adjacent tokens trigger a win]
 * <p>
 * Defines: NUMBER_OF_ROWS: Z
 *          NUMBER_OF_COLUMNS: Z
 *          NUMBER_TO_WIN: Z
 *          TOKENS_WHEN_FULL: NUMBER_OF_ROWS * NUMBER_OF_COLUMNS
 *          tokens_in_board: Z
 * <p>
 * Constraints: MIN_ROWS <= NUMBER_OF_ROWS <= MAX_ROWS
 *              MIN_COLS <= NUMBER_OF_COLUMNS <= MAX_COLS
 *              MIN_TO_WIN <= NUMBER_TO_WIN <= MAX_NUM_TO_WIN
 *              NUMBER_TO_WIN <= NUMBER_OF_ROWS AND
 *              NUMBER_TO_WIN <= NUMBER_OF_COLS AND
 *              0 <= tokens_in_board <= TOKENS_WHEN_FULL
 *              [self should not contain an empty row below an occupied row]
 *              [self should only contain blank spaces and uppercase letters]
 */
public interface IGameBoard {
    public static final int MAX_NUM_PLAYERS = 10;
    public static final int MIN_NUM_PLAYERS = 2;
    public static final int MAX_ROWS = 100;
    public static final int MAX_COLS = 100;
    public static final int MAX_NUM_TO_WIN = 25;
    public static final int MIN_COLS = 3;
    public static final int MIN_ROWS = 3;
    public static final int MIN_TO_WIN = 3;

    /**
     * Function returns number of rows in board
     *
     * @return int representing number of rows in the board
     *
     * @pre None
     * 
     * @post getNumRows = NUMBER_OF_ROWS AND self = #self AND tokens_in_board = #tokens_in_board
     */
    public int getNumRows();

    /**
     * Function returns the number of columns in board
     *
     * @return int representing number of columns in the board
     *
     * @pre None
     *
     * @post getNumColumns = NUMBER_OF_COLUMNS AND self = #self AND tokens_in_board = #tokens_in_board
     */
    public int getNumColumns();

    /**
     * Function returns the number of tokens in a row a player needs to achieve to win
     *
     * @return int representing the number of consecutive tokens needed to win
     *
     * @pre None
     *
     * @post getNumToWin = NUMBER_TO_WIN AND self = #self AND tokens_in_board = #tokens_in_board
     */
    public int getNumToWin();

    /**
     * Returns character in self at position pos.
     * 
     * @return [the character in self at position pos]
     *
     * @param pos BoardPosition object containing particular row and column values
     *
     * @pre None
     * 
     * @post whatsAtPos = [the character in self at position pos] AND
     *          tokens_in_board = #tokens_in_board AND 
     *          self = #self
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Updates self by having the token fall to the lowest available row in column c.
     * 
     * @param p uppercase letter character representing the player token to drop
     * @param c number representing selected column as an int
     *
     * @pre [p is an uppercase letter character] AND 
     *          0 <= c < NUMBER_OF_COLUMNS AND
     *          checkIfFree(c) == true
     *
     * @post [self = #self with character p added at the lowest empty row in column c] AND
     *          tokens_in_board = #tokens_in_board + 1
     */
    public void dropToken(char p, int c);

    /**
     * Returns boolean representing whether there is space remaining in the column
     *
     * @return true if column is not full, false otherwise.
     * 
     * @param c is the column to check for being free
     *
     * @pre 0 <= c < NUMBER_OF_COLUMNS
     *
     * @post checkIfFree = true IFF whatsAtPos(new BoardPosition(NUMBER_OF_ROWS-1,c)) == ' ', false OW AND
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkIfFree(int c) {
        return whatsAtPos(new BoardPosition(getNumRows() - 1, c)) == ' ';
    }

    /**
     * Returns boolean representing whether the last move in column c has resulted in a win
     *
     * @param c number representing column that token was placed in.
     *
     * @return true if win has occurred, false otherwise
     *
     * @pre 0 <= c < NUMBER_OF_COLUMNS
     *
     * @post checkForWin = [true IFF the last token placed in column c connnected NUMBER_TO_WIN tokens
     *          cardinally or diagonally] AND
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkForWin(int c) {
        // row represents row to check for token at column c
        int row = getNumRows();
        BoardPosition checkPos;
        // Iterates down the column until a player token is found
        do {
            checkPos = new BoardPosition(--row,c);
        } while (whatsAtPos(checkPos) == ' ');

        // store character last placed in column
        char checkToken = whatsAtPos(checkPos);

        //return true if horizontal, vertical, or diagonal win has occurred
        return checkHorizWin(checkPos, checkToken) || checkVertWin(checkPos, checkToken)
                || checkDiagWin(checkPos, checkToken);
    }

    /**
     * Returns boolean representing whether the board is completely full after checking for empty cells on top row.
     *
     * @return false if at least one free space remains and true if none remain
     *
     * @pre None
     *
     * @post checkTie = [true IFF checkIfFree() returns false for all columns, false OW] AND
     *          self = #self AND
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkTie() {
        //Iterate through every column in the game
        for (int x = 0; x < getNumColumns(); x++) {
            //If any column has a free space, return false
            if (checkIfFree(x)) { return false; }
        }

        //Otherwise return true
        return true;
    }

    /**
     * Determines if last move by player has resulted in at least NUMBER_TO_WIN tokens in a row horizontally.
     * Returns boolean representing whether win has occurred horizontally.
     *
     * @param pos BoardPosition object containing row and column position of last token dropped by player p
     * @param p is the character token to check the win condition for
     *
     * @return true if win has occurred by having at least NUMBER_TO_WIN tokens in a row horizontally, otherwise false
     *
     * @pre [p is an uppercase letter character]
     *
     * @post checkHorizWin = [true IFF the character at whatsAtPos(pos) connects NUMBER_TO_WIN consecutive 
     *          characters p in the same row, false OW] AND
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkHorizWin(BoardPosition pos, char p) {
        // Row and Col # are stored
        int rowNum = pos.getRow();
        int startCol = pos.getColumn();
        // There is 1 in a row to start
        int numConsecutive = 1;

        // Track the number of tokens checked past the start position
        int numChecked = 0;

        // testCol represents the column to check for a token. Will check one position to the left initially
        int testCol = startCol - 1;

        boolean searching = true;

        // Loop iterates while matching tokens are found to the left of the starting position and the minimum
        // column# has not been passed
        while (testCol >= 0 && searching && numChecked < getNumToWin()) {
            // If the next token in the column to the left matches p, numConsecutive is incremented, and testCol
            // is decremented to move one more space to the left.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(rowNum, testCol), p)) {
                numConsecutive++;
                testCol--;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }
        // Now checks space 1 to the right of the starting position
        testCol = startCol + 1;
        searching = true;
        // reset the number of tokens checked
        numChecked = 0;

        // check numConsecutive for early termination
        if (numConsecutive >= getNumToWin()) {
            return true;
        }

        // Loop iterates while matching tokens are found to the left of the starting position and the maximum
        // column# has not been passed
        while (testCol < getNumColumns() && searching && numChecked < getNumToWin()) {
            // If the next token in the column to the right matches p, numConsecutive is incremented, and testCol
            // is incremented to move one more space to the right.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(rowNum, testCol), p)) {
                numConsecutive++;
                testCol++;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }
        // Returns whether the number of consecutive horizontal tokens is greater than or equal to #required for win
        return numConsecutive >= getNumToWin();
    }

    /**
     * Determines if last move by player has resulted in at least NUMBER_TO_WIN tokens in a row vertically.
     * Returns boolean representing whether win has occurred vertically.
     *
     * @param pos BoardPosition object containing row and column position of last token dropped by player p
     * @param p is the character token to check the win condition for
     *
     * @return true if win has occurred by having at least NUMBER_TO_WIN tokens in a row vertically, otherwise false
     *
     * @pre [p is an uppercase letter character]
     *
     * @post checkVertWin = [true IFF the character at whatsAtPos(pos) is part
     *          of a line of at least NUMBER_TO_WIN consecutive p characters in 
     *          the same column as p, false OW] AND 
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkVertWin(BoardPosition pos, char p) {
        // Row and col# are stored
        int colNum = pos.getColumn();
        int startRow = pos.getRow();
        // There is 1 in a row to start
        int numConsecutive = 1;

        // Track the number of tokens checked past the start position
        int numChecked = 0;

        // testCol represents the column to check for a token. Will check one position below initially
        int testRow = startRow - 1;

        boolean searching = true;

        // Loop iterates while matching tokens are found below of the starting position and the minimum
        // row# has not been passed
        while (testRow >= 0 && searching && numChecked < getNumToWin()) {
            // If the next token in the row below matches p, numConsecutive is incremented, and testRow
            // is decremented to move one more space down.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(testRow, colNum), p)) {
                numConsecutive++;
                testRow--;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }
        // Returns whether the number of consecutive vertical tokens is greater than or equal to #required for win
        return numConsecutive >= getNumToWin();
    }

    /**
     * Determines if last move by player has resulted in at least NUMBER_TO_WIN tokens in a row diagonally.
     * Returns boolean representing whether win has occurred diagonally.
     *
     * @param pos BoardPosition object containing row and column position of last token dropped by player p
     * @param p is the character token to check the win condition for
     *
     * @return true if win has occurred by having at least NUMBER_TO_WIN tokens in a row diagonally, otherwise false
     *
     * @pre [p is an uppercase letter character]
     *
     * @post checkDiagWin = [true IFF the character at whatsAtPos(pos) is part
     *          of a line of at least NUMBER_TO_WIN consecutive p characters on
     *          the same diagonal as p, false OW] AND
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean checkDiagWin(BoardPosition pos, char p) {
        // Row and col# are stored
        int startRow = pos.getRow();
        int startCol = pos.getColumn();

        // There is 1 in a row to start for both diagonals
        int numConsecutiveLeft = 1;
        int numConsecutiveRight = 1;

        // Track the number of tokens checked past the start position
        int numChecked = 0;

        // testRow & testCol represent the row and column to check for a token. Will check one position down
        // and to the left initially (starting with diagonal from bottom left to top right)
        int testRow = startRow - 1;
        int testCol = startCol - 1;
        boolean searching = true;

        // Loop iterates while matching tokens are found down and to the left of the starting position and the minimum
        // row and column #s have not been passed

        while (testCol >= 0 && testRow >= 0 && searching && numChecked < getNumToWin()) {
            // If the next token on the diagonal matches p, numConsecutiveLeft is incremented, and testRow & testCol
            // are decremented to move one more space down and to the left.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(testRow, testCol), p)) {
                numConsecutiveLeft++;
                testRow--;
                testCol--;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }

        // check numConsecutive for early termination
        if (numConsecutiveLeft >= getNumToWin()) {
            return true;
        }

        // Now checks space 1 to the up and to the right of the starting position
        testRow = startRow + 1;
        testCol = startCol + 1;
        searching = true;
        // reset the number of tokens checked
        numChecked = 0;

        // Loop iterates while matching tokens are found up and to the right of the starting position and the maximum
        // row and column #s have not been passed
        while (testCol < getNumColumns() && testRow < getNumRows() && searching && numChecked < getNumToWin()) {
            // If the next token on the diagonal matches p, numConsecutiveLeft is incremented, and testRow & testCol
            // are incremented to move one more space up and to the right.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(testRow, testCol), p)) {
                numConsecutiveLeft++;
                testRow++;
                testCol++;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }

        // check numConsecutive for early termination
        if (numConsecutiveLeft >= getNumToWin()) {
            return true;
        }

        // Now checks space 1 to the up and to the left of the starting position (checking other diagonal)
        testRow = startRow + 1;
        testCol = startCol - 1;
        searching = true;
        // reset the number of tokens checked
        numChecked = 0;

        // Loop iterates while matching tokens are found up and to the left of the starting position and the maximum
        // row and minimum column #s have not been passed
        while (testCol >= 0 && testRow < getNumRows() && searching && numChecked < getNumToWin()) {
            // If the next token on the diagonal matches p, numConsecutiveRight is incremented, and testRow is
            // incremented while testCol is decremented to move one more space up and to the left.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(testRow, testCol), p)) {
                numConsecutiveRight++;
                testRow++;
                testCol--;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }

        // check numConsecutive for early termination
        if (numConsecutiveRight >= getNumToWin()) {
            return true;
        }

        // Now checks space 1 to the down and to the right of the starting position
        testRow = startRow - 1;
        testCol = startCol + 1;
        searching = true;
        // reset the number of tokens checked
        numChecked = 0;

        // Loop iterates while matching tokens are found down and to the right of the starting position and the minimum
        // row and maximum column #s have not been passed
        while (testCol < getNumColumns() && testRow >= 0 && searching && numChecked < getNumToWin()) {
            // If the next token on the diagonal matches p, numConsecutiveRight is incremented, and testRow is
            // decremented while testCol is incremented to move one more space down and to the right.
            // Otherwise, searching is set false
            if (isPlayerAtPos(new BoardPosition(testRow, testCol), p)) {
                numConsecutiveRight++;
                testRow--;
                testCol++;
                // increment the number of tokens checked in this direction
                numChecked++;
            }
            else {
                searching = false;
            }
        }
        // Returns whether the number of consecutive tokens on the left or right diagonal
        // is greater than or equal to #required for win
        return numConsecutiveLeft >= getNumToWin() || numConsecutiveRight >= getNumToWin();
    }

    /**
     * Returns a boolean representing whether specified player has a token in the specified position
     *
     * @param pos is the position being checked for a piece of a specified player
     * @param player is the character that represents the player being checked for
     *
     * @return true if the specified player is in the position, and false if the player is not
     *
     * @pre [player is an uppercase letter character]
     *
     * @post isPlayerAtPos = true IFF whatsAtPos(pos) == player, false OW AND
     *          self = #self AND 
     *          tokens_in_board = #tokens_in_board
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player) { return whatsAtPos(pos) == player; }
}
