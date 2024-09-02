package bankapplication;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class BankApplication extends Application {

  /*
   * Overview: BankApplication is immutable, providing the entry point for the JavaFX application.
   * It initializes and shows the primary stage of the application with the login menu.
   *
   * Abstraction Function: AF(c) = a JavaFX application that initializes and displays the primary stage with a login menu.
   * Representation Invariant: RI(c) = because the class is immutable and does not hold any varying properties.
   */

 
  /*
   * EFFECTS: Initializes the main application window and sets the scene to the login menu.
   */
  @Override
  public void start(Stage primaryStage) {
    LoginMenu loginMenu = new LoginMenu(primaryStage);
    Scene scene = loginMenu.createScene();
    primaryStage.setScene(scene);
    primaryStage.setTitle("Bank Account Application");
    primaryStage.show();
  }

  /*
   * EFFECTS: Launches the application.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /*
   * EFFECTS: Returns a string representation of the BankApplication. Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "JavaFX application that initializes and displays the primary stage with a login menu.";
  }

  /*
   * EFFECTS: Since BankApplication is immutable and contains no fields, the rep invariant is true by default.
   */
  public boolean repOK() {
    return true;
  }
}