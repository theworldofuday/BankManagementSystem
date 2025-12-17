# 🏦 Bank Management System

A comprehensive console-based banking application built with Core Java, featuring customer and admin modules for complete account management.

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Author](#author)

## ✨ Features

### Customer Module
- **Account Management**: Open Savings/Current accounts
- **Deposit**:  Add money to your account
- **Withdrawal**:  Withdraw with minimum balance validation
- **Fund Transfer**: Transfer money between accounts
- **Balance Inquiry**: Check current balance
- **Transaction History**: View all past transactions
- **Profile Management**: View personal details

### Admin Module
- **View All Customers**: List all registered users
- **View All Accounts**:  Monitor all bank accounts
- **Transaction Monitoring**: Track all transactions
- **Account Control**: Activate/Deactivate accounts
- **Search Accounts**: Find specific account details
- **Bank Reports**: Generate comprehensive reports

### Security Features
- Password protected login
- Input validation for all fields
- Minimum balance enforcement
- Transaction limits

## 🛠 Tech Stack

- **Language**: Java (JDK 8+)
- **Paradigm**: Object-Oriented Programming
- **Data Storage**: Java Serialization
- **Collections**: HashMap, ArrayList

## 📁 Project Structure

```
BankManagementSystem/
├── src/
│   ├── Main.java                 # Application entry point
│   ├── models/
│   │   ├── Account.java          # Account entity
│   │   ├── Transaction.java      # Transaction entity
│   │   └── User.java             # User entity
│   ├── services/
│   │   ├── AccountService.java   # Account operations
│   │   ├── AdminService.java     # Admin operations
│   │   └── TransactionService.java # User authentication
│   └── utils/
│       ├── Constants.java        # Application constants
│       ├── DataStorage.java      # Data persistence
│       └── InputValidator.java   # Input validation
├── data/                         # Serialized data files
└── README.md
```

## 🚀 Installation & Setup

### Prerequisites
- JDK 8 or higher
- Any IDE (VS Code, IntelliJ, Eclipse) or Command Line

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/BankManagementSystem.git
   cd BankManagementSystem
   ```

2. **Compile the project**
   ```bash
   javac -d bin src/*. java src/models/*.java src/services/*. java src/utils/*.java
   ```

3. **Run the application**
   ```bash
   cd bin
   java Main
   ```

## 📖 Usage

### Default Admin Credentials
- **Admin ID**: `ADMIN001`
- **Password**: `admin123`

### Quick Start
1. Run the application
2. Register as a new customer
3. Login with your email and password
4. Open a new bank account
5. Perform banking operations

## 📸 Sample Output

```
╔═══════════════════════════════════════════════════════════════╗
║          🏦  WELCOME TO SECURE BANK SYSTEM  🏦               ║
╚═══════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────┐
│            MAIN MENU                │
├─────────────────────────────────────┤
│  1. 👤 Customer Login               │
│  2. 📝 New Customer Registration    │
│  3. 🔐 Admin Login                  │
│  4. 🚪 Exit                         │
└─────────────────────────────────────┘
```

## 🔑 Key Concepts Used

- **OOP Principles**: Encapsulation, Abstraction, Inheritance
- **Java Collections**: HashMap for O(1) lookups, ArrayList for ordered data
- **Serialization**: Persistent data storage
- **File I/O**: Reading and writing data files
- **Exception Handling**: Robust error management
- **Input Validation**: Regex patterns for email, phone validation

## 👨‍💻 Author

**Uday**
- GitHub: [@theworldofuday](https://github.com/theworldofuday)

