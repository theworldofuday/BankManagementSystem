package services;

import models.*;
import utils.*;
import api.*;
import java. util. List;

public class AccountService {

    // Create new account with email notification
    public Account createAccount(String oderId, String accountType, double initialDeposit) {
        if (initialDeposit < Constants. MINIMUM_BALANCE) {
            System.out.println("\n⚠ Minimum initial deposit is ₹" + Constants. MINIMUM_BALANCE);
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

        // 📧 Send account opening email
        User user = DataStorage.getUser(userId);
        if (user != null && EmailService.isEmailEnabled()) {
            String emailBody = EmailTemplates.getAccountOpeningEmail(
                user.getName(), accountNumber, accountType, initialDeposit
            );
            EmailService.sendEmailAsync(user.getEmail(), 
                "🏦 Account Opened Successfully - Secure Bank", emailBody);
        }

        return account;
    }

    // Deposit with email notification
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
        DataStorage. updateAccount(account);

        String transactionId = DataStorage.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, "DEPOSIT",
                amount, account.getBalance(), "Cash Deposit");
        DataStorage. addTransaction(transaction);

        System.out.println("\n✓ Successfully deposited ₹" + String.format("%.2f", amount));
        System.out.println("  New Balance: ₹" + String. format("%.2f", account. getBalance()));

        // 📧 Send deposit confirmation email
        User user = DataStorage.getUser(account.getUserId());
        if (user != null && EmailService.isEmailEnabled()) {
            // Send HTML email (looks better)
            String htmlEmail = EmailTemplates.getDepositEmailHtml(
                user.getName(), accountNumber, amount, 
                account.getBalance(), transactionId
            );
            EmailService.sendEmailAsync(user. getEmail(), 
                "💰 Deposit Successful - ₹" + String.format("%.2f", amount), htmlEmail);
        }

        return true;
    }

    // Withdraw with email notification
    public boolean withdraw(String accountNumber, double amount) {
        if (amount > Constants. MAXIMUM_WITHDRAWAL) {
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
        DataStorage. updateAccount(account);

        String transactionId = DataStorage.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, "WITHDRAWAL",
                amount, account.getBalance(), "Cash Withdrawal");
        DataStorage.addTransaction(transaction);

        System.out.println("\n✓ Successfully withdrawn ₹" + String.format("%.2f", amount));
        System.out.println("  Remaining Balance: ₹" + String.format("%.2f", account.getBalance()));

        // 📧 Send withdrawal alert email
        User user = DataStorage.getUser(account.getUserId());
        if (user != null && EmailService.isEmailEnabled()) {
            String htmlEmail = EmailTemplates.getWithdrawalEmailHtml(
                user. getName(), accountNumber, amount, 
                account.getBalance(), transactionId
            );
            EmailService.sendEmailAsync(user. getEmail(), 
                "💸 Withdrawal Alert - ₹" + String. format("%.2f", amount), htmlEmail);
            
            // Send low balance alert if needed
            if (account.getBalance() < 1000) {
                String lowBalanceEmail = EmailTemplates.getLowBalanceAlert(
                    user.getName(), accountNumber, account.getBalance()
                );
                EmailService.sendEmailAsync(user.getEmail(), 
                    "⚠️ Low Balance Alert - Secure Bank", lowBalanceEmail);
            }
        }

        return true;
    }

    // Transfer with email notifications to both parties
    public boolean transfer(String fromAccount, String toAccount, double amount) {
        if (amount > Constants.MAXIMUM_TRANSFER) {
            System.out.println("\n⚠ Maximum transfer limit is ₹" + Constants.MAXIMUM_TRANSFER);
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

        // Record transactions
        String transactionId1 = DataStorage.generateTransactionId();
        Transaction debit = new Transaction(transactionId1, fromAccount, "TRANSFER-DR",
                amount, sender.getBalance(), "Transfer to " + toAccount);
        DataStorage.addTransaction(debit);

        String transactionId2 = DataStorage.generateTransactionId();
        Transaction credit = new Transaction(transactionId2, toAccount, "TRANSFER-CR",
                amount, receiver.getBalance(), "Transfer from " + fromAccount);
        DataStorage.addTransaction(credit);

        System.out.println("\n✓ Successfully transferred ₹" + String.format("%.2f", amount));
        System.out.println("  Your New Balance: ₹" + String.format("%.2f", sender.getBalance()));

        // 📧 Send emails to both sender and receiver
        if (EmailService.isEmailEnabled()) {
            User senderUser = DataStorage.getUser(sender. getUserId());
            User receiverUser = DataStorage.getUser(receiver.getUserId());
            
            if (senderUser != null) {
                String senderEmail = EmailTemplates.getTransferSenderEmail(
                    senderUser.getName(), fromAccount, toAccount, 
                    amount, sender.getBalance(), transactionId1
                );
                EmailService.sendEmailAsync(senderUser.getEmail(), 
                    "🔄 Fund Transfer Successful - ₹" + String.format("%.2f", amount), senderEmail);
            }
            
            if (receiverUser != null) {
                String receiverEmail = EmailTemplates.getTransferReceiverEmail(
                    receiverUser.getName(), fromAccount, toAccount, 
                    amount, receiver.getBalance(), transactionId2
                );
                EmailService.sendEmailAsync(receiverUser.getEmail(), 
                    "💰 Fund Received - ₹" + String. format("%.2f", amount), receiverEmail);
            }
        }

        return true;
    }

    // Generate and optionally email statement
    public void generateStatement(String accountNumber, boolean sendEmail) {
        String pdfPath = PDFService.generateAccountStatement(accountNumber);
        
        if (pdfPath != null && sendEmail) {
            Account account = DataStorage.getAccount(accountNumber);
            User user = DataStorage.getUser(account.getUserId());
            
            if (user != null) {
                String emailBody = EmailTemplates.getStatementEmail(
                    user.getName(), accountNumber, 
                    "All Transactions", "PDF statement attached."
                );
                EmailService.sendEmailWithAttachment(
                    user.getEmail(),
                    "📋 Your Account Statement - Secure Bank",
                    emailBody,
                    pdfPath
                );
            }
        }
    }

    // Existing methods remain same...
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
        System.out.println("| Trans.  ID     | Type       | Amount      | Balance After | Date & Time          |");
        System.out. println("|---------------|------------|-------------|---------------|----------------------|");
        
        for (Transaction t : transactions) {
            System. out.println(t);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    public List<Account> getUserAccounts(String userId) {
        return DataStorage.getAccountsByUserId(userId);
    }
}
