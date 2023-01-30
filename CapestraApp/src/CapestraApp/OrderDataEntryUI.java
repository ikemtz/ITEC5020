package CapestraApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class OrderDataEntryUI extends BaseUI {

    private TableView<OrderDetail> tableView;
    private ObservableList<OrderDetail> orderDetails;
    private Product selectedProduct;
    private Button addOrderDetailBTN, removeOrderDetailBTN, placeOrderBTN, clearBTN;
    private HBoxAndControl customerHBC, productHBC, quantityHBC;
    private int quantity = 1;
    // Create Order Scene Setup
    // Initial code was provide to me in the ITEC5020 Samples
    // Refactored by Isaac Martinez

    @Override
    public Scene createScene(MenuBar menuBar) {
        CapestraDB myDB = new CapestraDB();
        customerHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Customer", 150,
                myDB.getCustomerList(false));
        productHBC = UiFactory.createHBoxAndControl(ControlTypes.ComboBox, "Product", 150,
                myDB.getProductList());
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
        Label statusLBL = new Label("");

        addOrderDetailBTN.setOnAction(e -> {
            OrderDetail newOrderDetail = new OrderDetail(selectedProduct, quantity);
            orderDetails.add(newOrderDetail);
            updateCommandButtonStatus();
        });
        removeOrderDetailBTN.setOnAction(e -> {
            OrderDetail detailSelectedItem = tableView.getSelectionModel().getSelectedItem();
            orderDetails.remove(detailSelectedItem);
            updateCommandButtonStatus();
        });
        placeOrderBTN.setOnAction(e -> {
            Customer selectedCustomer = customerHBC.getSelectedValue();
            Order order = new Order(CapestraDB.employee, selectedCustomer);
            order.setOrderDetails(orderDetails.toArray(new OrderDetail[0]));
            
            myDB.addOrder(order,statusLBL);
            clearData();
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
        removeOrderDetailBTN.disableProperty().setValue(orderDetails.isEmpty());
        placeOrderBTN.disableProperty().setValue(orderDetails.isEmpty());
    }

    public void initOrderDetailTV() {
        orderDetails = FXCollections.observableArrayList();
        tableView = new TableView<>(orderDetails);
        tableView.getColumns().addAll(
                UiFactory.createTableColumn("Product Name", 200),
                UiFactory.createTableColumn("Quantity", 80),
                UiFactory.createTableColumn("Unit Price", 50),
                UiFactory.createTableColumn("Total", 50));
    }
}
