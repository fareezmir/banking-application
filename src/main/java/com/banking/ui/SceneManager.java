package com.banking.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import com.banking.model.domain.Customer;
import com.banking.ui.scene.CustomerScene;
import com.banking.ui.scene.LoginScene;
import com.banking.ui.scene.ManagerScene;
import com.banking.ui.scene.ShoppingScene;

public class SceneManager {
  private final Stage stage;

  public SceneManager(Stage stage) {
    this.stage = stage;
  }

  public void showManagerScene() {
    ManagerScene managerScene = new ManagerScene(stage);
    Scene scene = managerScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Manager Dashboard");
    stage.show();
  }

  public void showCustomerScene(Customer customer) {
    CustomerScene customerScene = new CustomerScene(stage, customer);
    Scene scene = customerScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Banking Details");
    stage.show();
  }

  public void showLoginScene() {
    LoginScene loginMenuScene = new LoginScene(stage);
    Scene loginMenu = loginMenuScene.createScene();
    stage.setScene(loginMenu);
    stage.setTitle("Bank Account Application");
    stage.show();
  }

  public void showShoppingScene(Customer customer) {
    ShoppingScene shoppingScene = new ShoppingScene(stage, customer);
    Scene scene = shoppingScene.createScene();
    stage.setScene(scene);
    stage.setTitle("Shopping Page");
    stage.show();
  }
}


