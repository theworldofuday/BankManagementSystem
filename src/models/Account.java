package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java. time.format.DateTimeFormatter;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String accountNumber;
    private String userId;
    private String accountType;
    private double balance;
    private LocalDateTime createdAt;
    private boolean isActive;

    public Account(String accountNumber, String userId, String accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = initialDeposit;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getUserId() { return userId; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setBalance(double balance) { this.balance = balance; }
    public void setActive(boolean active) { this.isActive = active; }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter. ofPattern("dd-MM-yyyy HH:mm:ss");
        return "\n╔══════════════════════════════════════╗\n" +
               "║         ACCOUNT DETAILS              ║\n" +
               "╠══════════════════════════════════════╣\n" +
               "  Account Number :  " + accountNumber + "\n" +
               "  Account Type   : " + accountType + "\n" +
               "  Balance        : ₹" + String.format("%.2f", balance) + "\n" +
               "  Status         : " + (isActive ?  "Active" : "Inactive") + "\n" +
               "  Created On     : " + createdAt. format(formatter) + "\n" +
               "╚══════════════════════════════════════╝";
    }
}
