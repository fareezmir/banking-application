package bankapplication;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public class ManagerScene implements Dimensions {

  /*
   * Overview: The ManagerScene class is mutable and responsible for creating and managing the GUI scene
   * for manager interactions within the banking application. It allows the manager to add or remove customers.
   * 
   * Abstraction Function: AF(c) = A GUI scene where a manager can interact with their admin permissions
   * represented by a stage 'stage' and associated with the 'manager'.
   * This GUI provides visual elements to display manager administration options.
   * 
   * Representation Invariant: RI(c) = True if manager != null && stage != null and false otherwise
   */

  private Stage stage;
  private Manager manager;

  //constructor
  public ManagerScene(Stage stage) {
    this.stage = stage;
    this.manager = new Manager();
  }

  /*
   * EFFECTS: Initializes and returns a new Scene with a background, and manager-specific interactions, 
   * with buttons to transition between scenes.
   */
  public Scene createScene() {

    stage.setResizable(false);
    StackPane pane = new StackPane();
    try {

      //Background
      InputStream main = Files.newInputStream(Paths.get("src/images/manager.png"));
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      //Elements
      Button logout = configureLogoutButton();
      Button addButton = configureAddCustomerButton();
      Button removeButton = configureDeleteCustomerButton();
      Text welcomeText = configureWelcomeText();

      //When the user presses logout, go to login screen
      logout.setOnAction(event -> {
        SceneHub scene = new SceneHub(stage);
        scene.showLoginScene();
      });

      //Show add customer scene when pressed
      addButton.setOnAction(event -> {
        stage.setScene(createCustomer());
        stage.show();
      });

      //Show remove customer scene when pressed
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

  /*
   * EFFECTS: Constructs and returns a scene for customer creation, displaying a form for username and password input
     to create the customer, and presents success or error feedback based on the input validity and outcome of adding a customer.
   */
  private Scene createCustomer() {
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
      VBox form = createForm();
      form.setTranslateX(420);
      form.setTranslateY(300);

      // Exit button configuration
      Button exit = configureExitButton();

      //Add button
      Button submit = new Button("Add");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(100);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(620);
      submit.setTranslateY(580);

      exit.setOnAction(event -> {
        stage.setScene(createScene()); // Return to the main scene
      });

      Text error = configureErrorText();
      Text success = configureSuccessText();

      submit.setOnAction(event -> {
        TextField usernameField = (TextField) form.getChildren().get(1);
        TextField passwordField = (TextField) form.getChildren().get(3);
        String username = usernameField.getText();
        String password = passwordField.getText();

        // There's whitespace in username or password, if theres nothing in the fields, or if username = admin
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

      // Adding components to the layout
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);

      return new Scene(layoutPane, WIDTH, HEIGHT);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Constructs and returns a scene for customer deletion, displaying a form for username input
   * to delete the specified customer, and presents success or error feedback based on the input validity
   * and outcome of deleting a customer.
   */
  private Scene removeCustomer() {
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
      VBox form = deleteForm();
      form.setTranslateX(420);
      form.setTranslateY(300);

      // Exit button configuration
      Button exit = configureExitButton();

      //Remove button
      Button submit = new Button("Remove");
      submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
      submit.setPrefWidth(100);
      submit.setPrefHeight(50);
      submit.setAlignment(Pos.CENTER);
      submit.setTranslateX(620);
      submit.setTranslateY(440);

      exit.setOnAction(event -> {
        stage.setScene(createScene()); // Return to the main scene
      });

      Text error = configureErrorText();
      Text success = configureSuccessText();

      submit.setOnAction(event -> {
        TextField usernameField = (TextField) form.getChildren().get(1);
        String username = usernameField.getText();

        //If There's whitespace in username or password or its left blank, error
        if (username.matches(".*\\s+.*") || username.isEmpty()) {
          success.setVisible(false);
          error.setText("Invalid Username.");
          error.setVisible(true);
        } else {
          //If removing customer worked
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

      // Adding components to the layout
      layoutPane.getChildren().addAll(imgView, form, submit, exit, error, success);

      return new Scene(layoutPane, WIDTH, HEIGHT);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /*
   * EFFECTS: Constructs and returns a addCustomerButton button with predefined style and positioning.
   */
  private Button configureAddCustomerButton() {
    //Add Customer Button
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

  /*
   * EFFECTS: Constructs and returns a removeCustomerButton button, styled and positioned.
   */
  private Button configureDeleteCustomerButton() {
    //Remove Customer Button
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

  /*
   * EFFECTS: Constructs and returns a logout button, styled and positioned.
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
   * EFFECTS: Constructs and returns a welcome text with the admin username, styled and positioned.
   */
  private Text configureWelcomeText() {
    Text welcomeText = new Text("Welcome, Admin");
    welcomeText.setFont(Font.font("Helvetica", 40));
    welcomeText.setFill(Color.web("#102736"));
    welcomeText.setTranslateY(-220);
    welcomeText.setTranslateX(-300);
    return welcomeText;
  }

  /*
   * EFFECTS: Constructs and returns a text displaying an error message, styled and positioned.
   */
  private Text configureErrorText() {
    Text error = new Text();
    error.setFont(Font.font("Helvetica", 40));
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
    success.setFont(Font.font("Helvetica", 40));
    success.setFill(Color.web("#0ED145"));
    success.setTranslateY(220);
    success.setTranslateX(50);
    return success;
  }

  /*
   * EFFECTS: Constructs and returns a TextField to input the customer username with predefined style.
   */
  private TextField configureUsernameField() {
    TextField usernameField = new TextField();
    usernameField.setPromptText("Enter Customer Username");
    usernameField.setPrefHeight(50);
    usernameField.setPrefWidth(500);
    usernameField.setStyle("-fx-font-size: 30px;");
    return usernameField;
  }

  /*
   * EFFECTS: Constructs and returns a TextField to input the customer password, with predefined style.
   */
  private TextField configurePasswordField() {
    TextField passwordField = new TextField();
    passwordField.setPromptText("Enter Customer Password");
    passwordField.setPrefHeight(50);
    passwordField.setPrefWidth(500);
    passwordField.setStyle("-fx-font-size: 30px;");
    return passwordField;
  }

  /*
   * EFFECTS: Constructs and returns an Exit button with predefined style and positioning to return to the main manager scene from a sub-scene.
   */
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

  /*
   * EFFECTS: Constructs and prepares the form for the create customer operation, returning a VBox with all form elements.
   */
  private VBox createForm() {

    // Username
    Label usernameLabel = new Label("Enter Customer Username");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);

    // Username Field
    TextField usernameField = configureUsernameField();

    // Password Label
    Label passwordLabel = new Label("Enter Customer Password");
    passwordLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    passwordLabel.setTextFill(Color.BLACK);

    // Password Field
    TextField passwordField = configurePasswordField();

    //Group all the options in a VBox
    VBox form = new VBox(20);
    form.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
    form.setAlignment(Pos.CENTER);

    return form;
  }

  /*
   * EFFECTS: Constructs and prepares the form for the remove customer operation, returning a VBox with all form elements.
   */
  private VBox deleteForm() {

    // Username
    Label usernameLabel = new Label("Enter Customer Username");
    usernameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    usernameLabel.setTextFill(Color.BLACK);

    // Username Field
    TextField usernameField = configureUsernameField();

    //Group all the options in a VBox
    VBox form = new VBox(15);
    form.getChildren().addAll(usernameLabel, usernameField);
    form.setAlignment(Pos.CENTER);

    return form;
  }

  /*
   * EFFECTS: Returns a String represention of the overview on the Customer Scene.
   * Implements the abstraction function.
   */
  @Override
  public String toString() {
    return "ManagerScene for admin on stage " + stage.getTitle() + "; provides interaction options to add and delete customers.";
  }

  /* EFFECTS: Returns true if the rep invariant holds for this
   * object; otherwise returns false
   */
  public boolean repOK() {
    if (manager == null || stage == null) {
      return false;
    }
    return true;
  }

}