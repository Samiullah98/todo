package com.todoapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private GridPane taskGrid;
    @FXML
    private Label welcomeLabel;

    private String currentUser;
    private int currentRow = 0;

    public void setCurrentUser(String username) {
        this.currentUser = username;
        welcomeLabel.setText("Hoş geldin, " + username + "!");
    }

    @FXML
    private void showAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todoapp/views/addTask.fxml"));
            Parent root = loader.load();
            
            AddTaskController controller = loader.getController();
            controller.setMainController(this);
            controller.setCurrentUser(currentUser);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/todoapp/styles/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {
        taskGrid.getChildren().clear();
        currentRow = 0;
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(currentUser + "_tasks.txt"));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    addTaskToGrid(parts[0], Boolean.parseBoolean(parts[1]));
                }
            }
        } catch (IOException e) {
            // Dosya henüz oluşturulmamış olabilir
        }
    }

    public void addTaskToGrid(String content, boolean completed) {
        HBox taskBox = new HBox(10);
        taskBox.getStyleClass().add("task-box");

        Label taskLabel = new Label(content);
        if (completed) {
            taskLabel.getStyleClass().add("completed-task");
        }
        HBox.setHgrow(taskLabel, Priority.ALWAYS);

        Button completeButton = new Button(completed ? "Geri Al" : "Tamamla");
        completeButton.getStyleClass().add("add-button");
        completeButton.setOnAction(e -> toggleTaskComplete(taskLabel, completeButton));

        Button deleteButton = new Button("Sil");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> deleteTask(taskBox, content));

        taskBox.getChildren().addAll(taskLabel, completeButton, deleteButton);

        taskGrid.add(taskBox, currentRow % 2, currentRow / 2);
        currentRow++;
        
        saveTasksToFile();
    }

    private void toggleTaskComplete(Label taskLabel, Button completeButton) {
        boolean isCompleted = taskLabel.getStyleClass().contains("completed-task");
        if (isCompleted) {
            taskLabel.getStyleClass().remove("completed-task");
            completeButton.setText("Tamamla");
        } else {
            taskLabel.getStyleClass().add("completed-task");
            completeButton.setText("Geri Al");
        }
        saveTasksToFile();
    }

    private void deleteTask(HBox taskBox, String content) {
        taskGrid.getChildren().remove(taskBox);
        saveTasksToFile();
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentUser + "_tasks.txt"))) {
            List<String> tasks = new ArrayList<>();
            taskGrid.getChildren().forEach(node -> {
                if (node instanceof HBox) {
                    HBox taskBox = (HBox) node;
                    Label taskLabel = (Label) taskBox.getChildren().get(0);
                    boolean isCompleted = taskLabel.getStyleClass().contains("completed-task");
                    tasks.add(taskLabel.getText() + "|" + isCompleted);
                }
            });
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 