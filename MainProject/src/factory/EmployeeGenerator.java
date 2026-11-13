package factory;

import java.util.Random;

/**
 * Generates employees with random personal details and a random monthly
 * salary between {@link #MIN_SALARY} and {@link #MAX_SALARY} NIS (inclusive).
 */
public class EmployeeGenerator {

    public static final int NUM_EMPLOYEES = 10000;
    public static final int MIN_SALARY = 3000;
    public static final int MAX_SALARY = 15000;

    /** IDs are a running counter starting here, so they are always unique. */
    public static final int FIRST_ID = 100000000;

    private static final String[] FIRST_NAMES = {
            "Daniel", "Noa", "Itay", "Maya", "Yossi", "Ronit", "Omer", "Yael",
            "Avi", "Shira", "Eitan", "Tamar", "Gal", "Lior", "Dana", "Roni"
    };

    private static final String[] LAST_NAMES = {
            "Cohen", "Levi", "Mizrachi", "Peretz", "Biton", "Dahan", "Avraham",
            "Friedman", "Katz", "Azulay", "Shapiro", "Ben David"
    };

    private static final String[] CITIES = {
            "Tel Aviv", "Jerusalem", "Haifa", "Ashdod", "Beer Sheva", "Gedera",
            "Rishon LeZion", "Netanya", "Holon", "Petah Tikva"
    };

    private static final String[] STREETS = {
            "Herzl", "Rothschild", "Ben Gurion", "Weizmann", "Jabotinsky",
            "HaNasi", "Allenby", "Dizengoff", "Bialik", "HaPalmach"
    };

    private static final Random RANDOM = new Random();

    private EmployeeGenerator() {
        // utility class
    }

    /** Generates the default number of employees (10,000). */
    public static Employee[] generateEmployees() {
        return generateEmployees(NUM_EMPLOYEES);
    }

    /** Generates {@code count} employees with random details. */
    public static Employee[] generateEmployees(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must not be negative: " + count);
        }

        Employee[] employees = new Employee[count];

        for (int i = 0; i < count; i++) {
            int id = FIRST_ID + i;
            String firstName = pickRandom(FIRST_NAMES);
            String lastName = pickRandom(LAST_NAMES);
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase().replace(" ", "")
                    + id + "@factory.co.il";
            String phone = "05" + RANDOM.nextInt(10) + "-" + (1000000 + RANDOM.nextInt(9000000));
            String address = pickRandom(STREETS) + " " + (1 + RANDOM.nextInt(150))
                    + ", " + pickRandom(CITIES);
            int salary = MIN_SALARY + RANDOM.nextInt(MAX_SALARY - MIN_SALARY + 1);

            employees[i] = new Employee(id, firstName, lastName, email, phone, address, salary);
        }

        return employees;
    }

    private static String pickRandom(String[] pool) {
        return pool[RANDOM.nextInt(pool.length)];
    }
}
