package CapestraApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ikemtz Simple base class to ensure commonality across all *UI classes
 */
public abstract class BaseUI {

    //This will ensure all *UI classes have a createScene method that matches this signature
    abstract Scene createScene(MenuBar menubar);
    private CapestraDB _myDb;

    //All screens need access to the database.
    protected CapestraDB getMyDB() {
        return _myDb;
    }

    public BaseUI() {
        _myDb = new CapestraDB();
    }

    // Creates the Title HBox (typically found at the top of the screen)
    public HBox createTitleHBox(String titleText) {
        Label resultLBL = new Label(titleText);
        resultLBL.setStyle("-fx-font: 24 arial;");
        HBox labelHB = new HBox(resultLBL);
        labelHB.alignmentProperty().setValue(Pos.CENTER);
        return labelHB;
    }
    
    public Scene applyStyleSheet(Scene scene)
    {
        scene.getStylesheets().add(CapestraApp.class.getResource("Capestra.css").toExternalForm());
        return scene;
    }
}
