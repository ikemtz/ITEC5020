package CapestraApp;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ikemtz
 *         The purpose of this class is to serve as a tuple storing references
 *         to an input control and an HBox
 *         Also has additional methods to simplify access to underlying UI
 *         controls
 */
public class HBoxAndControl {
    private final HBox hb;
    private final Control ctrl;
    private final ControlTypes controlType;

    public HBoxAndControl(HBox hb, Control ctrl) {
        this.hb = hb;
        this.ctrl = ctrl;
        switch (ctrl.getClass().getSimpleName()) {
            case "TextField":
                controlType = ControlTypes.TextField;
                break;
            case "ComboBox":
                controlType = ControlTypes.ComboBox;
                break;
            default:
                controlType = ControlTypes.PasswordField;
                break;
        }

    }

    public HBox getHBox() {
        return hb;
    }

    public Control getCtrl() {
        return ctrl;
    }

    public TextField getTextField() {
        return (TextField) ctrl;
    }

    public <TENTITY> ComboBox<TENTITY> getComboBox() {
        return (ComboBox<TENTITY>) ctrl;
    }

    // Returns the text of a text box
    public String getText() {
        return getTextField().getText().trim();
    }

    // Sets the text of a text box
    public void setText(String value) {
        getTextField().setText(value);
    }

    public void setReadonly(boolean value) {

        if (controlType == ControlTypes.TextField) {
            TextField tf = this.getTextField();
            tf.setEditable(!value);
        }
        ctrl.setStyle("-fx-background-color: lightgray");
    }

    // Returns the value of a combo box
    public <TENTITY> TENTITY getSelectedValue() {
        return (TENTITY) getComboBox().getValue();
    }

    public void clearValue() {
        getComboBox().valueProperty().setValue(null);
    }
}
