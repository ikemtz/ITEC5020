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

    //This function clears the data
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
            if (customerAdded) {
                clearData();
            }
        }
    } // end addCustomer()

    //Ensures that all customer properties are valid
    private boolean customerIsValid(Customer customer) {
        if (customer.getFirstName().isEmpty()) {
            this.statusLBL.setText("ERROR: First Name is required.");
            return false;
        } else if (customer.getLastName().isEmpty()) {
            this.statusLBL.setText("ERROR: Last Name is required.");
            return false;
        } else if (customer.getAddress().isEmpty()) {
            this.statusLBL.setText("ERROR: Address is required.");
            return false;
        } else if (customer.getCity().isEmpty()) {
            this.statusLBL.setText("ERROR: City is required.");
            return false;
        } else if (customer.getState().isEmpty()) {
            this.statusLBL.setText("ERROR: State is required.");
            return false;
        } else if (customer.getZip().isEmpty()) {
            this.statusLBL.setText("ERROR: Zip is required.");
            return false;
        } else if (customer.getPhone().isEmpty()) {
            this.statusLBL.setText("ERROR: Phone is required.");
            return false;
        } else if (customer.getEmail().isEmpty()) {
            this.statusLBL.setText("ERROR: Email is required.");
            return false;
        }
        return true;
    }
}
