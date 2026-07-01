package com.example.stud.controller;

import com.example.stud.entity.Course;
import com.example.stud.entity.Score;
import com.example.stud.entity.Student;
import com.example.stud.service.CourseService;
import com.example.stud.service.ScoreService;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudentService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

public class ScoreUpdateController {

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Student> studentId;

    @FXML
    private ComboBox<Course> courseId;

    @FXML
    private TextField score;

    @FXML
    private Label id;

    @FXML
    private Button updateButton;

    private final ScoreService scoreService = new ScoreService();

    private final StudentService studentService = new StudentService();

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
            Student student = this.studentId.getSelectionModel().getSelectedItem();
            Course course = this.courseId.getSelectionModel().getSelectedItem();
            if (student == null) {
                new Alert(Alert.AlertType.INFORMATION, "请选择学生").showAndWait();
                return;
            }
            if (course == null) {
                new Alert(Alert.AlertType.INFORMATION, "请选择课程").showAndWait();
                return;
            }
            String text = this.score.getText();
            if (text == null || text.isBlank()) {
                new Alert(Alert.AlertType.ERROR, "考试成绩不能为空").showAndWait();
                return;
            }
            Score score = new Score();
            score.setId(Integer.valueOf(this.id.getText()));
            score.setStudentId(student.getId());
            score.setStudentName(student.getName());
            score.setCourseId(course.getId());
            score.setCourseName(course.getName());
            score.setScore(new BigDecimal(text.trim()));
            scoreService.update(score);
            if (onUpdate != null) {
                onUpdate.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "ID/考试成绩必须为数字").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    public void setItem(Score score) {
        this.id.setText(String.valueOf(score.getId()));
        this.score.setText(score.getScore() == null ? "" : score.getScore().toPlainString());

        // 加载学生
        List<Student> students = studentService.findAll();
        this.studentId.setItems(FXCollections.observableList(students));
        this.studentId.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null
                        : item.getId() + " - " + item.getName() + " (" + item.getStudentNo() + ")");
            }
        });
        this.studentId.setButtonCell(this.studentId.getCellFactory().call(null));
        students.stream().filter(s -> s.getId().equals(score.getStudentId())).findFirst()
                .ifPresent(this.studentId::setValue);

        // 加载课程
        List<Course> courses = courseService.findAll();
        this.courseId.setItems(FXCollections.observableList(courses));
        this.courseId.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null
                        : item.getId() + " - " + item.getName() + " (" + item.getTeacher() + ")");
            }
        });
        this.courseId.setButtonCell(this.courseId.getCellFactory().call(null));
        courses.stream().filter(c -> c.getId().equals(score.getCourseId())).findFirst()
                .ifPresent(this.courseId::setValue);
    }
}
