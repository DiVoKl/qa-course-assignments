package factory;

/** Generates employees and prints only the time taken to sort them. */
public class Main {

    public static void main(String[] args) {
        Employee[] employees = EmployeeGenerator.generateEmployees(EmployeeGenerator.NUM_EMPLOYEES);
        SalarySorter.sort(employees);
    }
}
