package com.banking.model.state;

import com.banking.model.domain.Customer;

public interface CustomerState {
  void updateLevel(Customer customer);
  double purchaseFee();
  String getLevel();
}


