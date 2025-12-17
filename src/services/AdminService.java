package services;

import models.*;
import utils.*;
import java.util.*;

public class AdminService {

    public void viewAllUsers() {
        Map<String, User> users = DataStorage.getAllUsers();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                        ALL REGISTERED USERS                       ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        
        int count = 0;
        for (User user :  users.values()) {
            if (! user.isAdmin()) {
                count++;
                System.out. println("  " + count + ". " + user. getUserId() + " | " + user.getName() + " | " + user.getEmail());
            }
        }
        
        if (count == 0) {
            System.out. println("  No customers registered yet.");
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println("  Total Customers: " + count);
    }

    public void viewAllAccounts() {
        Map<String, Account> accounts = DataStorage. getAllAccounts();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out. println("║                              ALL BANK ACCOUNTS                                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("| %-15s | %-10s | %-12s | %-10s | %-8s |\n", 
            "Account No.", "User ID", "Type", "Balance", "Status");
        System.out.println("|-----------------|------------|--------------|------------|----------|");
        
        double totalBalance = 0;
        for (Account account : accounts.values()) {
            System.out.printf("| %-15s | %-10s | %-12s | ₹%-9. 2f | %-8s |\n",
                account.getAccountNumber(),
                account.getUserId(),
                account.getAccountType(),
                account.getBalance(),
                account.isActive() ? "Active" : "Inactive");
            totalBalance += account.getBalance();
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("  Total Accounts: " + accounts.size());
        System.out.println("  Total Bank Balance: ₹" + String. format("%.2f", totalBalance));
    }

    public void viewAllTransactions() {
        List<Transaction> transactions = DataStorage.getAllTransactions();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              ALL TRANSACTIONS                                          ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════╣");
        
        if (transactions.isEmpty()) {
            System.out.println("  No transactions recorded yet.");
        } else {
            System.out. println("| Trans. ID    | Type       | Amount      | Balance After | Date & Time          |");
            System. out.println("|--------------|------------|-------------|---------------|----------------------|");
            
            // Show last 20 transactions
            int start = Math.max(0, transactions.size() - 20);
            for (int i = start; i < transactions.size(); i++) {
                System.out.println(transactions.get(i));
            }
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("  Total Transactions: " + transactions.size());
    }

    public void activateDeactivateAccount(String accountNumber, boolean activate) {
        Account account = DataStorage.getAccount(accountNumber);
        if (account == null) {
            System.out.println("\n⚠ Account not found.");
            return;
        }

        account.setActive(activate);
        DataStorage.updateAccount(account);
        System.out.println("\n✓ Account " + accountNumber + " has been " + 
            (activate ? "activated" : "deactivated") + " successfully.");
    }

    public void searchAccount(String accountNumber) {
        Account account = DataStorage.getAccount(accountNumber);
        if (account == null) {
            System.out.println("\n⚠ Account not found.");
            return;
        }

        User user = DataStorage.getUser(account.getUserId());
        System.out.println(account);
        if (user != null) {
            System.out.println("\nAccount Holder Details:");
            System.out.println(user);
        }
    }

    public void generateBankReport() {
        Map<String, Account> accounts = DataStorage.getAllAccounts();
        Map<String, User> users = DataStorage.getAllUsers();
        List<Transaction> transactions = DataStorage. getAllTransactions();

        double totalDeposits = 0;
        double totalWithdrawals = 0;
        double totalTransfers = 0;
        int savingsCount = 0;
        int currentCount = 0;

        for (Transaction t : transactions) {
            switch (t.getTransactionType()) {
                case "DEPOSIT":
                    totalDeposits += t.getAmount();
                    break;
                case "WITHDRAWAL": 
                    totalWithdrawals += t.getAmount();
                    break;
                case "TRANSFER-DR":
                    totalTransfers += t.getAmount();
                    break;
            }
        }

        for (Account a : accounts.values()) {
            if (a.getAccountType().equalsIgnoreCase("SAVINGS")) savingsCount++;
            else currentCount++;
        }

        int customerCount = 0;
        for (User u : users.values()) {
            if (! u.isAdmin()) customerCount++;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out. println("║                     BANK SUMMARY REPORT                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("  📊 CUSTOMER STATISTICS");
        System.out.println("     Total Customers      : " + customerCount);
        System.out.println("     Total Accounts       : " + accounts.size());
        System.out.println("     - Savings Accounts   : " + savingsCount);
        System.out.println("     - Current Accounts   :  " + currentCount);
        System.out.println("  ────────────────────────────────────────────────────────────");
        System.out.println("  💰 FINANCIAL STATISTICS");
        System.out.println("     Total Deposits       : ₹" + String. format("%.2f", totalDeposits));
        System.out.println("     Total Withdrawals    : ₹" + String.format("%.2f", totalWithdrawals));
        System.out.println("     Total Transfers      : ₹" + String.format("%.2f", totalTransfers));
        System.out.println("     Total Transactions   : " + transactions.size());
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}
