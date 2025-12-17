package utils;

import models.*;
import java.io.*;
import java.util.*;

public class DataStorage {
    
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Account> accounts = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();

    static {
        createDataDirectory();
        loadAllData();
        initializeAdmin();
    }

    private static void createDataDirectory() {
        File directory = new File(Constants. DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private static void initializeAdmin() {
        if (! users.containsKey(Constants. ADMIN_ID)) {
            User admin = new User(Constants. ADMIN_ID, "Administrator", 
                Constants.ADMIN_PASSWORD, "admin@bank.com", "9999999999", true);
            users.put(Constants.ADMIN_ID, admin);
            saveUsers();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadAllData() {
        try {
            File usersFile = new File(Constants. USERS_FILE);
            if (usersFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile));
                users = (Map<String, User>) ois.readObject();
                ois.close();
            }

            File accountsFile = new File(Constants. ACCOUNTS_FILE);
            if (accountsFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(accountsFile));
                accounts = (Map<String, Account>) ois.readObject();
                ois. close();
            }

            File transactionsFile = new File(Constants.TRANSACTIONS_FILE);
            if (transactionsFile. exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(transactionsFile));
                transactions = (List<Transaction>) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            System.out.println("Note: Starting with fresh data.");
        }
    }

    public static void saveUsers() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.USERS_FILE));
            oos.writeObject(users);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving users data.");
        }
    }

    public static void saveAccounts() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.ACCOUNTS_FILE));
            oos.writeObject(accounts);
            oos.close();
        } catch (IOException e) {
            System. out.println("Error saving accounts data.");
        }
    }

    public static void saveTransactions() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.TRANSACTIONS_FILE));
            oos.writeObject(transactions);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving transactions data.");
        }
    }

    // User operations
    public static void addUser(User user) {
        users.put(user.getUserId(), user);
        saveUsers();
    }

    public static User getUser(String userId) {
        return users.get(userId);
    }

    public static User getUserByEmail(String email) {
        for (User user : users. values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public static Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }

    // Account operations
    public static void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        saveAccounts();
    }

    public static Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public static List<Account> getAccountsByUserId(String userId) {
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account. getUserId().equals(userId)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    public static Map<String, Account> getAllAccounts() {
        return new HashMap<>(accounts);
    }

    public static void updateAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        saveAccounts();
    }

    // Transaction operations
    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
    }

    public static List<Transaction> getTransactionsByAccount(String accountNumber) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(t);
            }
        }
        return accountTransactions;
    }

    public static List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    // Generate unique IDs
    public static String generateUserId() {
        return "USR" + String.format("%04d", users.size());
    }

    public static String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 10000000000L;
    }

    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}
