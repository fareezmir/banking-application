package com.banking.ui.scene;

import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.banking.model.domain.Manager;
import com.banking.ui.SceneManager;
import com.banking.util.Dimensions;

public class ManagerScene implements Dimensions {
  private final Stage stage;
  private final Manager manager;

  public ManagerScene(Stage stage) {
    this.stage = stage;
    this.manager = new Manager();
  }

  public Scene createScene() {
    stage.setResizable(false);
    StackPane pane = new StackPane();
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      Button logout = configureLogoutButton();
      Button addButton = configureAddCustomerButton();
      Button removeButton = configureDeleteCustomerButton();
      Text welcomeText = configureWelcomeText();

      logout.setOnAction(event -> {
        SceneManager scene = new SceneManager(stage);
        scene.showLoginScene();
      });

      addButton.setOnAction(event -> {
        stage.setScene(createCustomer());
        stage.show();
      });

      removeButton.setOnAction(event -> {
        stage.setScene(removeCustomer());
        stage.show();
      });

      pane.getChildren().addAll(imgView, addButton, removeButton, welcomeText, logout);
      return new Scene(pane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Scene createCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);
      VBox form = createForm();
      form.setTranslateX(420);
      form.setTranslateY(300);
      Button exit = configureExitButton();
      Button submit = new Button("Add");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(100);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(620);
      submit.setTranslateY(580);
      exit.setOnAction(event -> stage.setScene(createScene()));
      Text error = configureErrorText();
      Text success = configureSuccessText();
      submit.setOnAction(event -> {
        TextField usernameField = (TextField) form.getChildren().get(1);
        TextField passwordField = (TextField) form.getChildren().get(3);
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.matches(".*\\s+.*") || password.matches(".*\\s+.*") || username.isEmpty() || password.isEmpty() || username.equals("admin")) {
          success.setVisible(false);
          error.setText("Invalid Username or Password.");
          error.setVisible(true);
        } else {
          if (manager.addCustomer(username, password)) {
            error.setVisible(false);
            success.setText("Success! Customer has been Added.");
            success.setVisible(true);
          } else {
            success.setVisible(false);
            error.setText("Customer already Exists.");
            error.setVisible(true);
          }
        }
      });
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);
      return new Scene(layoutPane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Scene removeCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);
      VBox form = deleteForm();
      form.setTranslateX(420);
      form.setTranslateY(300);
      Button exit = configureExitButton();
      Button submit = new Button("Remove");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(100);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(620);
      submit.setTranslateY(440);
      exit.setOnAction(event -> stage.setScene(createScene()));
      Text error = configureErrorText();
      Text success = configureSuccessText();
      submit.setOnAction(event -> {
        TextField usernameField = (TextField) form.getChildren().get(1);
        String username = usernameField.getText();
        if (username.matches(".*\\s+.*") || username.isEmpty()) {
          success.setVisible(false);
          error.setText("Invalid Username.");
          error.setVisible(true);
        } else {
          if (manager.removeCustomer(username)) {
            error.setVisible(false);
            success.setText("Success! Customer has been Removed.");
            success.setVisible(true);
          } else {
            success.setVisible(false);
            error.setText("Username not Found.");
            error.setVisible(true);
          }
        }
      });
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);
      return new Scene(layoutPane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Button configureAddCustomerButton() {
    Button addCustomerButton = new Button("Add Customer");
    addCustomerButton.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 50));
    addCustomerButton.setStyle("-fx-background-color: #F5F5F5;");
    addCustomerButton.setTextFill(Color.GRAY);
    addCustomerButton.setTranslateY(-83);
    addCustomerButton.setTranslateX(-95);
    addCustomerButton.setPrefWidth(950);
    addCustomerButton.setPrefHeight(150);
    return addCustomerButton;
  }

  private Button configureDeleteCustomerButton() {
    Button removeCustomerButton = new Button("Remove Customer");
    removeCustomerButton.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 50));
    removeCustomerButton.setStyle("-fx-background-color: #F5F5F5;");
    removeCustomerButton.setTextFill(Color.GRAY);
    removeCustomerButton.setTranslateY(185);
    removeCustomerButton.setTranslateX(-95);
    removeCustomerButton.setPrefWidth(950);
    removeCustomerButton.setPrefHeight(150);
    return removeCustomerButton;
  }

  private Button configureLogoutButton() {
    Button logout = new Button("Logout");
    logout.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
    logout.setTextFill(Color.WHITE);
    logout.setPrefWidth(125);
    logout.setPrefHeight(50);
    logout.setTranslateY(-360);
    logout.setTranslateX(457);
    logout.setStyle("-fx-background-color: transparent;");
    return logout;
  }

  private Text configureWelcomeText() {
    Text welcomeText = new Text("Welcome, Admin");
    welcomeText.setFont(Font.font("Helvetica", 40));
    welcomeText.setFill(Color.web("#102736"));
    welcomeText.setTranslateY(-220);
    welcomeText.setTranslateX(-300);
    return welcomeText;
  }

  private Text configureErrorText() {
    Text error = new Text();
    error.setFont(Font.font("Helvetica", 40));
    error.setFill(Color.web("#FF151F"));
    error.setTranslateY(220);
    error.setTranslateX(50);
    return error;
  }

  private Text configureSuccessText() {
    Text success = new Text();
    success.setFont(Font.font("Helvetica", 40));
    success.setFill(Color.web("#0ED145"));
    success.setTranslateY(220);
    success.setTranslateX(50);
    return success;
  }

  private TextField configureUsernameField() {
    TextField usernameField = new TextField();
    usernameField.setPromptText("Enter Customer Username");
    usernameField.setPrefHeight(50);
    usernameField.setPrefWidth(500);
    usernameField.setStyle("-fx-font-size: 30px;");
    return usernameField;
  }

  private TextField configurePasswordField() {
    TextField passwordField = new TextField();
    passwordField.setPromptText("Enter Customer Password");
    passwordField.setPrefHeight(50);
    passwordField.setPrefWidth(500);
    passwordField.setStyle("-fx-font-size: 30px;");
    return passwordField;
  }

  private Button configureExitButton() {
    Button exit = new Button("Exit");
    exit.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
    exit.setTextFill(Color.WHITE);
    exit.setPrefWidth(125);
    exit.setPrefHeight(50);
    exit.setLayoutX(1069);
    exit.setLayoutY(15);
    exit.setStyle("-fx-background-color: transparent;");
    return exit;
  }

  private VBox createForm() {
    Label usernameLabel = new Label("Enter Customer Username");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);
    TextField usernameField = configureUsernameField();
    Label passwordLabel = new Label("Enter Customer Password");
    passwordLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    passwordLabel.setTextFill(Color.BLACK);
    TextField passwordField = configurePasswordField();
    VBox form = new VBox(20);
    form.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
    form.setAlignment(Pos.CENTER);
    return form;
  }

  private VBox deleteForm() {
    Label usernameLabel = new Label("Enter Customer Username");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);
    TextField usernameField = configureUsernameField();
    VBox form = new VBox(15);
    form.getChildren().addAll(usernameLabel, usernameField);
    form.setAlignment(Pos.CENTER);
    return form;
  }
}


