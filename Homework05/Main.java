
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankAccount implements Serializable {

    private final String userName;
    private double balance;

    public BankAccount(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited $" + amount + " to account: " + userName);
    }

    public void displayBalance() {
        System.out.println("Account " + userName + " has a balance of $" + balance);
    }
}

public class Main {

    static final Scanner input = new Scanner(System.in);
    private static final String FILE_NAME = "bankAccounts.dat";

    @SuppressWarnings({"CallToPrintStackTrace", "unused"})
    private static void saveAccounts(List<BankAccount> accounts) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(accounts);
            System.out.println("Accounts saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<BankAccount> loadAccounts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<BankAccount>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Return empty list if no accounts exist
        }
    }

    private static BankAccount findAccount(List<BankAccount> accounts, String username) {
        for (BankAccount account : accounts) {
            if (account.getUserName().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    private static void createNewAccount(List<BankAccount> accounts) {
        System.out.print("Enter username for new account: ");
        String username = input.nextLine();

        // Check if account already exists
        if (findAccount(accounts, username) != null) {
            System.out.println("An account with this username already exists!");
            return;
        }

        System.out.print("Enter initial deposit amount: $");
        double initialDeposit = input.nextDouble();
        input.nextLine(); // Consume newline

        BankAccount newAccount = new BankAccount(username, initialDeposit);
        accounts.add(newAccount);

        System.out.println("New account created successfully!");
    }

    public static void main(String[] args) {
        try (input) {
            List<BankAccount> accounts = loadAccounts();
            System.out.println("Welcome to the Bank System!");

            if (accounts.isEmpty()) {
                System.out.println("No existing accounts found. Let's create your first account.");
                createNewAccount(accounts);
                saveAccounts(accounts);
            }

            boolean exit = false;
            while (!exit) {
                displayMenu();
                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        // Create new account
                        createNewAccount(accounts);
                        saveAccounts(accounts);
                    }

                    case 2 -> {
                        // Deposit money
                        if (accounts.isEmpty()) {
                            System.out.println("No accounts exist! Please create an account first.");
                            break;
                        }

                        System.out.print("Enter username: ");
                        String depositUsername = input.nextLine();

                        BankAccount depositAccount = findAccount(accounts, depositUsername);
                        if (depositAccount == null) {
                            System.out.println("Account not found!");
                            break;
                        }

                        System.out.print("Enter amount to deposit: $");
                        double amount = input.nextDouble();
                        input.nextLine(); // Consume newline

                        depositAccount.deposit(amount);
                        saveAccounts(accounts);
                    }

                    case 3 -> {
                        // View specific account balance
                        if (accounts.isEmpty()) {
                            System.out.println("No accounts exist! Please create an account first.");
                            break;
                        }

                        System.out.print("Enter username: ");
                        String balanceUsername = input.nextLine();

                        BankAccount balanceAccount = findAccount(accounts, balanceUsername);
                        if (balanceAccount == null) {
                            System.out.println("Account not found!");
                            break;
                        }

                        balanceAccount.displayBalance();
                    }

                    case 4 -> {
                        // View all accounts
                        if (accounts.isEmpty()) {
                            System.out.println("No accounts exist!");
                        } else {
                            System.out.println("\n--- All Accounts ---");
                            for (BankAccount acc : accounts) {
                                acc.displayBalance();
                            }
                        }
                    }

                    case 5 -> {
                        // Exit and save
                        saveAccounts(accounts);
                        exit = true;
                        System.out.println("Thank you for using our banking system!");
                    }

                    default ->
                        System.out.println("Invalid option. Please try again.");
                }

                System.out.println();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== Banking Menu =====");
        System.out.println("1. Create new account");
        System.out.println("2. Deposit money");
        System.out.println("3. View account balance");
        System.out.println("4. View all accounts");
        System.out.println("5. Exit and save");
        System.out.print("Enter your choice: ");
    }
}
