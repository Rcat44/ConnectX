package cpsc2150.extendedConnectX.tests;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
    Noah  Fultz     (nfultz23)
    James Moore     (1091229)
    Reese Myers     (Rcat44)
    Bryan Pisciotti (bpisciotti)
 */

import cpsc2150.extendedConnectX.models.BoardPosition;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.IGameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameBoardMem {
    private static final int NUMBER_OF_ONE_DIGIT_COLS = 10;

    /**
     * Factory for an object of static type IGameBoard.
     *
     * @param numRows int representing the number of rows in the board
     * @param numColumns int representing the number of columns in the board
     * @param numToWin int representing the number of tokens in a row required to win
     *
     * @return object of dynamic type GameBoardMem
     *
     * @pre IGameBoard.MIN_ROWS <= numRows <= IGameBoard.MAX_ROWS
     *          IGameBoard.MIN_COLS <= numColumns <= IGameBoard.MAX_COLS
     *          IGameBoard.MIN_TO_WIN <= numToWin <= IGameBoard.MAX_NUM_TO_WIN
     *
     * @post IGameBoardFactory = new GameBoardMem(numRows, numColumns, numToWin)
     */
    private IGameBoard IGameBoardFactory(int numRows, int numColumns, int numToWin) {
        return new GameBoardMem(numRows, numColumns, numToWin);
    }

    /**
     * Helper method for creating and initializing a 2D array to be used in creating expected board states
     *
     * @param numRows int representing the number of rows in the board
     * @param numCols int representing the number of columns in the board
     * @param fillChar char representing character to fill the created 2D array with
     *
     * @return 2D character array representing the board
     *
     * @pre IGameBoard.MIN_ROWS <= numRows <= IGameBoard.MAX_ROWS
     *          IGameBoard.MIN_COLS <= numColumns <= IGameBoard.MAX_COLS
     *
     * @post initializeBoard = [A 2D character array of size numRows x numCols where each position contains fillChar]
     */
    private char[][] initializeBoard(int numRows, int numCols, char fillChar) {
        char[][] initializedBoard = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                initializedBoard[i][j] = fillChar;
            }
        }
        return initializedBoard;
    }

    /**
     * Helper method for creating and initializing a 2D array to be used in creating expected board states
     *
     * @param compareBoard 2D character array representing the expected state of the boar
     * @param rows int representing the number of rows in the board
     * @param cols int representing the number of columns in the board
     *
     * @return the string of the whole board with board spaces delineated horizontally with a '|' character,
     * the values stored as ' ' OR chars representing player tokens, and the column index at the top
     *
     * @pre IGameBoard.MIN_ROWS <= rows <= IGameBoard.MAX_ROWS AND
     *      IGameBoard.MIN_COLS <= cols <= IGameBoard.MAX_COLS AND
     *      rows == [the number of rows in compareBoard] AND
     *      cols == [the number of cols in compareBoard]
     *
     * @post @post [formatted string representing entire board is returned]
     */
    public String expectedArrayToString(char[][] compareBoard, int rows, int cols) {
        StringBuilder board = new StringBuilder();

        // Column numbers at top of board
        for (int i = 0; i < cols; i++) {
            board.append("|");
            // Adds space in front of one digit column numbers to right justify
            if (i < NUMBER_OF_ONE_DIGIT_COLS) {
                board.append(" ");
            }
            board.append(i);
        }
        board.append("|\n");

        // Appends higher rows first
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                board.append("|");
                board.append(compareBoard[i][j]);
                board.append(" ");
            }
            board.append("|\n");
        }
        // Converts StringBuilder to String and returns
        return board.toString();
    }

    @Test
    // tests constructor with numRows = 3, numCols = 3 and numToWin = 3 by checking getters and state of board
    public void testBoardConstructor_rows3_cols3_toWin3() {
        int numRows = 3;
        int numCols = 3;
        int numToWin = 3;
        char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

        IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);

        assertEquals(numRows, resultBoard.getNumRows());
        assertEquals(numCols, resultBoard.getNumColumns());
        assertEquals(numToWin, resultBoard.getNumToWin());
        assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
    }

    @Test
    // tests constructor with numRows = 100, numCols = 100, and numToWin = 25 by checking getters and state of board
    public void testBoardConstructor_rows100_cols100_toWin25() {
        int numRows = 100;
        int numCols = 100;
        int numToWin = 25;
        char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

        IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);

        assertEquals(numRows, resultBoard.getNumRows());
        assertEquals(numCols, resultBoard.getNumColumns());
        assertEquals(numToWin, resultBoard.getNumToWin());
        assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
    }

    @Test
    // tests constructor with numRows = 23, numCols = 74, and numToWin = 13 by checking getters and state of board
    public void testBoardConstructor_rows23_cols74_toWin13() {
        int numRows = 23;
        int numCols = 74;
        int numToWin = 13;
        char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

        IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);

        assertEquals(numRows, resultBoard.getNumRows());
        assertEquals(numCols, resultBoard.getNumColumns());
        assertEquals(numToWin, resultBoard.getNumToWin());
        assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
    }

    @Test
    // Checks that checkTie returns false for an empty board
    public void testCheckTie_emptyBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        assertFalse(testBoard.checkTie());
    }
    @Test
    // Checks that checkTie returns true for a full board
    public void testCheckTie_fullBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                testBoard.dropToken('X', c);
            }
        }

        assertTrue(testBoard.checkTie());
    }

    @Test
    // Checks that checkTie returns false for a board one token from being full
    public void testCheckTie_almostFullBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int c = 0; c < testBoard.getNumColumns() - 1; c++) {
            for (int r = 0; r < testBoard.getNumRows(); r++) {
                testBoard.dropToken('X', c);
            }
        }

        for (int r = 0; r < testBoard.getNumRows()-1; r++) {
            testBoard.dropToken('X', testBoard.getNumColumns() - 1);
        }

        assertFalse(testBoard.checkTie());
    }

    @Test
    // Checks that checkTie returns false for a board that is about halfway full
    public void testCheckTie_halfFullBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int c = 0; c < testBoard.getNumColumns() / 2; c++) {
            for (int r = 0; r < testBoard.getNumRows(); r++) {
                testBoard.dropToken('X', c);
            }
        }

        assertFalse(testBoard.checkTie());
    }

    @Test
    // Checks that checkIfFree returns true for an empty column
    public void testCheckIfFree_emptyCol() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        assertTrue(testBoard.checkIfFree(0));
    }

    @Test
    // Checks that checkIfFree returns false for a full column
    public void testCheckIfFree_fullCol() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int r = 0; r < testBoard.getNumRows(); r++) {
            testBoard.dropToken('X', 0);
        }

        assertFalse(testBoard.checkIfFree(0));
    }

    @Test
    // Checks that checkIfFree returns true for a column that is one token from being full
    public void testCheckIfFree_almostFullCol() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int r = 0; r < testBoard.getNumRows() - 1; r++) {
            testBoard.dropToken('X', 0);
        }

        assertTrue(testBoard.checkIfFree(0));
    }

    @Test
    // Checks that a token falls to the bottom of the appropriate column in an empty board
    public void testDropToken_emptyBoard() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

       expectedBoard[0][0] = 'X';

       IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);

       resultBoard.dropToken('X', 0);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
   }

    @Test
    // Checks that a token falls to the bottom of the column that is the last non-full column
    public void testDropToken_lastRemainingCol() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       char[][] expectedBoard = initializeBoard(numRows, numCols, 'X');
       for (int r = 0; r < numRows; r++) {
           expectedBoard[r][numCols-1] = ' ';
       }

       IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);
       for (int c = 0; c < numCols-1; c++ ) {
           for (int r = 0; r < numRows; r++) {
               resultBoard.dropToken('X',c);
           }
       }
       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());

       expectedBoard[0][numCols-1] = 'X';
       resultBoard.dropToken('X',numCols-1);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
   }



    @Test
    // Checks that a token falls on top of a token that already exists in a column
    public void testDropToken_colNotEmpty() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

       expectedBoard[0][0] = 'X';
       expectedBoard[0][1] = 'O';
       expectedBoard[0][2] = 'X';

       IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);
       resultBoard.dropToken('X', 0);
       resultBoard.dropToken('O', 1);
       resultBoard.dropToken('X',2);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());

       expectedBoard[1][2] = 'O';
       resultBoard.dropToken('O',2);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
   }



    @Test
    // Checks that a token falls in the last remaining position in a column when the token is one away from being full
    public void testDropToken_colAlmostFull() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       char[][] expectedBoard = initializeBoard(numRows, numCols, ' ');

       expectedBoard[0][0] = 'X';
       expectedBoard[0][1] = 'O';
       expectedBoard[0][2] = 'X';
       expectedBoard[1][2] = 'O';
       expectedBoard[2][2] = 'X';
       expectedBoard[3][2] = 'O';

       IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);
       resultBoard.dropToken('X', 0);
       resultBoard.dropToken('O', 1);
       resultBoard.dropToken('X',2);
       resultBoard.dropToken('O',2);
       resultBoard.dropToken('X', 2);
       resultBoard.dropToken('O', 2);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());

       expectedBoard[4][2] = 'X';
       resultBoard.dropToken('X',2);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
   }


    @Test
    // Checks that a token falls in the last remaining space in the board when the board is one away from being full
    public void testDropToken_lastSpaceInBoard() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       char[][] expectedBoard = initializeBoard(numRows, numCols, 'X');

       expectedBoard[numRows-1][numCols-1] = ' ';

       IGameBoard resultBoard = IGameBoardFactory(numRows, numCols, numToWin);
       for (int c = 0; c < numCols; c++ ) {
           for (int r = 0; r < numRows; r++) {
               if (c != numCols-1 || r != numRows-1) {
                   resultBoard.dropToken('X',c);
               }
           }
       }

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());

       expectedBoard[numRows-1][numCols-1] = 'X';
       resultBoard.dropToken('X',numCols-1);

       assertEquals(expectedArrayToString(expectedBoard, numRows, numCols), resultBoard.toString());
   }


    @Test
    // Checks that whatsAtPos returns ' ' for every position in a blank board
    public void testWhatsAtPos_emptyBoard() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       for (int c = 0; c < numCols; c++) {
           for (int r = 0; r < numRows; r++) {
               assertEquals(' ', testBoard.whatsAtPos(new BoardPosition(r, c)));
           }
       }
   }


    @Test
    // Checks that whatsAtPos returns a player token for every position in a full board
    public void testWhatsAtPos_nonEmptyBoard() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('X', 0);

       assertEquals('X', testBoard.whatsAtPos(new BoardPosition(0, 0)));
   }


    @Test
    // Checks that whatsAtPos returns a ' ' for a blank position above a player token
    public void testWhatsAtPos_blankSpaceAboveToken() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('X', 0);

       assertEquals(' ',testBoard.whatsAtPos(new BoardPosition(1, 0)));
   }


    @Test
    // Checks that whatsAtPos returns a player token for each position in a full column
    public void testWhatsAtPos_fullCol() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       for (int r = 0; r < numRows; r++) {
           testBoard.dropToken('X', 0);
       }

       for (int r = 0; r < numRows; r++) {
           assertEquals('X', testBoard.whatsAtPos(new BoardPosition(r, 0)));
       }
   }


    @Test
    // Checks that whatsAtPos returns a player token for each position in a full board
    public void testWhatsAtPos_fullBoard() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 5;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       for (int c = 0; c < numCols; c++) {
           for (int r = 0; r < numRows; r++) {
               testBoard.dropToken('X', c);
           }
       }

       for (int c = 0; c < numCols; c++) {
           for (int r = 0; r < numRows; r++) {
               assertEquals('X', testBoard.whatsAtPos(new BoardPosition(r, c)));
           }
       }
   }


    @Test
    // Checks that isPlayerAtPos returns false for all positions in an empty board
    public void testisPlayerAtPos_emptyBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);
        
        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                assertFalse(testBoard.isPlayerAtPos(new BoardPosition(r, c),'X'));
            }
        }
    }

    @Test
    // Checks that isPlayerAtPos returns true for a position where a player is located in a non-empty board
    public void testisPlayerAtPos_nonEmptyBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('X', 0);

        assertTrue(testBoard.isPlayerAtPos(new BoardPosition(0,0), 'X'));
    }

    @Test
    // Checks that isPlayerAtPos returns false for a blank position above a player token
    public void testIsPlayerAtPos_blankSpaceAboveToken() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('X', 0);
        
        assertFalse(testBoard.isPlayerAtPos(new BoardPosition(1, 0), 'X'));
    }

    @Test
    // Checks that isPlayerAtPos returns true for all positions in a column full of a single player's token
    public void testIsPlayerAtPos_fullCol() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int r = 0; r < numRows; r++) {
            testBoard.dropToken('X', 0);
        }

        for (int r = 0; r < numRows; r++) {
            assertTrue(testBoard.isPlayerAtPos(new BoardPosition(r, 0), 'X'));
        }
    }

    @Test
    // Checks that isPlayerAtPos returns true for all positions in a board full of a single player's token
    public void testIsPlayerAtPos_fullBoard() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 5;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                testBoard.dropToken('X', c);
            }
        }

        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                assertTrue(testBoard.isPlayerAtPos(new BoardPosition(r, c), 'X'));
            }
        }
    }

    @Test
    // Checks that checkHorizWin returns true when a token forms a chain of numToWin tokens horizontally
    // by being placed in the middle
    public void testCheckHorizWin_win_last_marker_middle() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);
        testBoard.dropToken('X', 0);

        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 2);

        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 4);

        testBoard.dropToken('X', 2);

        assertTrue(testBoard.checkHorizWin(new BoardPosition(1,2), 'X'));
    }

    @Test
    // Checks that checkHorizWin returns true when a token forms a chain of numToWin tokens
    // horizontally by being placed on the left
    public void testCheckHorizWin_win_last_marker_left() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);

        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 2);
        testBoard.dropToken('X', 2);

        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);
        testBoard.dropToken('O', 4);

        testBoard.dropToken('X', 0);

        assertTrue(testBoard.checkHorizWin(new BoardPosition(1,0), 'X'));
    }
    
    @Test
    // Checks that checkHorizWin returns true when a token forms a chain of numToWin tokens
    // horizontally by being placed on the right
    public void testCheckHorizWin_win_last_marker_right() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);

        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);
    
        testBoard.dropToken('O', 2);
        testBoard.dropToken('X', 2);

        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 4);

        testBoard.dropToken('X', 4);

        assertTrue(testBoard.checkHorizWin(new BoardPosition(1,4), 'X'));
    }

    @Test
    // Checks that checkHorizWin returns false when a token does not form a chain of numToWin tokens horizontally
    public void testCheckHorizWin_noWin() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);
        testBoard.dropToken('X', 0);

        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 2);

        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 4);

        testBoard.dropToken('X', 4);

        assertFalse(testBoard.checkHorizWin(new BoardPosition(1,4), 'X'));
    }

    @Test
    // Checks that checkVertWin returns false when a token does not form a chain of numToWin tokens vertically
    public void testCheckVertWin_noWin() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);
        testBoard.dropToken('O', 0);

        testBoard.dropToken('X', 1);
        testBoard.dropToken('X', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 0);

        assertFalse(testBoard.checkVertWin(new BoardPosition(2,0), 'O'));
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens vertically
    // and the chain touches the bottom of the board
    public void testCheckVertWin_win_tokens_touch_bottom() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);
        testBoard.dropToken('O', 0);
        testBoard.dropToken('O', 0);

        testBoard.dropToken('X', 1);
        testBoard.dropToken('X', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('X', 1);

        assertTrue(testBoard.checkVertWin(new BoardPosition(3,1), 'X'));

    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens vertically
    // and the chain rests atop another player's token
    public void testCheckVertWin_win_other_player_below() {
       int numRows = 6;
       int numCols = 6;
       int numToWin = 4;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('O', 0);
       testBoard.dropToken('O', 0);
       testBoard.dropToken('O', 0);

       testBoard.dropToken('O', 1);
       testBoard.dropToken('X', 1);
       testBoard.dropToken('X', 1);
       testBoard.dropToken('X', 1);

       testBoard.dropToken('X', 2);

       testBoard.dropToken('X', 1);


       assertTrue(testBoard.checkVertWin(new BoardPosition(4,1), 'X'));

    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens vertically
    // and token was the last token placed in the column
    public void testCheckVertWin_win_full_col() {

       int numRows = 6;
       int numCols = 6;
       int numToWin = 4;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('O', 0);
       testBoard.dropToken('O', 0);
       testBoard.dropToken('O', 0);

       testBoard.dropToken('O', 1);
       testBoard.dropToken('O', 1);
       testBoard.dropToken('X', 1);
       testBoard.dropToken('X', 1);
       testBoard.dropToken('X', 1);

       testBoard.dropToken('X', 2);
       testBoard.dropToken('X', 2);

       testBoard.dropToken('X', 1);

       assertTrue(testBoard.checkVertWin(new BoardPosition(5,1), 'X'));
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed at the bottom right of the left diagonal
    public void testCheckDiagWin_win_leftDiag_last_bottom() {

       int numRows = 5;
       int numCols = 5;
       int numToWin = 4;

       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('O', 1);
       testBoard.dropToken('O', 1);
       testBoard.dropToken('O', 1);
       testBoard.dropToken('X', 1);

       testBoard.dropToken('O', 2);
       testBoard.dropToken('X', 2);
       testBoard.dropToken('X', 2);

       testBoard.dropToken('O', 3);
       testBoard.dropToken('X', 3);

       testBoard.dropToken('X', 4);

       assertTrue(testBoard.checkDiagWin(new BoardPosition(0,4), 'X'));
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed at the top left of the left diagonal
    public void testCheckDiagWin_win_leftDiag_last_top() {
       int numRows = 5;
       int numCols = 5;
       int numToWin = 4;


       IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

       testBoard.dropToken('O', 1);
       testBoard.dropToken('O', 1);
       testBoard.dropToken('O', 1);

       testBoard.dropToken('O', 2);
       testBoard.dropToken('X', 2);
       testBoard.dropToken('X', 2);

       testBoard.dropToken('O', 3);
       testBoard.dropToken('X', 3);

       testBoard.dropToken('X', 4);

       testBoard.dropToken('X', 1);

       assertTrue(testBoard.checkDiagWin(new BoardPosition(3,1), 'X'));
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed in the middle of the left diagonal
    public void testCheckDiagWin_win_leftDiag_last_mid() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 1);
        testBoard.dropToken('O', 1);
        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 2);
        testBoard.dropToken('X', 2);
        testBoard.dropToken('X', 2);

        testBoard.dropToken('O', 3);

        testBoard.dropToken('X', 4);

        testBoard.dropToken('X', 3);

        assertTrue(testBoard.checkDiagWin(new BoardPosition(1,3), 'X'));    
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed at the bottom left of the right diagonal
    public void testCheckDiagWin_win_rightDiag_last_bottom() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);
        testBoard.dropToken('X', 4);

        testBoard.dropToken('O', 3);
        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 2);
        testBoard.dropToken('X', 2);

        testBoard.dropToken('X', 1);

        assertTrue(testBoard.checkDiagWin(new BoardPosition(0,1), 'X'));
 
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed at the top right of the right diagonal
    public void testCheckDiagWin_win_rightDiag_last_top() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);

        testBoard.dropToken('O', 3);
        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 2);
        testBoard.dropToken('X', 2);

        testBoard.dropToken('X', 1);

        testBoard.dropToken('X', 4);

        assertTrue(testBoard.checkDiagWin(new BoardPosition(3,4), 'X'));
    }

    @Test
    // Checks that checkVertWin returns true when a token forms a chain of numToWin tokens diagonally
    // and token was the last token placed in the middle of the right diagonal
    public void testCheckDiagWin_win_rightDiag_last_mid() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);
        testBoard.dropToken('O', 4);
        testBoard.dropToken('X', 4);

        testBoard.dropToken('O', 3);
        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('O', 2);

        testBoard.dropToken('X', 1);

        testBoard.dropToken('X', 2);

        assertTrue(testBoard.checkDiagWin(new BoardPosition(1,2), 'X')); 
    }

    @Test
    // Checks that checkVertWin returns true when a token does not form a chain of numToWin tokens diagonally
    public void testCheckDiagWin_loss() {
        int numRows = 5;
        int numCols = 5;
        int numToWin = 4;

        IGameBoard testBoard = IGameBoardFactory(numRows, numCols, numToWin);

        testBoard.dropToken('O', 0);
        testBoard.dropToken('X', 0);
        testBoard.dropToken('O', 0);

        testBoard.dropToken('X', 1);
        testBoard.dropToken('O', 1);
        testBoard.dropToken('X', 1);

        testBoard.dropToken('O', 2);
        testBoard.dropToken('O', 2);
        testBoard.dropToken('O', 2);

        testBoard.dropToken('O', 3);
        testBoard.dropToken('X', 3);
        testBoard.dropToken('X', 3);

        testBoard.dropToken('X', 4);
        testBoard.dropToken('X', 4);
        testBoard.dropToken('O', 4);

        testBoard.dropToken('X', 2);

        assertFalse(testBoard.checkDiagWin(new BoardPosition(3,2), 'X'));
    }
}
