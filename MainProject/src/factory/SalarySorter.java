package factory;

/**
 * Sorts employees in ascending order of monthly salary.
 */
public class SalarySorter {

    private SalarySorter() {
        // utility class
    }

    /* =========================================================================
     * Version 1 (TDD - Red stage): skeleton returning null so the tests compile
     * and fail.
     * =========================================================================
    public static Employee[] sort(Employee[] employees) {
        return null;
    }
    */

    /* =========================================================================
     * Version 2 (TDD - passing test 1): return the array as-is, without sorting.
     * =========================================================================
    public static Employee[] sort(Employee[] employees) {
        return employees;
    }
    */

    /* =========================================================================
     * Version 3 (TDD - Green stage): Bubble Sort, O(n^2).
     * =========================================================================
    public static Employee[] sort(Employee[] employees) {
        if (employees == null) {
            return null;
        }

        long startTime = System.nanoTime();
        int n = employees.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (employees[j].getMonthlySalary() > employees[j + 1].getMonthlySalary()) {
                    Employee temp = employees[j];
                    employees[j] = employees[j + 1];
                    employees[j + 1] = temp;
                    swapped = true;
                }
            }
            // No swaps in a full pass means the array is already sorted.
            if (!swapped) {
                break;
            }
        }

        long durationNanos = System.nanoTime() - startTime;
        System.out.printf("%.3f ms%n", durationNanos / 1_000_000.0);

        return employees;
    }
    */

    // Version 4 (TDD - Refactor stage, ACTIVE): Merge Sort, O(n log n).
    // Stable (equal salaries keep their original order) and uses no built-in
    // sorting functions.
    public static Employee[] sort(Employee[] employees) {
        if (employees == null) {
            return null;
        }

        long startTime = System.nanoTime();

        if (employees.length > 1) {
            Employee[] buffer = new Employee[employees.length];
            mergeSort(employees, buffer, 0, employees.length - 1);
        }

        long durationNanos = System.nanoTime() - startTime;
        System.out.printf("%.3f ms%n", durationNanos / 1_000_000.0);

        return employees;
    }

    private static void mergeSort(Employee[] arr, Employee[] buffer, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(arr, buffer, left, middle);
        mergeSort(arr, buffer, middle + 1, right);

        if (arr[middle].getMonthlySalary() <= arr[middle + 1].getMonthlySalary()) {
            return;
        }

        merge(arr, buffer, left, middle, right);
    }

    private static void merge(Employee[] arr, Employee[] buffer, int left, int middle, int right) {
        for (int k = left; k <= right; k++) {
            buffer[k] = arr[k];
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (buffer[i].getMonthlySalary() <= buffer[j].getMonthlySalary()) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= middle) {
            arr[k++] = buffer[i++];
        }
    }
}
