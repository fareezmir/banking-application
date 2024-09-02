package bankapplication;

public class Item {

  /*
   * Overview: Item represents an item in the bank application shopping menu.
   * It is an immutable object, providing a name for the item, along with its price.
   * 
   * Abstraction Function:
   * AF(c) = An item with a name n, and price p, such that for any item i, the name of the item is represented by 'i.getName()'
   *         and price of the item is represented by 'i.getPrice()'
   * 
   * Representation Invariant: RI(c) = true if name != null and name is not empty, and if price >= 0 and false otherwise.
   */
  private String name;
  private double price;

  //constructor
  public Item(String name, double price) {
    this.name = name;
    this.price = price;
  }

  /*
   * EFFECTS: Returns the name of the item.
   */
  public String getName() {
    return name;
  }

  /*
   * EFFECTS: Returns the price of the item.
   */
  public double getPrice() {
    return price;
  }

  /*
   * Returns a string representation of the item, showing its name and price. 
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "Item Name: " + getName() + " Item Price: " + getPrice();
  }

  /* EFFECTS: Returns true if the rep invariant holds for this
   * object; otherwise returns false
   */
  public boolean repOK() {
    if (price < 0) {
      return false;
    } else if (name == null || name.isEmpty()) {
      return false;
    }
    return true;
  }

}