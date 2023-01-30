package CapestraApp;

/**
 *
 * @author Ikemtz
 */
public class OrderDetail {

    private int id, productId, quantity;
    private String productName;
    private float unitPrice, total;
    public OrderDetail(){}
    public OrderDetail(Product product, int quantity){
        this.productId = product.getId();
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
        this.total = quantity * unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int value) {
        this.productId = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int value) {
        this.quantity = value;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }
    
    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float value) {
        this.unitPrice = value;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float value) {
        this.total = value;
    }

    void calculateTotal() {
        this.total = this.unitPrice * this.quantity;
    }

}
