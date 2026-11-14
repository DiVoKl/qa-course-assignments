package factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the employee generator's default size, required fields, salary
 * range, sequential unique IDs, and input validation.
 */
class EmployeeGeneratorTest {

    /** The no-argument overload should produce the assignment's standard dataset. */
    @Test
    void generatesTenThousandEmployeesByDefault() {
        Employee[] employees = EmployeeGenerator.generateEmployees();

        assertEquals(10000, employees.length);
    }

    /** Generated employee objects should be present and have all seven attributes populated. */
    @Test
    void everyEmployeeHasAllSevenFieldsFilled() {
        for (Employee e : EmployeeGenerator.generateEmployees(1000)) {
            assertNotNull(e);
            assertTrue(e.getId() > 0);
            assertFalse(isBlank(e.getFirstName()), "first name missing");
            assertFalse(isBlank(e.getLastName()), "last name missing");
            assertFalse(isBlank(e.getEmail()), "email missing");
            assertFalse(isBlank(e.getPhone()), "phone missing");
            assertFalse(isBlank(e.getAddress()), "address missing");
        }
    }

    /** Every generated salary must include a value within the configured inclusive range. */
    @Test
    void salariesAreBetween3000And15000() {
        for (Employee e : EmployeeGenerator.generateEmployees()) {
            int salary = e.getMonthlySalary();
            assertTrue(salary >= 3000 && salary <= 15000, "Salary out of range: " + salary);
        }
    }

    /** Sequential IDs must start at FIRST_ID and remain unique throughout the array. */
    @Test
    void idsAreUnique() {
        Employee[] employees = EmployeeGenerator.generateEmployees();
        // IDs are a running counter, so each one must be exactly FIRST_ID + index.
        for (int i = 0; i < employees.length; i++) {
            assertEquals(EmployeeGenerator.FIRST_ID + i, employees[i].getId());
        }
    }

    /** A negative requested count is invalid and should fail with a clear argument exception. */
    @Test
    void negativeEmployeeCountIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeGenerator.generateEmployees(-1));
    }

    /** Treats null and whitespace-only strings as missing test data. */
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
