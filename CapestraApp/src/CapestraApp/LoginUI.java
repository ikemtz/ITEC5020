package CapestraApp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

public class LoginUI extends BaseUI {

    public Employee employee;

    // Login Scene Setup
    // Initial code was provide to me in the ITEC5020 Samples
    // Refactored by Isaac Martinez
    @Override
    public Scene createScene(MenuBar menuBar) {
        HBoxAndControl usernameHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "User Name", 210);
        HBoxAndControl passwordHBC = UiFactory.createHBoxAndControl(ControlTypes.PasswordField, "Password", 210);
        Button loginBTN = new Button("  Login  ");
        Label statusLBL = new Label("");
        loginBTN.setOnAction(e -> {
            employeeLogin(usernameHBC.getText(), passwordHBC.getText(), statusLBL);
        });

        VBox vbox = new VBox(25, createTitleHBox("Login"),
                usernameHBC.getHBox(),
                passwordHBC.getHBox(),
                UiFactory.createCommandHBox(loginBTN),
                statusLBL);
        vbox.setPadding(UiFactory.DEFAULT_INSETS);
        VBox vboxOL = new VBox(25, menuBar, vbox);
        return new Scene(vboxOL, 550, 400);
    }

    // Attempt to log in as an employee
    // Success/failure is reported in statusLBL
    // The top-level employeeId is set by this method
    // Initial code was provide to me in the ITEC5020 Samples
    // Extended by Isaac Martinez
    public void employeeLogin(String username, String password, Label statusLBL) {
        CapestraDB myDB = new CapestraDB();
        employee = myDB.validateEmployeeCredentials(username, password, statusLBL);

    } // end employeeLogin()
}
