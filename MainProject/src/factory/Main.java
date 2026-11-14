package factory;

/**
 * Application entry point. Generates the assignment's default employee set
 * and delegates sorting and elapsed-time output to {@link SalarySorter}.
 */
public class Main {

    /**
     * Generates 10,000 employees and sorts them by monthly salary.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Employee[] employees = EmployeeGenerator.generateEmployees(EmployeeGenerator.NUM_EMPLOYEES);
        SalarySorter.sort(employees);
    }
}
