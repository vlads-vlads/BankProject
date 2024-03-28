import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1001, 500.00));
        accounts.add(new BankAccount(1002, 1000.00));
        BankAccount userAccount = null;
        boolean connected = true;

        while (connected) {
            System.out.println("\nHello");
            System.out.println("1. Create account");
            System.out.println("2. Log in");
            System.out.println("Please choose an option: ");
            try {
                int answer = scanner.nextInt();

                switch (answer) {
                    case 1:
                        userAccount = createAccount(scanner);
                        accounts.add(userAccount);
                        connected = false;
                        break;
                    case 2:
                        System.out.print("Enter your account ID: ");
                        int accountId = scanner.nextInt();
                        if (checkAccountById(accountId, accounts)) {
                            userAccount = findAccountById(accountId, accounts);
                            connected = false;
                        } else {
                            System.out.println("Wrong credentials");
                        }
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Print Balance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        userAccount.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        userAccount.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient's account ID: ");
                        int recipientId = scanner.nextInt();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        transfer(userAccount, recipientId, transferAmount, accounts, userAccount);
                        break;
                    case 4:
                        userAccount.printBalance();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.nextLine();
            }
        }
    }

    private static void transfer(BankAccount senderAccount, int recipientId, double amount, List<BankAccount> accounts, BankAccount userAccount) {
        BankAccount recipientAccount = findAccountById(recipientId, accounts);
        if (recipientAccount != null && senderAccount != null && recipientAccount.getId() != userAccount.getId()) {
            senderAccount.transfer(recipientAccount, amount);
        } else {
            System.out.println("Sender or recipient account not found.");
        }
    }

    private static BankAccount createAccount(Scanner scanner) {
        System.out.print("Enter your account ID: ");
        int accountId = scanner.nextInt();

        System.out.print("How much money do you want to deposit? ");
        double initialBalance = scanner.nextDouble();
        BankAccount newAccount =  new BankAccount(accountId, initialBalance);
        newAccount.serialize("./Accounts_rep");
        String transactionDetails = "New account is created with ID : " + newAccount.getId() + " | Balance $" + newAccount.getBalance();
        newAccount.writeTransactionToFile(transactionDetails);
        return newAccount;
    }

    private static boolean checkAccountById(int id, List<BankAccount> accounts) {
        for (BankAccount account : accounts) {
            if (account.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static BankAccount findAccountById(int id, List<BankAccount> accounts) {
        for (BankAccount account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }
}

