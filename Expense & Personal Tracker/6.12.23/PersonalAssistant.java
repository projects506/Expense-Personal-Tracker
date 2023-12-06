import SQLLogin.*;
import ExpenseTracker.*;
import EventTracker.*;
import java.util.Scanner;
import java.io.File;

class PersonalAssistant extends SQLLogin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tries = 5;
        boolean PARun = true;
        File fileEX = new File(
                "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Expenses Record.txt");
        fileEX.delete();
        File fileET = new File(
                "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Events Record.txt");
        fileET.delete();
        while (PARun) {
            System.out.println("Welcome to Your Personal Assistant");
            System.out.println("----x----x----x----x----x----x---");
            System.out.println(
                    "A Group Project By Group-7 \nNishant-220313\nAryyan Ahlawat-220338\nRaghav Arora-220370\nViresh Chauhan-220422");
            System.out.println("----x----x----x----x-");
            System.out.println("Please Login to continue forward");
            System.out.println("Enter Username: ");
            String USER = sc.nextLine();
            System.out.println("Enter Password: ");
            String PASSWORD = sc.nextLine();
            SQLLogin sql = new SQLLogin(USER, PASSWORD);
            if (sql.login == true) {
                System.out.println("Login Successfull.");
                System.out.println("What Can I help you with?");
                while (PARun) {
                    System.out.println("1. Manage Expenses \n2. Manage Calendar \n3.Quit");
                    System.out.print("Enter your choice: ");
                    int PAchoice = sc.nextInt();
                    switch (PAchoice) {
                        case 1:
                            boolean EXrun = true;

                            while (EXrun) {
                                ExpenseTracker ETM = new ExpenseTracker();
                                System.out.println("Expense Tracker/Manager Menu:");
                                System.out.println("1. Add Expense");
                                System.out.println("2. View Expenses");
                                System.out.println("3. Update");
                                System.out.println("4. Delete");
                                System.out.println("5. Quit");
                                System.out.print("Enter your choice: ");
                                int choice = sc.nextInt();

                                switch (choice) {
                                    case 1:
                                    try{
                                        System.out.print("Enter category: ");
                                        String category = sc.next();
                                    }
                                    catch(IOMissMatchException e)
                                        System.out.print("Enter amount: ");
                                        double amount = sc.nextDouble();
                                        ETM.insertSQL(category, amount);
                                        break;

                                    case 2:

                                        boolean runningView = true;
                                        while (runningView) {
                                            System.out.println("1. View All");
                                            System.out.println("2. View by Date");
                                            System.out.println("3. View by Category");
                                            System.out.println("4. Previous Menu");
                                            int choiceView = sc.nextInt();
                                            switch (choiceView) {
                                                case 1:
                                                    System.out.println("Do you Wish to print?(true of fale)");
                                                    boolean print = sc.nextBoolean();

                                                    ETM.viewSQL(print);

                                                    break;
                                                case 2:
                                                    System.out.print("Enter start date (yyyy-MM-dd ): ");
                                                    String startDate = sc.nextLine();
                                                    System.out.print("Enter end date (yyyy-MM-dd): ");
                                                    String endDate = sc.nextLine();
                                                    System.out.println("Do you Wish to print?(true of fale)");
                                                    print = sc.nextBoolean();
                                                    try {

                                                        ETM.viewSQL(startDate, endDate, print);
                                                    } catch (Exception ex) {
                                                        System.out
                                                                .println("Invalid date format. Please use yyyy-MM-dd ");
                                                    }
                                                    break;
                                                case 3:
                                                    System.out.print("Enter category: ");
                                                    String categoryInput = sc.next();
                                                    System.out.println("Do you Wish to print?(true of fale)");
                                                    print = sc.nextBoolean();
                                                    ETM.viewSQL(categoryInput, print);
                                                    break;
                                                case 4:
                                                    System.out.println("Going back to the previous menu.");
                                                    runningView = false;

                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please try again.");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        boolean update = true;
                                        while (update) {
                                            System.out.println("Enter your choice: ");
                                            System.out.println("1. Update Amount");
                                            System.out.println("2. Update by Category");
                                            System.out.println("3. Exit to last menu");
                                            int choiceUpdate = sc.nextInt();
                                            switch (choiceUpdate) {
                                                case 1:
                                                    System.out.println("Enter id");
                                                    int id = sc.nextInt();
                                                    System.out.println("Enter Amount");
                                                    int amountUpdate = sc.nextInt();
                                                    ETM.updateSQL(amountUpdate, id);
                                                    break;
                                                case 2:
                                                    System.out.println("Enter id");
                                                    id = sc.nextInt();
                                                    System.out.println("Enter Category");
                                                    String categoryUpdate = sc.nextLine();
                                                    ETM.updateSQL(categoryUpdate, id);
                                                    break;
                                                case 3:
                                                    System.out.println("Exiting to last menu");
                                                    update = false;
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice try again");
                                                    break;
                                            }
                                        }
                                        break;

                                    case 4:
                                        boolean delete = true;
                                        while (delete) {
                                            System.out.println("Enter your choice: ");
                                            System.out.println("1. Delete By ID");
                                            System.out.println("2. Delete By Dates");
                                            System.out.println("3. Delete by Category And ID");
                                            System.out.println("4. Delete entire  Category");
                                            System.out.println("5. Delete all entries");
                                            System.out.println("6. Exit to last menu");
                                            int choiceDel = sc.nextInt();
                                            switch (choiceDel) {
                                                case 1:
                                                    System.out.println("Enter your ID");
                                                    int id = sc.nextInt();
                                                    ETM.deleteSQL(id);
                                                    break;
                                                case 2:
                                                    System.out.println("Enter your start date (yyyy-MM-dd ):");
                                                    String startDate = sc.nextLine();
                                                    System.out.println("Enter your end date (yyyy-MM-dd ):");
                                                    String endDate = sc.nextLine();
                                                    ETM.deleteSQL(startDate, endDate);
                                                    break;
                                                case 3:
                                                    System.out.println("Enter your Category:");
                                                    String categoryDelete = sc.nextLine();
                                                    System.out.println("Enter ID:");
                                                    id = sc.nextInt();
                                                    ETM.deleteSQL(categoryDelete, id);
                                                    break;
                                                case 4:
                                                    System.out.println("Enter your Category:");
                                                    categoryDelete = sc.nextLine();
                                                    ETM.deleteSQL(categoryDelete);
                                                    break;
                                                case 5:
                                                    System.out.println(
                                                            "Are you sure you want to continue to delete all entries in the database(true/false):");
                                                    boolean choiceYN = sc.nextBoolean();
                                                    ETM.deleteSQL(choiceYN);
                                                    break;
                                                case 6:
                                                    delete = false;
                                                    System.out.println("Exiting to the previous menu");
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice try again");
                                                    break;
                                            }
                                        }
                                        break;

                                    case 5:
                                        ETM.closeConnection();
                                        EXrun = false;
                                        System.out.println("Exiting Personal Assistant.");
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;

                                }

                            }
                            break;
                        case 2:
                            EventTracker ET = new EventTracker();
                            boolean isRunning = true;
                            while (isRunning) {
                                System.out.println(
                                        "1. Add Event\n2. Remove Event\n3. Update Event\n4. View Events\n5. Quit");
                                System.out.print("Enter your choice: ");
                                int choice = sc.nextInt();
                                sc.nextLine();

                                switch (choice) {
                                    case 1:
                                        System.out.print("Enter event date (YYYY-MM-DD): ");
                                        String date = sc.nextLine();
                                        System.out.print("Enter event name: ");
                                        String event = sc.nextLine();
                                        System.out.print("Enter event description: ");
                                        String description = sc.nextLine();

                                        ET.insertSQL(date, event, description);
                                        break;
                                    case 2:
                                        System.out.print("Enter event name to remove: ");
                                        String eventToRemove = sc.nextLine();
                                        ET.deleteSQL(eventToRemove);
                                        break;
                                    case 3:
                                        System.out.print("Enter event name to update: ");
                                        String eventToUpdate = sc.nextLine();
                                        System.out.print("Enter updated event name: ");
                                        String updatedEvent = sc.nextLine();
                                        System.out.print("Enter updated event description: ");
                                        String updatedDescription = sc.nextLine();
                                        ET.updateSQL(eventToUpdate, updatedEvent, updatedDescription);
                                        break;
                                    case 4:
                                        System.out.println("Do you Wish to print?(true of fale)");
                                        boolean print = sc.nextBoolean();

                                        ET.viewSQL(print);
                                        break;
                                    case 5:
                                        System.out.println("Program closed.");
                                        ET.closeConnection();
                                        isRunning = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Exiting the Program.");
                            sc.close();

                            PARun = false;
                            break;
                        default:
                            System.out.println("Invalid Input. Please try again.");
                            break;
                    }

                }
            } else {
                --tries;
                System.out.println("Wrong Username or Password. Please try again you have " + tries + " left.");
                if (tries == 0) {
                    System.out.println(
                            "You have entered wrong password too many times, restart the application to try again");
                    sc.close();
                    PARun = false;

                }
            }
        }

    }
}
