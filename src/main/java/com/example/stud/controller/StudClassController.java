package com.example.stud.controller;

import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class StudClassController {
    @FXML
    private TableColumn<StudClass, Integer> cid;

    @FXML
    private TableColumn<StudClass, Integer> grade;

    @FXML
    private TableColumn<StudClass, String> myclass;

    @FXML
    private TableColumn<StudClass, String> mymajor;

    @FXML
    private TableView<StudClass> results;


    private final StudClassDao studClassDao = new StudClassDao();


    @FXML
    void insert(ActionEvent event) {
        System.out.println("insert clicked");
    }

    @FXML
    void search(ActionEvent event) throws SQLException {
        initTable();
        List<StudClass> studClasses = studClassDao.findAll();
        ObservableList<StudClass> observableList = FXCollections.observableList(studClasses);
        results.setItems(observableList);
    }

    private void initTable() {
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        myclass.setCellValueFactory(new PropertyValueFactory<>("name"));
        mymajor.setCellValueFactory(new PropertyValueFactory<>("major"));
    }
}
