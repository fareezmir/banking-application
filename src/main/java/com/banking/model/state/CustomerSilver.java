package com.banking.model.state;

import com.banking.model.domain.Customer;

public class CustomerSilver implements CustomerState {
  @Override
  public double purchaseFee() { return 20.0; }

  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() >= 20000) {
      customer.setLevelState(new CustomerPlatinum());
    } else if (customer.getBalance() >= 10000) {
      customer.setLevelState(new CustomerGold());
    }
  }

  @Override
  public String getLevel() { return "silver"; }
}


