package bankapplication;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Manager {

  /* Overview: Manager is immutable, which represents a bank manager. It includes operations
   * to remove and add a customer.
   *  
   * Abstraction Function:
   * AF(c) = Represents the set of operations that Manager can perform on customer records.
   *
   * Representation Invariant:
   * RI(c) = true as there is no state to validate 
   */

  /*
   * REQUIRES: username != null && !username.isEmpty()
   * MODIFIES: userFile
   * EFFECTS: Attempts to delete the file associated with the specified username.
   *          Returns true if the file exists and is successfully deleted, false otherwise.
   */
  public boolean removeCustomer(String username) {
    File userFile = new File("src/userfile/" + username + ".txt");

    //If the file exists, delete
    if (userFile.exists()) {
      boolean delete = userFile.delete();

      if (delete) {
        return true;
      }

      //If file for some reason cannot be deleted
      else {
        return false;
      }
    }

    //If file does not exist
    return false;
  }

  /*
   * REQUIRES: username != null && !username.isEmpty() && password != null && !password.isEmpty()
   * MODIFIES: userFile
   * EFFECTS: Creates a new customer file if a file with the given username does not already exist.
   *          The file contains the default initial balance and customer level. Returns true if 
   *          the file is successfully created, false if the username is already taken.
   */
  public boolean addCustomer(String username, String password) {

    //Default values for the customer
    double initialBalance = 100;
    String level = "silver";

    //File to create
    File userFile = new File("src/userfile/" + username + ".txt");

    //If the username is not taken
    if (!userFile.exists()) {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(userFile));
        writer.write("customer\n");
        writer.write(password + "\n");
        writer.write(username + "\n");
        writer.write(String.valueOf(initialBalance) + "\n");
        writer.write(level);
        writer.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

      return true;
    }

    return false; //Username taken
  }

  /*
   * EFFECTS: Returns a String represention of the overview on the Manager class.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "Manager class provides functionalities to manage customer records in the banking application.";
  }
  
  /* EFFECTS: Returns true since the rep invariant holds as there is no internal state of the class to validate
   * or maintain.
   */
  public boolean repOK() {
    return true;
  }
}