package factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link SalarySorter#sort(Employee[])}.
 *
 * The two mandatory requirements from the assignment:
 *   1. sort returns a value (not null).
 *   2. the returned array lost no records and is sorted by salary.
 */
class SalarySorterTest {

    /** A small deterministic array with known, unsorted salaries. */
    private static Employee[] createSampleArray() {
        return new Employee[]{
                new Employee(1, "A", "A", "a@a.com", "050-1111111", "Addr1", 12000),
                new Employee(2, "B", "B", "b@b.com", "050-2222222", "Addr2", 4500),
                new Employee(3, "C", "C", "c@c.com", "050-3333333", "Addr3", 15000),
                new Employee(4, "D", "D", "d@d.com", "050-4444444", "Addr4", 3000),
                new Employee(5, "E", "E", "e@e.com", "050-5555555", "Addr5", 4500)
        };
    }

    private static Employee[] copyOf(Employee[] source) {
        Employee[] copy = new Employee[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i];
        }
        return copy;
    }

    /** Asserts that every employee in {@code original} appears in {@code sorted} and nothing was added. */
    private static void assertNoRecordsLost(Employee[] original, Employee[] sorted) {
        assertNotNull(sorted, "sort returned null");
        assertEquals(original.length, sorted.length, "Employee count changed after sorting");

        // Each original employee must be matched by exactly one sorted slot that
        // hasn't been used yet, so the result is a true permutation of the input.
        boolean[] used = new boolean[sorted.length];
        for (Employee originalEmp : original) {
            boolean found = false;
            for (int j = 0; j < sorted.length; j++) {
                if (!used[j] && sorted[j] != null && sorted[j].getId() == originalEmp.getId()) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Employee with ID " + originalEmp.getId() + " was lost during sorting");
        }
    }

    private static void assertSortedBySalary(Employee[] sorted) {
        for (int i = 0; i < sorted.length - 1; i++) {
            assertTrue(sorted[i].getMonthlySalary() <= sorted[i + 1].getMonthlySalary(),
                    "Array is not sorted at index " + i + ": "
                            + sorted[i].getMonthlySalary() + " > " + sorted[i + 1].getMonthlySalary());
        }
    }

    // ---------------------------------------------------------------------
    // Requirement 1: the function returns a value
    // ---------------------------------------------------------------------

    @Test
    void sortReturnsNonNullValue() {
        Employee[] result = SalarySorter.sort(createSampleArray());

        assertNotNull(result, "The sort function returned null instead of an array");
    }

    // ---------------------------------------------------------------------
    // Requirement 2: no records lost, and the array is sorted
    // ---------------------------------------------------------------------

    @Test
    void sortDoesNotLoseRecords() {
        Employee[] original = createSampleArray();
        Employee[] backup = copyOf(original);

        Employee[] sorted = SalarySorter.sort(original);

        assertNoRecordsLost(backup, sorted);
    }

    @Test
    void sortReturnsArraySortedBySalaryAscending() {
        Employee[] sorted = SalarySorter.sort(createSampleArray());

        assertNotNull(sorted, "sort returned null");
        assertSortedBySalary(sorted);
    }

    @Test
    void sortPutsSampleSalariesInExpectedOrder() {
        Employee[] sorted = SalarySorter.sort(createSampleArray());

        assertNotNull(sorted, "sort returned null");
        int[] expected = {3000, 4500, 4500, 12000, 15000};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], sorted[i].getMonthlySalary(), "Wrong salary at index " + i);
        }
    }

    @Test
    void sortTenThousandGeneratedEmployeesKeepsAllRecordsAndIsSorted() {
        Employee[] original = EmployeeGenerator.generateEmployees(EmployeeGenerator.NUM_EMPLOYEES);
        Employee[] backup = copyOf(original);

        Employee[] sorted = SalarySorter.sort(original);

        assertNoRecordsLost(backup, sorted);
        assertSortedBySalary(sorted);
    }

    // ---------------------------------------------------------------------
    // Edge cases
    // ---------------------------------------------------------------------

    @Test
    void sortEmptyArrayReturnsEmptyArray() {
        Employee[] sorted = SalarySorter.sort(new Employee[0]);

        assertNotNull(sorted, "sort returned null for an empty array");
        assertEquals(0, sorted.length);
    }

    @Test
    void sortSingleEmployeeReturnsSameEmployee() {
        Employee only = new Employee(7, "X", "Y", "x@y.com", "050-7777777", "Addr7", 8000);

        Employee[] sorted = SalarySorter.sort(new Employee[]{only});

        assertNotNull(sorted, "sort returned null for a single employee");
        assertEquals(1, sorted.length);
        assertEquals(7, sorted[0].getId());
    }

    @Test
    void sortAlreadySortedArrayStaysSorted() {
        Employee[] input = SalarySorter.sort(createSampleArray());
        assertNotNull(input, "sort returned null");
        Employee[] backup = copyOf(input);

        Employee[] sorted = SalarySorter.sort(input);

        assertNoRecordsLost(backup, sorted);
        assertSortedBySalary(sorted);
    }
}
