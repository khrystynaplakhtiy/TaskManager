package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        readDisplayDataFromFile();
        System.out.println(displayTaskOptions());

        System.out.println(Arrays.deepToString(convertTaskToTable()));
        String[][] tasks = convertTaskToTable();
        String[][] updatedTask = addTask(tasks);
        System.out.println(Arrays.deepToString(updatedTask));
        listTasks(updatedTask);

    }

    public static void readDisplayDataFromFile() {
        File file = new File("tasks.csv");
        StringBuilder reading = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                reading.append(scan.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        System.out.println(reading);

    }

    public static String displayTaskOptions() {
        String taskHeader = ConsoleColors.BLUE + "Please select an option";
        String[] taskOptions = {"add", "remove", "list", "exit"};
        String finalOptionsString = taskHeader + "\n" + ConsoleColors.WHITE;
        for (String task : taskOptions
        ) {
            finalOptionsString = finalOptionsString + task + "\n";

        }
        return finalOptionsString;

    }

    public static String[][] convertTaskToTable() {
        File file = new File("tasks.csv");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int rowCount = countRows();

        String[][] tasks = new String[rowCount][3];

        while (scan.hasNextLine()) {
            for (int i = 0; i < rowCount; i++) {
                String line = scan.nextLine();
                String[] splittedLine = line.split(",");
                for (int j = 0; j < splittedLine.length; j++) {
                    tasks[i][j] = splittedLine[j];

                }
            }
        }
        return tasks;
    }

    public static int countRows() {
        File file = new File("tasks.csv");
        int rowCount = 0;
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                rowCount = rowCount + 1;
                scan.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return rowCount;
    }

    public static String getTaskInput(){
        System.out.println("Please select task");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }

//    public static void runOptionFromInput(String input){
//        switch (input) {
//            case "add":
//                addTask();
//                break;
//            case "remove":
//                removeTask();
//            case "list":
//                readDisplayDataFromFile();
//            case "exit":
//                exit();
//
//            default:
//                System.out.println("Please select a correct option.");
//        }
//
//    }
    public static String[][] addTask(String[][] tasks){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDesc = scanner.nextLine();
        System.out.println("Please add task due date");
        String taskDueDate = scanner.nextLine();
        System.out.println("Is your taks important true/false");
        String isImportant = scanner.nextLine();


        int newTaskLength = tasks.length+1;
        String[][] updatedTasks = copyArray(tasks);
        updatedTasks[3][0] = taskDesc;
        updatedTasks[newTaskLength-1][1] = taskDueDate;
        updatedTasks[newTaskLength-1][2] = isImportant;

        return updatedTasks;

    }

    public static String[][] copyArray(String[][] originalArray){
        int length = originalArray.length;
        String[][] target = new String[length+1][originalArray[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(originalArray[i], 0, target[i], 0, originalArray[i].length);
        }
        return target;

    }


    public static void listTasks(String[][] tasks){
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j< tasks[i].length; j++) {
                System.out.print(tasks[i][j]+ " ");
            }
            System.out.println();
        }

    }


}

