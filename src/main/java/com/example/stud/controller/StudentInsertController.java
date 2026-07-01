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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.util.List;

public class StudentInsertController {

    @FXML
    private ComboBox<StudClass> classId;

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

    private final StudClassService studClassService = new StudClassService();

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
            StudClass selectedClass = this.classId.getSelectionModel().getSelectedItem();
            student.setClassId(selectedClass == null ? null : selectedClass.getId());
            student.setCredits(this.credits.getText() == null || this.credits.getText().isBlank()
                    ? 0 : Integer.valueOf(this.credits.getText()));
            // System.out.println("待存储的数据" + student);
            studentService.insert(student);
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
    public void initialize() {
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
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
