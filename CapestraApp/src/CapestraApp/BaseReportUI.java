package CapestraApp;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ikemt Simple interface that ensure commonality across all *ReportUI
 * classes
 * @param <ENTITY> Generic parameter to support database object models
 */
public abstract class BaseReportUI<ENTITY> extends BaseUI {

    // These two properties allow for customization of the scene size
    protected int SceneWidth = 800;
    protected int SceneHeight = 500;
    
    // Returns the title of the scene
    abstract String getTitle();
    
    // Allows developers to setup the tableView (add columns, etc.)
    abstract void setupTableView(TableView<ENTITY> tableView);
    
    // Generic method to retrieve data from the database to be used by the Table View
    abstract ObservableList<ENTITY> getData();

    // This is will setup the UI for most reports
    @Override
    public Scene createScene(MenuBar menuBar) {

        // Create the table
        TableView<ENTITY> tableView;

        // Load the data into the table
        tableView = new TableView<>();
        tableView.setItems(getData());
        //  This will allow developers to add columns to the tableView
        setupTableView(tableView);

        // Finishing off the scene
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(menuBar, createTitleHBox(getTitle()), tableView);
        return applyStyleSheet(new Scene(vbox, SceneWidth, SceneHeight));
    }
}
