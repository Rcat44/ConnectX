package cpsc2150.extendedConnectX.models;

/**
 * This abstract class only contains a function to convert self to a string that represents the ConnectX board.
 */
public abstract class AbsGameBoard implements IGameBoard {
    private static final int NUMBER_OF_ONE_DIGIT_COLS = 10;
    /**
     * Converts all characters in self to a formatted string
     *
     * @return the string of the whole board with board spaces delineated horizontally with a '|' character,
     * the values stored as ' ' OR chars representing player tokens, and the column index at the top
     *
     * @pre None

     * @post toString = [a formatted string representing entire board is returned] AND 
     *          self = #self
     */
    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();

        // Column numbers at top of board
        for (int i = 0; i < getNumColumns(); i++) {
            board.append("|");
            // Adds space in front of one digit column numbers to right justify
            if (i < NUMBER_OF_ONE_DIGIT_COLS) {
                board.append(" ");
            }
            board.append(i);
        }
        board.append("|\n");

        // Appends higher rows first
        for (int i = getNumRows() - 1; i >= 0; i--) {
            for (int j = 0; j < getNumColumns(); j++) {
                board.append("|");
                board.append(this.whatsAtPos(new BoardPosition(i,j)));
                board.append(" ");
            }
            board.append("|\n");
        }
        // Converts StringBuilder to String and returns
        return board.toString();
    }
}
