package javaprojects.mobileapps.bank.models;

public class Bank {
    /*---------GLOBAL VARIABLES---------*/
    private Client[] clients = new Client[0]; // list of Clients of this Bank
    private String[] statements = new String[1]; // bank statements of all Clients
    private String status; // the status of each action

    /*---------CONSTRUCTORS---------*/

    /**
     * A default constructor
     * <p>
     * Every Bank starts off with no Clients and no bank statements
     */
    public Bank() {
        updateStatus();
    }

    /*---------ACCESSORS---------*/

    /**
     * Returns the Clients and their balance if all
     * preconditions are met
     *
     * @return - the Clients and their balance
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Returns the statements of a specified Client in this Bank
     *
     * @param name - the name of Client in this Bank
     * @return - the statements of a specified Client in this Bank
     */
    public String[] getStatement(String name) {

        Client thisClient = clientExists(name);

        if (thisClient == null) {
            this.status = "Error: From-Account " + name + " does not exist";
            return null;
        }

        return thisClient.getStatement();
    }

    /**
     * A private helper method that determines whether or not a Client
     * already exists or not in this Bank
     *
     * @param name - Client name
     * @return - the Client, if the Client exists, null otherwise
     */
    private Client clientExists(String name) {
        for (int i = 0; i < this.clients.length; i++) {
            if (this.clients[i].getName().equals(name))
                return this.clients[i];
        }
        return null;
    }

    /*---------MUTATORS---------*/

    /**
     * Deposits the amount specified into this Client's account
     *
     * @param toName - the Client who's account is being deposited to
     * @param value  - amount being deposited
     */
    public void deposit(String toName, double value) {

        Client thisClient = clientExists(toName);

        // error conditions
        if (thisClient == null)
            this.status = "Error: To-Account " + toName + " does not exist";

        else if (value <= 0)
            this.status = "Error: Non-Positive Amount";

        else {
            // no errors, deposits value into Client's account
            thisClient.deposit(value);
            updateStatus();
        }
    }

    /**
     * Withdraws the amount specified from this Client's account
     *
     * @param fromName - the Client who's account is being withdrawn from
     * @param value    - amount being withdrawn
     */
    public void withdraw(String fromName, double value) {

        Client thisClient = clientExists(fromName);

        // error conditions
        if (thisClient == null)
            this.status = "Error: From-Account " + fromName + " does not exist";

        else if (value <= 0)
            this.status = "Error: Non-Positive Amount";

        else if (thisClient.getBalance() < value)
            this.status = "Error: Amount too large to withdraw";

        else {
            // no errors, withdraw amount from the Client specified
            thisClient.withdraw(value);
            updateStatus();
        }
    }

    public void transfer(String fromName, String toName, double value) {

        Client fromClient = clientExists(fromName);
        Client toClient = clientExists(toName);

        // error conditions
        if (fromClient == null)
            this.status = "Error: From-Account " + fromName + " does not exist";

        else if (toClient == null)
            this.status = "Error: To-Account " + toName + " does not exist";

        else if (value <= 0)
            this.status = "Error: Non-Positive Amount";

        else if (fromClient.getBalance() < value)
            this.status = "Error: Amount too large to transfer";

        else {
            // no errors, withdraw from Client, deposit to Client
            fromClient.withdraw(value);
            toClient.deposit(value);
            updateStatus();
        }
    }

    public void addClient(String name, double value) {

        Client thisClient = clientExists(name);

        // error conditions
        if (this.clients.length == 6)
            this.status = "Error: Maximum Number of Accounts Reached";

        else if (thisClient != null && thisClient.getName().equals(name))
            this.status = "Error: Client " + name + " already exists";

        else if (value <= 0)
            this.status = "Error: Non-Positive Initial Balance";

        else {
            // no error, new client is added to the list of Clients
            Client[] copyClients = new Client[this.clients.length + 1];

            System.arraycopy(this.clients, 0, copyClients, 0, this.clients.length);

            Client newClient = new Client(name, value);

            copyClients[copyClients.length - 1] = newClient;

            this.clients = copyClients;
            updateStatus();
        }
    }

    /**
     * A private helper method that update the status of this Bank
     */
    private void updateStatus() {

        this.status = "Accounts: {";

        for (int i = 0; i < this.clients.length; i++) {
            this.status += this.clients[i].getStatus();

            if (i != this.clients.length - 1) {
                this.status += ", ";
            }
        }
        this.status += "}";
    }
}
