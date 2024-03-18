package cpsc2150.extendedConnectX.models;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
    Noah  Fultz     (nfultz23)
    James Moore     (1091229)
    Reese Myers     (Rcat44)
    Bryan Pisciotti (bpisciotti)
 */

/**
 * This class is for BoardPositions located on a ConnectX board. Each BoardPosition has a row number and column number.
 *
 * @invariant 0 <= Row < [the number of rows in the current board] AND
 *            0 <= Column < [the number of columns in the current board]
 */
public class BoardPosition
{
    private int Row;
    private int Column;

    /**
    * Constructor for the BoardPosition object. Sets the instance vars to the values passed in via params.
    *
    * @param aRow number representing the row index of the position
    * @param aColumn number representing the column index of the position
    *
    * @invariant 0 <= aRow < [the number of rows in the current board] AND
    *            0 <= aColumn < [the number of columns in the current board]
    *
    * @post Row = aRow AND Column = aColumn
    */
    public BoardPosition(int aRow, int aColumn)
    {
        //parameterized constructor for BoardPosition
        this.Row = aRow;
        this.Column = aColumn;
    }

    /**
    * Returns the integer value stored in the Row field of the object
    *
    * @return Row number for this instance of a BoardPosition
    *
    * @pre None
    *
    * @post getRow = Row AND Row = #Row AND Column = #Column
    */
    public int getRow()
    {
        //returns the row
        return this.Row;
    }

    /**
    * Returns the integer value stored in the Column field of the object
    *
    * @return Column number for this instance of a BoardPosition
    *
    * @pre None
    *
    * @post getColumn = Column AND Row = #Row AND Column = #Column
    */
    public int getColumn()
    {
        //returns the column
        return this.Column;
    }

    /**
    * Returns boolean representing whether another object equals the current BoardPosition object
    *
    * @param obj Object represents the other object that will be compared to this one
    *
    * @return true if obj is a BoardPosition object with equivalent row and column values
    *
    * @pre None
    *
    * @post equals = [true IFF obj is a BoardPosition with equal Row and Column values to self] AND
    *           Row = #Row AND 
    *           Column = #Column
    */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BoardPosition) {
            BoardPosition compare = (BoardPosition) obj;
            return ((this.Row == compare.Row) && (this.Column == compare.Column));
        }
        else {
            return false;
        }

    }

    /**
    * Converts Row and Column values to a formatted string
    *
    * @return string with row and column values formatted as "<Row>,<Column>"
    *
    * @pre None
    *
    * @post toString = "<Row>,<Column>" AND Row = #Row AND Column = #Column
    */
    @Override
    public String toString()
    {
        return (this.Row + "," + this.Column);
    }
}
