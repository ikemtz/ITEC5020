package CapestraApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class OrderDataEntryUI extends BaseDataEntryUI {

    private TableView<OrderDetail> tableView;
    private ObservableList<OrderDetail> orderDetails;
    private Product selectedProduct;
    private Button addOrderDetailBTN, removeOrderDetailBTN, placeOrderBTN, clearBTN;
    private HBoxAndControl customerHBC, productHBC, quantityHBC;
    private int quantity = 1;

    // Create Order Scene Setup
    @Override
    public Scene createScene(MenuBar menuBar) {
        customerHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Customer", 150,
                getMyDB().getCustomerList(false));
        productHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Product", 150,
                getMyDB().getProductList());
        quantityHBC = UiFactory.createHBoxAndControl(ControlTypes.TextField, "Quantity", 50);

        productHBC.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedProduct = (Product) newValue;
        });
        quantityHBC.setText(Integer.toString(quantity));
        quantityHBC.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            // The following few lines of code will restrict the quantity textbox to only
            // accept numbers
            if (!newValue.matches("\\d*")) { // Regex to detect numeric characters (note: match is negated)
                newValue = newValue.replaceAll("[^\\d]", ""); // Regex to match non-numeric characters
                quantityHBC.setText(newValue);
            }
            quantity = Integer.parseInt(newValue);
        });

        initOrderDetailTV();

        addOrderDetailBTN = new Button("Add Order Detail");
        removeOrderDetailBTN = new Button("Remove Order Detail");
        placeOrderBTN = new Button("Place Order");
        clearBTN = new Button("Clear");

        updateCommandButtonStatus();

        addOrderDetailBTN.setOnAction(e -> {
            addNewOrderDetailToTableView();
        });

        removeOrderDetailBTN.setOnAction(e -> {
            OrderDetail detailSelectedItem = tableView.getSelectionModel().getSelectedItem();
            orderDetails.remove(detailSelectedItem);
            updateCommandButtonStatus();
        });

        placeOrderBTN.setOnAction(e -> {
            this.addNewOrderToDb();
        });

        clearBTN.setOnAction(e -> {
            this.clearData();
        });

        VBox vbox = new VBox(15, createTitleHBox("Place Order"),
                customerHBC.getHBox(),
                productHBC.getHBox(),
                quantityHBC.getHBox(),
                tableView,
                UiFactory.createCommandHBox(
                        addOrderDetailBTN,
                        removeOrderDetailBTN,
                        placeOrderBTN,
                        clearBTN),
                statusLBL);
        vbox.setPadding(UiFactory.DEFAULT_INSETS);
        VBox vboxOL = new VBox(menuBar, vbox);
        return new Scene(vboxOL, 600, 500);
    }

    public void clearData() {
        this.quantity = 1;
        this.customerHBC.clearValue();
        this.productHBC.clearValue();
        this.quantityHBC.setText("1");
        this.orderDetails.clear();
        updateCommandButtonStatus();
    }

    // Will disable "place order" and "remove order detail" buttons
    // based on whether or not there are order details
    public void updateCommandButtonStatus() {
        boolean isOrderDetailsEmpty = orderDetails.isEmpty();
        removeOrderDetailBTN.disableProperty().setValue(isOrderDetailsEmpty);
        placeOrderBTN.disableProperty().setValue(isOrderDetailsEmpty);
    }

    //Initializes the OrderDetail Table View
    public void initOrderDetailTV() {
        orderDetails = FXCollections.observableArrayList();
        tableView = new TableView<>(orderDetails);
        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Product Name", 200),
                UiFactory.createTableColumn("Quantity", 80),
                UiFactory.createTableColumn("Unit Price", 50),
                UiFactory.createTableColumn("Total", 50));
    }

    // Adds new order detail record to table view
    public void addNewOrderDetailToTableView() {
        OrderDetail newOrderDetail = new OrderDetail(selectedProduct, quantity);
        orderDetails.add(newOrderDetail);
        updateCommandButtonStatus();
    }

    // Adds new order to database
    public void addNewOrderToDb() {
        Customer selectedCustomer = customerHBC.getSelectedValue();
        Order order = new Order(CapestraDB.employee, selectedCustomer);
        order.setOrderDetails(orderDetails.toArray(new OrderDetail[0]));
        getMyDB().addOrder(order, statusLBL);
        clearData();
    }
}
