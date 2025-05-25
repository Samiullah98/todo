package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import models.Task;

public class AddTaskController {
    @FXML
    private TextField taskTitle;
    
    @FXML
    private TextArea taskDescription;
    
    @FXML
    private DatePicker dueDate;
    
    @FXML
    private ComboBox<String> priorityLevel;
    
    private MainController mainController;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    public void initialize() {
        priorityLevel.getItems().addAll("High", "Medium", "Low");
        priorityLevel.setValue("Medium"); // Default value
        dueDate.setValue(LocalDate.now());
    }
    
    @FXML
    private void saveTask() {
        if (validateInput()) {
            Task newTask = new Task(
                taskTitle.getText().trim(),
                taskDescription.getText().trim(),
                dueDate.getValue(),
                priorityLevel.getValue()
            );
            
            mainController.addTask(newTask);
            closeWindow();
        }
    }
    
    @FXML
    private void cancelTask() {
        closeWindow();
    }
    
    private boolean validateInput() {
        if (taskTitle.getText().trim().isEmpty()) {
            showAlert("Error", "Task title is required!");
            return false;
        }
        if (dueDate.getValue() == null) {
            showAlert("Error", "Due date is required!");
            return false;
        }
        if (priorityLevel.getValue() == null) {
            showAlert("Error", "Priority level is required!");
            return false;
        }
        return true;
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void closeWindow() {
        Stage stage = (Stage) taskTitle.getScene().getWindow();
        stage.close();
    }
} 