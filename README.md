# Employee Salary Tracking System (TDD)

A Java system for a factory that generates 10,000 random employees and sorts them by
monthly salary (ascending). The program prints only the sort duration. The `sort` function was built
with TDD (Red → Green → Refactor). Bubble Sort came first, then a refactor to Merge Sort.

## Projects

| Project | Contents |
|---|---|
| [MainProject/](MainProject/) | Plain Java project: `Employee`, `EmployeeGenerator`, `SalarySorter`, `Main` |
| [TestProject/](TestProject/) | JUnit 5 test project: `SalarySorterTest`, `EmployeeGeneratorTest`. It loads `MainProject` as a module dependency. |

## Running in IntelliJ

1. **Main program**: open the `MainProject` folder as a project and run `factory.Main`.
2. **Tests**: open the `TestProject` folder as a project. It includes the `MainProject`
   module and a JUnit 5 Maven library (`org.junit.jupiter:junit-jupiter:5.10.2`), which IntelliJ
   downloads automatically. Right-click `test/factory` → **Run 'Tests in factory'**.

If IntelliJ shows "Project JDK is not defined", choose any installed JDK (17 or newer).

## TDD history of `sort`

All earlier versions are kept as comments in
[SalarySorter.java](MainProject/src/factory/SalarySorter.java). Only Version 4 is active.

| Version | Stage | Implementation | Test result |
|---|---|---|---|
| 1 | Red | `return null;` | 4 / 12 pass (all 8 sort tests fail) |
| 2 | Partial green | `return employees;` (no sorting) | 8 / 12 pass (the 4 sorting tests fail) |
| 3 | Green | Bubble Sort, O(n²) | 12 / 12 pass (10,000 employees sorted in ~90 ms) |
| 4 | Refactor | Merge Sort, O(n log n) | 12 / 12 pass (10,000 employees sorted in ~2–4 ms) |

`sort` prints its own execution time, measured with `System.nanoTime()`. No built-in sorting
functions are used.

## What the tests check

- **Return value**: `sort` returns non-null.
- **No lost records**: the length is unchanged, and every original employee ID is matched
  one-to-one in the result.
- **Sorted**: every salary is less than or equal to the next one. This is checked on a known
  sample and on 10,000 generated employees.
- **Edge cases**: empty array, single employee, already-sorted input.
- **Generator**: produces 10,000 employees by default, all 7 fields are filled, salaries are
  within 3,000–15,000, and IDs are unique.
