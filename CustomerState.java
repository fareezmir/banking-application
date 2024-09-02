package bankapplication;

public interface CustomerState {

  /*
   * Overview: CustomerState is an interface (immutable), representing the state of a customer in the banking application.
   * It defines the contract for customer states, including operations for updating the customer's level,
   * calculating purchase fees, and obtaining the current level
   */

  /*
   * MODIFIES: customer
   * EFFECTS: Updates the level state of the customer based on specific criteria. This method is
   * overridden by subclasses to implement specific level transition logic.
   */
  void updateLevel(Customer customer);

  /*
   * EFFECTS: Returns the purchase fee associated with the customer's current level state. This method will be
   * overridden by subclasses to return specific fees for different levels.
   */
  public double purchaseFee();

  /*
   * EFFECTS: Returns a string representation of the customer's current level state. This method will be
   * overridden by subclasses to return the name of the specific level.
   */
  public String getLevel();

}