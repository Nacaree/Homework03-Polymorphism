class BankAccount {

    protected final String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("deposited $" + amount + " into account " + accountNumber);
    }

    public void deposit(double amount, String currency) {
        balance += amount;
        System.out.println("deposited " + amount + " " + currency + " into account " + accountNumber);
    }

    public void withdraw(double amount) {
        if (balance < amount) {
            System.out.println("Cannot withdraw $" + amount + " due to insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrew $" + amount + " from account " + accountNumber);
    }

    public void displayBalance() {
        System.out.println("Account " + accountNumber + " has a balance of $" + balance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}

class SavingsAccount extends BankAccount {

    private final double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < 100) {
            System.out.println("Cannot withdraw $" + amount + " (Minimum balance of $100 required)");
            return;
        }
        super.withdraw(amount);
    }

    @Override
    public void displayBalance() {
        double balanceWithInterest = balance + (balance * interestRate / 100);
        System.out.println("Savings Account " + accountNumber + " has a balance of $" + balance + " (With interest: $" + balanceWithInterest + ")");
    }

}

public class Bank {

    public static void main(String[] args) {
        BankAccount account = new BankAccount("12345", 500);
        account.deposit(100);
        account.deposit(50, "EUR"); // Overloaded method
        account.withdraw(200);
        account.displayBalance();

        System.out.println("----------------------------");

        SavingsAccount savings = new SavingsAccount("67890", 600, 5);
        savings.deposit(200);
        savings.withdraw(550);
        savings.withdraw(400); // Should fail due to minimum balance requirement
        savings.displayBalance(); // Overridden method
    }
}
