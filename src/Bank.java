import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final String name = "DNB";
    private List<BankAccount> customers;

    public BankAccount currentUser;

    public Bank() {
        customers =  new ArrayList<>(5000);
    }

    private boolean checkAccountById(int id) {
        for (BankAccount account : customers) {
            if (account.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private BankAccount findAccountById(int id) {
        for (BankAccount account : customers) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    public boolean addNewCustomer(int id, double deposit) {
        if (!checkAccountById(id)) {
            BankAccount customer = new BankAccount(id, deposit);
            customers.add(customer);
            currentUser = customer;
            customer.serialize("./Accounts_rep");
            String transactionDetails = "New account is created with ID : " + customer.getId() + " | Balance $" + customer.getBalance();
            customer.writeTransactionToFile(transactionDetails);
            return true;
        }
        System.out.println("Account already exists");
        return false;
    }

    public boolean logIn(int id) {
        if(checkAccountById(id)) {
            currentUser = findAccountById(id);
            return true;
        } else {
            System.out.println("Wrong credentials");
            return false;
        }
    }

    public void transfer(int id, double amount) {
        BankAccount recipientAccount = findAccountById(id);
        if (recipientAccount != null && recipientAccount.getId() != currentUser.getId()) {
            currentUser.transfer(recipientAccount, amount);
        } else {
            System.out.println("Sender or recipient account not found.");
        }
    }


}
