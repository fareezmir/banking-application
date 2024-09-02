        package bankapplication;
        import java.util.ArrayList;
        import java.io.InputStream;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;
        import javafx.scene.paint.Color;
        import javafx.geometry.Pos;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.image.ImageView;
        import javafx.scene.image.Image;
        import javafx.scene.text.Text;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;

        public class ShoppingScene implements Dimensions {

          /*
           * Overview: ShoppingScene is mutable and represents the shopping interface for customers within
           * the banking application. It allows customers to purchase items and dynamically updates their
           * balance and level based on their actions.
           * 
           * Abstraction Function: AF(c) = A scene within 'stage' for 'customer', showing a list of 'listOfItems'
           * where each item has a name and a price. The scene includes functionality for purchasing items,
           * displaying success or error messages, and updating the customer's balance and level accordingly.
           * 
           * Representation Invariant: RI(c) = true if listOfItems != null && !listOfItems.isEmpty() &&
           * for all items in listOfItems, item.price > 0 && item.name != null && !item.name.isEmpty()
           * && customer != null && stage != null, and false otherwise.
           */
          private Stage stage;
          private ArrayList < Item > listOfItems;
          private Customer customer;

          //constructor
          public ShoppingScene(Stage stage, Customer customer) {
            this.stage = stage;
            listOfItems = new ArrayList < > ();
            this.customer = customer;
          }

          /*
           * EFFECTS: Initializes and returns a new Scene with a background, and customer-specific interactions to purchase items from 
           * the shop, with buttons to purchase said items.
           */
          public Scene createScene() {
            stage.setResizable(false);
            Pane shopping = new Pane();

            //Background
            try {
              InputStream main = Files.newInputStream(Paths.get("src/images/shopping2.png"));
              Image img = new Image(main);
              ImageView imgView = new ImageView(img);
              imgView.setFitWidth(WIDTH);
              imgView.setFitHeight(HEIGHT);

              //Text
              Text errorText = configureErrorText();
              Text successText = configureSuccessText();
              Text balanceText = configureBalanceText();
              Text levelText = configureLevelText();

              //Exit button configuration
              Button exit = configureExitButton();

              //Add the items
              listOfItems.add(new Item("Headphones", 50));
              listOfItems.add(new Item("Phone", 600));
              listOfItems.add(new Item("Water Bottle", 17));

              //Item Menu
              HBox itemsList = new HBox(300);
              itemsList.setAlignment(Pos.CENTER);
              itemsList.setTranslateY(640);
              itemsList.setTranslateX(170);

              //For each item, create a vertical menu with the needed options to buy an item + logic handling
              for (Item item: listOfItems) {
                VBox itemRow = createItemForm(item, errorText, successText, balanceText, levelText);
                itemsList.getChildren().add(itemRow);
              }

              //Exit to the main customer scene
              exit.setOnAction(event -> {
                SceneHub scene = new SceneHub(stage);
                scene.showCustomerScene(customer);
              });

              shopping.getChildren().addAll(imgView, itemsList, errorText, successText, balanceText, levelText, exit);
              return new Scene(shopping, WIDTH, HEIGHT);

            } catch (Exception e) {
              e.printStackTrace();
              return null;
            }
          }

          /*
           * MODIFIES: this.customer
           * EFFECTS: Attempts to purchase the specified item in the given quantity. Updates the customer's balance
           *          and level if the purchase is successful, and returns true, and false otherwise. 
           *          Displays success or error messages accordingly.
           */
          private boolean handlePurchase(Item item, int quantity, Text balanceText, Text levelText, Text errorText, Text successText) {

            double totalCostBeforeFee = item.getPrice() * quantity;

            //Check if item purchase is less than $50
            if (totalCostBeforeFee < 50) {
              errorText.setText("Minimum purchase must be $50.");
              successText.setVisible(false);
              errorText.setVisible(true);
              return false;
            }

            //If it passes the if statement, it means the total purchase price is >= $50. Thus, add the customer fee now.
            double totalCost = totalCostBeforeFee + customer.calculatePurchaseFee();

            //If customer balance is sufficient, withdraw
            if (customer.getBalance() >= totalCost) {
              customer.withdraw(totalCost);

              // Update balance and level Text after purchase
              balanceText.setText("$" + customer.getBalance());
              levelText.setText(customer.getLevel().toUpperCase());
              errorText.setVisible(false); //Hide error text if it was previously shown
              successText.setVisible(true);
              return true;
            }

            //If not enough balance     
            else {
              successText.setVisible(false); //Hide success text if it was previously shown
              errorText.setText("Insufficient Funds.");
              errorText.setVisible(true);
              return false;
            }

          }

          /*
           * EFFECTS: Constructs and returns a text displaying an error message, styled and positioned.
           */
          private Text configureErrorText() {
            Text error = new Text();
            error.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
            error.setFill(Color.web("#FF151F"));
            error.setTranslateY(210);
            error.setTranslateX(65);
            return error;
          }

          /*
           * EFFECTS: Constructs and returns a text displaying an success message, styled and positioned.
           */
          private Text configureSuccessText() {
            Text success = new Text();
            success.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
            success.setFill(Color.web("#0ED145"));
            success.setTranslateY(210);
            success.setTranslateX(65);
            return success;
          }

          /*
           * EFFECTS: Constructs and returns an Exit button with predefined style and positioning, used for returning to 
           * the main customer scene from the shopping scene.
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
           * EFFECTS: Constructs and returns a text displaying the customer's balance, styled and positioned.
           */
          private Text configureBalanceText() {
            Text welcomeText = new Text("$" + customer.getBalance());
            welcomeText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 50));
            welcomeText.setFill(Color.WHITE);
            welcomeText.setTranslateY(208);
            welcomeText.setTranslateX(1000);
            return welcomeText;
          }

          /*
           * EFFECTS: Constructs and returns a text displaying the customer's level, styled and positioned.
           */
          private Text configureLevelText() {
            Text levelText = new Text(customer.getLevel().toUpperCase());
            levelText.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 15));
            String colorHex = "#FFD700";
            levelText.setFill(Color.web(colorHex));
            levelText.setTranslateY(155);
            levelText.setTranslateX(1000);
            return levelText;
          }

          /*
           * EFFECTS: Constructs and prepares the form to purchase an item, returning a VBox with all form elements.
           */
          private VBox createItemForm(Item item, Text errorText, Text successText, Text balanceText, Text levelText) {
            VBox itemRow = new VBox(20);
            itemRow.setAlignment(Pos.CENTER);

            // Item name and price label
            Label itemName = new Label(item.getName() + " - $" + item.getPrice());
            itemName.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            // Quantity input field
            TextField quantityField = new TextField();
            quantityField.setPromptText("Qty");
            quantityField.setPrefWidth(100);
            quantityField.setFont(Font.font("Arial", 14));

            //Item Button
            Button itemButton = new Button("Buy");
            itemButton.setPrefSize(100, 40);
            itemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            //Adds the logic for when a customer purchases an item
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

          /*
           * EFFECTS: Returns a string representation of the shopping scene, showing the number of items available for purchase for a 
           * customer, along with their respective prices. It also shows the Customer's current and balance. Implements the abstraction function.
           */
          @Override
          public String toString() {
            String itemsList = "";
            for (Item item: listOfItems) {
              itemsList += item.getName() + " :$" + item.getPrice() + "| ";
            }
            return "ShoppingScene for Customer: " + customer.getUsername() + " with items: [" + itemsList + "] on stage: " + stage.getTitle() +
              ". The scene allows for purchasing items, displays transaction outcomes, and updates balance and level as needed.\n" +
              "Current balance: $" + customer.getBalance() + "\n" + "Current level: " + customer.getLevel();
          }

          /*
           * EFFECTS: Returns true if the representation invariant holds for this object; otherwise returns false.
           */
          public boolean repOK() {
            if (listOfItems == null || listOfItems.isEmpty()) {
              return false;
            }
            for (Item item: listOfItems) {
              if (item.getPrice() <= 0 || item.getName() == null || item.getName().isEmpty()) {
                return false;
              }
            }

            if (customer == null || stage == null) {
              return false;
            }
            return true;
          }

        }