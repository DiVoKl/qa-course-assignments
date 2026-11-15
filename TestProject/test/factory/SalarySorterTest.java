package factory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the sorter's return value, record preservation, ascending order,
 * stability, edge cases, and timing output.
 *
 * The assignment's core requirements are that sorting returns an array without
 * losing employees and orders the records by monthly salary.
 */
class SalarySorterTest {

    /** Builds a small deterministic input used by tests that need known salaries. */
    private static Employee[] createSampleArray() {
        return new Employee[]{
                new Employee(1, "A", "A", "a@a.com", "050-1111111", "Addr1", 12000),
                new Employee(2, "B", "B", "b@b.com", "050-2222222", "Addr2", 4500),
                new Employee(3, "C", "C", "c@c.com", "050-3333333", "Addr3", 15000),
                new Employee(4, "D", "D", "d@d.com", "050-4444444", "Addr4", 3000),
                new Employee(5, "E", "E", "e@e.com", "050-5555555", "Addr5", 4500)
        };
    }

    /** Creates a test employee with the requested ID and salary. */
    private static Employee createEmployee(int id, int salary) {
        return new Employee(id, "Test", "Employee", "test@example.com",
                "050-0000000", "Test Address", salary);
    }

    /** Copies array references so in-place sorting does not alter the test snapshot. */
    private static Employee[] copyOf(Employee[] source) {
        Employee[] copy = new Employee[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i];
        }
        return copy;
    }

    /**
     * Verifies the result has the same number of employees and contains each
     * original ID exactly once.
     */
    private static void assertNoRecordsLost(Employee[] original, Employee[] sorted) {
        assertNotNull(sorted, "sort returned null");
        assertEquals(original.length, sorted.length, "Employee count changed after sorting");

        // Track matched slots to detect both dropped and duplicated employee IDs.
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

    /** Checks adjacent values because a non-decreasing array is sorted ascending. */
    private static void assertSortedBySalary(Employee[] sorted) {
        for (int i = 0; i < sorted.length - 1; i++) {
            assertTrue(sorted[i].getMonthlySalary() <= sorted[i + 1].getMonthlySalary(),
                    "Array is not sorted at index " + i + ": "
                            + sorted[i].getMonthlySalary() + " > " + sorted[i + 1].getMonthlySalary());
        }
    }

    /** Captures only the output produced by the operation under test. */
    private static String captureStandardOutput(Runnable operation) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try (PrintStream temporaryOut = new PrintStream(capturedOutput)) {
            System.setOut(temporaryOut);
            operation.run();
        } finally {
            System.setOut(originalOut);
        }

        return capturedOutput.toString();
    }

    /** Checks that each output line reports a parseable, non-negative duration. */
    private static void assertValidTimingOutput(String output, int expectedLineCount) {
        String[] lines = output.split("\\r?\\n");
        assertEquals(expectedLineCount, lines.length, "Unexpected number of timing lines");

        for (String line : lines) {
            assertTrue(line.matches("\\d+[.,]\\d{3} ms"),
                    "Expected elapsed milliseconds, but got: " + line);
            double milliseconds = Double.parseDouble(line.substring(0, line.length() - 3)
                    .replace(',', '.'));
            assertTrue(milliseconds >= 0 && !Double.isInfinite(milliseconds),
                    "Elapsed time must be finite and non-negative: " + line);
        }
    }

    // ---------------------------------------------------------------------
    // Requirement 1: the function returns a value
    // ---------------------------------------------------------------------

    /** Sorting a valid sample should return a non-null result. */
    @Test
    void sortReturnsNonNullValue() {
        Employee[] result = SalarySorter.sort(createSampleArray());

        assertNotNull(result, "The sort function returned null instead of an array");
    }

    // ---------------------------------------------------------------------
    // Requirement 2: no records lost, and the array is sorted
    // ---------------------------------------------------------------------

    /** Sorting must preserve every employee ID from the input. */
    @Test
    void sortDoesNotLoseRecords() {
        Employee[] original = createSampleArray();
        Employee[] backup = copyOf(original);

        Employee[] sorted = SalarySorter.sort(original);

        assertNoRecordsLost(backup, sorted);
    }

    /** The result must be in ascending salary order. */
    @Test
    void sortReturnsArraySortedBySalaryAscending() {
        Employee[] sorted = SalarySorter.sort(createSampleArray());

        assertNotNull(sorted, "sort returned null");
        assertSortedBySalary(sorted);
    }

    /** Known salary values ensure the sample is ordered into the expected exact sequence. */
    @Test
    void sortPutsSampleSalariesInExpectedOrder() {
        Employee[] sorted = SalarySorter.sort(createSampleArray());

        assertNotNull(sorted, "sort returned null");
        int[] expected = {3000, 4500, 4500, 12000, 15000};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], sorted[i].getMonthlySalary(), "Wrong salary at index " + i);
        }
    }

    /** A full-size generated dataset must remain intact and become salary-sorted. */
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

    /** An empty input is valid and should remain an empty, non-null array. */
    @Test
    void sortEmptyArrayReturnsEmptyArray() {
        Employee[] sorted = SalarySorter.sort(new Employee[0]);

        assertNotNull(sorted, "sort returned null for an empty array");
        assertEquals(0, sorted.length);
    }

    /** A one-element input requires no reordering and must retain that employee. */
    @Test
    void sortSingleEmployeeReturnsSameEmployee() {
        Employee only = new Employee(7, "X", "Y", "x@y.com", "050-7777777", "Addr7", 8000);

        Employee[] sorted = SalarySorter.sort(new Employee[]{only});

        assertNotNull(sorted, "sort returned null for a single employee");
        assertEquals(1, sorted.length);
        assertEquals(7, sorted[0].getId());
    }

    /** Sorting an already ordered input should preserve its ordering and records. */
    @Test
    void sortAlreadySortedArrayStaysSorted() {
        Employee[] input = SalarySorter.sort(createSampleArray());
        assertNotNull(input, "sort returned null");
        Employee[] backup = copyOf(input);

        Employee[] sorted = SalarySorter.sort(input);

        assertNoRecordsLost(backup, sorted);
        assertSortedBySalary(sorted);
    }

    /** Documents the sorter's current null-input behavior. */
    @Test
    void sortNullArrayReturnsNull() {
        String output = captureStandardOutput(() -> assertNull(SalarySorter.sort(null)));

        assertEquals("", output, "Null input should not produce timing output");
    }

    /** The sorter works in place and returns the original array object. */
    @Test
    void sortReturnsTheSameArrayInstanceAfterSortingDescendingInput() {
        Employee[] employees = new Employee[]{
                createEmployee(4, 400),
                createEmployee(3, 300),
                createEmployee(2, 200),
                createEmployee(1, 100)
        };

        Employee[] sorted = SalarySorter.sort(employees);

        assertSame(employees, sorted);
        assertEquals(1, sorted[0].getId());
        assertEquals(2, sorted[1].getId());
        assertEquals(3, sorted[2].getId());
        assertEquals(4, sorted[3].getId());
    }

    /** A reverse-ordered 1,000-item input exercises sorting beyond the small examples. */
    @Test
    void sortHandlesReverseOrderedInputOfOneThousandEmployees() {
        Employee[] employees = new Employee[1000];
        for (int i = 0; i < employees.length; i++) {
            employees[i] = createEmployee(i + 1, employees.length - i);
        }
        Employee[] backup = copyOf(employees);

        Employee[] sorted = SalarySorter.sort(employees);

        assertNoRecordsLost(backup, sorted);
        assertSortedBySalary(sorted);
    }

    /** The minimum and maximum generator salaries sort correctly with duplicates. */
    @Test
    void sortHandlesExactSalaryRangeBoundaries() {
        Employee[] employees = new Employee[]{
                createEmployee(1, EmployeeGenerator.MAX_SALARY),
                createEmployee(2, EmployeeGenerator.MIN_SALARY),
                createEmployee(3, EmployeeGenerator.MAX_SALARY),
                createEmployee(4, EmployeeGenerator.MIN_SALARY)
        };

        Employee[] sorted = SalarySorter.sort(employees);

        int[] expectedIds = {2, 4, 1, 3};
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], sorted[i].getId());
        }
    }

    /** Repeated IDs must not hide lost or substituted employee objects. */
    @Test
    void sortPreservesExactEmployeeObjectsWhenIdsAreRepeated() {
        Employee first = createEmployee(1, 500);
        Employee second = createEmployee(1, 200);
        Employee third = createEmployee(2, 500);
        Employee fourth = createEmployee(1, 100);
        Employee[] employees = {first, second, third, fourth};

        Employee[] sorted = SalarySorter.sort(employees);

        assertSame(employees, sorted);
        assertSame(fourth, sorted[0]);
        assertSame(second, sorted[1]);
        assertSame(first, sorted[2]);
        assertSame(third, sorted[3]);
    }

    /** Equal salaries must not change the employees' original relative order. */
    @Test
    void sortKeepsEmployeesWithEqualSalariesInTheirOriginalOrder() {
        Employee[] employees = new Employee[]{
                createEmployee(1, 500),
                createEmployee(2, 200),
                createEmployee(3, 500),
                createEmployee(4, 500),
                createEmployee(5, 100)
        };

        Employee[] sorted = SalarySorter.sort(employees);

        int[] expectedIds = {5, 2, 1, 3, 4};
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], sorted[i].getId());
        }
    }

    /** Integer boundary values verify comparisons do not overflow or truncate salaries. */
    @Test
    void sortHandlesNegativeAndExtremeSalaryValues() {
        Employee[] employees = new Employee[]{
                createEmployee(1, Integer.MAX_VALUE),
                createEmployee(2, 0),
                createEmployee(3, Integer.MIN_VALUE),
                createEmployee(4, -1)
        };

        Employee[] sorted = SalarySorter.sort(employees);

        int[] expectedIds = {3, 4, 2, 1};
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], sorted[i].getId());
        }
    }

    /** Captures standard output to ensure sorting prints only its elapsed-time value. */
    @Test
    void sortPrintsOnlyElapsedMilliseconds() {
        String output = captureStandardOutput(() -> SalarySorter.sort(createSampleArray()));

        assertValidTimingOutput(output, 1);
    }

    /** Valid inputs, including empty and single-item arrays, report a valid duration. */
    @Test
    void sortReportsTimingForEmptyAndSingleEmployeeArrays() {
        String output = captureStandardOutput(() -> {
            SalarySorter.sort(new Employee[0]);
            SalarySorter.sort(new Employee[]{createEmployee(1, 100)});
        });

        assertValidTimingOutput(output, 2);
    }
}
