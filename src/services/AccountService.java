package services;

import models.*;
import utils.*;
import java. util. List;

public class AccountService {

    public Account createAccount(String userId, String accountType, double initialDeposit) {
        if (initialDeposit < Constants.MINIMUM_BALANCE) {
            System.out.println("\n⚠ Minimum initial deposit is ₹" + Constants.MINIMUM_BALANCE);
            return null;
        }

        String accountNumber = DataStorage.generateAccountNumber();
        Account account = new Account(accountNumber, userId, accountType, initialDeposit);
        DataStorage.addAccount(account);

        // Record transaction
        String transactionId = DataStorage.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, "DEPOSIT",
                initialDeposit, initialDeposit, "Initial Deposit - Account Opening");
        DataStorage.addTransaction(transaction);

        return account;
    }

    public boolean deposit(String accountNumber, double amount) {
        if (amount < Constants.MINIMUM_DEPOSIT) {
            System.out. println("\n⚠ Minimum deposit amount is ₹" + Constants. MINIMUM_DEPOSIT);
            return false;
        }

        Account account = DataStorage.getAccount(accountNumber);
        if (account == null || !account.isActive()) {
            System.out.println("\n⚠ Account not found or inactive.");
            return false;
        }

        account.deposit(amount);
        DataStorage.updateAccount(account);

        String transactionId = DataStorage.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, "DEPOSIT",
                amount, account.getBalance(), "Cash Deposit");
        DataStorage. addTransaction(transaction);

        System.out.println("\n✓ Successfully deposited ₹" + String.format("%.2f", amount));
        System.out.println("  New Balance: ₹" + String. format("%.2f", account. getBalance()));
        return true;
    }

    public boolean withdraw(String accountNumber, double amount) {
        if (amount > Constants.MAXIMUM_WITHDRAWAL) {
            System.out.println("\n⚠ Maximum withdrawal limit is ₹" + Constants. MAXIMUM_WITHDRAWAL);
            return false;
        }

        Account account = DataStorage.getAccount(accountNumber);
        if (account == null || !account.isActive()) {
            System.out. println("\n⚠ Account not found or inactive.");
            return false;
        }

        if (account.getBalance() - amount < Constants.MINIMUM_BALANCE) {
            System.out.println("\n⚠ Insufficient balance.  Minimum balance of ₹" + 
                Constants.MINIMUM_BALANCE + " must be maintained.");
            return false;
        }

        account.withdraw(amount);
        DataStorage.updateAccount(account);

        String transactionId = DataStorage.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, "WITHDRAWAL",
                amount, account.getBalance(), "Cash Withdrawal");
        DataStorage. addTransaction(transaction);

        System.out.println("\n✓ Successfully withdrawn ₹" + String.format("%.2f", amount));
        System.out.println("  Remaining Balance: ₹" + String.format("%.2f", account.getBalance()));
        return true;
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        if (amount > Constants.MAXIMUM_TRANSFER) {
            System.out.println("\n⚠ Maximum transfer limit is ₹" + Constants. MAXIMUM_TRANSFER);
            return false;
        }

        if (fromAccount. equals(toAccount)) {
            System.out.println("\n⚠ Cannot transfer to the same account.");
            return false;
        }

        Account sender = DataStorage.getAccount(fromAccount);
        Account receiver = DataStorage.getAccount(toAccount);

        if (sender == null || ! sender.isActive()) {
            System.out.println("\n⚠ Sender account not found or inactive.");
            return false;
        }

        if (receiver == null || ! receiver.isActive()) {
            System.out.println("\n⚠ Receiver account not found or inactive.");
            return false;
        }

        if (sender.getBalance() - amount < Constants.MINIMUM_BALANCE) {
            System.out.println("\n⚠ Insufficient balance for transfer.");
            return false;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);
        DataStorage.updateAccount(sender);
        DataStorage.updateAccount(receiver);

        // Record debit transaction
        String transactionId1 = DataStorage.generateTransactionId();
        Transaction debit = new Transaction(transactionId1, fromAccount, "TRANSFER-DR",
                amount, sender.getBalance(), "Transfer to " + toAccount);
        DataStorage.addTransaction(debit);

        // Record credit transaction
        String transactionId2 = DataStorage.generateTransactionId();
        Transaction credit = new Transaction(transactionId2, toAccount, "TRANSFER-CR",
                amount, receiver.getBalance(), "Transfer from " + fromAccount);
        DataStorage.addTransaction(credit);

        System.out.println("\n✓ Successfully transferred ₹" + String.format("%.2f", amount));
        System.out.println("  Your New Balance: ₹" + String.format("%.2f", sender.getBalance()));
        return true;
    }

    public void checkBalance(String accountNumber) {
        Account account = DataStorage.getAccount(accountNumber);
        if (account == null) {
            System.out.println("\n⚠ Account not found.");
            return;
        }
        System.out.println(account);
    }

    public void displayTransactionHistory(String accountNumber) {
        List<Transaction> transactions = DataStorage. getTransactionsByAccount(accountNumber);
        
        if (transactions.isEmpty()) {
            System.out.println("\n📋 No transactions found for this account.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out. println("║                              TRANSACTION HISTORY                                       ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("| Trans.  ID    | Type       | Amount      | Balance After | Date & Time          |");
        System.out.println("|--------------|------------|-------------|---------------|----------------------|");
        
        for (Transaction t : transactions) {
            System. out.println(t);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    public List<Account> getUserAccounts(String userId) {
        return DataStorage.getAccountsByUserId(userId);
    }
}
