package CapestraApp;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerReportUI extends BaseReportUI<Customer> {

    // retrieves a list of customers from the database to be loaded into the tableView  
    @Override
    public ObservableList<Customer> getData() {
        return getMyDB().getCustomerList(true);
    }

    // Returns the title of the scene
    @Override
    public String getTitle() {
        return "Customer Information";
    }

    @Override
    public void setupTableView(TableView<Customer> tableView) {

        TableColumn<Customer, Integer> idCol = UiFactory.createTableColumn("Id", 25);
        idCol.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().addAll(idCol,
                UiFactory.createTableColumn("First Name", 80),
                UiFactory.createTableColumn("Last Name", 80),
                UiFactory.createTableColumn("Address", 150),
                UiFactory.createTableColumn("City", 90),
                UiFactory.createTableColumn("Zip", 75),
                UiFactory.createTableColumn("Phone", 75),
                UiFactory.createTableColumn("Email", 120));

    }
}
