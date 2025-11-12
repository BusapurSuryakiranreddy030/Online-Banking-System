import java.util.*;

class Account {
    private String accountNumber;
    private String name;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrawn: " + amount);
        return true;
    }

    public boolean transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add("Transferred " + amount + " to " + recipient.getAccountNumber());
            recipient.transactionHistory.add("Received " + amount + " from " + accountNumber);
            return true;
        }
        return false;
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for " + accountNumber + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class BankingSystem {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Online Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Funds");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> checkBalance();
                case 3 -> depositMoney();
                case 4 -> withdrawMoney();
                case 5 -> transferFunds();
                case 6 -> transactionHistory();
                case 7 -> {
                    System.out.println("Thank you for using Online Banking System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Initial Deposit: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        accounts.put(accNum, new Account(accNum, name, balance));
        System.out.println("Account created successfully!");
    }

    private static void checkBalance() {
        Account account = getAccount();
        if (account != null) {
            System.out.println("Your balance is: " + account.getBalance());
        }
    }

    private static void depositMoney() {
        Account account = getAccount();
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
            System.out.println("Deposited successfully!");
        }
    }

    private static void withdrawMoney() {
        Account account = getAccount();
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful!");
            }
        }
    }

    private static void transferFunds() {
        System.out.print("Enter your Account Number: ");
        String senderAccNum = scanner.nextLine();
        System.out.print("Enter Recipient Account Number: ");
        String recipientAccNum = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account sender = accounts.get(senderAccNum);
        Account recipient = accounts.get(recipientAccNum);
        
        if (sender == null || recipient == null) {
            System.out.println("Invalid account number(s)!");
            return;
        }
        
        if (sender.transfer(recipient, amount)) {
            System.out.println("Transfer successful!");
        }
    }

    private static void transactionHistory() {
        Account account = getAccount();
        if (account != null) {
            account.printTransactionHistory();
        }
    }

    private static Account getAccount() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        Account account = accounts.get(accNum);
        if (account == null) {
            System.out.println("Account not found!");
        }
        return account;
    }
}
