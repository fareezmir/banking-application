package bankapplication;

import java.io.InputStream;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.text.Text;

public class LoginMenu implements Dimensions {
  /*
   * Overview: LoginMenu provides the GUI for user login in the banking application.
   * It is mutable as it reacts to user input to transition between scenes based on the authentication outcome.
   * 
   * Abstraction Function: AF(c) = A login screen represented by a stage, allowing users 
   *                       (either managers or customers) to authenticate and navigate to the respective parts of 
   *                       the banking application based on their roles, where each user is identified by a username and password.
.  *
   * 
   * Representation Invariant: RI(c) = true if stage != null, and false otherwise.
   */

  private Stage stage;

  //constructor
  public LoginMenu(Stage stage) {
    this.stage = stage;
  }

  /*
   * EFFECTS: Creates and returns the login scene with a background, input fields for username and password,
   * and a login button, with transitions to either the manager or customer scene depending on authentication.
   */
  public Scene createScene() {

    try {
      //Layout
      stage.setResizable(false);
      Pane layout = new Pane();
      layout.setPrefSize(WIDTH, HEIGHT);

      //Background
      InputStream main = Files.newInputStream(Paths.get("src/images/BMO2.png"));
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      //Vbox for options
      VBox options = new VBox(15);
      options.setTranslateX(395);
      options.setTranslateY(258);

      Button loginButton = new Button("Login");

      //Modify Labels
      Label userNameLabel = new Label("Username:");
      userNameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
      userNameLabel.setTextFill(Color.WHITE);

      TextField userNameField = new TextField();
      userNameField.setStyle("-fx-font-size: 30px;");
      userNameField.setPrefHeight(40);
      userNameField.setPrefWidth(500);
      userNameField.setPromptText("Enter Username");

      //Password
      Label passwordLabel = new Label("Password:");
      passwordLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
      passwordLabel.setTextFill(Color.WHITE);

      PasswordField passwordField = new PasswordField();
      passwordField.setStyle("-fx-font-size: 30px;");
      passwordField.setPrefHeight(40);
      passwordField.setPrefWidth(500);
      passwordField.setPromptText("Enter Password");

      //Login button
      loginButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      loginButton.setPrefWidth(100);
      loginButton.setPrefHeight(50);
      loginButton.setAlignment(Pos.CENTER);
      loginButton.setTranslateX(585);
      loginButton.setTranslateY(550);
      
      Text error = new Text();
      
      loginButton.setOnAction(event -> {
        String username = userNameField.getText();
        String password = passwordField.getText();
        String role = authenticateUser(username, password);

        switch (role) {
        case "manager":
          transitionManager();
          break;
        case "customer":
          transitionCustomer(username);
          break;
        default:
          error.setText("Invalid Username/Password.");
          error.setFill(Color.RED.brighter());
          error.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20)); 
          break;
        }
      });

      options.getChildren().addAll(userNameLabel, userNameField, passwordLabel, passwordField, error);
      layout.getChildren().addAll(imgView, options, loginButton);
      
      return new Scene(layout, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Authenticates a user based on the given username and password. If the username and password
   * match the manager's credentials, returns "manager". If the credentials match a customer stored in the
   * user files, returns "customer". Otherwise, returns "error".
   */
  private String authenticateUser(String username, String password) {
    if (username.equals("admin") && password.equals("admin")) {
      return "manager";
    } else {
      File userFile = new File("src/userfile/" + username + ".txt");
      if (userFile.exists()) {
        try {
          BufferedReader reader = new BufferedReader(new FileReader(userFile));
          String role = reader.readLine();
          String storedPassword = reader.readLine();
          if (storedPassword.equals(password)) {
            return role; // Returns "customer"
          }
        } catch (IOException e) {
          System.err.println(e);
        }
      }
    }
    return "error";
  }

  /*
   * EFFECTS: Initiates the transition to the manager scene, updating the stage with the manager scene.
   */
  private void transitionManager() {
    SceneHub manager = new SceneHub(stage);
    manager.showManagerScene();
  }

  /*
   * EFFECTS: Initiates the transition to the customer scene, updating the stage with the customer scene.
   */
  private void transitionCustomer(String username) {
    try {
      File userFile = new File("src/userfile/" + username + ".txt");
      Customer customer = new Customer(userFile);
      SceneHub customerHub = new SceneHub(stage);
      customerHub.showCustomerScene(customer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * EFFECTS: Returns a String represention of the overview on the LoginMenu Scene.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "CustomerScene for LoginMenu on stage: " + stage.getTitle() +
      ", providing interaction options to input a username and password to login to the bank application.";
  }

  /* EFFECTS: Returns true if the rep invariant holds for this
   * object; otherwise returns false
   */
  public boolean repOK() {
    return stage != null;
  }
}