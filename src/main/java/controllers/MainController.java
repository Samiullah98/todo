package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import models.Task;
import utils.TaskFileHandler;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import java.util.ArrayList;

public class MainController {
    @FXML
    private Button addTaskButton;

    @FXML
    private GridPane taskGrid;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private int currentRow = 0;
    private String currentUser;

    @FXML
    private void openAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addTask.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(scene);
            
            AddTaskController controller = loader.getController();
            controller.setMainController(this);
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
        loadUserTasks();
    }

    private void loadUserTasks() {
        tasks.clear();
        taskGrid.getChildren().clear();
        currentRow = 0;
        
        tasks.addAll(TaskFileHandler.loadTasks(currentUser));
        for (Task task : tasks) {
            displayTask(task);
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        TaskFileHandler.saveTask(currentUser, task);
        displayTask(task);
    }

    private void displayTask(Task task) {
        // Create task card
        VBox taskCard = new VBox(10);
        taskCard.getStyleClass().addAll("task-card", "gold-background");
        taskCard.setPadding(new Insets(15));
        
        // Title
        Label titleLabel = new Label(task.getTitle());
        titleLabel.getStyleClass().add("task-title");
        
        // Description
        Label descLabel = new Label(task.getDescription());
        descLabel.getStyleClass().add("description-label");
        descLabel.setWrapText(true);
        
        // Due Date
        Label dateLabel = new Label("Due: " + task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateLabel.getStyleClass().add("date-label");
        
        // Priority with background
        Label priorityLabel = new Label("Priority: " + task.getPriority());
        priorityLabel.getStyleClass().add("priority-" + task.getPriority().toLowerCase());
        
        // Complete checkbox with custom styling
        CheckBox completeCheck = new CheckBox("Complete");
        completeCheck.setSelected(task.isCompleted());
        completeCheck.setOnAction(e -> {
            task.setCompleted(completeCheck.isSelected());
            TaskFileHandler.updateTasks(currentUser, new ArrayList<>(tasks));
        });
        
        // Delete button
        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> removeTask(task, taskCard));
        
        // Create a horizontal box for checkbox and delete button
        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(completeCheck, deleteBtn);
        
        // Add all elements to card
        taskCard.getChildren().addAll(
            titleLabel,
            descLabel,
            dateLabel,
            priorityLabel,
            actionBox
        );
        
        // Add card to grid
        int col = currentRow % 2;
        int row = currentRow / 2;
        taskGrid.add(taskCard, col, row);
        currentRow++;
    }

    private void removeTask(Task task, VBox taskCard) {
        tasks.remove(task);
        taskGrid.getChildren().remove(taskCard);
        TaskFileHandler.updateTasks(currentUser, new ArrayList<>(tasks));
    }

    @FXML
    public void initialize() {
        taskGrid.getStyleClass().add("task-grid");
        addTaskButton.getStyleClass().add("add-button");
    }
} 