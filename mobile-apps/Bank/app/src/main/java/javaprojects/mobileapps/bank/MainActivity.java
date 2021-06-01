package javaprojects.mobileapps.bank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import javaprojects.mobileapps.bank.models.Bank;

public class MainActivity extends AppCompatActivity {

    Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bank = new Bank();
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
     * The method that activates when you want to add a new Client
     * Takes the plain text from the name of client text view and the
     * plain text value from init balance from text view as parameters
     * to create a new Client
     *
     * @param view
     */
    public void newAccountClicked(View view) {
        String clientName = getInputOfTextField(R.id.clientName);
        double initBalance = Double.parseDouble(getInputOfTextField(R.id.initBalance));

        bank.addClient(clientName, initBalance);

        setContentsOfTextView(R.id.results, bank.getStatus());
    }

    /**
     * The method that activates when you want to invoke a service type.
     *
     * @param view
     */
    public void serviceTypeClicked(View view) {
        String serviceType = getItemSelected(R.id.service);
        String fromClient = getInputOfTextField(R.id.fromAccount);
        String toClient = getInputOfTextField(R.id.toAccount);
        double amount = Double.parseDouble(getInputOfTextField(R.id.amount));


        if (serviceType.equals("Deposit")) {
            bank.deposit(toClient, amount);
            setContentsOfTextView(R.id.results, bank.getStatus());

        } else if (serviceType.equals("Withdraw")) {
            bank.withdraw(fromClient, amount);
            setContentsOfTextView(R.id.results, bank.getStatus());

        } else if (serviceType.equals("Transfer")) {
            bank.transfer(fromClient, toClient, amount);
            setContentsOfTextView(R.id.results, bank.getStatus());

        } else if (serviceType.equals("Print Statement")) {

            String statements = "";

            for (int i = 0; i < bank.getStatement(fromClient).length; i++) {
                statements += bank.getStatement(fromClient)[i] + "\n";
            }

            setContentsOfTextView(R.id.results, statements);
        }
    }
}