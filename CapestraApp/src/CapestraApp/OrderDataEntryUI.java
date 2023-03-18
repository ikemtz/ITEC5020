package CapestraApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
/*
 * Capestra Order Entry System
 *
 * OrderDataEntryUI.java - UI Scene for Order Data Entry screen
 * Modified by: Isaac Martinez
 *
 */
public class OrderDataEntryUI extends BaseDataEntryUI {

  private TableView<OrderDetail> tableView;
  private ObservableList<OrderDetail> orderDetails;
  private Customer selectedCustomer;
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
    customerHBC.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
      selectedCustomer = (Customer) newValue;
    });
    quantityHBC.setText(Integer.toString(quantity));

    // This listener was setup only to allow numeric values in the quantity
    // textField
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
      removeOrderDetailRecordFromTableView();
    });

    placeOrderBTN.setOnAction(e -> {
      this.addRecordToDb();
    });

    clearBTN.setOnAction(e -> {
      this.clearData(true);
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
    return applyStyleSheet(new Scene(vboxOL, 750, 500));
  }

  // Clears all data from the form
  // completeWipe should be true on clear button click and after successful place
  // order
  public void clearData(boolean completeWipe) {

    this.quantity = 1;
    this.quantityHBC.setText("1");
    this.productHBC.clearValue();
    if (completeWipe) {
      this.customerHBC.clearValue();
      this.orderDetails.clear();
    }
    this.statusLBL.setText("");
    updateCommandButtonStatus();
  }

  // Will disable "place order" and "remove order detail" buttons
  // based on whether or not there are order details
  public void updateCommandButtonStatus() {
    boolean isOrderDetailsEmpty = orderDetails.isEmpty();
    removeOrderDetailBTN.disableProperty().setValue(isOrderDetailsEmpty);
    placeOrderBTN.disableProperty().setValue(isOrderDetailsEmpty);
  }

  // Initializes the OrderDetail Table View
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
  // Also does some basic validation on OrderDetail
  public void addNewOrderDetailToTableView() {
    if (!isOrderDetailRecordValid()) {
    } else {
      OrderDetail newOrderDetail = new OrderDetail(selectedProduct, quantity);
      orderDetails.add(newOrderDetail);
      clearData(false);
      updateCommandButtonStatus();
    }
  }

  // Removes an Order Detail Record from the Table View
  public void removeOrderDetailRecordFromTableView() {
    OrderDetail detailSelectedItem = tableView.getSelectionModel().getSelectedItem();
    orderDetails.remove(detailSelectedItem);
    updateCommandButtonStatus();
  }

  @Override
  // Adds new order to database
  public void addRecordToDb() {
    Order order = new Order(CapestraDB.employee, customerHBC.getSelectedValue());
    order.setOrderDetails(orderDetails.toArray(new OrderDetail[0]));
    if (!isOrderRecordValid(order)) {
    } else {
      clearData(true);
      getMyDB().addOrder(order, statusLBL);
    }
  }

  public boolean isOrderRecordValid(Order order) {
    StringBuilder errorMessages = new StringBuilder();
    if (order.getCustomerId() == 0) {
      errorMessages.append("Customer is required; ");
    }
    if (order.getOrderDetails().length == 0) {
      errorMessages.append("At least one order detail is required; ");
    }
    return this.displayErrorMessagesIfAny(errorMessages);
  }

  public boolean isOrderDetailRecordValid() {
    StringBuilder errorMessages = new StringBuilder();
    if (selectedCustomer == null) {
      errorMessages.append("Customer is required; ");
    } 
    if (quantity < 1) {
      errorMessages.append("Quantity should be at least 1; ");
    } 
    if (selectedProduct == null) {
      errorMessages.append("Product is required; ");
    } else if (quantity > selectedProduct.getQuantity()) {
      errorMessages
          .append("Sorry we don't have enough of ")
          .append(selectedProduct.getName())
          .append(", MAX(")
          .append(selectedProduct.getQuantity())
          .append(");");
    }
    return this.displayErrorMessagesIfAny(errorMessages);
  }

}
