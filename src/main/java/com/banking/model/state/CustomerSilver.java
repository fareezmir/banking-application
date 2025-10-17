package com.banking.model.state;

import com.banking.config.ApplicationConfig;
import com.banking.model.domain.Customer;

public class CustomerSilver implements CustomerState {
  @Override
  public double purchaseFee() { return ApplicationConfig.FEE_SILVER; }

  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() >= ApplicationConfig.PLATINUM_MIN_BALANCE) {
      customer.setLevelState(new CustomerPlatinum());
    } else if (customer.getBalance() >= ApplicationConfig.GOLD_MIN_BALANCE) {
      customer.setLevelState(new CustomerGold());
    }
  }

  @Override
  public String getLevel() { return "silver"; }
}


