package com.banking.ui.scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.banking.ui.scene.SceneManager;
import com.banking.model.domain.Customer;
import com.banking.util.Dimensions;

public class LoginScene implements Dimensions {

  private final Stage stage;

  public LoginScene(Stage stage) {
    this.stage = stage;
  }

  public Scene createScene() {
    try {
      stage.setResizable(false);
      Pane layout = new Pane();
      layout.setPrefSize(WIDTH, HEIGHT);

      Image img = new Image(getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif"));
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      VBox options = new VBox(15);
      options.setTranslateX(395);
      options.setTranslateY(258);

      Button loginButton = new Button("Login");

      Label userNameLabel = new Label("Username:");
      userNameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
      userNameLabel.setTextFill(Color.WHITE);

      TextField userNameField = new TextField();
      userNameField.setStyle("-fx-font-size: 30px;");
      userNameField.setPrefHeight(40);
      userNameField.setPrefWidth(500);
      userNameField.setPromptText("Enter Username");

      Label passwordLabel = new Label("Password:");
      passwordLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
      passwordLabel.setTextFill(Color.WHITE);

      PasswordField passwordField = new PasswordField();
      passwordField.setStyle("-fx-font-size: 30px;");
      passwordField.setPrefHeight(40);
      passwordField.setPrefWidth(500);
      passwordField.setPromptText("Enter Password");

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

  private String authenticateUser(String username, String password) {
    if (username.equals("admin") && password.equals("admin")) {
      return "manager";
    } else {
      File userFile = new File("data/userfile/" + username + ".txt");
      if (userFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
          String role = reader.readLine();
          String storedPassword = reader.readLine();
          if (storedPassword.equals(password)) {
            return role; // "customer"
          }
        } catch (IOException e) {
          System.err.println(e);
        }
      }
    }
    return "error";
  }

  private void transitionManager() {
    SceneManager manager = new SceneManager(stage);
    manager.showManagerScene();
  }

  private void transitionCustomer(String username) {
    try {
      File userFile = new File("data/userfile/" + username + ".txt");
      Customer customer = new Customer(userFile);
      SceneManager customerHub = new SceneManager(stage);
      customerHub.showCustomerScene(customer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


