package com.banking;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.banking.ui.scene.LoginScene;

public class BankingApplication extends Application {

  @Override
  public void start(Stage primaryStage) {
    LoginScene loginScene = new LoginScene(primaryStage);
    Scene scene = loginScene.createScene();
    primaryStage.setScene(scene);
    primaryStage.setTitle("Bank Account Application");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}


