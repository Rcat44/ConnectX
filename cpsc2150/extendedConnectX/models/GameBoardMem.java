package cpsc2150.extendedConnectX.models;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
    Noah  Fultz     (nfultz23)
    James Moore     (1091229)
    Reese Myers     (Rcat44)
    Bryan Pisciotti (bpisciotti)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for a GameBoardMem representing a ConnectX board. The GameBoardMem is represented by a Hash Map
 * with Character keys and BoardPosition entries.
 *
 * @invariant 0 <= currentNumTokens <= MAX_TOKENS_ALLOWED AND
 *              IGameBoard.MIN_ROWS <= NUM_ROWS <= IGameBoard.MAX_ROWS AND
 *              IGameBoard.MIN_COLS <= NUM_COLUMNS <= IGameBoard.MAX_COLS AND
 *              IGameBoard.MIN_TO_WIN <= NUM_TO_WIN <= IGameBoard.MAX_NUM_TO_WIN AND
 *              NUM_TO_WIN <= NUM_ROWS AND
 *              NUM_TO_WIN <= NUM_COLS AND
 *              MAX_TOKENS_ALLOWED = NUM_ROWS * NUM_COLUMNS AND
 *              [self should not contain an empty row below an occupied row] AND
 *              [self should only contain blank spaces and uppercase letters]
 * <p>
 * @corresponds NUMBER_OF_ROWS = NUM_ROWS
 * @corresponds NUMBER_OF_COLUMNS = NUM_COLUMNS
 * @corresponds NUMBER_TO_WIN = NUM_TO_WIN
 * @corresponds TOKENS_WHEN_FULL = MAX_TOKENS_ALLOWED
 * @corresponds tokens_in_board = currentNumTokens
 * @corresponds self = board
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard {

    private final int NUM_ROWS;
    private final int NUM_COLUMNS;
    private final int NUM_TO_WIN;
    private final int MAX_TOKENS_ALLOWED;
    private int currentNumTokens;

    private Map<Character, List<BoardPosition>> board;

    /**
     * Constructor for a GameBoardMem object.
     *
     * @param numRows int representing the number of rows in the board
     * @param numColumns int representing the number of columns in the board
     * @param numToWin int representing the number of tokens in a row required to win
     *
     * @pre IGameBoard.MIN_ROWS <= numRows <= IGameBoard.MAX_ROWS AND
     *       IGameBoard.MIN_COLS <= numColumns <= IGameBoard.MAX_COLS AND
     *       IGameBoard.MIN_TO_WIN <= numToWin <= IGameBoard.MAX_NUM_TO_WIN AND
     *       numToWin <= numRows AND
     *       numToWin <= numColumns
     * 
     * @post this.NUM_ROWS = numRows AND
     *       this.NUM_COLUMNS = numColumns AND 
     *       this.NUM_TO_WIN = numToWin AND
     *       this.MAX_TOKENS_ALLOWED = this.NUMBER_OF_ROWS * this.NUMBER_OF_COLUMNS AND
     *       this.currentNumTokens = 0 AND 
     *       board = new HashMap<>
     */
    public GameBoardMem(int numRows, int numColumns, int numToWin) {
        this.NUM_ROWS = numRows;
        this.NUM_COLUMNS = numColumns;
        this.NUM_TO_WIN = numToWin;
        this.MAX_TOKENS_ALLOWED = this.NUM_ROWS * this.NUM_COLUMNS;
        this.currentNumTokens = 0;

        this.board = new HashMap<>();
    }
    
    

    /**
     * Function returns number of rows in board
     *
     * @return int representing number of rows in the board
     *
     * @pre None
     * 
     * @post getNumRows = NUM_ROWS AND 
     *          self = #self AND 
     *          currentNumTokens = #currentNumTokens
     */
    public int getNumRows() {
        return this.NUM_ROWS;
    }

    /**
     * Function returns the number of columns in board
     *
     * @return int representing number of columns in the board
     *
     * @pre None
     *
     * @post getNumColumns = NUM_COLUMNS AND
     *          self = #self AND
     *          currentNumTokens = #currentNumTokens
     */
    public int getNumColumns() {
        return this.NUM_COLUMNS;
    }

    /**
     * Function returns the number of tokens in a row a player needs to achieve to win
     *
     * @return int representing the number of consecutive tokens needed to win
     *
     * @pre None
     *
     * @post getNumToWin = NUM_TO_WIN AND 
     *          self = #self AND 
     *          currentNumTokens = #currentNumTokens
     */
    public int getNumToWin() {
        return this.NUM_TO_WIN;
    }

    /**
     * Returns character in self at position pos.
     *
     * @return the character at the given position pos on the board
     *
     * @param pos BoardPosition object containing particular row and column values
     *
     * @pre None
     *
     * @post whatsAtPos = [the player character associated with position pos, or ' ' if the position is empty] AND
     *          currentNumTokens = #currentNumTokens AND 
     *          self = #self
     */
    public char whatsAtPos(BoardPosition pos) {

        for (Map.Entry<Character, List<BoardPosition>> mapEntry : board.entrySet()) {
            char key = mapEntry.getKey();
            List<BoardPosition> mappedPositions = mapEntry.getValue();
            
            for (BoardPosition position : mappedPositions) {
                if (pos.equals(position)) {
                    return key;
                }
            }
        }

        return ' ';
    }

    /**
     * Updates self by having the token fall to the lowest available row in column c.
     *
     * @param p uppercase letter character representing the player
     * @param c number representing selected column as an int
     *
     * @pre [p is an uppercase letter character] AND
     *          0 <= c < NUM_COLUMNS AND
     *          checkIfFree(c) == true
     *
     * @post [self = #self with a new entry added to the list for character p corresponding to the lowest open row in c] AND
     *          currentNumTokens = #currentNumTokens + 1
     */
    public void dropToken(char p, int c) {
        // Initializes dropRow to -1
        int dropRow = -1;

        // Outer loop iterates through each list in the map
        for (Map.Entry<Character, List<BoardPosition>> mapEntry : board.entrySet()) {
            List<BoardPosition> mappedPositions = mapEntry.getValue();

            // Inner loop iterates through each position in the current list
            for (BoardPosition position : mappedPositions) {
                // If a position is found in column c, and the position's row is
                // greater than dropRow, dropRow is set to the position's row
                if (position.getColumn() == c && position.getRow() > dropRow) {
                    dropRow = position.getRow();
                }
            }
        }
        // Increments dropRow because token must be dropped one position above the column's highest token
        // (Or in row 0 if dropRow was never updated in the loop)
        dropRow++;

        // If the board contains a key value of p, then the new position representing the location of the token
        // being dropped can be added to the existing list mapped to p.
        // Else, a new list will need to be created with its sole element being the new position, and then
        // the new list will need to be mapped to p.
        if (board.containsKey(p)) {
            board.get(p).add(new BoardPosition(dropRow, c));
        }
        else {
            List <BoardPosition> playerList = new ArrayList<>();
            playerList.add(new BoardPosition(dropRow, c));
            board.put(p, playerList);
        }
        // Increments currentNumTokens
        this.currentNumTokens++;
    }

    /**
     * Returns a boolean representing whether the specified player has a token in the specified position
     *
     * @param pos is the position being checked for a piece of a specified player
     * @param player is the character that represents the player being checked for
     *
     * @return true if the specified player is in the position, and false if the player is not
     *
     * @pre [player is an uppercase letter character]
     *
     * @post isPlayerAtPos = [true IFF a position equivalent to pos is found in the list mapped to player, false OW] AND
     *          self = #self AND 
     *          currentNumTokens = #currentNumTokens
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (!board.containsKey(player)) {
            return false;
        }
        // playerList is the list mapped to by player
        List <BoardPosition> playerList = board.get(player);

        // Iterates through the entire list searching for an equivalent position to pos.
        // If an equivalent position is found, returns true
        for (BoardPosition position : playerList) {
            if (pos.equals(position)) {
                return true;
            }
        }
        // Returns false if no match was found
        return false;
    }

    /**
     * Returns boolean representing whether the board is completely full after checking how many tokens have been played
     *
     * @return false if at least one free space remains and true if none remain
     *
     * @pre None
     *
     * @post checkTie = true IFF currentNumTokens == MAX_TOKENS_ALLOWED, false OW AND
     *          self = #self AND 
     *          currentNumTokens = #currentNumTokens
     */
    @Override
    public boolean checkTie() {
        return currentNumTokens == this.MAX_TOKENS_ALLOWED;
    }
}