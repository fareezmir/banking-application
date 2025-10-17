package com.banking.model.domain;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import com.banking.model.state.*;

public class Customer {
  private double balance;
  private String username;
  private String password;
  private String role;
  private BufferedReader customerData;
  private CustomerState levelState;

  public Customer(File userFile) {
    try {
      customerData = new BufferedReader(new FileReader(userFile));
      this.role = customerData.readLine();
      this.password = customerData.readLine();
      this.username = customerData.readLine();
      this.balance = Double.parseDouble(customerData.readLine());
      String level = customerData.readLine();
      setInitialState(level);
      customerData.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setLevelState(CustomerState levelState) { this.levelState = levelState; }

  public boolean deposit(double amount) {
    if (amount > 0) {
      balance += amount;
      balance = Double.parseDouble(String.format("%.2f", balance));
      updateLevel();
      updateCustomer();
      return true;
    }
    return false;
  }

  public boolean withdraw(double amount) {
    if (amount > 0 && amount <= balance) {
      balance -= amount;
      balance = Double.parseDouble(String.format("%.2f", balance));
      updateLevel();
      updateCustomer();
      return true;
    }
    return false;
  }

  public double getBalance() { return balance; }
  public String getLevel() { return levelState.getLevel(); }
  public String getUsername() { return username; }
  public String getRole() { return role; }

  private void updateLevel() { levelState.updateLevel(this); }
  public double calculatePurchaseFee() { return levelState.purchaseFee(); }

  private void updateCustomer() {
    File userFile = new File("data/userfile/" + username + ".txt");
    try (BufferedWriter customerUpdater = new BufferedWriter(new FileWriter(userFile))) {
      customerUpdater.write(role + "\n");
      customerUpdater.write(password + "\n");
      customerUpdater.write(username + "\n");
      customerUpdater.write(balance + "\n");
      customerUpdater.write(getLevel());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setInitialState(String level) {
    switch (level) {
      case "gold":
        this.levelState = new CustomerGold();
        break;
      case "platinum":
        this.levelState = new CustomerPlatinum();
        break;
      case "silver":
      default:
        this.levelState = new CustomerSilver();
        break;
    }
  }
}


