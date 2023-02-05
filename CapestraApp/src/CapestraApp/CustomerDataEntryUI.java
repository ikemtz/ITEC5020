package CapestraApp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

public class CustomerDataEntryUI extends BaseDataEntryUI {

  private HBoxAndControl firstnameHBC, lastnameHBC, addressHBC, cityHBC, stateHBC, zipHBC, phoneHBC, emailHBC;

  // Add Customer Scene Setup
  @Override
  public Scene createScene(MenuBar menuBar) {

    firstnameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "First Name", 150);
    lastnameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Last Name", 150);
    addressHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Address", 210);
    cityHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "City", 100);
    stateHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "State", 90);
    zipHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Zip", 75);
    phoneHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Phone", 100);
    emailHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Email", 150);

    Button addCustBTN = new Button("Add Customer");

    addCustBTN.setOnAction(e -> {
      addCustomerToDb();
    });

    Button clearBTN = new Button("Clear");
    clearBTN.setOnAction(e -> {
      clearData();
    });

    VBox vbox = new VBox(15,
        createTitleHBox("Add Customer"),
        firstnameHBC.getHBox(),
        lastnameHBC.getHBox(),
        addressHBC.getHBox(),
        cityHBC.getHBox(),
        stateHBC.getHBox(),
        zipHBC.getHBox(),
        phoneHBC.getHBox(),
        emailHBC.getHBox(),
        UiFactory.createCommandHBox(addCustBTN, clearBTN),
        statusLBL);
    vbox.setPadding(UiFactory.DEFAULT_INSETS);

    VBox vboxOL = new VBox(menuBar, vbox);

    return new Scene(vboxOL, 600, 500);
  }

  // This function clears the data
  public void clearData() {
    firstnameHBC.setText("");
    lastnameHBC.setText("");
    addressHBC.setText("");
    cityHBC.setText("");
    stateHBC.setText("");
    zipHBC.setText("");
    phoneHBC.setText("");
    emailHBC.setText("");
  }

  // Add a (new) customer to the DB
  public void addCustomerToDb() {
    Customer customer = new Customer(0,
        firstnameHBC.getText(),
        lastnameHBC.getText(),
        addressHBC.getText(),
        cityHBC.getText(),
        stateHBC.getText(),
        zipHBC.getText(),
        phoneHBC.getText(),
        emailHBC.getText());
    if (customerIsValid(customer)) {
      boolean customerAdded = getMyDB().addCustomer(customer, statusLBL);
      this.updateStatusLabel(!customerAdded);
      if (customerAdded) {
        clearData();
      }
    }
  } // end addCustomer()

  // Ensures that all customer properties are valid
  private boolean customerIsValid(Customer customer) {
    StringBuilder errorMessages = new StringBuilder();
    if (customer.getFirstName().isEmpty()) {
      errorMessages.append("First Name is required; ");
    }
    if (customer.getLastName().isEmpty()) {
      errorMessages.append("Last Name is required; ");
    }
    if (customer.getAddress().isEmpty()) {
      errorMessages.append("Address is required; ");
    }
    if (customer.getCity().isEmpty()) {
      errorMessages.append("City is required; ");
    }
    if (customer.getState().isEmpty()) {
      errorMessages.append("State is required; ");
    }
    if (customer.getZip().isEmpty()) {
      errorMessages.append("Zip is required; ");
    }
    if (customer.getPhone().isEmpty()) {
      errorMessages.append("Phone is required; ");
    }
    if (customer.getEmail().isEmpty()) {
      errorMessages.append("Email is required; ");
    }
    boolean hasErrors = errorMessages.length() > 0;
    if (hasErrors) {
      this.statusLBL.setText("ERROR: " + errorMessages.toString().trim());
    }
    this.updateStatusLabel(hasErrors);
    return !hasErrors;
  }
}
