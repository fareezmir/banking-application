package com.banking.model.state;

import com.banking.config.ApplicationConfig;
import com.banking.model.domain.Customer;

public class CustomerGold implements CustomerState {
  @Override
  public double purchaseFee() { return ApplicationConfig.FEE_GOLD; }

  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() >= ApplicationConfig.PLATINUM_MIN_BALANCE) {
      customer.setLevelState(new CustomerPlatinum());
    } else if (customer.getBalance() < ApplicationConfig.GOLD_MIN_BALANCE) {
      customer.setLevelState(new CustomerSilver());
    }
  }

  @Override
  public String getLevel() { return "gold"; }
}


