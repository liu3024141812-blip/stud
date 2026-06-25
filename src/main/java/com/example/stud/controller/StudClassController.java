package com.example.stud.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.example.stud.entity.StudClass;

public class StudClassController {

    @FXML
    void insert(ActionEvent event) {
        System.out.println("insert clicked");

    }

    @FXML
    void query(ActionEvent event) {
        System.out.println("query clicked");
        StudClass studClass = new StudClass();
        studClass.setId(1);
        studClass.setName("3班");
        studClass.setMajor("计科");
        studClass.setGrade(23);
    }
    @FXML
    private TableColumn<?, ?> cid;

    @FXML
    private TableColumn<?, ?> grader;

    @FXML
    private TableView<?> result;



}
