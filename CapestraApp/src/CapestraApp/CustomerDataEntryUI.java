package CapestraApp;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

public class CustomerDataEntryUI extends BaseUI {

    private HBoxAndControl firstnameHBC, lastnameHBC, addressHBC, cityHBC, stateHBC, zipHBC, phoneHBC, emailHBC;

    // Add Customer Scene Setup
    // Initial code was provide to me in the ITEC5020 Samples
    // Extended by Isaac Martinez
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
        Label statusLBL = new Label("");
        addCustBTN.setOnAction(e -> addCustomer(new Customer(0, firstnameHBC.getText(), lastnameHBC.getText(),
                addressHBC.getText(), cityHBC.getText(), stateHBC.getText(), zipHBC.getText(), phoneHBC.getText(),
                emailHBC.getText()), statusLBL));

        Button clearBTN = new Button("Clear");
        clearBTN.setOnAction(e -> {
            clearData();
        });

        VBox vbox = new VBox(15, createTitleHBox("Add Customer"),
                firstnameHBC.getHBox(), lastnameHBC.getHBox(), addressHBC.getHBox(), cityHBC.getHBox(), stateHBC.getHBox(),
                zipHBC.getHBox(),
                phoneHBC.getHBox(), emailHBC.getHBox(),
                UiFactory.createCommandHBox(addCustBTN, clearBTN),
                statusLBL);
        vbox.setPadding(UiFactory.DEFAULT_INSETS);

        VBox vboxOL = new VBox(menuBar, vbox);

        return new Scene(vboxOL, 600, 500);
    }

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
    // Initial code was provide to me in the ITEC5020 Samples
    // Extended by Isaac Martinez
    public void addCustomer(Customer customer, Label statusLBL) {
        CapestraDB myDB = new CapestraDB();
        myDB.addCustomer(customer, statusLBL);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer added", ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            clearData();
        }
    } // end addCustomer()
}
