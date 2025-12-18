package api;

import java.time.LocalDateTime;
import java. time.format.DateTimeFormatter;

public class EmailTemplates {
    
    private static final String BANK_NAME = "Secure Bank";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm: ss");
    
    // ==================== PLAIN TEXT TEMPLATES ====================
    
    // Welcome email for new customer
    public static String getWelcomeEmail(String name, String oderId, String email) {
        return "═══════════════════════════════════════════════════════\n" +
               "        WELCOME TO " + BANK_NAME. toUpperCase() + "!\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Thank you for registering with " + BANK_NAME + "!\n\n" +
               "Your registration details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "User ID    : " + userId + "\n" +
               "Email      : " + email + "\n" +
               "Registered : " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "You can now login to create your bank account and start\n" +
               "using our banking services.\n\n" +
               "For any assistance, contact our 24/7 customer support.\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════\n" +
               "This is an automated message. Please do not reply.\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Account opening confirmation
    public static String getAccountOpeningEmail(String name, String accountNumber, 
                                                 String accountType, double balance) {
        return "═══════════════════════════════════════════════════════\n" +
               "        NEW ACCOUNT OPENED SUCCESSFULLY!\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Congratulations!  Your new bank account has been opened.\n\n" +
               "Account Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Account Number : " + accountNumber + "\n" +
               "Account Type   : " + accountType + "\n" +
               "Initial Balance: ₹" + String.format("%.2f", balance) + "\n" +
               "Opened On      : " + LocalDateTime.now().format(formatter) + "\n" +
               "Status         : Active\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "You can now perform deposits, withdrawals, and transfers.\n\n" +
               "Security Tips:\n" +
               "• Never share your password with anyone\n" +
               "• Always logout after banking\n" +
               "• Report suspicious activity immediately\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Deposit confirmation
    public static String getDepositEmail(String name, String accountNumber, 
                                         double amount, double newBalance, 
                                         String transactionId) {
        return "═══════════════════════════════════════════════════════\n" +
               "        💰 DEPOSIT SUCCESSFUL\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Your account has been credited successfully.\n\n" +
               "Transaction Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Transaction ID   : " + transactionId + "\n" +
               "Account Number   : " + accountNumber + "\n" +
               "Transaction Type :  DEPOSIT\n" +
               "Amount Credited  : ₹" + String. format("%.2f", amount) + "\n" +
               "New Balance      : ₹" + String.format("%.2f", newBalance) + "\n" +
               "Date & Time      : " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "⚠️ If you did not make this transaction, please contact\n" +
               "   our customer support immediately.\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Withdrawal confirmation
    public static String getWithdrawalEmail(String name, String accountNumber, 
                                            double amount, double newBalance, 
                                            String transactionId) {
        return "═══════════════════════════════════════════════════════\n" +
               "        💸 WITHDRAWAL SUCCESSFUL\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Your account has been debited successfully.\n\n" +
               "Transaction Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Transaction ID   : " + transactionId + "\n" +
               "Account Number   : " + accountNumber + "\n" +
               "Transaction Type : WITHDRAWAL\n" +
               "Amount Debited   : ₹" + String. format("%.2f", amount) + "\n" +
               "New Balance      : ₹" + String.format("%.2f", newBalance) + "\n" +
               "Date & Time      : " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "⚠️ SECURITY ALERT:\n" +
               "   If you did not make this transaction, please:\n" +
               "   1. Contact our helpline immediately\n" +
               "   2. Change your password\n" +
               "   3. Request account freeze if needed\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Fund transfer - Sender
    public static String getTransferSenderEmail(String name, String fromAccount, 
                                                 String toAccount, double amount, 
                                                 double newBalance, String transactionId) {
        return "═══════════════════════════════════════════════════════\n" +
               "        🔄 FUND TRANSFER SUCCESSFUL\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Your fund transfer has been processed successfully.\n\n" +
               "Transaction Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Transaction ID     : " + transactionId + "\n" +
               "From Account       : " + fromAccount + "\n" +
               "To Account         : " + toAccount + "\n" +
               "Amount Transferred : ₹" + String.format("%.2f", amount) + "\n" +
               "Your New Balance   : ₹" + String.format("%.2f", newBalance) + "\n" +
               "Date & Time        : " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "⚠️ If you did not initiate this transfer, please contact\n" +
               "   our customer support immediately.\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Fund transfer - Receiver
    public static String getTransferReceiverEmail(String name, String fromAccount, 
                                                   String toAccount, double amount, 
                                                   double newBalance, String transactionId) {
        return "═══════════════════════════════════════════════════════\n" +
               "        💰 FUND RECEIVED\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Good news! You have received a fund transfer.\n\n" +
               "Transaction Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Transaction ID   : " + transactionId + "\n" +
               "From Account     : " + fromAccount + "\n" +
               "Your Account     : " + toAccount + "\n" +
               "Amount Received  : ₹" + String. format("%.2f", amount) + "\n" +
               "Your New Balance : ₹" + String.format("%.2f", newBalance) + "\n" +
               "Date & Time      :  " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Low balance alert
    public static String getLowBalanceAlert(String name, String accountNumber, double balance) {
        return "═══════════════════════════════════════════════════════\n" +
               "        ⚠️ LOW BALANCE ALERT\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Your account balance is running low.\n\n" +
               "Account Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Account Number   : " + accountNumber + "\n" +
               "Current Balance  : ₹" + String. format("%.2f", balance) + "\n" +
               "Minimum Balance  : ₹500. 00\n" +
               "───────────────────────────────────────────────────────\n\n" +
               "Please deposit funds to avoid any inconvenience.\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // Account statement email
    public static String getStatementEmail(String name, String accountNumber, 
                                           String period, String attachmentInfo) {
        return "═══════════════════════════════════════════════════════\n" +
               "        📋 ACCOUNT STATEMENT\n" +
               "═══════════════════════════════════════════════════════\n\n" +
               "Dear " + name + ",\n\n" +
               "Please find your account statement attached.\n\n" +
               "Statement Details:\n" +
               "───────────────────────────────────────────────────────\n" +
               "Account Number :  " + accountNumber + "\n" +
               "Period         : " + period + "\n" +
               "Generated On   : " + LocalDateTime.now().format(formatter) + "\n" +
               "───────────────────────────────────────────────────────\n\n" +
               attachmentInfo + "\n\n" +
               "Best Regards,\n" +
               BANK_NAME + " Team\n\n" +
               "═══════════════════════════════════════════════════════";
    }
    
    // ==================== HTML TEMPLATES (Better looking) ====================
    
    public static String getDepositEmailHtml(String name, String accountNumber, 
                                              double amount, double newBalance, 
                                              String transactionId) {
        return "<!DOCTYPE html>" +
               "<html><head><style>" +
               "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
               ". container { background-color: white; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto; }" +
               ".header { background-color: #2E7D32; color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }" +
               ".content { padding: 20px; }" +
               ".details { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin:  15px 0; }" +
               ".amount { font-size: 24px; color: #2E7D32; font-weight: bold; }" +
               ".footer { text-align: center; color: #666; font-size:  12px; margin-top: 20px; }" +
               "</style></head><body>" +
               "<div class='container'>" +
               "<div class='header'><h2>💰 Deposit Successful</h2></div>" +
               "<div class='content'>" +
               "<p>Dear <strong>" + name + "</strong>,</p>" +
               "<p>Your account has been credited successfully.</p>" +
               "<div class='details'>" +
               "<p><strong>Transaction ID: </strong> " + transactionId + "</p>" +
               "<p><strong>Account Number:</strong> " + accountNumber + "</p>" +
               "<p><strong>Amount Credited:</strong> <span class='amount'>₹" + String.format("%.2f", amount) + "</span></p>" +
               "<p><strong>New Balance:</strong> ₹" + String.format("%.2f", newBalance) + "</p>" +
               "<p><strong>Date: </strong> " + LocalDateTime.now().format(formatter) + "</p>" +
               "</div>" +
               "<p style='color: #d32f2f;'>⚠️ If you did not make this transaction, please contact us immediately.</p>" +
               "</div>" +
               "<div class='footer'><p>" + BANK_NAME + " | This is an automated message</p></div>" +
               "</div></body></html>";
    }
    
    public static String getWithdrawalEmailHtml(String name, String accountNumber, 
                                                 double amount, double newBalance, 
                                                 String transactionId) {
        return "<! DOCTYPE html>" +
               "<html><head><style>" +
               "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
               ".container { background-color: white; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto; }" +
               ".header { background-color: #D32F2F; color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }" +
               ".content { padding: 20px; }" +
               ". details { background-color: #f9f9f9; padding:  15px; border-radius:  5px; margin: 15px 0; }" +
               ".amount { font-size: 24px; color: #D32F2F; font-weight: bold; }" +
               ".footer { text-align: center; color: #666; font-size: 12px; margin-top: 20px; }" +
               "</style></head><body>" +
               "<div class='container'>" +
               "<div class='header'><h2>💸 Withdrawal Alert</h2></div>" +
               "<div class='content'>" +
               "<p>Dear <strong>" + name + "</strong>,</p>" +
               "<p>Your account has been debited. </p>" +
               "<div class='details'>" +
               "<p><strong>Transaction ID:</strong> " + transactionId + "</p>" +
               "<p><strong>Account Number:</strong> " + accountNumber + "</p>" +
               "<p><strong>Amount Debited:</strong> <span class='amount'>₹" + String.format("%.2f", amount) + "</span></p>" +
               "<p><strong>Remaining Balance:</strong> ₹" + String.format("%.2f", newBalance) + "</p>" +
               "<p><strong>Date:</strong> " + LocalDateTime.now().format(formatter) + "</p>" +
               "</div>" +
               "<p style='color: #d32f2f;'>⚠️ If you did not make this transaction, contact us immediately! </p>" +
               "</div>" +
               "<div class='footer'><p>" + BANK_NAME + " | This is an automated message</p></div>" +
               "</div></body></html>";
    }
}
