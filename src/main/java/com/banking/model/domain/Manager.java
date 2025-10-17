package com.banking.model.domain;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Manager {

  public boolean removeCustomer(String username) {
    File userFile = new File("data/userfile/" + username + ".txt");
    if (userFile.exists()) {
      return userFile.delete();
    }
    return false;
  }

  public boolean addCustomer(String username, String password) {
    double initialBalance = 100;
    String level = "silver";
    File userFile = new File("data/userfile/" + username + ".txt");
    if (!userFile.exists()) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
        writer.write("customer\n");
        writer.write(password + "\n");
        writer.write(username + "\n");
        writer.write(String.valueOf(initialBalance) + "\n");
        writer.write(level);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return true;
    }
    return false;
  }
}


