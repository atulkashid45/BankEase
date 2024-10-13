# BankEase: Efficient Bank Management System
![Java](https://img.shields.io/badge/Language-Java-red.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)
![Status](https://img.shields.io/badge/Status-Complete-orange.svg)
![Version](https://img.shields.io/badge/Version-1.0-green.svg)

BankEase is a powerful Bank Management System designed to automate and optimize core banking operations. Built using **Java** for the backend logic and **MySQL** for the database, it offers an efficient platform to manage customer data, accounts, and transactions while providing an intuitive user experience for both bank staff and customers.

## Key Features

- **Customer Management**: Seamlessly handle customer information, including personal details, account status, and transaction history.
- **Account Management**: Allows creation, update, and closure of bank accounts, as well as viewing account details.
- **Transaction Tracking**: Record and track every transaction, including deposits, withdrawals, and transfers.
- **User Authentication**: Secure login functionality for both customers and staff using account numbers and PINs.
- **Fund Transfer**: Enable customers to transfer funds between accounts quickly and securely.
- **Transaction History**: Provide a detailed view of each customer’s transaction history, including timestamps and transaction amounts.
- **Balance Inquiry**: Customers can instantly check their current balance.
- **Administrative Functions**: Bank staff can manage and monitor accounts, transactions, and customer details.

## Project Structure

The system is modular, making it easy to maintain and extend in the future. The following classes play a key role in the system:

- **Main.java**: The entry point of the system, responsible for initializing components and providing the user with options (e.g., login, create account).
- **Login.java**: Handles secure customer login, validating account numbers and PINs.
- **AccountCreation.java**: Manages the creation of new customer accounts with unique account numbers and PINs.
- **Customer.java**: A class representing customers, including their personal information, account details, and methods to update or retrieve data.
- **Transaction.java**: Manages all transactions, including deposits, withdrawals, and fund transfers.
- **BalanceInquiry.java**: Allows customers to view their current balance in real-time.
- **AccountClosure.java**: Handles closing customer accounts and archiving their information.
- **AdminPanel.java**: Provides administrators (bank staff) with additional tools to manage customers, monitor transactions, and view system logs.

## Database Schema

The system uses **MySQL** for persistent data storage. Below is a brief overview of the database structure:

- **customers**: Stores customer information such as name, contact details, and unique account number.
- **accounts**: Contains account-specific data including balance and account type.
- **transactions**: Records all financial transactions, storing details like date, amount, type (deposit, withdrawal, transfer), and account numbers involved.
- **staff**: Stores bank staff credentials and roles for system access.

## Usage

Upon running the program, the system presents options based on the user’s role:

- **Customers**:
  1. Login using account number and PIN.
  2. Access services such as balance inquiry, fund transfers, and transaction history.
  
- **Bank Staff (Admin)**:
  1. Login with admin credentials.
  2. Manage customer accounts, approve account closures, view transaction reports, and more.

### How to Run the System

1. Ensure you have **Java** and **MySQL** installed.
2. Clone the repository from GitHub:  
   ```bash
   git clone https://github.com/your-username/BankEase.git
