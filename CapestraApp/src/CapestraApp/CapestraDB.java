/*
 * Capestra Order Entry System
 *
 * CapestraDB.java - Interface to the MySQL database (the back end)
 * Modified: 2020 07 31 - 14:30
 */
package CapestraApp;

// Library Imports
import java.sql.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;

public class CapestraDB {

  private final int ER_DUP_ENTRY = 1062; // MySQL error code for duplicate entry
  private Connection connection;
  private Statement stmt;

  // Initialze the database; gain access
  // Initial code was provide to me in the ITEC5020 Samples 
  private void initializeDB() {
    String connectionName = "jdbc:mysql://localhost:3306/";
    String databaseName = "orderentrysystem";
    String databaseUserName = "root";
    String databasePassword = "root";

    try {
      // Load the appropriate driver
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Driver loaded");

      // Establish a connection
      connection = DriverManager.getConnection(connectionName
          + databaseName, databaseUserName, databasePassword);
      System.out.println("Database connected");

      // Create a statement
      stmt = connection.createStatement();
    } catch (Exception ex) {
      System.err.println("\n*****  MySQL '" + databaseName
          + "' database is not available  *****");
      System.err.printf("*****  DatabaseName:%s  UserName:%s  Password:%s  *****\n\n",
          databaseName, databaseUserName, databasePassword);
      ex.printStackTrace();
    }
  } // end initializeDB()

  // Return employeeId for a specific employee
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public Employee validateEmployeeCredentials(String username, String password, Label statusLBL) {
    initializeDB();
    PreparedStatement pstmt = null;
    Employee employee = null;
    try {
      String query = "SELECT id, first_name, last_name, email "
          + "FROM employee WHERE username=? and password=?";
      pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, username);
      pstmt.setString(2, password);

      ResultSet rset = pstmt.executeQuery();
      if (rset.next()) { 
        employee = readEmployee(rset);
        statusLBL.setText("Welcome back " + employee.getFirstName()  + " " + employee.getLastName());
      } else {
        statusLBL.setText("Employee not found");
        employee = null;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    closeDB();
    return employee;
  } // end findEmployee()

  // Return a list of employees extracting employee data from the database
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public ObservableList<Employee> getEmployeeList(boolean sortById) {
    initializeDB();
    ObservableList<Employee> employees = FXCollections.observableArrayList();

    try {
      String query = "SELECT id, first_name, last_name, email "
          + "FROM employee ORDER BY ";

      query = query + (sortById ? "id" : "last_name, first_name");
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next()) {
        employees.add(readEmployee(rset));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    closeDB();
    return employees;
  } // end makeEmployeeList()

  // Simple method to convert a employee database table result set (row) to an Employee object
  public Employee readEmployee(ResultSet rset) throws SQLException {
    int employeeId = rset.getInt(1);
    String firstName = rset.getString(2);
    String lastName = rset.getString(3);
    String email = rset.getString(4);
    return new Employee(employeeId, firstName, lastName, email);
  }

  // Create a list of all customer information
  public ObservableList<Customer> getCustomerList(boolean sortById) {
    initializeDB();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    try {
      String sql = "SELECT * FROM customer ORDER BY ";
      sql = sql + (sortById ? "id" : "last_name, first_name");
      stmt = connection.createStatement();
      // Execute the query and retrieve the results
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        // Create a new Customer object
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setZip(rs.getString("zip"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));

        // Add the details of a customer to the list "customers"
        customers.add(customer);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      closeDB();
    }
    closeDB();
    return customers;
  } // end getCustomerList()

  // Create a list of all product information
  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public ObservableList<Product> getProductList(boolean sortById) {
    initializeDB();
    ObservableList<Product> products = FXCollections.observableArrayList();
    try {
      // We need to use an inner join here to get the related data from the category
      // table
      String sql = "SELECT p.*, c.category_name FROM product p inner join category c on p.category_key = c.id ORDER BY ";
      sql = sql + (sortById ? "p.id" : "p.product_name");
      stmt = connection.createStatement();
      // Execute the query and retrieve the results
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        // Create a new Customer object
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("product_name"));
        product.setDescription(rs.getString("product_description"));
        product.setPrice(rs.getFloat("unit_price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setCategoryKey(rs.getInt("category_key"));
        product.setCategory(rs.getString("category_name"));

        // Add the details of a customer to the list "customers"
        products.add(product);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      closeDB();
    }
    closeDB();
    return products;
  } // end getCustomerList()

  // Initial code was provide to me in the ITEC5020 Samples
  // Extended by Isaac Martinez
  public boolean addCustomer(Customer customer, Label statusLBL) {
    boolean added = false;
    initializeDB();

    PreparedStatement pstmt = null;
    try {
      connection.setAutoCommit(false);
      String sql = "INSERT INTO customer(first_name, last_name, address, "
          + "city, state, zip, phone, email) VALUES(?,?,?,?,?,?,?,?)";
      pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, customer.getFirstName());
      pstmt.setString(2, customer.getLastName());
      pstmt.setString(3, customer.getAddress());
      pstmt.setString(4, customer.getCity());
      pstmt.setString(5, customer.getState());
      pstmt.setString(6, customer.getZip());
      pstmt.setString(7, customer.getPhone());
      pstmt.setString(8, customer.getEmail());

      // Execute the statement
      pstmt.executeUpdate();
      connection.commit();
      statusLBL.setText("Customer '" + customer.getFirstName() + " "
          + customer.getLastName() + "' has been added");
      added = true;
    } catch (SQLException ex) {
      if (ex.getErrorCode() == ER_DUP_ENTRY) {
        statusLBL.setText("Customer '" + customer.getFirstName()
            + " " + customer.getLastName() + " is aleady in the databse");
      } else {
        ex.printStackTrace();
      }
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (connection != null) {
          connection.setAutoCommit(false);
          connection.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }

    return added;
  }

  // Close/release the database
  private void closeDB() {
    try {
      connection.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  } // end closeDB()

} // end class CapestraDB
