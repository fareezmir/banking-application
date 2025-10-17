package com.banking.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class FileUtils {
  private FileUtils() {}

  public static BufferedReader openUserFile(String username) throws IOException {
    File userFile = new File("data/userfile/" + username + ".txt");
    return new BufferedReader(new FileReader(userFile));
  }
}


