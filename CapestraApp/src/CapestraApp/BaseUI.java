package CapestraApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ikemtz Simple interface to ensure commonality across all *UI classes
 */
public abstract class BaseUI {

    abstract Scene createScene(MenuBar menubar);

    public HBox createTitleHBox(String titleText) {
        Label resultLBL = new Label(titleText); 
        resultLBL.setStyle("-fx-font: 24 arial;");
        HBox labelHB = new HBox( resultLBL);
        labelHB.alignmentProperty().setValue(Pos.CENTER);
        return labelHB;
    }
}
