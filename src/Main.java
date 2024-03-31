import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        bank.addNewCustomer(1, 100);
        bank.addNewCustomer(2, 900);
        bank.addNewCustomer(3, 750);
        bank.addNewCustomer(4, 300);


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
                        System.out.print("Enter your account ID: ");
                        int accountId = scanner.nextInt();
                        System.out.print("How much money do you want to deposit? ");
                        double initialBalance = scanner.nextDouble();

                        if (bank.addNewCustomer(accountId, initialBalance)) {
                            connected = false;
                        }
                        break;
                    case 2:
                        System.out.print("Enter your account ID: ");
                        int accId = scanner.nextInt();
                        if (bank.logIn(accId)) {
                            connected = false;
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
                        bank.currentUser.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        bank.currentUser.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient's account ID: ");
                        int recipientId = scanner.nextInt();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        bank.transfer(recipientId, transferAmount);
                        break;
                    case 4:
                        bank.currentUser.printBalance();
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

}

