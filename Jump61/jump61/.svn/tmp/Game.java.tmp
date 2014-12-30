// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.

// Comments that start with "//" are intended to be removed from your
// solutions.
package jump61;

import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;

import java.util.Scanner;
import java.util.Random;
import java.util.Observable;

import static jump61.Side.*;
import static jump61.GameException.error;

/** Main logic for playing (a) game(s) of Jump61.
 *  @author
 */
class Game extends Observable {

    /** Name of resource containing help message. */
    private static final String HELP = "jump61/Help.txt";

    /** A list of all commands. */
    private static final String[] COMMAND_NAMES = {
        "auto", "clear", "dump", "help", "manual",
        "quit", "seed", "set", "size", "start",
    };

    /** A new Game that takes command/move input from INPUT, prints
     *  normal output on OUTPUT, prints prompts for input on PROMPTS,
     *  and prints error messages on ERROROUTPUT. The Game now "owns"
     *  INPUT, PROMPTS, OUTPUT, and ERROROUTPUT, and is responsible for
     *  closing them when its play method returns. */
    Game(Reader input, Writer prompts, Writer output, Writer errorOutput) {
        _exit = -1;
        _board = new MutableBoard(Defaults.BOARD_SIZE);
        _readonlyBoard = new ConstantBoard(_board);
        _prompter = new PrintWriter(prompts, true);
        _inp = new Scanner(input);
        _inp.useDelimiter("\\p{Blank}*(?=[\r\n])|(?<=\n)|\\p{Blank}+");
        _out = new PrintWriter(output, true);
        _err = new PrintWriter(errorOutput, true);
    }

    /** Returns a readonly view of the game board.  This board remains valid
     *  throughout the session. */
    Board getBoard() {
        return _readonlyBoard;
    }

    /** Return true iff there is a game in progress. */
    boolean gameInProgress() {
        return _playing;
    }

    /** Play a session of Jump61.  This may include multiple games,
     *  and proceeds until the user exits.  Returns an exit code: 0 is
     *  normal; any positive quantity indicates an error.  */
    int play() {
        _out.println("Welcome to " + Defaults.VERSION);
        _out.flush();
        _board.clear(Defaults.BOARD_SIZE);
        // FIXME
        _prompter.close();
        _out.close();
        _err.close();
        return _exit;
    }

    /** Get a move from my input and place its row and column in
     *  MOVE.  Returns true if this is successful, false if game stops
     *  or ends first. */
    boolean getMove(int[] move) {
        while (_playing && _move[0] == 0) {
            if (promptForNext()) {
                readExecuteCommand();
            } else {
                _exit = 0;
                return false;
            }
        }
        if (_move[0] > 0) {
            move[0] = _move[0];
            move[1] = _move[1];
            _move[0] = 0;
            return true;
        } else {
            return false;
        }
    }

    /** Add a spot to R C, if legal to do so. */
    void makeMove(int r, int c) {
        assert _board.isLegal(_board.whoseMove(), r, c);
        // FIXME
    }

    /** Add a spot to square #N, if legal to do so. */
    void makeMove(int n) {
        assert _board.isLegal(_board.whoseMove(), n);
        // FIXME
    }

    /** Report a move by PLAYER to ROW COL. */
    void reportMove(Side player, int row, int col) {
        message("%s moves %d %d.%n", player.toCapitalizedString(), row, col);
    }

    /** Return a random integer in the range [0 .. N), uniformly
     *  distributed.  Requires N > 0. */
    int randInt(int n) {
        return _random.nextInt(n);
    }

    /** Send a message to the user as determined by FORMAT and ARGS, which
     *  are interpreted as for String.format or PrintWriter.printf. */
    void message(String format, Object... args) {
        _out.printf(format, args);
    }

    /** Check whether we are playing and there is an unannounced winner.
     *  If so, announce and stop play. */
    private void checkForWin() {
        // FIXME
    }

    /** Send announcement of winner to my user output. */
    private void announceWinner() {
        _out.printf("%s wins.%n", _board.getWinner().toCapitalizedString());
    }

    /** Make the player of COLOR an AI for subsequent moves. */
    private void setAuto(Side color) {
        // FIXME
    }

    /** Make the player of COLOR take manual input from the user for
     *  subsequent moves. */
    private void setManual(Side color) {
        // FIXME
    }

    /** Return the Player playing COLOR. */
    private Player getPlayer(Side color) {
        // FIXME
        return _players[color.ordinal()];
    }

    /** Set getPlayer(COLOR) to PLAYER. */
    private void setPlayer(Side color, Player player) {
        // FIXME
    }

    /** Stop any current game and clear the board to its initial
     *  state. */
    void clear() {
        // FIXME
    }

    /** Print the current board using standard board-dump format. */
    private void dump() {
        _out.println(_board);
    }

    /** Print a board with row/column numbers. */
    private void printBoard() {
        _out.println(_board.toDisplayString());
    }

