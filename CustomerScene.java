package bankapplication;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

public class CustomerScene implements Dimensions {

  /*
   * Overview: The CustomerScene class is mutable and responsible for creating and managing the GUI scene
   * for customer interactions within the banking application. It allows customers to view their account
   * balance and level, and to navigate to deposit, withdraw, and shopping scenes.
   * 
   * Abstraction Function: AF(c) = A GUI scene where customers can interact with their account
   * represented by a stage 'stage' and associated with a specific customer 'customer'. 
   * This GUI provides visual elements to display customer account details and navigation options.
   * 
   * Representation Invariant: RI(c) = True if customer != null && stage != null and false otherwise
   */

  private Stage stage;
  private Customer customer;

  //constructor
  public CustomerScene(Stage stage, Customer customer) {
    this.stage = stage;
    this.customer = customer;
  }

  /*
   * EFFECTS: Initializes and returns a new Scene with a background, and customer-specific interactions, 
   * with buttons to transition between scenes.
   */
  public Scene createScene() {
    stage.setResizable(false);

    try {

      //Background
      InputStream main = Files.newInputStream(Paths.get("src/images/customer.png"));
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      //Elements
      Button logout = configureLogoutButton();
      Button shop = configureShopButton();
      Button withdrawButton = configureWithdrawButton();
      Button depositButton = configureDepositButton();
      Text welcomeText = configureWelcomeText();
      Text levelText = configureLevelText();
      Text balanceText = configureBalanceText();

      StackPane pane = new StackPane();
      pane.getChildren().addAll(imgView, withdrawButton, depositButton, welcomeText, logout, shop, levelText, balanceText);

      //When the user presses logout, go to login screen
      logout.setOnAction(event -> {
        SceneHub scene = new SceneHub(stage);
        scene.showLoginScene();
      });

      //Show withdraw scene when pressed
      withdrawButton.setOnAction(event -> {
        stage.setScene(withdrawCustomer());
        stage.show();
      });

      //Show deposit scene when pressed
      depositButton.setOnAction(event -> {
        stage.setScene(depositCustomer());
        stage.show();
      });

      //Show shop scene when pressed
      shop.setOnAction(event -> {
        transitionShopping();
      });

      return new Scene(pane, WIDTH, HEIGHT);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Constructs and returns a scene for customer withdrawals, displaying a form for amount input, 
   * and handles withdrawal operations, and presents success or error feedback based on the input validity and withdrawal outcome.
   */
  private Scene withdrawCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);

    try {

      // Background
      InputStream main = Files.newInputStream(Paths.get("src/images/software.png"));
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      // Creating the form
      VBox form = Form();
      form.setTranslateX(420);
      form.setTranslateY(300);

      // Exit button configuration
      Button exit = configureExitButton();
      exit.setLayoutX(1069);
      exit.setLayoutY(15);

      //Remove button
      Button submit = new Button("Confirm Withdraw");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(200);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(570);
      submit.setTranslateY(440);

      exit.setOnAction(event -> {
        stage.setScene(createScene()); // Return to the main scene
      });

      Text error = configureErrorText();
      Text success = configureSuccessText();

      submit.setOnAction(event -> {
        TextField withdrawField = (TextField) form.getChildren().get(1);
        String input = withdrawField.getText();

        //If the input is a number
        if (input.matches("\\d+(\\.\\d{1,2})?")) { //regex to ensure that the input is in a proper money format
          double amount = Double.parseDouble(input); // Convert number to a double

          // Check if withdrawal was successful
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

      // Adding components to the layout
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);

      return new Scene(layoutPane, WIDTH, HEIGHT);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Constructs and returns a scene for customer deposits, displaying a form for amount input, 
   * and handles deposal operations, and presents success or error feedback based on the input validity and deposal outcome.
   */
  private Scene depositCustomer() {
    stage.setResizable(false);
    Pane layoutPane = new Pane();
    layoutPane.setPrefSize(WIDTH, HEIGHT);

    try {
      // Background
      InputStream main = Files.newInputStream(Paths.get("src/images/software.png"));
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      // Creating the form
      VBox form = Form();
      form.setTranslateX(420);
      form.setTranslateY(300);

      // Exit button configuration
      Button exit = configureExitButton();
      exit.setLayoutX(1069);
      exit.setLayoutY(15);

      //Submit button
      Button submit = new Button("Confirm Deposit");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(200);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(570);
      submit.setTranslateY(440);

      exit.setOnAction(event -> {
        stage.setScene(createScene()); // Return to the main scene
      });

      //Error and success texts
      Text error = configureErrorText();
      Text success = configureSuccessText();

      //When submit button is pressed
      submit.setOnAction(event -> {
        TextField depositField = (TextField) form.getChildren().get(1);
        String input = depositField.getText();

        //If the input is a number
        if (input.matches("\\d+(\\.\\d{1,2})?")) { //regex to ensure that the input is in a proper money format
          double amount = Double.parseDouble(input); // Convert number to a double

          // Check if deposit was successful
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

      // Adding components to the layout
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);

      return new Scene(layoutPane, WIDTH, HEIGHT);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Initiates the transition to the shopping scene, updating the stage with the shopping scene.
   */
  private void transitionShopping() {
    SceneHub shopping = new SceneHub(stage);
    shopping.showShoppingScene(customer);
  }

  /*
   * EFFECTS: Constructs and returns a Withdraw button, styled and positioned.
   */
  private Button configureWithdrawButton() {
    //Add Customer Button
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

  /*
   * EFFECTS: Constructs and returns a Deposit button, styled and positioned.
   */
  private Button configureDepositButton() {
    //Remove Customer Button
    Button depositButton = new Button("Deposit");
    depositButton.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 38));
    depositButton.setStyle("-fx-background-color: transparent;");
    depositButton.setTextFill(Color.WHITE); // Set the text color to white
    depositButton.setTranslateY(263);
    depositButton.setTranslateX(206);
    depositButton.setPrefWidth(240);
    depositButton.setPrefHeight(165);
    return depositButton;
  }

  /*
   * EFFECTS: Constructs and returns a Logout button, styled and positioned.
   */
  private Button configureLogoutButton() {
    //Logout Button
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

  /*
   * EFFECTS: Constructs and returns a Shop button, styled and positioned.
   */
  private Button configureShopButton() {
    //Shop Button
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

  /*
   * EFFECTS: Constructs and returns a welcome text with the customer's username, styled and positioned.
   */
  private Text configureWelcomeText() {
    Text welcomeText = new Text("Welcome, " + customer.getUsername().toUpperCase());
    welcomeText.setFont(Font.font("Helvetica", 35));
    welcomeText.setFill(Color.web("#102736"));
    welcomeText.setTranslateY(-238);
    welcomeText.setTranslateX(-450);
    return welcomeText;
  }

  /*
   * EFFECTS: Constructs and returns a text displaying the customer's membership level, styled and positioned, with color coding based on the level.
   */
  private Text configureLevelText() {
    Text levelText = new Text(customer.getLevel().toUpperCase());
    levelText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 30));

    String colorHex;

    // Conditionally set color based on the level
    switch (customer.getLevel()) {
    case "gold":
      colorHex = "#FFD700"; // Gold colour
      break;
    case "platinum":
      colorHex = "#E5E4E2"; // Platinum colour
      break;
    case "silver":
    default:
      colorHex = "#C0C0C0"; // Silver colour
      break;
    }

    levelText.setFill(Color.web(colorHex));
    levelText.setTranslateY(-170);
    levelText.setTranslateX(-285);

    return levelText;
  }

  /*
   * EFFECTS: Constructs and returns a text displaying the customer's balance, styled and positioned.
   */
  private Text configureBalanceText() {
    Text balanceText = new Text("$" + customer.getBalance());
    balanceText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 100));
    balanceText.setFill(Color.WHITE);
    balanceText.setTranslateY(-20);
    balanceText.setTranslateX(-30);
    return balanceText;
  }

  /*
   * EFFECTS: Constructs and returns a TextField for amount input in withdrawal or deposit screens, with predefined style.
   */
  private TextField configureWithDepFields() {
    TextField field = new TextField();
    field.setPromptText("Enter Amount");
    field.setPrefHeight(50);
    field.setPrefWidth(500);
    field.setStyle("-fx-font-size: 30px;");
    return field;
  }

  /*
   * EFFECTS: Constructs and returns an Exit button styled and positioned, used for returning to the main customer scene from a sub-scene.
   */
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

  /*
   * EFFECTS: Constructs and returns a text displaying an error message, styled and positioned.
   */
  private Text configureErrorText() {
    Text error = new Text();
    error.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    error.setFill(Color.web("#FF151F"));
    error.setTranslateY(220);
    error.setTranslateX(50);
    return error;
  }

  /*
   * EFFECTS: Constructs and returns a text displaying an success message, styled and positioned.
   */
  private Text configureSuccessText() {
    Text success = new Text();
    success.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    success.setFill(Color.web("#0ED145"));
    success.setTranslateY(220);
    success.setTranslateX(50);
    return success;
  }

  /*
   * EFFECTS: Constructs and prepares the form for the withdrawal or deposit operation, returning a VBox with all form elements.
   */
  private VBox Form() {

    // Username
    Label usernameLabel = new Label("Enter Amount:");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);

    // Username Field
    TextField fields = configureWithDepFields();

    //Group all the options in a VBox
    VBox form = new VBox(15);
    form.getChildren().addAll(usernameLabel, fields);
    form.setAlignment(Pos.CENTER);

    return form;
  }

  /*
   * EFFECTS: Returns a String represention of the overview on the Customer Scene.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "CustomerScene for " + customer.getUsername() + " on stage " + stage.getTitle() +
      "; provides interaction options with their account, including transactions, shopping, and viewing account balance.";
  }

  /* EFFECTS: Returns true if the rep invariant holds for this
   * object; otherwise returns false
   */
  public boolean repOK() {
    if (customer == null || stage == null) {
      return false;
    }
    return true;
  }

}