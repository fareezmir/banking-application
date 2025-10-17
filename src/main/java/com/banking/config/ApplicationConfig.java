package com.banking.config;

public final class ApplicationConfig {
  private ApplicationConfig() {}

  // Balance thresholds
  public static final double GOLD_MIN_BALANCE = 10_000.0;
  public static final double PLATINUM_MIN_BALANCE = 20_000.0;

  // Purchase fees by tier
  public static final double FEE_SILVER = 20.0;
  public static final double FEE_GOLD = 10.0;
  public static final double FEE_PLATINUM = 0.0;

  // Defaults for new customers
  public static final double DEFAULT_INITIAL_BALANCE = 100.0;
  public static final String DEFAULT_INITIAL_LEVEL = "silver";

  // Data directory
  public static final String USER_DATA_DIR = "data/userfile";
}


