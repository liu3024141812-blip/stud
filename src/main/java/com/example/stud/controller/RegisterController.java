package com.example.stud.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField username;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void register(ActionEvent event) throws IOException {
        System.out.println("注册中");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/login.fxml"));
        Parent root =loader.load();
        Stage stage =(Stage) this.registerButton.getScene().getWindow();
stage.setScene(new Scene(root));

    }

}
