package javaprojects.mobileapps.bank.models;

public class Transaction {
    /*---------GLOBAL VARIABLES---------*/
    private String action; // action being taken
    private double value; // current account balance

    /*---------CONSTRUCTORS---------*/

    /**
     * A default constructor
     * <p>
     * Does not set the action or value of this latest Transaction
     */
    public Transaction() {

    }

    /**
     * A custom constructor
     *
     * @param action - the action being taken (DEPOSIT or WITHDRAW)
     * @param value  - the amount being deposited or withdrawn
     */
    public Transaction(String action, double value) {
        this.action = action;
        this.value = value;
    }

    /*---------ACCESSORS---------*/

    /**
     * Returns the action and amount of the last action
     *
     * @return - the action and amount of the last action
     */
    public String getStatus() {
        return "Transaction " + this.action + ": $" + String.format("%.2f", this.value);
    }

    /**
     * Returns the action of the latest Transaction
     *
     * @return - the action of the latest Transaction
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Returns the value of the latest Transction
     *
     * @return - the value of the latest Transaction
     */
    public double getValue() {
        return value;
    }

    /*---------MUTATORS---------*/

    /**
     * Sets the action of the latest Transaction
     *
     * @param action - latest action of the Transition
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Sets the value of the latest Transaction
     *
     * @param value - the value of the latest Transaction
     */
    public void setValue(double value) {
        this.value = value;
    }
}
