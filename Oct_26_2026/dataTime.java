
/*
 - LocalDate
 - LocalTime
 - LocalDateTime
 - ZonedDateTime
 - DateTimeFormatter
*/

import java.time.LocalDate;
import java.util.Scanner;

class dataTime {

    private LocalDate date;

    // Constructor
    dataTime(LocalDate date) {
        this.date = date;
    }

    void displayMonth() {
        System.out.println("Month: " + date.getMonth());
    }

    void displayYear() {
        System.out.println("Year: " + date.getYear());
    }

    void displayDay() {
        System.out.println("Day of the week: " + date.getDayOfWeek());
    }

    void lookBackMonths(int months) {
        if (months < 0) {
            System.out.println("Please enter a positive number.");
        } else {
            LocalDate pastDate = date.minusMonths(months);
            System.out.println(months + " months before " + date
                    + " is " + pastDate);
        }
    }

    void checkLeapYear() {
        if (date.isLeapYear()) {
            System.out.println(date.getYear() + " is a leap year.");
        } else {
            System.out.println(date.getYear() + " is not a leap year.");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a date (yyyy-MM-dd): ");
        String input = scanner.nextLine();

        LocalDate userDate = LocalDate.parse(input);

        dataTime explorer = new dataTime(userDate);

        int choice;

        do {
            System.out.println(
                    "\n========== DATE EXPLORER ==========");
            System.out.println("1. Display Month");
            System.out.println("2. Display Year");
            System.out.println("3. Display Day of the Week");
            System.out.println("4. Look Back by Custom Months");
            System.out.println("5. Check Leap Year");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    explorer.displayMonth();
                    break;

                case 2:
                    explorer.displayYear();
                    break;

                case 3:
                    explorer.displayDay();
                    break;

                case 4:
                    System.out.print(
                            "How many months do you want to look back? ");
                    int months = scanner.nextInt();
                    explorer.lookBackMonths(months);
                    break;

                case 5:
                    explorer.checkLeapYear();
                    break;

                case 6:
                    System.out.println("Exiting Date Explorer. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        scanner.close();
    }
}