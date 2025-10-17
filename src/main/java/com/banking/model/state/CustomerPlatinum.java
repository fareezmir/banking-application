package com.banking.model.state;

import com.banking.model.domain.Customer;

public class CustomerPlatinum implements CustomerState {
  @Override
  public double purchaseFee() { return 0.0; }

  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() < 10000) {
      customer.setLevelState(new CustomerSilver());
    } else if (customer.getBalance() < 20000) {
      customer.setLevelState(new CustomerGold());
    }
  }

  @Override
  public String getLevel() { return "platinum"; }
}


