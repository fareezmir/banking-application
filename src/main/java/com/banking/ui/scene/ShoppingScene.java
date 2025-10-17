package com.banking.ui.scene;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.banking.model.domain.Customer;
import com.banking.model.domain.Item;
import com.banking.ui.SceneManager;
import com.banking.util.Dimensions;

public class ShoppingScene implements Dimensions {
  private final Stage stage;
  private final ArrayList<Item> listOfItems;
  private final Customer customer;

  public ShoppingScene(Stage stage, Customer customer) {
    this.stage = stage;
    this.listOfItems = new ArrayList<>();
    this.customer = customer;
  }

  public Scene createScene() {
    stage.setResizable(false);
    Pane shopping = new Pane();
    try {
      InputStream main = getClass().getClassLoader().getResourceAsStream("assets/images/overview.gif");
      Image img = new Image(main);
      ImageView imgView = new ImageView(img);
      imgView.setFitWidth(WIDTH);
      imgView.setFitHeight(HEIGHT);

      Text errorText = configureErrorText();
      Text successText = configureSuccessText();
      Text balanceText = configureBalanceText();
      Text levelText = configureLevelText();
      Button exit = configureExitButton();

      listOfItems.add(new Item("Headphones", 50));
      listOfItems.add(new Item("Phone", 600));
      listOfItems.add(new Item("Water Bottle", 17));

      HBox itemsList = new HBox(300);
      itemsList.setAlignment(Pos.CENTER);
      itemsList.setTranslateY(640);
      itemsList.setTranslateX(170);
      for (Item item : listOfItems) {
        VBox itemRow = createItemForm(item, errorText, successText, balanceText, levelText);
        itemsList.getChildren().add(itemRow);
      }

      exit.setOnAction(event -> {
        SceneManager scene = new SceneManager(stage);
        scene.showCustomerScene(customer);
      });

      shopping.getChildren().addAll(imgView, itemsList, errorText, successText, balanceText, levelText, exit);
      return new Scene(shopping, WIDTH, HEIGHT);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private boolean handlePurchase(Item item, int quantity, Text balanceText, Text levelText, Text errorText, Text successText) {
    double totalCostBeforeFee = item.getPrice() * quantity;
    if (totalCostBeforeFee < 50) {
      errorText.setText("Minimum purchase must be $50.");
      successText.setVisible(false);
      errorText.setVisible(true);
      return false;
    }
    double totalCost = totalCostBeforeFee + customer.calculatePurchaseFee();
    if (customer.getBalance() >= totalCost) {
      customer.withdraw(totalCost);
      balanceText.setText("$" + customer.getBalance());
      levelText.setText(customer.getLevel().toUpperCase());
      errorText.setVisible(false);
      successText.setVisible(true);
      return true;
    } else {
      successText.setVisible(false);
      errorText.setText("Insufficient Funds.");
      errorText.setVisible(true);
      return false;
    }
  }

  private Text configureErrorText() {
    Text error = new Text();
    error.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    error.setFill(Color.web("#FF151F"));
    error.setTranslateY(210);
    error.setTranslateX(65);
    return error;
  }

  private Text configureSuccessText() {
    Text success = new Text();
    success.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
    success.setFill(Color.web("#0ED145"));
    success.setTranslateY(210);
    success.setTranslateX(65);
    return success;
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

  private Text configureBalanceText() {
    Text welcomeText = new Text("$" + customer.getBalance());
    welcomeText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 50));
    welcomeText.setFill(Color.WHITE);
    welcomeText.setTranslateY(208);
    welcomeText.setTranslateX(1000);
    return welcomeText;
  }

  private Text configureLevelText() {
    Text levelText = new Text(customer.getLevel().toUpperCase());
    levelText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 15));
    levelText.setFill(Color.web("#FFD700"));
    levelText.setTranslateY(155);
    levelText.setTranslateX(1000);
    return levelText;
  }

  private VBox createItemForm(Item item, Text errorText, Text successText, Text balanceText, Text levelText) {
    VBox itemRow = new VBox(20);
    itemRow.setAlignment(Pos.CENTER);

    Label itemName = new Label(item.getName() + " - $" + item.getPrice());
    itemName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    TextField quantityField = new TextField();
    quantityField.setPromptText("Qty");
    quantityField.setPrefWidth(100);
    quantityField.setFont(Font.font("Arial", 14));
    Button itemButton = new Button("Buy");
    itemButton.setPrefSize(100, 40);
    itemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    itemButton.setOnAction(event -> {
      try {
        int quantity = Integer.parseInt(quantityField.getText());
        if (handlePurchase(item, quantity, balanceText, levelText, errorText, successText)) {
          errorText.setVisible(false);
          successText.setText("Success! " + item.getName() + " Purchased.");
          successText.setVisible(true);
        } else {
          successText.setVisible(false);
          errorText.setVisible(true);
        }
      } catch (NumberFormatException e) {
        errorText.setText("Invalid Quantity.");
        successText.setVisible(false);
        errorText.setVisible(true);
      }
    });

    itemRow.getChildren().addAll(itemName, quantityField, itemButton);
    return itemRow;
  }
}


