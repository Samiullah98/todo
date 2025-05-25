package utils;

import models.Task;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskFileHandler {
    private static final String TASKS_DIRECTORY = "tasks";

    public static void saveTask(String username, Task task) {
        try {
            createTasksDirectoryIfNotExists();
            String fileName = TASKS_DIRECTORY + "/" + username + "_tasks.txt";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                // Format: title|description|dueDate|priority|completed
                writer.write(String.format("%s|%s|%s|%s|%b%n",
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.isCompleted()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks(String username) {
        List<Task> tasks = new ArrayList<>();
        String fileName = TASKS_DIRECTORY + "/" + username + "_tasks.txt";
        
        if (!new File(fileName).exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Task task = new Task(
                        parts[0], // title
                        parts[1], // description
                        LocalDate.parse(parts[2]), // dueDate
                        parts[3]  // priority
                    );
                    task.setCompleted(Boolean.parseBoolean(parts[4]));
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static void updateTasks(String username, List<Task> tasks) {
        try {
            createTasksDirectoryIfNotExists();
            String fileName = TASKS_DIRECTORY + "/" + username + "_tasks.txt";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (Task task : tasks) {
                    writer.write(String.format("%s|%s|%s|%s|%b%n",
                        task.getTitle(),
                        task.getDescription(),
                        task.getDueDate(),
                        task.getPriority(),
                        task.isCompleted()
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createTasksDirectoryIfNotExists() {
        File directory = new File(TASKS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
} 