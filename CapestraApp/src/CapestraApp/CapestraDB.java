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

    public static Employee employee;
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
        try {
            String query = "SELECT id, first_name, last_name, email FROM employee WHERE username=? and password=?";
            pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                employee = readEmployee(rset);
                statusLBL.setText("Welcome back " + employee.toString() + ", employee id: "+ employee.getId());
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

    // Simple method to convert a employee database table result set (row) to an
    // Employee object
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
    public ObservableList<Product> getProductList() {
        initializeDB();
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            // We need to use an inner join here to get the related data from the category
            // table
            String sql = "SELECT p.*, c.category_name FROM product p inner join category c on p.category_key = c.id ORDER BY p.product_name";
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
    } // end getProductList()

    // Create a list of all order information 
    public ObservableList<Order> getOrderList() {
        initializeDB();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            // We need to use an inner join here to get the related data from the category
            // table
            String sql = "SELECT * FROM vw_order_report";
            stmt = connection.createStatement();
            // Execute the query and retrieve the results
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Create a new Customer object
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setEmployeeId(rs.getInt("employee_id"));
                order.setDetailCount(rs.getInt("detail_count"));
                order.setGrandTotal(rs.getFloat("grand_total"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerEmail(rs.getString("customer_email"));
                order.setEmployeeName(rs.getString("employee_name"));
                order.setEmployeeEmail(rs.getString("employee_email"));

                // Add the details of a customer to the list "customers"
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeDB();
        }
        closeDB();
        return orders;
    } // end getOrderList()

    // Create a list of all order information 
    public ObservableList<OrderDetail> getOrderDetailList(int orderId) {
        initializeDB();
        ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();

        PreparedStatement pstmt = null;
        try {
            // We need to use an inner join here to get the related data from the category
            // table
            String sql = "SELECT * FROM vw_order_detail_report "
                    + "WHERE order_id=?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(orderId));
            // Execute the query and retrieve the results
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Create a new Customer object
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(rs.getInt("id"));
                orderDetail.setProductId(rs.getInt("product_id"));
                orderDetail.setProductName(rs.getString("product_name"));
                orderDetail.setUnitPrice(rs.getFloat("unit_price"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.calculateTotal();
                // Add the details of a customer to the list "customers"
                orderDetails.add(orderDetail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeDB();
        }
        closeDB();
        return orderDetails;
    } // end getOrderList()

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
            statusLBL.setText("Customer '" + customer.toString() + "' has been added");
            added = true;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == ER_DUP_ENTRY) {
                statusLBL.setText("Customer '" + customer.toString() + "' is aleady in the databse");
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

    // Initial code was provide to me in the ITEC5020 Samples
    // Extended by Isaac Martinez
    public boolean addOrder(Order order, Label statusLBL) {
        boolean added = false;
        initializeDB();

        PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO `order`(customer_id, employee_id) VALUES(?,?)";
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getCustomerId());
            pstmt.setInt(2, order.getEmployeeId());

            // Execute the statement
            pstmt.executeUpdate();
            
            // Get the new Order Id
            ResultSet generatedKeysResultSet = pstmt.getGeneratedKeys();
            generatedKeysResultSet.next();
            int newOrderId = generatedKeysResultSet.getInt(1);
            
            for (OrderDetail orderDetail : order.getOrderDetails()) {

                sql = "INSERT INTO `order_detail`(`order_id`,`product_id`,`quantity`) VALUES (?,?,?);";
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, newOrderId);
                pstmt.setInt(2, orderDetail.getProductId());
                pstmt.setInt(3, orderDetail.getQuantity());
                pstmt.executeUpdate();
            }

            connection.commit();
            statusLBL.setText("Order for " + order.getCustomerName() + " has been added");
            added = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
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
