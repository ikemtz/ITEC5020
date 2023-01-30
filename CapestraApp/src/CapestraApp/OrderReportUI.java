package CapestraApp;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

//This Scene doesn't follow the standard as it has two table views Order and OrderDetails (master and child)
public class OrderReportUI extends BaseReportUI<Order> {

    // Create a list of orders to be loaded into the table 
    @Override
    public ObservableList<Order> getData() {
        return getMyDB().getOrderList();
    }

    @Override
    public String getTitle() {
        return "Order Information";
    }

    @Override
    public void setupTableView(TableView<Order> tableView) {
        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Id", 50),
                UiFactory.createTableColumn("Customer Name", 200),
                UiFactory.createTableColumn("Employee Name", 200),
                UiFactory.createTableColumn("Detail Count", 100),
                UiFactory.createTableColumn("Grand Total", 100),
                UiFactory.createTableColumn("Order Status", 100));
    }

    public void setupDetailTableView(TableView<OrderDetail> tableView) {

        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Product Id", 75),
                UiFactory.createTableColumn("Product Name", 200),
                UiFactory.createTableColumn("Unit Price", 75),
                UiFactory.createTableColumn("Quantity", 75),
                UiFactory.createTableColumn("Total", 75));
    }

    // This Scene doesn't follow the standard as it has two table views Order and OrderDetails (master and child)
    // This is why I had to override the base createScene method
    @Override
    public Scene createScene(MenuBar menuBar) {

        // Create the Order Detail Table table
        TableView<OrderDetail> orderDetailView = new TableView<>();

        // Create the Order Table table
        TableView<Order> orderView = new TableView<>();
        orderView.setPlaceholder(new Label("Select an Order record above."));
        orderView.setItems(getData());
        
        // handle tableView selected row change
        orderView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        orderView.getSelectionModel().getSelectedItems().addListener(handleOrderSelectionChange(orderDetailView));
        
        //  This will allow developers to add columns to the tableView
        setupTableView(orderView);

        //  This will allow developers to add columns to the tableView
        setupDetailTableView(orderDetailView);

        // Finishing off the scene
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(menuBar,
                createTitleHBox(getTitle()), orderView,
                createTitleHBox("Order Detail Information"), orderDetailView
        );
        return new Scene(vbox, SceneWidth, SceneHeight);
    }

    public ListChangeListener<Order> handleOrderSelectionChange(TableView<OrderDetail> orderDetailView) {
        return (ListChangeListener.Change<? extends Order> changed) -> {
            if (changed.next()) {
                int orderId = changed.getAddedSubList().get(0).getId();
                orderDetailView.setItems(getMyDB().getOrderDetailList(orderId));
            }
        };
    }
}
