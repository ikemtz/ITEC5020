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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class CapestraApp extends Application {

  // Employee informaiton of the currently logged-in employee
  private Employee employee = null;  

  // Application scenes
  private Scene loginSCN, createCustomerSCN, createOrderSCN, employeeTableSCN, customerTableSCN, orderTableSCN,
      productTableSCN;

  // Inset values to standardize the appearance of scenes
  private final Insets DEFAULT_INSETS = new Insets(25, 5, 15, 48);

  @Override
  public void start(Stage primaryStage) {
    // Set the title of the application. You need to update this title
    primaryStage.setTitle("Capestra Order Entry - Isaac Martinez");

    // Login Scene Setup
    createLoginScene(primaryStage);

    // Add Customer Scene Setup
    createCustomerScene(primaryStage);

    // Place Order Scene Setup
    createOrderScene(primaryStage);

    // Employee Information Report Scene Setup
    createEmployeeInfoReportScene(primaryStage);

    // Customer Information Scene Setup
    createCustomerInfoReportScene(primaryStage);

    // Order Information Report Scene Setup
    createOrderInfoReportScene(primaryStage);

    // Product Information Report Scene Setup
    createProductInfoReportScene(primaryStage);

    // Go to Login Scene to begin the app
    primaryStage.setScene(loginSCN);
    primaryStage.show();
  } // end start()

  // Set up the menus
  // This code was provide to me in the ITEC5020 Samples
  public MenuBar addMenu(Stage primaryStage) {
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
    menu11.setOnAction(e -> navigateToScene(primaryStage, loginSCN));
    menu12.setOnAction(e -> navigateToScene(primaryStage, createCustomerSCN));
    menu13.setOnAction(e -> navigateToScene(primaryStage, createOrderSCN));
    menu21.setOnAction(e -> navigateToScene(primaryStage, employeeTableSCN));
    menu22.setOnAction(e -> navigateToScene(primaryStage, customerTableSCN));
    menu23.setOnAction(e -> navigateToScene(primaryStage, orderTableSCN));
    menu24.setOnAction(e -> navigateToScene(primaryStage, productTableSCN));

    return menuBar;
  } // end addMenu()

  // Method to stop navigation to other scenes if user is not logged in.
  public void navigateToScene(Stage primaryStage, Scene scene) {
    if (employee != null) {
      primaryStage.setScene(scene);
    } else {
      Alert alert = new Alert(AlertType.ERROR, "Please Login.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  /*
   * *************************************
   * Methods to set up each of the scenes
   * *************************************
   */
  // Login Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void createLoginScene(Stage primaryStage) {      
    Label titleLBL = new Label("Login");
    HBoxAndControl usernameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "User Name", 210);
    HBoxAndControl passwordHBC = UiFactory.createHBoxAndControl(ControlTypes.PasswordField, "Password", 210);
    Button loginBTN = new Button("  Login  ");
    Label statusLBL = new Label("");
    loginBTN.setOnAction(e -> {
      employeeLogin(usernameHBC.getText(), passwordHBC.getText(), statusLBL);
    });

    VBox vbox = new VBox(25, titleLBL, usernameHBC.getHBox(), passwordHBC.getHBox(), loginBTN, statusLBL);
    vbox.setPadding(DEFAULT_INSETS);

    VBox vboxOL = new VBox(25, addMenu(primaryStage), vbox);

    loginSCN = new Scene(vboxOL, 550, 400);
  } // end createLoginScene()

  // Add Customer Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void createCustomerScene(Stage primaryStage) {
    Label titleLBL = new Label("Add Customer");

    HBoxAndControl firstnameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "First Name", 150);
    HBoxAndControl lastnameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Last Name", 150);
    HBoxAndControl addressHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Address", 210);
    HBoxAndControl cityHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "City", 100);
    HBoxAndControl stateHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "State", 90);
    HBoxAndControl zipHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Zip", 75);
    HBoxAndControl phoneHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Phone", 100);
    HBoxAndControl emailHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Email", 150);

    Button addCustBTN = new Button("Add Customer");
    Label statusLBL = new Label("");
    addCustBTN.setOnAction(e -> addCustomer(new Customer(0, firstnameHBC.getText(), lastnameHBC.getText(),
        addressHBC.getText(), cityHBC.getText(), stateHBC.getText(), zipHBC.getText(), phoneHBC.getText(),
        emailHBC.getText()), statusLBL));

    VBox vbox = new VBox(15, titleLBL,
        firstnameHBC.getHBox(), lastnameHBC.getHBox(), addressHBC.getHBox(), cityHBC.getHBox(), stateHBC.getHBox(),
        zipHBC.getHBox(),
        phoneHBC.getHBox(), emailHBC.getHBox(), addCustBTN, statusLBL);
    vbox.setPadding(DEFAULT_INSETS);

    VBox vboxOL = new VBox(addMenu(primaryStage), vbox);

    createCustomerSCN = new Scene(vboxOL, 600, 500);
  } // end createAddCustomerScene()

  // Place Order Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void createOrderScene(Stage primaryStage) {
    Label titleLBL = new Label("Place Order");

    CapestraDB myDB = new CapestraDB();
    HBoxAndControl customerHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Customer", 150,
        myDB.getCustomerList(false));
    HBoxAndControl productHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Product", 150,
        myDB.getProductList(false));
    HBoxAndControl quantityHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Quantity", 50);

    HBoxAndControl unitPriceHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Unit Price", 100);
    unitPriceHBC.setReadonly(true);

    HBoxAndControl totalHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Total", 125);
    totalHBC.setReadonly(true);

    productHBC.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
      Product selectedProduct = (Product) newValue;
      unitPriceHBC.setText("$" + selectedProduct.getPrice());
    });
    quantityHBC.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
      // The following few lines of code will restrict the quantity textbox to only
      // accept numbers
      if (!newValue.matches("\\d*")) {
        quantityHBC.setText(newValue = newValue.replaceAll("[^\\d]", ""));
      }

      // The following lines of code will update the total based on "newValue"
      int newInteger = Integer.parseInt(newValue);
      float unitPrice = ((Product) productHBC.getSelectedValue()).getPrice();
      totalHBC.setText("$" + (newInteger * unitPrice));
    });
    Button placeOrderBTN = new Button("Place Order");
    Label statusLBL = new Label("");

    VBox vbox = new VBox(15, titleLBL,
        customerHBC.getHBox(), productHBC.getHBox(), quantityHBC.getHBox(), unitPriceHBC.getHBox(), totalHBC.getHBox(),
        placeOrderBTN, statusLBL);
    vbox.setPadding(DEFAULT_INSETS);
    VBox vboxOL = new VBox(addMenu(primaryStage), vbox);
    createOrderSCN = new Scene(vboxOL, 600, 500);
  } // end createPlaceOrderScene()

  // Employee Information Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  @SuppressWarnings({ "unchecked" })
  public void createEmployeeInfoReportScene(Stage primaryStage) {

    Label titleLBL = new Label("Employee Information");

    // Create the table
    TableView<Employee> employeeTable;

    // Load the data into the table and add in each of the columns
    employeeTable = new TableView<>();
    employeeTable.setItems(getEmployeeList());
    employeeTable.getColumns().addAll(
      UiFactory.createTableColumn("Id", 100),
      UiFactory.createTableColumn("First Name", 200),
      UiFactory.createTableColumn("Last Name", 200),
      UiFactory.createTableColumn("Email", 300));

    VBox vbox = new VBox(20);
    vbox.getChildren().addAll(addMenu(primaryStage), titleLBL, employeeTable);
    employeeTableSCN = new Scene(vbox, 800, 500);
  } // end createEmployeeInfoReportScene()

  // Create a list of employees to be loaded into the table
  // Initial code was provide to me in the ITEC5020 Samples
  public ObservableList<Employee> getEmployeeList() {
    ObservableList<Employee> employees;
    CapestraDB myDB = new CapestraDB();
    employees = myDB.getEmployeeList(true);
    return employees;
  } // end makeEmployeeList()

  // Customer Information Report Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void createCustomerInfoReportScene(Stage primaryStage) {
    Label customerLbl = new Label("Customer Information");

    TableColumn<Customer, Integer> idCol = UiFactory.createTableColumn("Id", 25);
    idCol.setStyle("-fx-alignment: CENTER;");

    // Create a TableView
    TableView<Customer> customerTable = new TableView<>();
    customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    customerTable.setItems(getCustomerList());
    customerTable.getColumns().addAll(idCol, 
      UiFactory.createTableColumn("First Name", 80), 
      UiFactory.createTableColumn("Last Name", 80),
      UiFactory.createTableColumn("Address", 150),
      UiFactory.createTableColumn("City", 90),
      UiFactory.createTableColumn("Zip", 75),
      UiFactory.createTableColumn("Phone", 75),
      UiFactory.createTableColumn("Email", 120));

    VBox vbox = new VBox(20);
    vbox.getChildren().addAll(addMenu(primaryStage), customerLbl, customerTable);
    customerTableSCN = new Scene(vbox, 800, 500);
  } // end createCustomerInfoReportScene()

  // Order Information Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples 
  public void createOrderInfoReportScene(Stage primaryStage) {
    Label titleLBL = new Label("Order Information");
    Button exampleBTN = new Button("Example Button");
    exampleBTN.setOnAction(e -> primaryStage.setScene(orderTableSCN));

    VBox vbox = new VBox(20);
    vbox.getChildren().addAll(addMenu(primaryStage), titleLBL, exampleBTN);
    orderTableSCN = new Scene(vbox, 800, 500);
  } // end createOrderInfoReportScene()

  // Product Information Report Scene Setup
  // Initial code was provide to me in the ITEC5020 Samples 
  public void createProductInfoReportScene(Stage primaryStage) {
    Label titleLBL = new Label("Product Information");
    Button exampleBTN = new Button("Example Button");
    exampleBTN.setOnAction(e -> primaryStage.setScene(productTableSCN));

    VBox vbox = new VBox(20);
    vbox.getChildren().addAll(addMenu(primaryStage), titleLBL, exampleBTN);
    productTableSCN = new Scene(vbox, 800, 500);
  } // end createProductInfoReportScene()

  /*
   * ********************************************
   * Additional Functionality ("helper methods")
   * ********************************************
   */
  // Attempt to log in as an employee
  // Success/failure is reported in statusLBL
  // The top-level employeeId is set by this method
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void employeeLogin(String username, String password, Label statusLBL) {
    CapestraDB myDB = new CapestraDB();
    employee = myDB.validateEmployeeCredentials(username, password, statusLBL);

  } // end employeeLogin()

  // Creates a list to load into the table
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public ObservableList<Customer> getCustomerList() {
    ObservableList<Customer> customers;
    CapestraDB myDB = new CapestraDB();
    customers = myDB.getCustomerList(true);
    return customers;
  } // end getCustomerList()

  // Add a (new) customer to the DB
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public void addCustomer(Customer customer, Label statusLBL) {
    CapestraDB myDB = new CapestraDB();
    myDB.addCustomer(customer, statusLBL);
    Alert alert = new Alert(AlertType.INFORMATION, "Customer added", ButtonType.OK);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.OK) {
      // clear the form
    }
  } // end addCustomer()

  // The starting method for every Java/JavaFX program
  // (By conventions, main should be the last method in the class)
  public static void main(String[] args) {
    launch(args);
  } // end main()

} // end class CapestraApp
