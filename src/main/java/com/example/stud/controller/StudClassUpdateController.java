package com.example.stud.controller;

import com.example.stud.entity.StudClass;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudClassService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudClassUpdateController {

    @FXML
    private Button cancelbutton;

    @FXML
    private Label cid;

    @FXML
    private TextField grade;

    @FXML
    private TextField myclass;

    @FXML
    private TextField mymajor;

    @FXML
    private Button updatebutton;

    private final StudClassService studClassService = new StudClassService();

    public StudClass studClass = new StudClass();

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
            StudClass studClass = new StudClass();
            studClass.setId(Integer.valueOf(this.cid.getText()));
            studClass.setMajor(this.mymajor.getText());
            studClass.setGrade(Integer.valueOf(this.grade.getText()));
            studClass.setName(this.myclass.getText());
            // System.out.println("将要更新:" + studClass);
            studClassService.update(studClass);
            if (onUpdate != null) {
                onUpdate.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "ID/年级必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    public void setItem(StudClass studClass) {
        this.cid.setText(String.valueOf(studClass.getId()));
        this.mymajor.setText(studClass.getMajor());
        this.grade.setText(String.valueOf(studClass.getGrade()));
        this.myclass.setText(studClass.getName());
    }
}
