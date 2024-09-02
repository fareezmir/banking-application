package bankapplication;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Customer {

  /* 
   * Overview: Customer is mutable, which represents a bank customer with a balance,
   * username, password, and membership level. It includes operations for account
   * deposits, withdrawals, level updates, and purchase fee calculations based on
   * the membership level.
   *  
   * Abstraction Function:
   * AF(c) = A customer with a balance b, username u, password p, role r, and a membership level l,
   *         such that for any customer c, the balance is represented by 'balance', the username by 'username',
   *         the password by 'password', the role by 'role', and the membership level is determined by the state represented by 
   *         'levelState.getLevel()'.
   *
   * Representation Invariant:
   * RI(c) = true if:
   * - balance >= 0
   * - username, password and role are not null or empty
   * - levelState is not null
   * and false otherwise
   */

  private double balance;
  private String username;
  private String password;
  private String role;
  private BufferedReader customerData;
  private CustomerState levelState;

  //constructor
  public Customer(File userFile) {
    try {
      customerData = new BufferedReader(new FileReader(userFile));
      this.role = customerData.readLine();
      this.password = customerData.readLine();
      this.username = customerData.readLine();
      this.balance = Double.parseDouble(customerData.readLine());
      String level = customerData.readLine();
      setInitialState(level); // Set the initial state based on the level string
      customerData.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * MODIFIES: this.levelState
   * EFFECTS: Sets the customer's level state to the given levelState.
   */
  public void setLevelState(CustomerState levelState) {
    this.levelState = levelState;
  }

  /*
   * MODIFIES: this (current state of Customer object)
   * EFFECTS: Deposits the specified amount into the customer's account.
   *          Returns true if the operation is successful and false otherwise.
   * REQUIRES: amount > 0
   */
  public boolean deposit(double amount) {
    if (amount > 0) {
      balance += amount;
      balance = Double.parseDouble(String.format("%.2f", balance)); //format the balance so it shows 2 decimal points max
      updateLevel();
      updateCustomer();
      return true;
    }

    return false;

  }

  /*
   * MODIFIES: this (current state of Customer object)
   * EFFECTS: Withdraws the specified amount out of the customer's account.
   *          Returns true if the operation is successful and false otherwise.
   * REQUIRES: amount > 0 && amount <= balance
   */

  public boolean withdraw(double amount) {
    if (amount > 0 && amount <= balance) {
      balance -= amount;
      balance = Double.parseDouble(String.format("%.2f", balance)); //format the balance so it shows 2 decimal points max
      updateLevel();
      updateCustomer();
      return true;
    }
    return false;
  }

  /*
   * EFFECTS: Returns the current balance of the customer.
   */
  public double getBalance() {
    return balance;
  }

  /*
   * EFFECTS: Returns the current level of the customer.
   */
  public String getLevel() {
    return levelState.getLevel();
  }

  /*
   * EFFECTS: Returns the username of the customer.
   */
  public String getUsername() {
    return username;
  }

  /*
   * EFFECTS: Returns the role of the customer.
   */
  public String getRole() {
    return role;
  }

  /*
   * MODIFIES: this (Current state of Customer object)
   * EFFECTS: Updates the customer's level based on the current balance.
   */
  private void updateLevel() {
    levelState.updateLevel(this);
  }

  /*
   * EFFECTS: Returns the fee from a purchase, which is determined by the customer level
   */
  public double calculatePurchaseFee() {
    return levelState.purchaseFee();
  }

  /*
   * MODIFIES: this (Current state of Customer object)
   * EFFECTS: Writes the data of the customer in a text file, incorporating its most important
   * data (username, password, level, and balance). Prints the stack trace if an Exception occurs.
   */
  private void updateCustomer() {
    File userFile = new File("src/userfile/" + username + ".txt");
    try {
      BufferedWriter customerUpdater = new BufferedWriter(new FileWriter(userFile));
      customerUpdater.write(role + "\n");
      customerUpdater.write(password + "\n");
      customerUpdater.write(username + "\n");
      customerUpdater.write(balance + "\n");
      customerUpdater.write(getLevel());
      customerUpdater.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * MODIFIES: this (Current state of Customer object)
   * EFFECTS: Sets the customer's level state based on the given level string.
   */
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

  /*
   * EFFECTS: Returns a String represention of the Customer object. 
   * Implements the abstraction function
   */
  @Override
  public String toString() {
    return "Customer {" + "Balance = " + balance + ", Username = " + username + ", Password = " + password + ", Level = " + levelState.getLevel() + ", Role = " + role + "}";
  }

  /* 
   * EFFECTS: Returns true if the rep invariant holds for this
   * object; otherwise returns false
   */
  public boolean repOK() {
    if (balance < 0) {
      return false;
    } else if (username == null || username.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
      return false;
    } else if (levelState == null) {
      return false;
    }
    return true;
  }

}