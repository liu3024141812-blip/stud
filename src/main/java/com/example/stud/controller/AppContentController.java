package com.example.stud.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class AppContentController {

    @FXML
    private Button classButton;

    @FXML
    private Button courseButton;

    @FXML
    private ImageView avatarImage;

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
        this.avatarImage.setClip(new Circle(23, 23, 23));
        this.navigateTo("/com/example/stud/stud-class-view.fxml");
        this.setActiveButton(this.classButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) this.logoutButton.getScene().getWindow();
        stage.setScene(SceneUtil.createScene(root));
    }

    @FXML
    void toclass(ActionEvent event) {
        this.navigateTo("/com/example/stud/stud-class-view.fxml");
        this.setActiveButton(this.classButton);
    }

    @FXML
    void tocourse(ActionEvent event) {
        this.navigateTo("/com/example/stud/course-view.fxml");
        this.setActiveButton(this.courseButton);
    }

    @FXML
    void toscore(ActionEvent event) {
        this.navigateTo("/com/example/stud/score-view.fxml");
        this.setActiveButton(this.scoreButton);
    }

    @FXML
    void tostudent(ActionEvent event) {
        this.navigateTo("/com/example/stud/student-view.fxml");
        this.setActiveButton(this.studentButton);
    }

    public void navigateTo(String fxml) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.main.getChildren().setAll(root);
    }

    private void setActiveButton(Button activeButton) {
        this.classButton.getStyleClass().remove("nav-button-active");
        this.studentButton.getStyleClass().remove("nav-button-active");
        this.courseButton.getStyleClass().remove("nav-button-active");
        this.scoreButton.getStyleClass().remove("nav-button-active");
        activeButton.getStyleClass().add("nav-button-active");
    }
}
