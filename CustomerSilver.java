package bankapplication;

public class CustomerSilver implements CustomerState {
  /*
   * Overview: CustomerSilver represents a Silver level state in the context of a customer's membership.
   * It is an immutable state object in the State Design Pattern, providing specific behaviors
   * for purchase fee calculation and level updating based on the customer's balance.
   * 
   * Abstraction Function: AF(c) = a state where the customer receives a $20 purchase fee when purchasing items,
   * and represents the lowest level state.
   * 
   * Representation Invariant: RI(c) = true because the state is immutable and does not hold any varying properties.
   */

  /*
   * EFFECTS: returns the associated purchase fee for silver customers
   */
  @Override
  public double purchaseFee() {
    return 20.0;
  }

  /*
   * MODIFIES: customer
   * EFFECTS: Updates the customer's membership level based on their current balance.
   *          If balance >= 20,000, upgrades to platinum; if balance >= 10,000, upgrades to gold.
   */
  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() >= 20000) {
      customer.setLevelState(new CustomerPlatinum());
    } else if (customer.getBalance() >= 10000) {
      customer.setLevelState(new CustomerGold());
    }
  }

  /*
   * EFFECTS: returns String representation of the level
   */
  @Override
  public String getLevel() {
    return "silver";
  }

  /*
   * EFFECTS: Returns a string representation of the Silver level state, noting that there is a purchase fee of $10.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "CustomerState: Silver (Purchase Fee: $20)";
  }

  /*
   * EFFECTS: Since CustomerSilver is immutable and contains no fields, the rep invariant is true by default.
   */
  public boolean repOk() {
    return true;
  }

}