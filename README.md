# Bank Application

**Name**: Fareez Mir  
**Student ID**: 501159472  

## Overview

The Bank Application is a JavaFX-based banking system simulation. Developed as part of a university project, it allows users to log in as either a Manager or a Customer. Managers have the capability to manage customer accounts, while Customers can perform various banking operations such as viewing their balance, making deposits, withdrawing money, and purchasing items online. The application is designed with object-oriented principles and incorporates design patterns to ensure robust and maintainable code.

## Features

- **Manager Functions**: 
  - Add and delete customer accounts.
  - Login and logout of the system.
  - Manage customer data efficiently.
  
- **Customer Functions**:
  - Login and logout of the system.
  - View account balance.
  - Deposit and withdraw money.
  - Make online purchases with account validation.
  - Dynamic account level management based on balance.

## Project Structure

### 1. Use Case Diagram

The use case diagram for the bank application showcases interactions between users and the system. The application consists of two primary actors: the Manager and the Customer. 

- **Manager**: 
  - Can "AddCustomer" and "DeleteCustomer".
  - Can "Login" and "Logout" of the system.

- **Customer**: 
  - Can "Login" and "Logout".
  - Can "ViewBalance", "DepositMoney", "WithdrawMoney", and make "OnlinePurchase".

Validation processes are included for depositing, withdrawing, and purchasing, ensuring that all operations are secure and correctly reflected in the customer’s data file.

### 2. Class Diagram

The class diagram outlines the structure and relationships between classes within the system, including:

- **Customer**: Handles customer data and operations.
- **CustomerState**: Interface representing different account levels (Silver, Gold, Platinum).
- **ManagerScene**: Aggregates Manager-specific tasks.
- **CustomerScene**: Manages customer operations and data.
- **LoginMenu**: Entry point for user authentication.
- **SceneHub**: Controls the transition between different scenes.
- **ShoppingScene**: Manages the shopping experience for customers.

### 3. State Design Pattern

The application utilizes the State Design Pattern to manage customer account levels dynamically. The `CustomerState` interface and its implementations (`CustomerSilver`, `CustomerGold`, `CustomerPlatinum`) allow for seamless transitions between account levels based on the customer’s balance. This pattern ensures that the system is flexible and can handle changes in customer behavior at runtime.

## How to Run

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/bank-application.git
    ```
2. **Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse)**.
3. **Build and run the project**:
    - Ensure that JavaFX is correctly set up in your environment.
    - Run the `BankApplication` class to start the application.
