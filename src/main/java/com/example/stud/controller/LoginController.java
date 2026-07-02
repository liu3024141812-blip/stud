package com.example.stud.controller;

import com.example.stud.util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField username;

    @FXML
    void login(ActionEvent event) throws IOException {
        String usernameText = this.username.getText();
        String passwordText = this.password.getText();
        if (!(usernameText.equals("admin") && passwordText.equals("123"))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "用户名或密码不正确");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/app-content.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) this.loginButton.getScene().getWindow();
        stage.setScene(SceneUtil.createScene(root));
        stage.setTitle("学生管理系统");
        stage.setMaximized(true);
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/register.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) this.registerButton.getScene().getWindow();
        stage.setScene(SceneUtil.createScene(root));
    }

    public void initialize() {
        this.username.setText("admin");
        this.password.setText("123");
    }
}
