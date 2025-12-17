package services;

import models.*;
import utils.*;

public class TransactionService {

    public User registerUser(String name, String password, String email, String phone) {
        // Validate inputs
        if (! InputValidator.isValidName(name)) {
            System.out.println("\n⚠ Invalid name.  Use only letters and spaces (2-50 characters).");
            return null;
        }
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("\n⚠ Invalid email format.");
            return null;
        }
        if (!InputValidator. isValidPhone(phone)) {
            System.out.println("\n⚠ Invalid phone number.  Enter 10-digit Indian mobile number.");
            return null;
        }
        if (! InputValidator.isValidPassword(password)) {
            System. out.println("\n⚠ Password must be at least 4 characters.");
            return null;
        }

        // Check if email already exists
        if (DataStorage.getUserByEmail(email) != null) {
            System.out.println("\n⚠ Email already registered.");
            return null;
        }

        String userId = DataStorage.generateUserId();
        User user = new User(userId, name, password, email, phone, false);
        DataStorage.addUser(user);

        return user;
    }

    public User loginUser(String email, String password) {
        User user = DataStorage.getUserByEmail(email);
        if (user == null) {
            System.out.println("\n⚠ No account found with this email.");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("\n⚠ Incorrect password.");
            return null;
        }
        return user;
    }

    public User adminLogin(String adminId, String password) {
        User admin = DataStorage.getUser(adminId);
        if (admin == null || !admin.isAdmin()) {
            System.out.println("\n⚠ Invalid admin credentials.");
            return null;
        }
        if (!admin.getPassword().equals(password)) {
            System.out.println("\n⚠ Incorrect password.");
            return null;
        }
        return admin;
    }
}
