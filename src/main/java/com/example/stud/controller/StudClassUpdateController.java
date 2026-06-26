package com.example.stud.controller;

import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import com.example.stud.util.DbUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    StudClassDao studClassDao= new StudClassDao();

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) throws SQLException {
     this.cid.setText("2");
        StudClass studClass=new StudClass();
        studClass.setId(Integer.valueOf(this.cid.getText()));
        studClass.setMajor(this.mymajor.getText());
        studClass.setName(this.myclass.getText());
        studClass.setGrade(Integer.valueOf(this.cid.getText()));
        System.out.println("将要更新"+studClass);
        studClassDao.update(studClass);
    }

}