package factory;

/**
 * Sorts employee arrays by monthly salary in ascending order.
 *
 * The active implementation is stable Merge Sort, with O(n log n) time and
 * O(n) auxiliary space. Sorting is performed in place on the supplied array.
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

    /**
     * Sorts the given array in place and prints the elapsed time in milliseconds.
     *
     * Equal-salary employees retain their input order. A {@code null} array is
     * returned unchanged and does not produce timing output.
     *
     * @param employees array to sort
     * @return the same array instance, sorted by ascending monthly salary, or
     *         {@code null} when the input is {@code null}
     */
    public static Employee[] sort(Employee[] employees) {
        if (employees == null) {
            return null;
        }

        long startTime = System.nanoTime();

        // Arrays of length zero or one are already sorted.
        if (employees.length > 1) {
            Employee[] buffer = new Employee[employees.length];
            mergeSort(employees, buffer, 0, employees.length - 1);
        }

        long durationNanos = System.nanoTime() - startTime;
        System.out.printf("%.3f ms%n", durationNanos / 1_000_000.0);

        return employees;
    }

    /**
     * Recursively sorts the inclusive range {@code [left, right]}.
     *
     * @param arr array being sorted
     * @param buffer reusable temporary storage for merging
     * @param left first index in the current range
     * @param right last index in the current range
     */
    private static void mergeSort(Employee[] arr, Employee[] buffer, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(arr, buffer, left, middle);
        mergeSort(arr, buffer, middle + 1, right);

        // The two halves are already ordered relative to each other; no merge is needed.
        if (arr[middle].getMonthlySalary() <= arr[middle + 1].getMonthlySalary()) {
            return;
        }

        merge(arr, buffer, left, middle, right);
    }

    /**
     * Merges two sorted adjacent ranges back into {@code arr}.
     *
     * Choosing from the left range on equal salaries preserves stable order.
     *
     * @param arr array receiving the merged range
     * @param buffer temporary storage used to avoid overwriting unread values
     * @param left first index in the left range
     * @param middle last index in the left range
     * @param right last index in the right range
     */
    private static void merge(Employee[] arr, Employee[] buffer, int left, int middle, int right) {
        // Copy both halves before writing merged values into the original array.
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

        // Any remaining values can only be in the left half; the right half is
        // already in its final position when it is exhausted first.
        while (i <= middle) {
            arr[k++] = buffer[i++];
        }
    }
}
