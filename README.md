# Employee Salary Tracking System (TDD)

A Java application that generates 10,000 factory employees and sorts them by monthly salary in
ascending order. It prints the sort duration. The sorter was developed with Test-Driven Development
(TDD): Red → Green → Refactor.

## Project layout

| Location | Purpose |
|---|---|
| [MainProject/src/factory/Employee.java](MainProject/src/factory/Employee.java) | Immutable data model containing an employee's ID, personal details, and monthly salary. |
| [MainProject/src/factory/EmployeeGenerator.java](MainProject/src/factory/EmployeeGenerator.java) | Creates employees with sequential IDs, random personal details, and salaries in the configured range. |
| [MainProject/src/factory/SalarySorter.java](MainProject/src/factory/SalarySorter.java) | Sorts employee arrays by salary using a stable, in-place Merge Sort and prints elapsed time. |
| [MainProject/src/factory/Main.java](MainProject/src/factory/Main.java) | Application entry point; generates the default employee array and sorts it. |
| [TestProject/test/factory/EmployeeGeneratorTest.java](TestProject/test/factory/EmployeeGeneratorTest.java) | Tests generated employee count, fields, salary range, IDs, and invalid input. |
| [TestProject/test/factory/SalarySorterTest.java](TestProject/test/factory/SalarySorterTest.java) | Tests sorting order, record preservation, stability, edge cases, and timing output. |

`MainProject` contains the production code. `TestProject` contains the JUnit 5 tests and depends on
`MainProject`.

## Current version

The active sorter is **Version 4: Merge Sort**. Before this refactor, we implemented and tested
Bubble Sort as Version 3, then replaced it with Merge Sort to improve performance on larger arrays.
The current sorter works in place in O(n log n) time, uses a temporary buffer, and keeps employees
with equal salaries in their original relative order. It prints the elapsed time in milliseconds.
A null array is returned as null; an empty or single-item array is returned unchanged.

This repository snapshot also includes expanded JUnit coverage for reverse-ordered input, exact
salary boundaries, and rejection of a negative employee count. Existing tests cover the standard
10,000-employee case, preservation of all employee IDs, stable sorting, and other edge cases.

## Running the application and tests

The repository includes JUnit Platform Console Standalone 1.10.2 at
`lib/junit-platform-console-standalone-1.10.2.jar`. `TestProject` references this jar directly as a
test dependency; no Maven download or separate JUnit library definition is required.

- **IntelliJ IDEA**: open the repository and load both `MainProject` and `TestProject` modules.
  Run `factory.Main` from `MainProject`, or run either test class from `TestProject/test/factory`.
- **VS Code**: the workspace Java settings include `lib/**/*.jar`. Run tests from the Testing view
  after Java test discovery completes.

If IntelliJ reports that the project JDK is not defined, select an installed JDK (17 or newer).

## TDD history of `sort`

The earlier implementations are preserved as commented history in
[SalarySorter.java](MainProject/src/factory/SalarySorter.java); only Version 4 is active. The pass
counts below describe the original TDD test set at each stage, before the suite was expanded.

| Version | Stage | Implementation | Historical test result |
|---|---|---|---|
| 1 | Red | `return null;` | 4 / 12 pass; all 8 sort tests fail |
| 2 | Partial green | `return employees;` without sorting | 8 / 12 pass; the 4 sorting tests fail |
| 3 | Green | Bubble Sort, O(n²) | 12 / 12 pass; 10,000 employees sorted in about 90 ms |
| 4 | Refactor | Merge Sort, O(n log n) | 12 / 12 pass; 10,000 employees sorted in about 2–4 ms |

The timing values are historical examples and vary by computer and runtime. The sorter uses no
built-in sorting functions.

## Test coverage

- **Sorter result**: non-null output for normal inputs, expected salary order, and the same array
  instance returned after in-place sorting.
- **Record preservation**: array length is unchanged and every original employee ID appears once.
- **Input arrangements**: sample data, generated 10,000-employee data, reverse-ordered data, and
  already-sorted data.
- **Salary cases**: repeated salaries (including stability), minimum and maximum generated salaries,
  negative values, and integer extremes.
- **Sorter edge cases**: empty, one-item, and null arrays; output contains only the elapsed-time
  value.
- **Generator behavior**: default and custom counts, all required fields, salary bounds, sequential
  IDs, and rejection of negative counts.
