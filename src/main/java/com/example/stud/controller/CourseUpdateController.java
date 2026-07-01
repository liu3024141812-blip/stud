package com.example.stud.controller;

import com.example.stud.entity.Course;
import com.example.stud.service.CourseService;
import com.example.stud.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CourseUpdateController {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField credits;

    @FXML
    private TextField image;

    @FXML
    private Label id;

    @FXML
    private TextField name;

    @FXML
    private TextField teacher;

    @FXML
    private Button updateButton;

    @FXML
    private ImageView imagePreview;

    @FXML
    private Button previewButton;

    private final CourseService courseService = new CourseService();

    public Runnable onUpdate;

    @FXML
    void cancel(ActionEvent event) {
        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    @FXML
    void update(ActionEvent event) {
        try {
            Course course = new Course();
            course.setId(Integer.valueOf(this.id.getText()));
            course.setName(this.name.getText());
            course.setTeacher(this.teacher.getText());
            course.setImage(this.image.getText());
            course.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            // System.out.println("将要更新:" + course);
            courseService.update(course);
            if (onUpdate != null) {
                onUpdate.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "ID/学分必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void previewImage(ActionEvent event) {
        String url = this.image.getText();
        if (url != null && !url.isBlank()) {
            try {
                Image img = new Image(url, 200, 150, true, true);
                this.imagePreview.setImage(img);
            } catch (Exception e) {
                this.imagePreview.setImage(null);
            }
        }
    }

    public void setItem(Course course) {
        this.id.setText(String.valueOf(course.getId()));
        this.name.setText(course.getName());
        this.teacher.setText(course.getTeacher());
        this.image.setText(course.getImage());
        this.credits.setText(String.valueOf(course.getCredits() == null ? 0 : course.getCredits()));
        // 异步加载预览图，避免阻塞界面打开
        String url = course.getImage();
        if (url != null && !url.isBlank()) {
            javafx.application.Platform.runLater(() -> {
                try {
                    Image img = new Image(url, 200, 150, true, true, true);
                    img.progressProperty().addListener((obs, oldV, newV) -> {
                        if (newV.doubleValue() >= 1.0) {
                            this.imagePreview.setImage(img);
                        }
                    });
                    img.exceptionProperty().addListener((obs, oldV, newV) -> {
                        if (newV != null) this.imagePreview.setImage(null);
                    });
                    if (img.getProgress() >= 1.0) {
                        this.imagePreview.setImage(img);
                    }
                } catch (Exception e) {
                    this.imagePreview.setImage(null);
                }
            });
        }
    }
}
