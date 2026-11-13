package factory;

/**
 * Entry point: generates 10,000 random employees, sorts them by monthly
 * salary and prints the sorted report.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Employee Salary Tracking System ===");

        System.out.println("Generating " + EmployeeGenerator.NUM_EMPLOYEES + " random employees...");
        Employee[] employees = EmployeeGenerator.generateEmployees(EmployeeGenerator.NUM_EMPLOYEES);

        // sort() prints its own execution time.
        System.out.println("Sorting employees by monthly salary...");
        Employee[] sorted = SalarySorter.sort(employees);

        System.out.println();
        System.out.println("=== Employee Report - Sorted by Monthly Salary (Ascending) ===");
        for (int i = 0; i < sorted.length; i++) {
            System.out.println((i + 1) + ". " + sorted[i]);
        }

        System.out.println("=== End of Report: " + sorted.length + " employees ===");
    }
}
