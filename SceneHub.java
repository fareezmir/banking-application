package bankapplication;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHub {
  /*
   * Overview: SceneHub is a mutable class that acts as a controller for scene transitions within the banking application.
   * It showcases the display of various scenes based on user interactions or specific application states.
   * 
   * Abstraction Function: AF(c) = A controller for 'stage' that can switch between various scenes including manager,
   * customer, login, and shopping scenes, allowing for user navigation throughout the application.
   * 
   * Representation Invariant: RI(c) = true if stage != null.
   */

  private Stage stage;

  //constructor
  public SceneHub(Stage stage) {
    this.stage = stage;
  }

  /*
   * MODIFIES: this.stage
   * EFFECTS: Transitions the application to the manager dashboard scene. 
   *          Updates the stage's scene to ManagerScene and sets the title to "Manager Dashboard".
   */
  public void showManagerScene() {
    ManagerScene managerScene = new ManagerScene(stage);
    Scene scene = managerScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Manager Dashboard");
    stage.show();
  }

  /*
   * MODIFIES: this.stage
   * EFFECTS: Transitions the application to the customer details scene for a specific customer. 
   *          Updates the stage's scene to CustomerScene and sets the title to "Banking Details".
   */
  public void showCustomerScene(Customer customer) {
    CustomerScene customerScene = new CustomerScene(stage, customer);
    Scene scene = customerScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Banking Details");
    stage.show();
  }

  /*
   * MODIFIES: this.stage
   * EFFECTS: Transitions the application to the login menu scene. 
   *          Sets the stage's scene to LoginMenu, and sets the title to "Bank Account Application".
   */
  public void showLoginScene() {
    LoginMenu loginMenuScene = new LoginMenu(stage);
    Scene loginMenu = loginMenuScene.createScene();
    stage.setScene(loginMenu);
    stage.setTitle("Bank Account Application");
    stage.show();
  }
  /*
   * MODIFIES: this.stage
   * EFFECTS: Transitions the application to the shopping scene for a specific customer. 
   *          Updates the stage's scene to ShoppingScene and sets the title to "Shopping Page".
   */
  public void showShoppingScene(Customer customer) {
    ShoppingScene shoppingScene = new ShoppingScene(stage, customer);
    Scene scene = shoppingScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Shopping Page");
    stage.show();
  }

  /*
   * EFFECTS: Returns a string representation of the SceneHub, overviewing its role in managing scene transitions. Implements 
   * the abstraction function.
   */
  @Override
  public String toString() {
    return "SceneHub: A hub to manage scene transitions within the bank application.";
  }

  /*
   * EFFECTS: Returns true if the representation invariant holds for this object; otherwise, returns false.
   */
  public boolean repOK() {
    return this.stage != null;
  }

}