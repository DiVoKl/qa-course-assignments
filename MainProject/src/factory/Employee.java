package factory;

/**
 * An employee at the factory, holding the seven mandatory attributes.
 */
public class Employee {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final String address;
    private final int monthlySalary;

    public Employee(int id, String firstName, String lastName, String email,
                    String phone, String address, int monthlySalary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.monthlySalary = monthlySalary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + firstName + " " + lastName
                + " | Salary: " + monthlySalary + " NIS"
                + " | Email: " + email
                + " | Phone: " + phone
                + " | Address: " + address;
    }
}
