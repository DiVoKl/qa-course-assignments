package factory;

/**
 * Immutable data model for one factory employee.
 *
 * Each instance stores the seven employee attributes required by the
 * assignment. The values are set at construction and cannot be changed later.
 */
public class Employee {

    /** Unique employee identifier. */
    private final int id;
    /** Employee's given name. */
    private final String firstName;
    /** Employee's family name. */
    private final String lastName;
    /** Employee's email address. */
    private final String email;
    /** Employee's phone number. */
    private final String phone;
    /** Employee's street and city address. */
    private final String address;
    /** Monthly salary, stored as an integer number of NIS. */
    private final int monthlySalary;

    /**
     * Creates an employee with all seven required attributes.
     *
     * @param id unique employee identifier
     * @param firstName employee's given name
     * @param lastName employee's family name
     * @param email employee's email address
     * @param phone employee's phone number
     * @param address employee's address
     * @param monthlySalary monthly salary in NIS
     */
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

    /** @return the employee's unique identifier */
    public int getId() {
        return id;
    }

    /** @return the employee's given name */
    public String getFirstName() {
        return firstName;
    }

    /** @return the employee's family name */
    public String getLastName() {
        return lastName;
    }

    /** @return the employee's email address */
    public String getEmail() {
        return email;
    }

    /** @return the employee's phone number */
    public String getPhone() {
        return phone;
    }

    /** @return the employee's address */
    public String getAddress() {
        return address;
    }

    /** @return the employee's monthly salary in NIS */
    public int getMonthlySalary() {
        return monthlySalary;
    }

    /** @return a readable summary containing all employee attributes */
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
