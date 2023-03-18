package CapestraApp;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
 /*
 * Capestra Order Entry System
 *
 * EmployeeReportUI.java - UI Scene for Employee Report
 * Modified by: Isaac Martinez
 *
 */
public class EmployeeReportUI extends BaseReportUI<Employee> {

    // retrieves a list of employees from the database to be loaded into the tableView  
    @Override
    public ObservableList<Employee> getData() {
        return getMyDB().getEmployeeList(true);
    }

    // Returns the title of the scene
    @Override
    public String getTitle() {
        return "Employee Information";
    }

    // Sets up the table view for the report scene
    @Override
    void setupTableView(TableView<Employee> tableView) {
        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Id", 100),
                UiFactory.createTableColumn("First Name", 175),
                UiFactory.createTableColumn("Last Name", 175),
                UiFactory.createTableColumn("Email", 300));
    }
}
