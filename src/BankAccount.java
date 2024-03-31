import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {
    private final int id;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(int id, double balance) {
        this.id = id;
        this.balance = balance;
        transactionHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful.");
            addTransaction(new Transaction("Deposit", amount));
        } else {
            System.out.println("Invalid amount for deposit.");
        }
        String transactionDetails = "Account id: " + getId() + " | Deposit: Amount $" + amount + " | Balance $" + getBalance();
        writeTransactionToFile(transactionDetails);
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            balance -= amount;
            System.out.println("Withdrawal successful.");
            addTransaction(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Invalid amount for withdrawal or insufficient balance.");
        }
        String transactionDetails = "Withdrawal: Amount $" + amount + " | Balance $" + getBalance();
        writeTransactionToFile(transactionDetails);
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            System.out.println("Transfer successful.");
            recipient.addTransaction(new Transaction("Transfer from ID " + this.getId(), amount));
            addTransaction(new Transaction("Transfer to ID " + recipient.getId(), amount));
        } else {
            System.out.println("Invalid amount for transfer or insufficient balance.");
        }
        String senderTransaction = "Transfer to Account " + recipient.getId() + ": Amount $" + amount + " | Balance $" + getBalance();
        writeTransactionToFile(senderTransaction);


        String recipientTransaction = "Transfer from Account " + id + ": Amount $" + amount + " | Balance $" + recipient.getBalance();
        recipient.writeTransactionToFile(recipientTransaction);
    }

    public void printBalance() {
        System.out.println("Balance: $" + getBalance());
    }

    public void writeTransactionToFile(String transactionDetails) {
        String filename = "transaction_records.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(transactionDetails);
        } catch (IOException e) {
            System.err.println("Error writing transaction to file: " + e.getMessage());
        }
    }

    public void serialize(String folderPath) {
        String filename = folderPath + File.separator + "account_" + id + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("BankAccount serialized successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error serializing BankAccount: " + e.getMessage());
        }
    }

    public static BankAccount deserialize(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            BankAccount account = (BankAccount) in.readObject();
            System.out.println("BankAccount deserialized successfully from " + filename);
            return account;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing BankAccount: " + e.getMessage());
            return null;
        }
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History for Account ID: " + getId());
        System.out.println("-".repeat(68));
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.printf("%-18s %-18s %-18s\n", "Type", "Amount", "Date");
            System.out.println("-".repeat(68));
            for (Transaction transaction : transactionHistory) {
                System.out.printf("%-18s $%-18.2f %-18s\n", transaction.getType(), transaction.getAmount(), transaction.getDate());
            }
        }
    }
}


