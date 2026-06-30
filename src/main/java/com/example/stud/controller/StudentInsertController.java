package com.example.stud.controller;

import com.example.stud.entity.Student;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StudentInsertController {

    @FXML
    private TextField classId;

    @FXML
    private TextField credits;

    @FXML
    private TextField name;

    @FXML
    private TextField studentNo;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    public Runnable runnable = null;

    private final StudentService studentService = new StudentService();

    @FXML
    void cancel(ActionEvent event) {
        if (runnable != null) {
            runnable.run();
        }
    }

    @FXML
    void save(ActionEvent event) {
        try {
            Student student = new Student();
            student.setName(this.name.getText());
            student.setStudentNo(this.studentNo.getText());
            student.setClassId(this.classId.getText() == null || this.classId.getText().isBlank()
                    ? null : Integer.valueOf(this.classId.getText()));
            student.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            System.out.println("待存储的数据" + student);
            studentService.insert(student);
            if (runnable != null) {
                runnable.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "班级编号/学分必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
