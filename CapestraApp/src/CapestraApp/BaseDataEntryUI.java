package CapestraApp;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author Ikemtz * Simple base class to ensure commonality across all
 *         *DataEntryUI classes
 */
public abstract class BaseDataEntryUI extends BaseUI {

  // This label will provide the DB feedback mechanism for all data entry screens
  public Label statusLBL = new Label("");

  public BaseDataEntryUI() {
    statusLBL.setWrapText(true);
  }

  public abstract void addRecordToDb();

  public boolean displayErrorMessagesIfAny(StringBuilder errorMessages) {
    boolean hasErrors = errorMessages.length() > 0;
    if (hasErrors) {
      this.statusLBL.setText("ERROR: " + errorMessages.toString().trim());
    } else {
      this.statusLBL.setText("");
    }
    this.updateStatusLblColor(hasErrors);
    return !hasErrors;
  }

  public void updateStatusLblColor(boolean hasErrors) {
    if (hasErrors) {
      statusLBL.setTextFill(Color.RED);
    } else {
      statusLBL.setTextFill(Color.BLACK);
    }
  }
}
