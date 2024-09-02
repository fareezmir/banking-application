package bankapplication;

public class CustomerPlatinum implements CustomerState {

  /*
   * Overview: CustomerPlatinum represents a Platinum level state in the context of a customer's membership.
   * It is an immutable state object in the State Design Pattern, providing specific behaviors
   * for purchase fee calculation and level updating based on the customer's balance.
   * 
   * Abstraction Function: AF(c) = a state where the customer receives no purchase fee when purchasing items, 
   * and represents the highest tier of the Customer's state.
   * 
   * Representation Invariant: RI(c) = true because the state is immutable and does not hold any varying properties.
   */

  /*
   * EFFECTS: returns the associated purchase fee for platinum customers
   */
  @Override
  public double purchaseFee() {
    return 0.0;
  }

  /*
   * MODIFIES: customer
   * EFFECTS: Updates the customer's membership level based on their current balance.
   *          If balance < 10,000, downgrades to silver; if balance < 20,000, downgrades to gold.
   */
  @Override
  public void updateLevel(Customer customer) {
    if (customer.getBalance() < 10000) {
      customer.setLevelState(new CustomerSilver());
    } else if (customer.getBalance() < 20000) {
      customer.setLevelState(new CustomerGold());
    }
  }

  /*
   * EFFECTS: returns String representation of the level.
   */
  @Override
  public String getLevel() {
    return "platinum";
  }

  /*
   * EFFECTS: Returns a string representation of the Platinum level state, noting that there is no purchase fee.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "CustomerState: Platinum (Purchase Fee: none)";
  }

  /*
   * EFFECTS: Since CustomerPlatinum is immutable and contains no fields, the rep invariant is true by default.
   */
  public boolean repOK() {
    return true;
  }

}