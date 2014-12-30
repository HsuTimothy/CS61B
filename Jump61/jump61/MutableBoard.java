package jump61;

import static jump61.Side.*;
import static jump61.Square.square;
import java.util.Stack;

/** A Jump61 board state that may be modified.
 *  @author Michael Ferrin
 */
class MutableBoard extends Board {

    /** Stores size of board for all to use.*/
    private static int _boardSize;
    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _boardSize = N;
        _gameBoard = new Square[_boardSize][_boardSize];
        for (int x = 0; x < _boardSize; x++) {
            for (int y = 0; y < _boardSize; y++) {
                _gameBoard[x][y] = square(WHITE, 1);
            }
        }
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        int i = 0;
        _boardSize = board0.size();
        _gameBoard = new Square[_boardSize][_boardSize];
        for (int x = 0; x < _boardSize; x++) {
            for (int y = 0; y < _boardSize; y++) {
                _gameBoard[x][y] = board0.get(i);
                i++;
            }
        }
    }

    @Override
    void clear(int N) {
        MutableBoard copyB = new MutableBoard(N);
        _gameBoard = copyB._gameBoard;
        undoStack.removeAllElements();
        announce();
    }

    @Override
    void copy(Board board) {
        int i = 0;
        for (int x = 0; x < _boardSize; x++) {
            for (int y = 0; y < _boardSize; y++) {
                _gameBoard[x][y] = board.get(i);
                i++;
            }
        }
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
    }

    @Override
    int size() {
        return _boardSize;
    }

    @Override
    Square get(int n) {
        int irow = row(n) - 1;
        int icol = col(n) - 1;
        return _gameBoard[irow][icol];
    }

    @Override
    int numOfSide(Side side) {
        int numBlue = 0;
        int numRed = 0;
        int numWhite = 0;
        for (int x = 0; x < _boardSize; x++) {
            for (int y = 0; y < _boardSize; y++) {
                if (_gameBoard[x][y].getSide() == RED) {
                    numRed++;
                } else if (_gameBoard[x][y].getSide() == BLUE) {
                    numBlue++;
                } else {
                    numWhite++;
                }
            }
        }
        if (side == RED) {
            return numRed;
        } else if (side == BLUE) {
            return numBlue;
        }
        return numWhite;
    }

    @Override
    int numPieces() {
        int total = 0;
        for (int x = 0; x < _boardSize; x++) {
            for (int y = 0; y < _boardSize; y++) {
                total = total + _gameBoard[x][y].getSpots();
            }
        }
        return total;
    }

    @Override
    void addSpot(Side player, int r, int c) {
        markUndo();
        int currentSpots = _gameBoard[r - 1][c - 1].getSpots();
        int numberOfSpots = _gameBoard[r - 1][c - 1].getSpots();
        int numberOfNeighbors = neighbors(r, c);
        if (numberOfSpots == numberOfNeighbors) {
            overflow(r, c, player);
        } else {
            _gameBoard[r - 1][c - 1] = square(player, (currentSpots + 1));
        }
        announce();
    }

    /** Helper method to handle overflow from addSpot.
        @param row is a row
        @param column is a col
        @param player is side*/
    private void overflow(int row, int column, Side player) {
        if (winYet()) {
            return;
        }
        _gameBoard[row - 1][column - 1] = square(player, 1);
        conquerAllNeighbors(player, row, column);
    }

    /** Handles which neighbors to overflow into.
        @param player is side
        @param row is a row
        @param col is a col*/
    private void conquerAllNeighbors(Side player, int row, int col) {
        int mRow = row - 1;
        int pRow = row + 1;
        int mCol = col - 1;
        int pCol = col + 1;
        if (mRow > 0 && mRow <= _boardSize) {
            overflowAddSpot(player, mRow, col);
        }
        if (pRow > 0 && pRow <= _boardSize) {
            overflowAddSpot(player, pRow, col);
        }
        if (mCol > 0 && mCol <= _boardSize) {
            overflowAddSpot(player, row, mCol);
        }
        if (pCol > 0 && pCol <= _boardSize) {
            overflowAddSpot(player, row, pCol);
        }
    }

    /** Second add spot that doesnt call undo history.
        @param player side
        @param row is a row
        @param col is a column*/
    private void overflowAddSpot(Side player, int row, int col) {
        int numberOfSpots2 = _gameBoard[row - 1][col - 1].getSpots();
        int numberOfNeighbors2 = neighbors(row, col);
        if (numberOfSpots2 == numberOfNeighbors2) {
            overflow(row, col, player);
        } else {
            int spotsNow = _gameBoard[row - 1][col - 1].getSpots();
            _gameBoard[row - 1][col - 1] = square(player, (spotsNow + 1));
        }
    }

    @Override
    void addSpot(Side player, int n) {
        int tRow = row(n);
        int tCol = col(n);
        addSpot(player, tRow, tCol);
    }

    @Override
    void set(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), square(player, num));
    }

    @Override
    void set(int n, int num, Side player) {
        internalSet(n, square(player, num));
        announce();
    }

    @Override
    void undo() {
        this.copy(undoStack.pop());
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        undoStack.push(new MutableBoard(this));
    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        int jrow = row(ind) - 1;
        int jcol = col(ind) - 1;
        _gameBoard[jrow][jcol] = sq;
    }

    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean winYet() {
        if (numOfSide(RED) == (size() * size())
            || numOfSide(BLUE) == (size() * size())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /** Method to print out the board for testing purposes. */
    private void printit() {
        System.out.print(_gameBoard[0][0].getSpots());
        System.out.print(_gameBoard[0][1].getSpots());
        System.out.print(_gameBoard[0][2].getSpots());
        System.out.print(_gameBoard[0][3].getSpots());
        System.out.print(_gameBoard[0][4].getSpots());
        System.out.print(_gameBoard[0][5].getSpots());
        System.out.println("");
        System.out.print(_gameBoard[1][0].getSpots());
        System.out.print(_gameBoard[1][1].getSpots());
        System.out.print(_gameBoard[1][2].getSpots());
        System.out.print(_gameBoard[1][3].getSpots());
        System.out.print(_gameBoard[1][4].getSpots());
        System.out.print(_gameBoard[1][5].getSpots());
        System.out.println("");
        System.out.print(_gameBoard[2][0].getSpots());
        System.out.print(_gameBoard[2][1].getSpots());
        System.out.print(_gameBoard[2][2].getSpots());
        System.out.print(_gameBoard[2][3].getSpots());
        System.out.print(_gameBoard[2][4].getSpots());
        System.out.print(_gameBoard[2][5].getSpots());
        System.out.println("");
        System.out.print(_gameBoard[3][0].getSpots());
        System.out.print(_gameBoard[3][1].getSpots());
        System.out.print(_gameBoard[3][2].getSpots());
        System.out.print(_gameBoard[3][3].getSpots());
        System.out.print(_gameBoard[3][4].getSpots());
        System.out.print(_gameBoard[3][5].getSpots());
        System.out.println("");
        System.out.print(_gameBoard[4][0].getSpots());
        System.out.print(_gameBoard[4][1].getSpots());
        System.out.print(_gameBoard[4][2].getSpots());
        System.out.print(_gameBoard[4][3].getSpots());
        System.out.print(_gameBoard[4][4].getSpots());
        System.out.print(_gameBoard[4][5].getSpots());
        System.out.println("");
        System.out.print(_gameBoard[5][0].getSpots());
        System.out.print(_gameBoard[5][1].getSpots());
        System.out.print(_gameBoard[5][2].getSpots());
        System.out.print(_gameBoard[5][3].getSpots());
        System.out.print(_gameBoard[5][4].getSpots());
        System.out.print(_gameBoard[5][5].getSpots());
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    /** The gameboard. */
    private Square[][] _gameBoard;

    /** Stack for unod history. */
    private Stack<Board> undoStack = new Stack<Board>();
}
