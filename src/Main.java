import models.*;
import services.*;
import utils.*;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static TransactionService transactionService = new TransactionService();
    private static AccountService accountService = new AccountService();
    private static AdminService adminService = new AdminService();
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out. println("║                                                               ║");
        System.out.println("║          🏦  WELCOME TO SECURE BANK SYSTEM  🏦               ║");
        System.out.println("║                                                               ║");
        System.out.println("║              Your Trusted Banking Partner                     ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter your choice:  ");

            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    customerRegistration();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
                    System.out.println("║     Thank you for using Secure Bank System.  Goodbye!  👋      ║");
                    System. out.println("╚═══════════════════════════════════════════════════════════════╝");
                    System.exit(0);
                default: 
                    System.out.println("\n⚠ Invalid choice.  Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out. println("│            MAIN MENU                │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. 👤 Customer Login               │");
        System.out. println("│  2. 📝 New Customer Registration    │");
        System.out. println("│  3. 🔐 Admin Login                  │");
        System.out.println("│  4. 🚪 Exit                         │");
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void customerRegistration() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out. println("║        NEW CUSTOMER REGISTRATION      ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine();
        System.out.print("Create Password: ");
        String password = scanner.nextLine();

        User user = transactionService. registerUser(name, password, email, phone);
        if (user != null) {
            System. out.println("\n✓ Registration Successful!");
            System.out. println("  Your User ID: " + user.getUserId());
            System.out. println("  Please login to create your bank account.");
        }
    }

    private static void customerLogin() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           CUSTOMER LOGIN              ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        currentUser = transactionService.loginUser(email, password);
        if (currentUser != null) {
            System.out.println("\n✓ Login Successful!  Welcome, " + currentUser.getName() + "!");
            customerDashboard();
        }
    }

    private static void customerDashboard() {
        while (true) {
            System. out.println("\n┌─────────────────────────────────────────┐");
            System.out. println("│          CUSTOMER DASHBOARD             │");
            System.out.println("├─────────────────────────────────────────┤");
            System. out.println("│  1. 🏦 Open New Account                 │");
            System.out. println("│  2. 💵 Deposit Money                    │");
            System.out. println("│  3. 💸 Withdraw Money                   │");
            System.out.println("│  4. 🔄 Transfer Funds                   │");
            System.out.println("│  5. 💰 Check Balance                    │");
            System.out. println("│  6. 📜 Transaction History              │");
            System.out. println("│  7. 📋 View My Accounts                 │");
            System.out.println("│  8. 👤 View Profile                     │");
            System. out.println("│  9. 🚪 Logout                           │");
            System.out. println("└─────────────────────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    openNewAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    transactionHistory();
                    break;
                case 7:
                    viewMyAccounts();
                    break;
                case 8:
                    viewProfile();
                    break;
                case 9:
                    currentUser = null;
                    System.out.println("\n✓ Logged out successfully.");
                    return;
                default:
                    System.out. println("\n⚠ Invalid choice.");
            }
        }
    }

    private static void openNewAccount() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          OPEN NEW ACCOUNT             ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.println("Select Account Type:");
        System.out. println("1. Savings Account");
        System.out.println("2. Current Account");
        int typeChoice = getIntInput("Enter choice (1/2): ");

        String accountType = (typeChoice == 1) ? "SAVINGS" : "CURRENT";
        double initialDeposit = getDoubleInput("Enter Initial Deposit (Min ₹" + Constants. MINIMUM_BALANCE + "): ");

        Account account = accountService.createAccount(currentUser.getUserId(), accountType, initialDeposit);
        if (account != null) {
            System.out.println("\n✓ Account Created Successfully!");
            System.out. println(account);
        }
    }

    private static void depositMoney() {
        String accountNumber = selectAccount();
        if (accountNumber == null) return;

        double amount = getDoubleInput("Enter deposit amount:  ₹");
        accountService.deposit(accountNumber, amount);
    }

    private static void withdrawMoney() {
        String accountNumber = selectAccount();
        if (accountNumber == null) return;

        double amount = getDoubleInput("Enter withdrawal amount: ₹");
        accountService.withdraw(accountNumber, amount);
    }

    private static void transferFunds() {
        System.out.println("\n--- FUND TRANSFER ---");
        String fromAccount = selectAccount();
        if (fromAccount == null) return;

        System.out.print("Enter beneficiary account number: ");
        String toAccount = scanner.nextLine();
        double amount = getDoubleInput("Enter transfer amount: ₹");

        accountService.transfer(fromAccount, toAccount, amount);
    }

    private static void checkBalance() {
        String accountNumber = selectAccount();
        if (accountNumber == null) return;
        accountService.checkBalance(accountNumber);
    }

    private static void transactionHistory() {
        String accountNumber = selectAccount();
        if (accountNumber == null) return;
        accountService.displayTransactionHistory(accountNumber);
    }

    private static void viewMyAccounts() {
        List<Account> accounts = accountService. getUserAccounts(currentUser.getUserId());
        if (accounts.isEmpty()) {
            System.out. println("\n📋 You don't have any accounts.  Please open one.");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           YOUR ACCOUNTS               ║");
        System.out.println("╚═══════════════════════════════════════╝");

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void viewProfile() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           YOUR PROFILE                ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println(currentUser);
    }

    private static String selectAccount() {
        List<Account> accounts = accountService. getUserAccounts(currentUser.getUserId());
        if (accounts. isEmpty()) {
            System.out.println("\n⚠ You don't have any accounts. Please open one first.");
            return null;
        }

        if (accounts.size() == 1) {
            return accounts.get(0).getAccountNumber();
        }

        System.out. println("\nSelect an account:");
        for (int i = 0; i < accounts. size(); i++) {
            Account acc = accounts.get(i);
            System.out.println((i + 1) + ". " + acc.getAccountNumber() + " (" + acc.getAccountType() + 
                ") - ₹" + String.format("%.2f", acc.getBalance()));
        }

        int choice = getIntInput("Enter choice: ");
        if (choice < 1 || choice > accounts. size()) {
            System.out.println("\n⚠ Invalid selection.");
            return null;
        }

        return accounts.get(choice - 1).getAccountNumber();
    }

    private static void adminLogin() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            ADMIN LOGIN                ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("Enter Admin ID: ");
        String adminId = scanner. nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User admin = transactionService.adminLogin(adminId, password);
        if (admin != null) {
            System. out.println("\n✓ Admin Login Successful!");
            adminDashboard();
        }
    }

    private static void adminDashboard() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────────┐");
            System.out.println("│           ADMIN DASHBOARD               │");
            System.out. println("├─────────────────────────────────────────┤");
            System.out.println("│  1. 👥 View All Customers               │");
            System.out.println("│  2. 🏦 View All Accounts                │");
            System.out. println("│  3. 📜 View All Transactions            │");
            System.out.println("│  4. 🔍 Search Account                   │");
            System. out.println("│  5. ✅ Activate Account                 │");
            System.out.println("│  6. ❌ Deactivate Account               │");
            System.out.println("│  7. 📊 Generate Bank Report             │");
            System.out.println("│  8. 🚪 Logout                           │");
            System.out.println("└─────────────────────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    adminService.viewAllUsers();
                    break;
                case 2:
                    adminService.viewAllAccounts();
                    break;
                case 3:
                    adminService. viewAllTransactions();
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    String accNo = scanner.nextLine();
                    adminService.searchAccount(accNo);
                    break;
                case 5:
                    System.out.print("Enter Account Number to Activate: ");
                    String activateAcc = scanner.nextLine();
                    adminService.activateDeactivateAccount(activateAcc, true);
                    break;
                case 6:
                    System.out.print("Enter Account Number to Deactivate: ");
                    String deactivateAcc = scanner.nextLine();
                    adminService.activateDeactivateAccount(deactivateAcc, false);
                    break;
                case 7:
                    adminService.generateBankReport();
                    break;
                case 8:
                    System.out.println("\n✓ Admin logged out successfully.");
                    return;
                default:
                    System.out.println("\n⚠ Invalid choice.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(scanner. nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
