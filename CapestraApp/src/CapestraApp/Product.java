// CategoryProduct.java - Extract - Example code for Week 9
//
package CapestraApp;

/**
 * POJO (plan old java object) class for Product table
 * @author Ikemtz
 */
// Initial code was provide to me in the ITEC5020 Samples
public class Product {

  private int id, quantity, categoryKey = 0;
  private float unit_price = 0;
  private String category, name, description;

  public Product() {
    category = name = description = "";
  }

  public Product(int id, int categoryKey, String category, String name, String description,
      float price) {
    this.id = id;
    this.category = category;
    this.name = name;
    this.description = description;
    this.unit_price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getCategoryKey() {
    return categoryKey;
  }

  public void setCategoryKey(int categoryKey) {
    this.categoryKey = categoryKey;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return unit_price;
  }

  public void setPrice(float price) {
    this.unit_price = price;
  }

  @Override
  public String toString() {
    return name;
  }

} // end CategoryProduct class
