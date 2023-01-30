 
package CapestraApp;

import javafx.scene.control.Label;

/**
 *
 * @author Ikemtz * 
 * Simple base class to ensure commonality across all *DataEntryUI classes
 */
public abstract class BaseDataEntryUI extends BaseUI {
    //This label will provide the DB feedback mechanism for all data entry screens
        public Label statusLBL = new Label("");
}
