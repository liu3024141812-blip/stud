package com.example.stud.controller;

import com.example.stud.entity.StudClass;
import com.example.stud.entity.Student;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudClassService;
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

import java.util.List;

public class StudentUpdateController {

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<StudClass> classId;

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

    private final StudClassService studClassService = new StudClassService();

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
            StudClass selectedClass = this.classId.getSelectionModel().getSelectedItem();
            student.setClassId(selectedClass == null ? null : selectedClass.getId());
            student.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            // System.out.println("将要更新:" + student);
            studentService.update(student);
            if (onUpdate != null) {
                onUpdate.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "ID/学分必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    public void setItem(Student student) {
        this.id.setText(String.valueOf(student.getId()));
        this.name.setText(student.getName());
        this.studentNo.setText(student.getStudentNo());
        this.credits.setText(String.valueOf(student.getCredits() == null ? 0 : student.getCredits()));
        // 加载可选班级列表
        try {
            List<StudClass> studClasses = studClassService.findAll();
            this.classId.setItems(FXCollections.observableList(studClasses));
            this.classId.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(StudClass item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null
                            : item.getGrade() + "级 " + item.getMajor() + " " + item.getName());
                }
            });
            this.classId.setButtonCell(this.classId.getCellFactory().call(null));
            // 回显已选班级
            if (student.getClassId() != null) {
                this.classId.getItems().stream()
                        .filter(sc -> sc.getId().equals(student.getClassId()))
                        .findFirst()
                        .ifPresent(this.classId::setValue);
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
