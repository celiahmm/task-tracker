package com.exemple;

/**
 * Enum representing the possible states of a task.
 */
public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;


    /**
     * Converts string input to TaskStatus enum.
     * Handles case-insensitive input and dash-to-underscore conversion.
     * Returns null if the input is null, empty, or invalid.
     *
     * @param status String representation of status
     * @return       TaskStatus enum or null if invalid
     */
    public static TaskStatus helper(String status) {
        if (status != null && !status.isEmpty()) {
            status = status.toUpperCase();
            status = status.replace('-', '_');

            try {
                return TaskStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status.");
            }
        }
        return null;
    }
}
