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

import com.banking.ui.SceneManager;
import com.banking.util.Dimensions;
import com.banking.model.domain.Customer;

public class CustomerScene implements Dimensions {
  private final Stage stage;
  private final Customer customer;

  public CustomerScene(Stage stage, Customer customer) {
    this.stage = stage;
    this.customer = customer;
  }

  public Scene createScene() {
    stage.setResizable(false);
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/customer_tiers.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      Button logout = configureLogoutButton();
      Button shop = configureShopButton();
      Button withdrawButton = configureWithdrawButton();
      Button depositButton = configureDepositButton();
      Text welcomeText = configureWelcomeText();
      Text levelText = configureLevelText();
      Text balanceText = configureBalanceText();

      StackPane pane = new StackPane();
      pane.getChildren().addAll(imgView, withdrawButton, depositButton, welcomeText, logout, shop, levelText, balanceText);

      logout.setOnAction(event -> {
        SceneManager scene = new SceneManager(stage);
        scene.showLoginScene();
      });
      withdrawButton.setOnAction(event -> {
        stage.setScene(withdrawCustomer());
        stage.show();
      });
      depositButton.setOnAction(event -> {
        stage.setScene(depositCustomer());
        stage.show();
      });
      shop.setOnAction(event -> transitionShopping());
      return new Scene(pane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Scene withdrawCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);
      VBox form = form();
      form.setTranslateX(420);
      form.setTranslateY(300);
      Button exit = configureExitButton();
      exit.setLayoutX(1069);
      exit.setLayoutY(15);
      Button submit = new Button("Confirm Withdraw");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(200);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(570);
      submit.setTranslateY(440);
      exit.setOnAction(event -> stage.setScene(createScene()));
      Text error = configureErrorText();
      Text success = configureSuccessText();
      submit.setOnAction(event -> {
        TextField withdrawField = (TextField) form.getChildren().get(1);
        String input = withdrawField.getText();
        if (input.matches("\\d+(\\.\\d{1,2})?")) {
          double amount = Double.parseDouble(input);
          if (customer.withdraw(amount)) {
            error.setVisible(false);
            success.setText("Sucess! Amount has been Withdrawn.");
            success.setVisible(true);
          } else {
            success.setVisible(false);
            error.setText("Invalid Amount.");
            error.setVisible(true);
          }
        } else {
          success.setVisible(false);
          error.setText("Invalid Input.");
          error.setVisible(true);
        }
      });
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);
      return new Scene(layoutPane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Scene depositCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);
      VBox form = form();
      form.setTranslateX(420);
      form.setTranslateY(300);
      Button exit = configureExitButton();
      exit.setLayoutX(1069);
      exit.setLayoutY(15);
      Button submit = new Button("Confirm Deposit");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(200);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(570);
      submit.setTranslateY(440);
      exit.setOnAction(event -> stage.setScene(createScene()));
      Text error = configureErrorText();
      Text success = configureSuccessText();
      submit.setOnAction(event -> {
        TextField depositField = (TextField) form.getChildren().get(1);
        String input = depositField.getText();
        if (input.matches("\\d+(\\.\\d{1,2})?")) {
          double amount = Double.parseDouble(input);
          if (customer.deposit(amount)) {
            error.setVisible(false);
            success.setText("Sucess! Amount has been Deposited.");
            success.setVisible(true);
          } else {
            success.setVisible(false);
            error.setText("Invalid Amount.");
            error.setVisible(true);
          }
        } else {
          success.setVisible(false);
          error.setText("Invalid Input.");
          error.setVisible(true);
        }
      });
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);
      return new Scene(layoutPane, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private void transitionShopping() {
    SceneManager shopping = new SceneManager(stage);
    shopping.showShoppingScene(customer);
  }

  private Button configureWithdrawButton() {
    Button withdrawButton = new Button("Withdraw");
    withdrawButton.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 38));
    withdrawButton.setStyle("-fx-background-color: transparent;");
    withdrawButton.setTextFill(Color.WHITE);
    withdrawButton.setTranslateY(263);
    withdrawButton.setTranslateX(-218);
    withdrawButton.setPrefWidth(240);
    withdrawButton.setPrefHeight(165);
    return withdrawButton;
  }

  private Button configureDepositButton() {
    Button depositButton = new Button("Deposit");
    depositButton.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 38));
    depositButton.setStyle("-fx-background-color: transparent;");
    depositButton.setTextFill(Color.WHITE);
    depositButton.setTranslateY(263);
    depositButton.setTranslateX(206);
    depositButton.setPrefWidth(240);
    depositButton.setPrefHeight(165);
    return depositButton;
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

  private Button configureShopButton() {
    Button shop = new Button("Shop");
    shop.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
    shop.setTextFill(Color.WHITE);
    shop.setPrefWidth(125);
    shop.setPrefHeight(50);
    shop.setTranslateY(-360);
    shop.setTranslateX(295);
    shop.setStyle("-fx-background-color: transparent;");
    return shop;
  }

  private Text configureWelcomeText() {
    Text welcomeText = new Text("Welcome, " + customer.getUsername().toUpperCase());
    welcomeText.setFont(Font.font("Helvetica", 35));
    welcomeText.setFill(Color.web("#102736"));
    welcomeText.setTranslateY(-238);
    welcomeText.setTranslateX(-450);
    return welcomeText;
  }

  private Text configureLevelText() {
    Text levelText = new Text(customer.getLevel().toUpperCase());
    levelText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 30));
    String colorHex;
    switch (customer.getLevel()) {
      case "gold": colorHex = "#FFD700"; break;
      case "platinum": colorHex = "#E5E4E2"; break;
      case "silver":
      default: colorHex = "#C0C0C0"; break;
    }
    levelText.setFill(Color.web(colorHex));
    levelText.setTranslateY(-170);
    levelText.setTranslateX(-285);
    return levelText;
  }

  private Text configureBalanceText() {
    Text balanceText = new Text("$" + customer.getBalance());
    balanceText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 100));
    balanceText.setFill(Color.WHITE);
    balanceText.setTranslateY(-20);
    balanceText.setTranslateX(-30);
    return balanceText;
  }

  private TextField configureWithDepFields() {
    TextField field = new TextField();
    field.setPromptText("Enter Amount");
    field.setPrefHeight(50);
    field.setPrefWidth(500);
    field.setStyle("-fx-font-size: 30px;");
    return field;
  }

  private Button configureExitButton() {
    Button exit = new Button("Exit");
    exit.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
    exit.setTextFill(Color.WHITE);
    exit.setPrefWidth(125);
    exit.setPrefHeight(50);
    exit.setLayoutX(457);
    exit.setLayoutY(-360);
    exit.setStyle("-fx-background-color: transparent;");
    return exit;
  }

  private Text configureErrorText() {
    Text error = new Text();
    error.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    error.setFill(Color.web("#FF151F"));
    error.setTranslateY(220);
    error.setTranslateX(50);
    return error;
  }

  private Text configureSuccessText() {
    Text success = new Text();
    success.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    success.setFill(Color.web("#0ED145"));
    success.setTranslateY(220);
    success.setTranslateX(50);
    return success;
  }

  private VBox form() {
    Label usernameLabel = new Label("Enter Amount:");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);
    TextField fields = configureWithDepFields();
    VBox form = new VBox(15);
    form.getChildren().addAll(usernameLabel, fields);
    form.setAlignment(Pos.CENTER);
    return form;
  }
}


