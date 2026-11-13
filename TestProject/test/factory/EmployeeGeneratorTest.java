package factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link EmployeeGenerator}.
 */
class EmployeeGeneratorTest {

    @Test
    void generatesTenThousandEmployeesByDefault() {
        Employee[] employees = EmployeeGenerator.generateEmployees();

        assertEquals(10000, employees.length);
    }

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

    @Test
    void salariesAreBetween3000And15000() {
        for (Employee e : EmployeeGenerator.generateEmployees()) {
            int salary = e.getMonthlySalary();
            assertTrue(salary >= 3000 && salary <= 15000, "Salary out of range: " + salary);
        }
    }

    @Test
    void idsAreUnique() {
        Employee[] employees = EmployeeGenerator.generateEmployees();
        // IDs are a running counter, so each one must be exactly FIRST_ID + index.
        for (int i = 0; i < employees.length; i++) {
            assertEquals(EmployeeGenerator.FIRST_ID + i, employees[i].getId());
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
