package CapestraApp;

// POJO (plan old java object) class for Employee table
// Initial code was provide to me in the ITEC5020 Samples
// Extended by Isaac Martinez
public class Employee {

  private int id;
  private String firstName, lastName, email;

  public Employee() {
    this.id = 0;
    this.firstName = this.lastName = this.email = "";
  }

  public Employee(int id, String firstName, String lastName, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + id + ", firstName=" + firstName
        + ", lastName=" + lastName + ", email=" + email + '}';
  }

}
