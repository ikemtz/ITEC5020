package CapestraApp;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ikemtz
 *         The purpose of this class is to simplify the creation of UI controls
 *         Eliminating dozens of lines of code from the CapestraApp.java class
 */
public final class UiFactory {

  /*
   * Factory method to create Table columns
   */
  public static <ENTITY_TYPE, VALUE_TYPE> TableColumn<ENTITY_TYPE, VALUE_TYPE> createTableColumn(String propertyName,
      int minWidth) {
    TableColumn<ENTITY_TYPE, VALUE_TYPE> column = new TableColumn<>(propertyName);
    column.setMinWidth(minWidth);
    column.setCellValueFactory(new PropertyValueFactory<>(convertToCamelCase(propertyName)));
    return column;
  }

  /*
   * This is overloading the next method, making the data argument optional.
   */
  public static HBoxAndControl createHBoxAndControl(ControlTypes controlType, String label, int width) {
    return createHBoxAndControl(controlType, label, width, null);
  }

  // This "factory method" creates and lays out a Label-Control pair
  // controlType = TextField or ComboBox
  // label = text for the label
  // width = width of the control
  // Using this method saves duplicating lots of code for each label/HBox/(textbox
  // or comboBox)
  public static <ENTITY> HBoxAndControl createHBoxAndControl(ControlTypes controlType, String label, int width,
      ObservableList<ENTITY> data) {
    Label lbl = new Label(label + ":");
    lbl.setPrefWidth(100);

    HBox hbox = new HBox(5, lbl);
    hbox.setAlignment(Pos.BASELINE_LEFT);
    switch (controlType) {
      case TextField: {
        TextField tf = new TextField();
        tf.setPromptText("Enter " + label);
        tf.setPrefWidth(width);
        hbox.getChildren().add(tf);
        return new HBoxAndControl(hbox, tf);
      }
      case PasswordField: {
        PasswordField tf = new PasswordField();
        tf.setPromptText("Enter " + label);
        tf.setPrefWidth(width);
        hbox.getChildren().add(tf);
        return new HBoxAndControl(hbox, tf);
      }
      default:
        ComboBox<ENTITY> cb = new ComboBox(data);
        cb.setPromptText("Select a value");
        hbox.getChildren().add(cb);
        return new HBoxAndControl(hbox, cb);
    }
  }

  /*
   * Converts a title case string (ie: First Name, Product Description) to camel
   * case (ie: firstname, productDescription)
   */
  public static String convertToCamelCase(String value) {

    if (value == null || value.isEmpty())
      return "";

    if (value.length() == 1)
      return value.toUpperCase();

    // split the string by space
    String[] parts = value.split(" ");

    StringBuilder sb = new StringBuilder(value.length());

    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      if (i == 0) {
        sb.append(part.substring(0, 1).toLowerCase())
            .append(part.substring(1));
      } else {
        sb.append(part);
      }
    }
    return sb.toString().trim();
  }
}