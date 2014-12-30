package game2048;

import ucb.util.CommandArgs;

import game2048.gui.Game;
import static game2048.Main.Side.*;

/** The main class for the 2048 game.
 *  @author Michael Ferrin
 */
public class Main {

    /** Size of the board: number of rows and of columns. */
    static final int SIZE = 4;
    /** Representation for the magic number.*/
    static final int WINNER = 2048;
    /** Number of squares on the board. */
    static final int SQUARES = SIZE * SIZE;

    /** Symbolic names for the four sides of a board. */
    static enum Side { NORTH, EAST, SOUTH, WEST };

    /** The main program.  ARGS may contain the options --seed=NUM,
     *  (random seed); --log (record moves and random tiles
     *  selected.); --testing (take random tiles and moves from
     *  standard input); and --no-display. */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log --testing --no-display",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log ] [ --testing ] [ --no-display ]");
            System.exit(1);
        }

        Main game = new Main(options);

        while (game.play()) {
            /* No action */
        }
        System.exit(0);
    }

    /** A new Main object using OPTIONS as options (as for main). */
    Main(CommandArgs options) {
        boolean log = options.contains("--log"),
            display = !options.contains("--no-display");
        long seed = !options.contains("--seed") ? 0 : options.getLong("--seed");
        _testing = options.contains("--testing");
        _game = new Game("2048", SIZE, seed, log, display, _testing);
    }

    /** Reset the score for the current game to 0 and clear the board. */
    void clear() {
        _score = 0;
        _count = 0;
        _game.clear();
        _game.setScore(_score, _maxScore);
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[r][c] = 0;
            }
        }
    }

    /** Play one game of 2048, updating the maximum score. Return true
     *  iff play should continue with another game, or false to exit. */
    boolean play() {
        /*set a second random piece to start the game.
          setRandomPiece below should only happen at
          the beginning of the game and if a key is
          pressed causing a move and/or a merge. If
          the key is pressed and nothing happens, dont
          call setRandomPiece. Maybe I could set an index
          in my move and merge methods to see if they get
          called and if not, dont call setRandomPiece*/
        setRandomPiece();

        while (true) {
            setRandomPiece();
            if (gameOver()) {
                _game.endGame();
            }

        GetMove:
            while (true) {
                String key = _game.readKey();

                switch (key) {
                case "Up": case "Down": case "Left": case "Right":

                    if (!gameOver() && tiltBoard(keyToSide(key))) {
                        break GetMove;
                    }
                    break;
                case "New Game":
                    _score = 0;
                    for (int row3 = 0; row3 < 4; row3++) {
                        for (int col3 = 0; col3 < 4; col3++) {
                            if (_board[row3][col3] != 0) {
                                _board[row3][col3] = 0;
                            }
                        }
                    }
                    _game.clear();
                    setRandomPiece();
                    setRandomPiece();
                    break;
                case "Quit":
                    return false;
                default:
                    break;
                }
            }
        }
    }


    /** Return true iff the current game is over (no more moves
        possible). gameOver should also return true assuming _board
        is full and NORTH, EAST, SOUTH or WEST can not merge.  I could
        possibly set a case where, if no spot on the board is equal to
        zero and no bordering tiles are the same value then return true. */
    boolean gameOver() {
        for (int row2 = 0; row2 < 4; row2++) {
            for (int col2 = 0; col2 < 4; col2++) {
                if (_board[row2][col2] == WINNER) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Add a tile to a random, empty position, choosing a value (2 or
     *  4) at random.  Has no effect if the board is currently full. */
    void setRandomPiece() {
        if (_count == SQUARES) {
            return;
        }
        int[] x = _game.getRandomTile();
        if (_board[x[1]][x[2]] != 0) {
            setRandomPiece();
        } else {
            _game.addTile(x[0], x[1], x[2]);
            _board[x[1]][x[2]] = x[0];
        }
    }

    /** Perform the result of tilting the board toward SIDE.
     *  Returns true iff the tilt changes the board. **/
    boolean tiltBoard(Side side) {
        /* As a suggestion (see the project text), you might try copying
         * the board to a local array, turning it so that edge SIDE faces
         * north.  That way, you can re-use the same logic for all
         * directions.  (As usual, you don't have to). */
        int[][] board = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                board[r][c] =
                    _board[tiltRow(side, r, c)][tiltCol(side, r, c)];
            }
        }
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0) {
                    int idx = 0;
                    while (idx < row && board[idx][col] != 0) {
                        idx++;
                    }
                    _game.moveTile(_board[tiltRow(side, row, col)]
                                   [tiltCol(side, row, col)],
                                   tiltRow(side, row, col),
                                   tiltCol(side, row, col),
                                   tiltRow(side, idx, col),
                                   tiltCol(side, idx, col));
                    if (idx != row) {
                        board[idx][col] = board[row][col];
                        board[row][col] = 0;
                    }
                    mergeThoseTiles(side, row, board, col, idx);
                }
            }
        }
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[tiltRow(side, r, c)][tiltCol(side, r, c)]
                    = board[r][c];
            }
        }
        _game.setScore(_score, _maxScore);
        _game.displayMoves();
        return true;
    }
    /**Merge function as a helper for move.
       @param side side from tiltBoard.
       @param row row from tiltBoard.
       @param board board form tiltBoard.
       @param col col form tiltBoard.
       @param idx idx from tiltBoard.*/
    void mergeThoseTiles(Side side, int row, int[][] board, int col, int idx) {
        int inx = row + 1;
        while (inx < 4 && board[inx][col] == 0) {
            inx++;
        }
        if (inx < 4 && board[idx][col] == board[inx][col]) {
            _game.mergeTile(board[inx][col], board[inx][col] * 2,
                            tiltRow(side, inx, col), tiltCol(side, inx, col),
                            tiltRow(side, idx, col), tiltCol(side, idx, col));
            board[idx][col] = board[inx][col] * 2;
            _score += board[idx][col];
            board[inx][col] = 0;
            if (_score > _maxScore) {
                _maxScore = _score;
            }
        }
    }

    /** Return the row number on a playing board that corresponds to row R
     *  and column C of a board turned so that row 0 is in direction SIDE (as
     *  specified by the definitions of NORTH, EAST, etc.).  So, if SIDE
     *  is NORTH, then tiltRow simply returns R (since in that case, the
     *  board is not turned).  If SIDE is WEST, then column 0 of the tilted
     *  board corresponds to row SIZE - 1 of the untilted board, and
     *  tiltRow returns SIZE - 1 - C. */
    int tiltRow(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return r;
        case EAST:
            return c;
        case SOUTH:
            return SIZE - 1 - r;
        case WEST:
            return SIZE - 1 - c;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the column number on a playing board that corresponds to row
     *  R and column C of a board turned so that row 0 is in direction SIDE
     *  (as specified by the definitions of NORTH, EAST, etc.). So, if SIDE
     *  is NORTH, then tiltCol simply returns C (since in that case, the
     *  board is not turned).  If SIDE is WEST, then row 0 of the tilted
     *  board corresponds to column 0 of the untilted board, and tiltCol
     *  returns R. */
    int tiltCol(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return c;
        case EAST:
            return SIZE - 1 - r;
        case SOUTH:
            return SIZE - 1 - c;
        case WEST:
            return r;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    Side keyToSide(String key) {
        switch (key) {
        case "Up":
            return NORTH;
        case "Down":
            return SOUTH;
        case "Left":
            return WEST;
        case "Right":
            return EAST;
        default:
            throw new IllegalArgumentException("unknown key designation");
        }
    }
    /** Represents the board: _board[r][c] is the tile value at row R,
     *  column C, or 0 if there is no tile there. */
    private final int[][] _board = new int[SIZE][SIZE];

    /** True iff --testing option selected. */
    private boolean _testing;
    /** THe current input source and output sink. */
    private Game _game;
    /** The score of the current game, and the maximum final score
     *  over all games in this session. */
    private int _score, _maxScore;
    /** Number of tiles on the board. */
    private int _count;
}
