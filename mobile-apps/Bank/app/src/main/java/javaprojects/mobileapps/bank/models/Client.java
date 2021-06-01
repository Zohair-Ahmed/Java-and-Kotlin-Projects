package javaprojects.mobileapps.bank.models;

public class Client {

    /*---------GLOBAL VARIABLES---------*/
    private String name; // name of the client
    private double balance; // current account balance
    private Transaction statement = new Transaction(); // statement of the transaction, updated after every mutator
    private String[] statements = new String[1]; // hold string statements

    /*---------CONSTRUCTORS---------*/

    /**
     * A custom constructor
     * Sets the name of the client, and amount of initial balance
     *
     * @param name    - name of client
     * @param balance - initial account balance
     */
    public Client(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.statements[0] = getStatus();
    }

    /*---------ACCESSORS---------*/

    /**
     * Returns the name and total balance of this Client
     *
     * @return - the name and total balance of this Client
     */
    public String getStatus() {
        return this.name + ": $" + String.format("%.2f", this.balance);
    }

    /**
     * Returns all the statements of the Client
     *
     * @return - all the statements of the Client
     */
    public String[] getStatement() {
        return this.statements;
    }

    /**
     * Returns the name of the Client
     *
     * @return - the name of the Client
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the balance of this Client
     *
     * @return - the balance of this Client
     */
    public double getBalance() {
        return balance;
    }

    /*---------MUTATORS---------*/

    /**
     * Withdraws the amount of money from this Client's balance
     *
     * @param value - the amount of money to be withdrawn
     */
    public void withdraw(double value) {
        this.balance -= value;

        this.statement.setAction("WITHDRAW");
        this.statement.setValue(value);

        addStatement(this.statement);
        this.statements[0] = getStatus();
    }

    /**
     * Deposits the amount of money from this Client's balance
     *
     * @param value - the amount of money to be deposited
     */
    public void deposit(double value) {
        this.balance += value;

        this.statement.setAction("DEPOSIT");
        this.statement.setValue(value);

        addStatement(this.statement);
        this.statements[0] = getStatus();
    }

    /**
     * A helper method that adds the last Transaction statement to the list
     * of the last Transaction
     *
     * @param statement - the status of the last Transaction
     */
    private void addStatement(Transaction statement) {
        String[] copyStatements = new String[this.statements.length + 1];

        for (int i = 0; i < this.statements.length; i++)
            copyStatements[i] = this.statements[i];

        copyStatements[copyStatements.length - 1] = statement.getStatus();

        this.statements = copyStatements;
    }
}
