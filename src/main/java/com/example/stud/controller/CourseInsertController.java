package com.example.stud.controller;

import com.example.stud.entity.Course;
import com.example.stud.service.CourseService;
import com.example.stud.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CourseInsertController {

    @FXML
    private TextField credits;

    @FXML
    private TextField image;

    @FXML
    private TextField name;

    @FXML
    private TextField teacher;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView imagePreview;

    @FXML
    private Button previewButton;

    public Runnable runnable = null;

    private final CourseService courseService = new CourseService();

    @FXML
    void cancel(ActionEvent event) {
        if (runnable != null) {
            runnable.run();
        }
    }

    @FXML
    void save(ActionEvent event) {
        try {
            Course course = new Course();
            course.setName(this.name.getText());
            course.setTeacher(this.teacher.getText());
            course.setImage(this.image.getText());
            course.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            // System.out.println("待存储的数据" + course);
            courseService.insert(course);
            if (runnable != null) {
                runnable.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "学分必须是整数").showAndWait();
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

    @FXML
    public void initialize() {
        // 图片预览区域初始化
    }
}
