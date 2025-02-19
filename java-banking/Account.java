import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private Client client;
    private double balance;
    private List<Transaction> transactions;

    public Account(Client client) {
        this.client = client;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Getters and Setters
    public Client getClient() {
        return client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
