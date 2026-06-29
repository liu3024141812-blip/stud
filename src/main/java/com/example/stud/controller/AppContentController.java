package com.example.stud.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppContentController {

    @FXML
    private Button classButton;

    @FXML
    private Button courseButton;

    @FXML
    private Button logoutButton;

    @FXML
    private StackPane main;

    @FXML
    private Button scoreButton;

    @FXML
    private Button studentButton;

    @FXML
    public void initialize() {
        this.navigateTo("/com/example/stud/stud-class-view.fxml");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) this.logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void toclass(ActionEvent event) {
        System.out.println("路由跳转页面");
        this.navigateTo("/com/example/stud/stud-class-view.fxml");
    }

    @FXML
    void tocourse(ActionEvent event) {

    }

    @FXML
    void toscore(ActionEvent event) {

    }

    @FXML
    void tostudent(ActionEvent event) {

    }

    public void navigateTo(String fxml) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.main.getChildren().clear();
        this.main.getChildren().add(root);
    }

}
