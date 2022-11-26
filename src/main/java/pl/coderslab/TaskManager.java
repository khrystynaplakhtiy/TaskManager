package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;

import java.util.Scanner;


public class TaskManager {

    static String fileName = "tasks.csv";
    static String[][] currentTasks = convertTaskToTable();


    public static void main(String[] args) {

        readDisplayDataFromFile();
        interact();

    }

    public static void readDisplayDataFromFile() {
        File file = new File(fileName);
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
        File file = new File(fileName);
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
        File file = new File(fileName);
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


    public static void interact() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(displayTaskOptions());
            String option = scanner.next();
            executeSelectedOption(option, currentTasks);
            if (option.equals("exit")){
                System.out.println(ConsoleColors.RED+"Bye, bye");
                break;
            }
        }
    }

    public static void executeSelectedOption(String option, String[][] tasks) {
        switch (option) {
            case "add":
                addTask(tasks);
                break;
            case "remove":
                removeTask(tasks);
                break;
            case "list":
                listTasks(tasks);
                break;
            case "exit":
                exit(tasks);
                break;

            default:
                System.out.println(ConsoleColors.RED+"Please select a correct option");
                System.out.println(ConsoleColors.RESET);
        }

    }

    public static void addTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDesc = scanner.nextLine();
        System.out.println("Please add task due date");
        String taskDueDate = scanner.nextLine();
        System.out.println("Is your taks important true/false");
        String isImportant = scanner.nextLine();


        int newTaskLength = tasks.length + 1;
        String[][] updatedTasks = copyArray(tasks);
        updatedTasks[newTaskLength - 1][0] = taskDesc;
        updatedTasks[newTaskLength - 1][1] = taskDueDate;
        updatedTasks[newTaskLength - 1][2] = isImportant;

        currentTasks = updatedTasks;

    }

    public static String[][] copyArray(String[][] originalArray) {
        int length = originalArray.length;
        String[][] target = new String[length + 1][originalArray[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(originalArray[i], 0, target[i], 0, originalArray[i].length);
        }
        return target;

    }


    public static void listTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void removeTask(String[][] tasks) {
        System.out.println("Please select number to remove");
        Scanner scanner = new Scanner(System.in);
        String taskNumber = scanner.next();

        while (true) {
            if (StringUtils.isNumeric(taskNumber)) {
                int convertedNumber = Integer.parseInt(taskNumber);
                if (convertedNumber >= 0) {
                    currentTasks = ArrayUtils.remove(tasks, convertedNumber);
                    System.out.println("Value was successfully deleted");
                    break;
                }

            } else {
                System.out.println("Incorrect argument passed.Please give number greater or equal to 0");
                taskNumber = scanner.next();
            }

        }

    }

    public static void exit(String[][] tasks) {
        Path path1 = Paths.get(fileName);
        String[] linesToWrite = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            linesToWrite[i] = String.join(",", tasks[i]);
        }

        try {
            Files.write(path1, Arrays.asList(linesToWrite));
        } catch (IOException ex) {
            System.out.println("Could not save file - error: " + ex.getMessage());
        }
        System.out.println("File successfully saved");

    }

}





