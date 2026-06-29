package com.example.stud.controller;

import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    StudClassDao studClassDao = new StudClassDao();

    public StudClass studClass = new StudClass();

    @FXML
    void cancel(ActionEvent event) {

    }
public Runnable onUpdate;
    @FXML
    void update(ActionEvent event) throws SQLException {
    //    this.cid.setText("4");
        StudClass studClass = new StudClass();
        studClass.setId(Integer.valueOf(this.cid.getText()));
        studClass.setMajor(this.mymajor.getText());
        studClass.setGrade(Integer.valueOf(this.grade.getText()));
        studClass.setName(this.myclass.getText());
        System.out.println("将要更新:" + studClass);
        studClassDao.update(studClass);
if(onUpdate!=null){
    onUpdate.run();
}

    }
    public void setItem(StudClass studClass){
        this.cid.setText(String.valueOf(studClass.getId()));
        this.mymajor.setText(studClass.getMajor());
        this.grade.setText(String.valueOf(studClass.getGrade()));
        this.myclass.setText(studClass.getName());
    }
}
