package CapestraApp;

/**
 * POJO (plan old java object) class for Order table
 * @author Ikemtz
 */
public class Order {

  private int id, customerId, employeeId, detailCount;
  private String orderStatus,
      customerName, customerEmail,
      employeeName, employeeEmail;
  private float grandTotal;

  private OrderDetail[] orderDetails;

  public Order() {
    orderDetails = new OrderDetail[] {};
  }

  public Order(Employee employee, Customer customer) {
    if (customer != null) {
      this.customerId = customer.getId();
      this.customerName = customer.toString();
    }
    this.employeeId = employee.getId();
  }

  public int getId() {
    return id;
  }

  public void setId(int value) {
    this.id = value;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int value) {
    this.customerId = value;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(int value) {
    this.employeeId = value;
  }

  public int getDetailCount() {
    return detailCount;
  }

  public void setDetailCount(int value) {
    detailCount = value;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String value) {
    this.orderStatus = value;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String value) {
    this.customerName = value;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String value) {
    this.customerEmail = value;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String value) {
    this.employeeName = value;
  }

  public String getEmployeeEmail() {
    return employeeEmail;
  }

  public void setEmployeeEmail(String value) {
    this.employeeEmail = value;
  }

  public float getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(float value) {
    this.grandTotal = value;
  }

  public OrderDetail[] getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(OrderDetail[] value) {
    orderDetails = value;
    this.detailCount = value.length;
  }

  public void setUnitPrice(OrderDetail[] value) {
    this.orderDetails = value;
  }
}
