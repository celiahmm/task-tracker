package com.exemple;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

/* Manages tasks with CRUD operations and JSON persistence */
public class TaskManager {

    private List<Task> tasks;
    private static final String JSON_FILE = "tasks.json";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasksFromFile();
    }

    /* Loads tasks from JSON file (deserialization) */
    private void loadTasksFromFile() {
        tasks = JsonUtil.readTasksFromFile(JSON_FILE);
    }

    /* Saves tasks to JSON file (serialization) */
    private void saveTasksToFile() {
        JsonUtil.writeTasksToFile(JSON_FILE, tasks);
    }

    /* Adds a new task and returns its ID */
    public int addTask(String description) {
        // new ID
        int newId;
        if (tasks.isEmpty()) {
            newId = 1;
        } else {
            newId = tasks.stream()
                    .mapToInt(Task::getId)
                    .max()
                    .getAsInt() + 1;
        }

        TaskStatus newStatus = TaskStatus.TODO;
        // createAt & updateAt
        String currentDateTime = LocalDateTime.now().format(formatter);

        // create and add new task
        Task task = new Task(newId, description, newStatus, currentDateTime, currentDateTime);

        tasks.add(task);
        saveTasksToFile();

        return newId;
    }

    /* Deletes a task by ID and reassigns remaining IDs */
    public void deleteTask(int id) {
        loadTasksFromFile();
        tasks.removeIf(task -> task.getId() == id);
        reassignIds();
        saveTasksToFile();
    }

    /* Updates task description by ID */
    public void updateTask(int id, String description) {
        loadTasksFromFile();
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
            }
        }
        saveTasksToFile();
    }

    /* Marks a task as in progress */
    public void markAsInProgress(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
        saveTasksToFile();
    }

    /* Mark a task as done */
    public void markAsDone(int id) {
        loadTasksFromFile();
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.DONE);
            }
        }
        saveTasksToFile();
    }


    /* Displays tasks in a formatted table */
    private void displayTaskList(List<Task> taskList) {
        // calculate optimal column widths
        int idWidth = 2;
        int descWidth = 11;
        int statusWidth = 6;
        int creationWidth = 20;
        int updateWidth = 20;

        for (Task task : taskList) {
            descWidth = Math.max(descWidth, task.getDescription().length());
            statusWidth = Math.max(statusWidth, task.getStatus().toString().length());
            creationWidth = Math.max(creationWidth, task.getCreateAt().length());
            updateWidth = Math.max(updateWidth, task.getUpdateAt().length());
        }

        descWidth += 2; // safety margin


        String lineFormat = "| %-" + idWidth + "s | %-" + descWidth + "s | %-" +
                statusWidth + "s | %-" + creationWidth + "s | %-" + updateWidth + "s |";

        int totalWidth = idWidth + descWidth + statusWidth + creationWidth + updateWidth + 16;
        String separator = "-".repeat(totalWidth);

        // display table
        System.out.println(separator);
        System.out.printf(lineFormat, "ID", "Description", "Status", "Date of creation", "Last updated");
        System.out.println();
        System.out.println(separator);

        // display task data
        for (Task task : taskList) {
            System.out.printf(lineFormat,
                    task.getId(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getCreateAt(),
                    task.getUpdateAt());
            System.out.println();
        }

        System.out.println(separator);
    }


    /* Display all tasks */
    public void displayTasks () {
        loadTasksFromFile();
        displayTaskList(tasks);
    }
    /* Display all tasks by status */
    public void displayTasksByStatus(TaskStatus status){
        loadTasksFromFile();

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found with status " + status + ".");
            return;
        }

        displayTaskList(filteredTasks);
    }

    public void printUsage() {
        System.out.println("""
                The list of commands and their usage is given below:
                
                # Adding a new task
                task-cli add "Buy groceries"
                # Output: Task added successfully (ID: 1)
                
                # Updating and deleting tasks
                task-cli update 1 "Buy groceries and cook dinner"
                task-cli delete 1
                
                # Marking a task as in progress or done
                task-cli mark-in-progress 1
                task-cli mark-done 1
                
                # Listing all tasks
                task-cli list
                
                # Listing tasks by status
                task-cli list done
                task-cli list todo
                task-cli list in-progress
                """);
    }

    /* Reassigns IDs starting from 1 */
    public void reassignIds() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setId(i + 1);
        }
    }
}
