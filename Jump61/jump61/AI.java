package jump61;

import java.util.ArrayList;

/** An automated Player.
 *  @author Michael Ferrin
 */
class AI extends Player {

    /** Time allotted to all but final search depth (milliseconds). */
    private static final long TIME_LIMIT = 15000;

    /** Number of calls to minmax between checks of elapsed time. */
    private static final long TIME_CHECK_INTERVAL = 10000;

    /** Number of milliseconds in one second. */
    private static final double MILLIS = 1000.0;

    /** Positive infinity. */
    private static final int WON_GAME = Integer.MAX_VALUE;

    /** Negative infinity. */
    private static final int LOSE_GAME = Integer.MIN_VALUE;

    /** Enum strategy toggle. */
    private static enum Strategy { TWO_TWO, RANDOM_VALID, MIN_MAX };

    /** Set the enum strategy toggle. */
    private static final Strategy STRATEGY = Strategy.MIN_MAX;

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    AI(Game game, Side color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        if (STRATEGY == Strategy.TWO_TWO) {
            getGame().makeMove(2, 2);
        } else if (STRATEGY == Strategy.MIN_MAX) {
            Move theMovee = minmax(getSide(),
                new MutableBoard(getBoard()), 3, Integer.MAX_VALUE,
                possibleMoves(getSide(), getBoard()));
            int whichMovee = theMovee.index();
            int roww = whichMovee / getBoard().size() + 1;
            int coll = whichMovee % getBoard().size() + 1;
            getGame().reportMove(getSide(), roww, coll);
            getGame().makeMove(whichMovee);
        }
    }

    /** Return the minimum of CUTOFF and the minmax value of board B
     *  (which must be mutable) for player P to a search depth of D
     *  (where D == 0 denotes statically evaluating just the next move).
     *  If MOVES is not null and CUTOFF is not exceeded, set MOVES to
     *  a list of all highest-scoring moves for P; clear it if
     *  non-null and CUTOFF is exceeded. the contents of B are
     *  invariant over this call. */
    private Move minmax(Side p, Board b, int d, int cutoff,
                       ArrayList<Integer> moves) {
        Move bestSoFar = new Move(2, LOSE_GAME);
        if (d == 0) {
            return depthZero(p, b, moves, bestSoFar);
        }
        for (int move: moves) {
            b.addSpot(p, move);
            Move response = new Move(move, minmax(p.opposite(), b,
                d - 1, -bestSoFar.value(), moves).value());
            b.undo();
            if (-response.value() >= bestSoFar.value()) {
                bestSoFar = response;
                if (-response.value() >= cutoff) {
                    break;
                }
            }
        }
        return bestSoFar;
    }

    /** Returns heuristic value of board B for player P.
     *  Higher is better for P. */
    private int staticEval(Side p, Board b) {
        int ranking = b.numOfSide(p) - b.numOfSide(p.opposite());
        return ranking;
    }

    /** Returns a list of all the valid moves a player can make.
    @param player is the color
    @param board is the board believe it or not*/
    private ArrayList<Integer> possibleMoves(Side player, Board board) {
        ArrayList<Integer> allMoves = new ArrayList<Integer>();
        int m = 0;
        while (m < board.size() * board.size()) {
            if (board.isLegal(player, m)) {
                allMoves.add(m);
                m++;
            } else {
                m++;
            }
        }
        return allMoves;
    }

    /** Abstracting the base case for minmax.
    @param p is the player
    @param b is the board
    @param moves comes from possibleMoves
    @param bestSoFar is the best move so far
    @return the base case best value*/
    private Move depthZero(Side p, Board b,
        ArrayList<Integer> moves, Move bestSoFar) {
        for (int move: moves) {
            b.addSpot(p, move);
            Move currentMove = new Move(move, staticEval(p, b));
            if (currentMove.value() > bestSoFar.value()) {
                bestSoFar = currentMove;
            }
            b.undo();
        }
        return bestSoFar;
    }

}
