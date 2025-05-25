package com.todoapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddTaskController {
    @FXML
    private TextArea taskContent;

    private MainController mainController;
    private String currentUser;

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    @FXML
    private void handleAdd() {
        String content = taskContent.getText().trim();
        if (!content.isEmpty()) {
            mainController.addTaskToGrid(content, false);
        }
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) taskContent.getScene().getWindow();
        stage.close();
    }
} 