    /** Print a help message. */
    private void help() {
        Main.printHelpResource(HELP, _out);
    }

    /** Seed the random-number generator with SEED. */
    private void setSeed(long seed) {
        _random.setSeed(seed);
    }

    /** Place SPOTS spots on square R:C and color the square red or
     *  blue depending on whether COLOR is "r" or "b".  If SPOTS is
     *  0, clears the square, ignoring COLOR.  SPOTS must be less than
     *  the number of neighbors of square R, C. */
    private void setSpots(int r, int c, int spots, String color) {
        // FIXME
    }

    /** Stop any current game and set the board to an empty N x N board
     *  with numMoves() == 0.  Requires 2 <= N <= 10. */
    private void setSize(int n) {
        // FIXME
        announce();
    }

    /** Begin accepting moves for game.  If the game is won,
     *  immediately print a win message and end the game. */
    private void restartGame() {
        // FIXME
        announce();
    }

    /** Save move R C in _move.  Error if R and C do not indicate an
     *  existing square on the current board. */
    private void saveMove(int r, int c) {
        if (!_board.exists(r, c)) {
            throw error("move %d %d out of bounds", r, c);
        }
        _move[0] = r;
        _move[1] = c;
    }

    /** Returns a color (player) name from _inp: either RED or BLUE.
     *  Throws an exception if not present. */
    private Side readSide() {
        return Side.parseSide(_inp.next("[rR][eE][dD]|[Bb][Ll][Uu][Ee]"));
    }

    /** Read and execute one command.  Leave the input at the start of
     *  a line, if there is more input. */
    private void readExecuteCommand() {
        // FIXME
    }

    /** Return the full, lower-case command name that uniquely fits
     *  COMMAND.  COMMAND may be any prefix of a valid command name,
     *  as long as that name is unique.  If the name is not unique or
     *  no command name matches, returns COMMAND in lower case. */
    private String canonicalizeCommand(String command) {
        command = command.toLowerCase();

        if (command.startsWith("#")) {
            return "#";
        }

        String fullName;
        fullName = null;
        for (String name : COMMAND_NAMES) {
            if (name.equals(command)) {
                return command;
            }
            if (name.startsWith(command)) {
                if (fullName != null) {
                    throw error("%s is not a unique command abbreviation",
                                command);
                }
                fullName = name;
            }
        }
        if (fullName == null) {
            return command;
        } else {
            return fullName;
        }
    }

    /** Gather arguments and execute command CMND.  Throws GameException
     *  on errors. */
    private void executeCommand(String cmnd) {
        switch (canonicalizeCommand(cmnd)) {
        case "\n": case "\r\n":
            return;
        case "#":
            break;
        case "auto":
            setAuto(readSide());
            break;
        case "clear":
            clear();
            break;
        case "dump":
            dump();
            break;
        case "help":
            help();
            break;
        case "manual":
            setManual(readSide());
            break;
        case "quit":
            _exit = 0;
            _playing = false;
            break;
        case "seed":
            setSeed(_inp.nextLong());
            break;
        case "set":
            setSpots(_inp.nextInt(), _inp.nextInt(), _inp.nextInt(),
                     _inp.next("[brBR]"));
            break;
        case "size":
            setSize(_inp.nextInt());
            break;
        case "start":
            restartGame();
            break;
        default:
            throw error("bad command: '%s'", cmnd);
        }
    }

    /** Print a prompt and wait for input. Returns true iff there is another
     *  token. */
    private boolean promptForNext() {
        if (_playing) {
            _prompter.print(_board.whoseMove());
        }
        _prompter.print("> ");
        _prompter.flush();
        return _inp.hasNext();
    }

    /** Send an error message to the user formed from arguments FORMAT
     *  and ARGS, whose meanings are as for printf. */
    void reportError(String format, Object... args) {
        _err.print("Error: ");
        _err.printf(format, args);
        _err.println();
    }

    /** Notify all Oberservers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    /** Writer on which to print prompts for input. */
    private final PrintWriter _prompter;
    /** Scanner from current game input.  Initialized to return
     *  newlines as tokens. */
    private final Scanner _inp;
    /** Outlet for responses to the user. */
    private final PrintWriter _out;
    /** Outlet for error responses to the user. */
    private final PrintWriter _err;

    /** The board on which I record all moves. */
    private final Board _board;
    /** A readonly view of _board. */
    private final Board _readonlyBoard;

    /** A pseudo-random number generator used by players as needed. */
    private final Random _random = new Random();

    /** True iff a game is currently in progress. */
    private boolean _playing;
    /** When set to a non-negative value, indicates that play should terminate
     *  at the earliest possible point, returning _exit.  When negative,
     *  indicates that the session is not over. */
    private int _exit;

    /** Current players, indexed by color (RED, BLUE). */
    private final Player[] _players = new Player[Side.values().length];

   /** Used to return a move entered from the console.  Allocated
     *  here to avoid allocations. */
    private final int[] _move = new int[2];
}
