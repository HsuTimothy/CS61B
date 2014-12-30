package jump61;

/** A Player that gets its moves from manual input.
 *  @author Michael Ferrin
 */
class HumanPlayer extends Player {

    /** A new player initially playing COLOR taking manual input of
     *  moves from GAME's input source. */
    HumanPlayer(Game game, Side color) {
        super(game, color);
    }

    @Override
    /** Retrieve moves using getGame().getMove() until a legal one is found and
     *  make that move in getGame().  Report erroneous moves to player. */
    void makeMove() {
        Game ongoingGame = getGame();
        if (ongoingGame.getMove(mover)) {
            ongoingGame.makeMove(mover[0], mover[1]);
        }
    }

    /** variable to be passed into getMove. */
    private int[] mover = new int[2];
}
