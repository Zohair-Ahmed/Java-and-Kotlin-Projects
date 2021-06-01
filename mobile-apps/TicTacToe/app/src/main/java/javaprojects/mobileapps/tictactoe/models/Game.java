package javaprojects.mobileapps.tictactoe.models;

public class Game {

    /*---------GLOBAL VARIABLES---------*/
    final private char[][] gameBoard = new char[3][3]; // the game board

    private String player1Name; // name of player 1
    private String player2Name; // name of player 2
    private String currPlayer = "No one"; // current player
    private String status; // the status of the game

    private int spacesAvail = 9; // the num of spaces occupied

    private boolean hasWinner = false; // checks if there is a winner of this game
    private boolean isTie = false; // checks if there is a tie

    /*---------CONSTRUCTORS---------*/

    /**
     * Creates a new TicTacToe game board
     */
    public Game() {
        initBoard(); // initializes the board to '_'
        updateStatus(); // updates the status
    }

    /**
     * Creates a new TicTacToe game board with inputted player names
     *
     * @param player1Name - name of player 1
     * @param player2Name - name of player 2
     */
    public Game(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.currPlayer = this.player1Name;

        initBoard(); // initializes the board to '_'
        updateStatus(); // updates the status
    }

    /*---------ACCESSORS---------*/

    /**
     * Returns who is the current player
     *
     * @return - who is the current player
     */
    public String getCurrentPlayer() {
        return this.currPlayer;
    }

    /**
     * Returns the status of the game (any error messages,
     * whether the game is over or the name of player due to move)
     *
     * @return - the status of the game
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Returns the game board
     *
     * @return - the game board
     */
    public char[][] getBoard() {
        return this.gameBoard;
    }

    /**
     * Returns the current state of this Game's game board
     *
     * @return - the current state of this Game's game board
     */
    public String displayBoard() {
        String gameBoard = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard += this.gameBoard[i][j] + "  ";
            }
            gameBoard += "\n";
        }

        return gameBoard;
    }

    /*---------MUTATORS---------*/

    /**
     * Sets which player plays first
     * 'x' is associated with Player 1
     * 'o' is associated with Player 2
     *
     * @param ch - the character belonging to either player 1 or player 2
     */
    public void setWhoPlaysFirst(char ch) {
        if (ch == 'x')
            this.currPlayer = this.player1Name;
        else
            this.currPlayer = this.player2Name;

        updateStatus();
    }

    /**
     * Places the player's character in the specified position
     *
     * @param row - the row to be placed
     * @param col - the col to be placed
     */
    public void move(int row, int col) {

        if (this.hasWinner) // already a winner
            this.status = "Error: game is already over with a winner.";

        else if (this.isTie) // game ended with a tie
            this.status = "Error: game is already over with a tie.";

        else if (row < 1 || row > 3) // invalid row
            this.status = "Error: row " + row + " is invalid.";

        else if (col < 1 || col > 3) // invalid col
            this.status = "Error: col " + col + " is invalid.";

        else if (this.gameBoard[row - 1][col - 1] != '_') // space already occupied
            this.status = "Error: slot @ (" + row + ", " + col + ") is already occupied.";

        else { // good to move
            if (getCurrentPlayer().equals(this.player1Name))
                this.gameBoard[row - 1][col - 1] = 'x';
            else
                this.gameBoard[row - 1][col - 1] = 'o';

            --this.spacesAvail; // decrements number of spaces available

            // check if there is now Winner or tie with latest move
            if (checkGameOver(row - 1, col - 1)) {
                this.currPlayer = null;
                return;
            }

            changePlayer(); // change player if game's not over
        }
    }

    /*---------HELPER METHODS---------*/

    /**
     * A private helper method that initializes the board
     * to '_'
     */
    private void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.gameBoard[i][j] = '_';
            }
        }
    }

    /**
     * A private helper method to update the status of the game
     */
    private void updateStatus() {
        this.status = getCurrentPlayer() + "'s turn to play...";
    }

    /**
     * A private helper method that changes the player, indicating
     * the next player's turn
     */
    private void changePlayer() {
        if (this.currPlayer.equals(this.player1Name))
            this.currPlayer = this.player2Name;
        else
            this.currPlayer = this.player1Name;

        updateStatus();
    }

    /**
     * A private helper method that checks if the game is over with the latest move
     *
     * @param row - row of move
     * @param col - col of move
     * @return - true if the game has a winner or ends with a tie, false otherwise
     */
    private boolean checkGameOver(int row, int col) {

        if (!this.hasWinner && this.spacesAvail == 0) {
            this.status = "Game is over with a tie between " + this.player1Name + " and " + this.player2Name + ".";
            this.isTie = true;
            return true;

        } else if (isWinner(row, col)) {
            this.status = "Game is over with " + getCurrentPlayer() + " being the winner.";
            this.currPlayer = null;
            this.hasWinner = true;
            return true;
        }

        return false;
    }

    /**
     * A private helper method that determines if the latest move corresponds to a winning play
     *
     * @param row - row of move
     * @param col - col of move
     * @return - true if there is the latest move is a winning move
     */
    private boolean isWinner(int row, int col) {

        // all moves have to check horizontal and vertical
        if (checkVertical(col)) // checks if the vertical is the same mark
            return true;
        if (checkHorizontal(row)) // checks if the horizontal is the same mark
            return true;

        // moves at the top or bottom ends have to check diagonals as well
        if ((row == 0 && col == 0) || (row == 2 && col == 2))
            return checkLeftDiagonal(); // if latest move is at the top left or bottom right

        else if ((row == 0 && col == 2) || (row == 2 && col == 0))
            return checkRightDiagonal(); // if latest move is at the top right or bottom left

        else if (row == 1 && col == 1)
            return checkLeftDiagonal() || checkRightDiagonal(); // if latest move is at the centre

        return false;
    }

    /**
     * A private helper method that checks if the vertical is the same
     * symbol with the latest move
     *
     * @param col - which col did the latest move take place
     * @return - true if the vertical col is the same symbol, false otherwise
     */
    private boolean checkVertical(int col) {
        return (this.gameBoard[0][col] == this.gameBoard[1][col]
                && this.gameBoard[0][col] == this.gameBoard[2][col]);
    }

    /**
     * A private helper method that checks if the horizontal is the same
     * symbol with the latest move
     *
     * @param row - which row did the latest move take place
     * @return - true if the horizontal row is the same symbol, false otherwise
     */
    private boolean checkHorizontal(int row) {
        return (this.gameBoard[row][0] == this.gameBoard[row][1]
                && this.gameBoard[row][1] == this.gameBoard[row][2]);
    }

    /**
     * A private helper method that checks if the left diagonal is the same
     * symbol with the latest move
     *
     * @return - true if the left diagonal is the same symbol, false otherwise
     */
    private boolean checkLeftDiagonal() {
        return (this.gameBoard[0][0] == this.gameBoard[1][1]
                && this.gameBoard[1][1] == this.gameBoard[2][2]);
    }

    /**
     * A private helper method that checks if the right diagonal is the same
     * symbol with the latest move
     *
     * @return - true if the right diagonal is the same symbol, false otherwise
     */
    private boolean checkRightDiagonal() {
        return (this.gameBoard[0][2] == this.gameBoard[1][1]
                && this.gameBoard[1][1] == this.gameBoard[2][0]);
    }
}
