/*
 * Capestra Order Entry System
 *
 * CapestraApp.java - User interface (the front end)
 * Modified by: Isaac Martinez
 *
 */
package CapestraApp;

// Library Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class CapestraApp extends Application {

  // Employee informaiton of the currently logged-in employee
  private LoginUI loginUI;

  // Application scenes
  private Scene loginSCN;

  @Override
  public void start(Stage primaryStage) {
    // Set the title of the application. You need to update this title
    primaryStage.setTitle("Capestra Order Entry - Isaac Martinez");

    // Login Scene Setup
    loginUI = new LoginUI();
    loginSCN = loginUI.createScene(createMenuBar(primaryStage));
    
    // Go to Login Scene to begin the app
    primaryStage.setScene(loginSCN);
    primaryStage.show();
  } // end start()

  // Set up the menus
  // This code was provide to me in the ITEC5020 Samples
  // Small refactoring from Isaac Martinez
  public MenuBar createMenuBar(Stage primaryStage) {
    Menu menu1 = new Menu("Action");
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(menu1);
    MenuItem menu11 = new MenuItem("Login");
    MenuItem menu12 = new MenuItem("Add Customer");
    MenuItem menu13 = new MenuItem("Place Order");

    Menu menu2 = new Menu("Report");
    menuBar.getMenus().add(menu2);
    MenuItem menu21 = new MenuItem("Employee Info");
    MenuItem menu22 = new MenuItem("Customer Info");
    MenuItem menu23 = new MenuItem("Order Info");
    MenuItem menu24 = new MenuItem("Product Info");

    menu1.getItems().addAll(menu11, menu12, menu13);
    menu2.getItems().addAll(menu21, menu22, menu23, menu24);

    // Associate menu items with corresponding scenes
    menu11.setOnAction(e -> navigateToScene(primaryStage, loginUI));
    menu12.setOnAction(e -> navigateToScene(primaryStage, new CustomerDataEntryUI()));
    menu13.setOnAction(e -> navigateToScene(primaryStage, new OrderDataEntryUI()));
    menu21.setOnAction(e -> navigateToScene(primaryStage, new EmployeeReportUI()));
    menu22.setOnAction(e -> navigateToScene(primaryStage, new CustomerReportUI()));
    menu23.setOnAction(e -> navigateToScene(primaryStage, new OrderReportUI()));
    menu24.setOnAction(e -> navigateToScene(primaryStage, new ProductReportUI()));

    return menuBar;
  } // end createMenuBar()

  // Method to stop navigation to other scenes if user is not logged in.
  public void navigateToScene(Stage primaryStage, BaseUI baseUI) {
    if (this.loginUI.employee != null) {
      primaryStage.setScene(baseUI.createScene(createMenuBar(primaryStage)));
    } else {
      Alert alert = new Alert(AlertType.ERROR, "Please Login.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  // The starting method for every Java/JavaFX program
  // (By conventions, main should be the last method in the class)
  public static void main(String[] args) {
    launch(args);
  } // end main()

} // end class CapestraApp
