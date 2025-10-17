package com.banking.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.banking.config.ApplicationConfig;
import com.banking.model.domain.Customer;

public class CustomerTest {
  private Path tempDir;
  private File userFile;

  @BeforeEach
  void setup() throws Exception {
    tempDir = Files.createTempDirectory("banking-tests");
    userFile = tempDir.resolve("testuser.txt").toFile();
    try (BufferedWriter w = new BufferedWriter(new FileWriter(userFile))) {
      w.write("customer\n");
      w.write("pass\n");
      w.write("testuser\n");
      w.write("1000\n");
      w.write("silver\n");
    }
  }

  @AfterEach
  void cleanup() throws Exception {
    if (userFile != null) userFile.delete();
    if (tempDir != null) Files.deleteIfExists(tempDir);
  }

  @Test
  void deposit_increasesBalance_andFormatsTwoDecimals() {
    Customer c = new Customer(userFile);
    assertTrue(c.deposit(12.345));
    assertEquals("silver", c.getLevel());
    assertEquals(1012.35, c.getBalance(), 0.001);
  }

  @Test
  void withdraw_decreasesBalance_andPreventsOverdraft() {
    Customer c = new Customer(userFile);
    assertTrue(c.withdraw(100));
    assertEquals(900.00, c.getBalance(), 0.001);
    assertFalse(c.withdraw(2000));
  }
}


