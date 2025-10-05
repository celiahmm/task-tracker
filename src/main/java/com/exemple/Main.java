package com.exemple;

public class Main {
    public static void main(String[] args) {

        TaskManager tasks = new TaskManager();

        if (args.length == 0) {
            System.out.println("No command provided.");
            tasks.printUsage();
            return;
        }

        String command = args[0];
        switch (command) {
            case "add":
                if (args.length > 1) {
                    int taskId = tasks.addTask(args[1]);
                    System.out.println("Task added successfully (ID: " + taskId + ")");
                } else {
                    System.out.println("Usage: add \"Task description\"");
                }
                break;
            case "update":
                if (args.length > 2) {
                    try {
                        tasks.updateTask(Integer.parseInt(args[1]), args[2]);
                        System.out.println("Task updated successfully!");
                    } catch (NumberFormatException e) {
                        System.err.println("Error: ID must be a valid number");
                    }
                } else {
                    System.out.println("Usage: update <id> \"Description\"");
                }
                break;
            case "delete":
                if (args.length > 1) {
                    try {
                        tasks.deleteTask(Integer.parseInt(args[1]));
                        System.out.println("Task deleted successfully!");
                    } catch (NumberFormatException e) {
                        System.err.println("Error: ID must be a valid number");
                    }
                } else {
                    System.out.println("Usage: delete <id>");
                }
                break;
            case "mark-in-progress":
                if (args.length > 1) {
                    try {
                        tasks.markAsInProgress(Integer.parseInt(args[1]));
                        System.out.println("Task marked as in progress!");
                    } catch (NumberFormatException e) {
                        System.err.println("Error: ID must be a valid number");
                    }
                } else {
                    System.out.println("Usage: mark-in-progress <id>");
                }
                break;
            case "mark-done":
                if (args.length > 1) {
                    try {
                        tasks.markAsDone(Integer.parseInt(args[1]));
                        System.out.println("Task marked as done!");
                    } catch (NumberFormatException e) {
                        System.err.println("Error: ID must be a valid number");
                    }
                } else {
                    System.out.println("Usage: mark-done <id>");
                }
                break;
            case "list":
                if (args.length > 1) {
                    TaskStatus status = TaskStatus.helper(args[1]);
                    if (status != null) {
                        tasks.displayTasksByStatus(status);
                    } else {
                        System.out.println("Invalid status. Use: todo, in-progress, or done");
                    }
                } else {
                    tasks.displayTasks();
                }
                break;

            default:
                System.out.println(command + " is not a command.");
                tasks.printUsage();
        }
    }
}
