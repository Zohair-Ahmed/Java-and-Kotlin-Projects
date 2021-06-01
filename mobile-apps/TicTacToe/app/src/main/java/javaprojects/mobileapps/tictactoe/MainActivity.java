package javaprojects.mobileapps.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import javaprojects.mobileapps.tictactoe.models.Game;

public class MainActivity extends AppCompatActivity {

    Game ticTacToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticTacToe = new Game();
        updateDisplay();
    }

    /* this mutator sets the output label */
    private void setContentsOfTextView(int id, String newContents) {
        View view = findViewById(id);
        TextView textView = (TextView) view;
        textView.setText(newContents);
    }

    /* this accessor retrieves input entered on the text view  */
    private String getInputOfTextField(int id) {
        View view = findViewById(id);
        EditText editText = (EditText) view;
        return editText.getText().toString();
    }

    /* this accessor retrieves input chosen from some spinner (drop-down menu) */
    private String getItemSelected(int id) {
        View view = findViewById(id);
        Spinner spinner = (Spinner) view;
        return spinner.getSelectedItem().toString();
    }

    /**
     * Start/Restart a new TicTacToe game
     * @param view
     */
    public void startClicked(View view) {

        String playerX = getInputOfTextField(R.id.playerXName);
        String playerO = getInputOfTextField(R.id.playerOName);
        String whoPlaysFirst = getItemSelected(R.id.playFirstSpinner);

        ticTacToe = new Game(playerX, playerO);

        if(whoPlaysFirst.equals("Player X"))
            ticTacToe.setWhoPlaysFirst('x');
        else
            ticTacToe.setWhoPlaysFirst('o');

        updateDisplay();
    }

    /**
     * Move character to desired location
     * @param view
     */
    public void moveClicked(View view) {

        int row = Integer.parseInt(getInputOfTextField(R.id.rowNum));
        int col = Integer.parseInt(getInputOfTextField(R.id.colNum));

        ticTacToe.move(row, col);

        updateDisplay();
    }

    /**
     * A private method that displays the game board and
     * who's turn it is
     */
    private void updateDisplay() {
        String gameBoard = "Current Game Board: \n\n" + ticTacToe.displayBoard();
        String status = "Current Game Status: \n " + ticTacToe.getStatus();
        setContentsOfTextView(R.id.display, gameBoard + "\n" + status);
    }
}