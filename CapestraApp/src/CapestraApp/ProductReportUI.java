package CapestraApp;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
 /*
 * Capestra Order Entry System
 *
 * EmployeeReportUI.java - UI Scene for Product Report
 * Modified by: Isaac Martinez
 *
 */
// Product Information Scene Setup
public class ProductReportUI extends BaseReportUI<Product> {

    // Create a list of employees to be loaded into the table
    // Initial code was provide to me in the ITEC5020 Samples
    // Simplified by Isaac Martinez
    @Override
    public ObservableList<Product> getData() {
        return getMyDB().getProductList();
    }

    @Override
    public String getTitle() {
        return "Product Information";
    }

    @Override
    public void setupTableView(TableView<Product> tableView) {
        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Id", 35),
                UiFactory.createTableColumn("Name", 200),
                UiFactory.createTableColumn("Description", 350),
                UiFactory.createTableColumn("Price", 50),
                UiFactory.createTableColumn("Quantity", 50),
                UiFactory.createTableColumn("Category", 75));

    }
}
