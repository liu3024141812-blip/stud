package com.example.stud.controller;

import com.example.stud.entity.Student;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentUpdateController {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField classId;

    @FXML
    private TextField credits;

    @FXML
    private Label id;

    @FXML
    private TextField name;

    @FXML
    private TextField studentNo;

    @FXML
    private Button updateButton;

    private final StudentService studentService = new StudentService();

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
            Student student = new Student();
            student.setId(Integer.valueOf(this.id.getText()));
            student.setName(this.name.getText());
            student.setStudentNo(this.studentNo.getText());
            student.setClassId(this.classId.getText() == null || this.classId.getText().isBlank()
                    ? null : Integer.valueOf(this.classId.getText()));
            student.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            System.out.println("将要更新:" + student);
            studentService.update(student);
            if (onUpdate != null) {
                onUpdate.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "ID/班级编号/学分必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    public void setItem(Student student) {
        this.id.setText(String.valueOf(student.getId()));
        this.name.setText(student.getName());
        this.studentNo.setText(student.getStudentNo());
        this.classId.setText(student.getClassId() == null ? "" : String.valueOf(student.getClassId()));
        this.credits.setText(String.valueOf(student.getCredits() == null ? 0 : student.getCredits()));
    }
}
