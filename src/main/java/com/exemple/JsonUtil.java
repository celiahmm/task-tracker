package com.exemple;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* Simple JSON parser for Task objects without external dependencies */
public class JsonUtil {

    /* Reads tasks from JSON file */
    public static List<Task> readTasksFromFile(String filename) {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return tasks;
        }

        try {
            String content = Files.readString(Paths.get(filename));
            content = content.trim();

            if (content.isEmpty() || content.equals("[]")) {
                return tasks;
            }

            // Remove opening and closing brackets
            content = content.substring(1, content.length() - 1).trim();

            if (content.isEmpty()) {
                return tasks;
            }

            // Split by task objects
            List<String> taskJsons = splitTaskObjects(content);

            for (String taskJson : taskJsons) {
                Task task = parseTask(taskJson);
                if (task != null) {
                    tasks.add(task);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return tasks;
    }

    /* Splits JSON array into individual task objects */
    private static List<String> splitTaskObjects(String content) {
        List<String> result = new ArrayList<>();
        int braceCount = 0;
        StringBuilder currentObject = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (c == '{') {
                braceCount++;
                currentObject.append(c);
            } else if (c == '}') {
                braceCount--;
                currentObject.append(c);

                if (braceCount == 0) {
                    result.add(currentObject.toString().trim());
                    currentObject = new StringBuilder();
                }
            } else if (braceCount > 0) {
                currentObject.append(c);
            }
        }

        return result;
    }

    /* Parses a single task from JSON string */
    private static Task parseTask(String json) {
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                json = json.substring(1);
            }
            if (json.endsWith("}")) {
                json = json.substring(0, json.length() - 1);
            }

            int id = 0;
            String description = "";
            TaskStatus status = TaskStatus.TODO;
            String createdAt = "";
            String updatedAt = "";

            String[] pairs = splitJsonPairs(json);

            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length != 2) continue;

                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim();

                // Remove quotes from string values
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                switch (key) {
                    case "id":
                        id = Integer.parseInt(value);
                        break;
                    case "description":
                        description = value;
                        break;
                    case "status":
                        status = TaskStatus.valueOf(value.toUpperCase().replace("-", "_"));
                        break;
                    case "createdAt":
                    case "createAt":
                        createdAt = value;
                        break;
                    case "updatedAt":
                    case "updateAt":
                        updatedAt = value;
                        break;
                }
            }

            return new Task(id, description, status, createdAt, updatedAt);

        } catch (Exception e) {
            System.err.println("Error parsing task: " + e.getMessage());
            return null;
        }
    }

    /* Splits JSON object into key-value pairs */
    private static String[] splitJsonPairs(String json) {
        List<String> pairs = new ArrayList<>();
        StringBuilder currentPair = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
                currentPair.append(c);
            } else if (c == ',' && !inQuotes) {
                pairs.add(currentPair.toString().trim());
                currentPair = new StringBuilder();
            } else {
                currentPair.append(c);
            }
        }

        if (currentPair.length() > 0) {
            pairs.add(currentPair.toString().trim());
        }

        return pairs.toArray(new String[0]);
    }

    /* Writes tasks to JSON file */
    public static void writeTasksToFile(String filename, List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("[\n");

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + task.getId() + ",\n");
                writer.write("    \"description\": \"" + escapeJson(task.getDescription()) + "\",\n");
                writer.write("    \"status\": \"" + task.getStatus().toString().toLowerCase() + "\",\n");
                writer.write("    \"createdAt\": \"" + escapeJson(task.getCreateAt()) + "\",\n");
                writer.write("    \"updatedAt\": \"" + escapeJson(task.getUpdateAt()) + "\"\n");
                writer.write("  }");

                if (i < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");

        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }

    /* Escapes special characters for JSON */
    private static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}

