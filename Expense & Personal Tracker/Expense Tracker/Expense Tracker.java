import java.util.Scanner;

class Expense {
    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    // Getters for expense attributes
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class ExpenseTracker {
    private static final int MAX_EXPENSES = 100; // Maximum number of expenses
    private Expense[] expenses = new Expense[MAX_EXPENSES];
    private int expenseCount = 0;
    
    public static void main(String[] args) {
        PersonalAssistant personalAssistant = new PersonalAssistant();
        personalAssistant.run();
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Personal Assistant Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. Update Expense");
            System.out.println("3. Add Category");
            System.out.println("4. View Expenses");
            System.out.println("5. Input Salary");
            System.out.println("6. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (expenseCount < MAX_EXPENSES) {
                        System.out.print("Enter category: ");
                        String category = scanner.next();
                        System.out.print("Enter amount: ");
                        double amount = scanner.nextDouble();
                        expenses[expenseCount] = new Expense(category, amount);
                        expenseCount++;
                        System.out.println("Expense added.");
                    } else {
                        System.out.println("Maximum number of expenses reached.");
                    }
                    break;

                case 2:
                    // Implement the logic to update an expense by category or other criteria.
                    // You can use a loop to search for the expense in the array and then update it.
                    // Not included in this basic example.
                    break;

                case 3:
                    // Implement the logic to add a category. You can maintain a list of categories.
                    // Not included in this basic example.
                    break;

                case 4:
                    System.out.println("Expenses:");
                    for (int i = 0; i < expenseCount; i++) {
                        System.out.println("Category: " + expenses[i].getCategory() +
                                ", Amount: " + expenses[i].getAmount());
                    }
                    break;

                case 5:
                    // Implement the logic to input salary and perform any salary-related calculations.
                    // Not included in this basic example.
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting Personal Assistant.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}