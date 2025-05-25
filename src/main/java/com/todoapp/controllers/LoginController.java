package com.todoapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private static final String USER_DATA_FILE = "userdata.txt";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todoapp/views/main.fxml"));
                Parent root = loader.load();
                MainController mainController = loader.getController();
                mainController.setCurrentUser(username);
                mainController.loadTasks();

                Stage stage = (Stage) usernameField.getScene().getWindow();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/todoapp/styles/styles.css").toExternalForm());
                stage.setScene(scene);
            } catch (IOException e) {
                messageLabel.setText("Hata oluştu: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Geçersiz kullanıcı adı veya şifre!");
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Kullanıcı adı ve şifre boş olamaz!");
            return;
        }

        if (userExists(username)) {
            messageLabel.setText("Bu kullanıcı adı zaten kullanılıyor!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
            messageLabel.setText("Kayıt başarılı! Giriş yapabilirsiniz.");
        } catch (IOException e) {
            messageLabel.setText("Kayıt sırasında hata oluştu: " + e.getMessage());
        }
    }

    private boolean validateLogin(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(USER_DATA_FILE));
            return lines.stream()
                    .map(line -> line.split(":"))
                    .anyMatch(parts -> parts.length == 2 &&
                            parts[0].equals(username) &&
                            parts[1].equals(password));
        } catch (IOException e) {
            return false;
        }
    }

    private boolean userExists(String username) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(USER_DATA_FILE));
            return lines.stream()
                    .map(line -> line.split(":")[0])
                    .anyMatch(user -> user.equals(username));
        } catch (IOException e) {
            return false;
        }
    }
} 