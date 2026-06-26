package com.example.stud.controller;
import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class StudClassInsertController {
    @FXML
    private Button cancelButton;

    @FXML
    private TextField grade;

    @FXML
    private TextField myclass;

    @FXML
    private TextField mymajor;

    @FXML
    private Button saveButton;

    private StudClassDao studClassDao=new StudClassDao();
    @FXML
    void cancel(ActionEvent event) {
        System.out.println("取消成功");
    }

    @FXML
    void save(ActionEvent event) throws SQLException {
        System.out.println("保存成功");
        StudClass studClass = new StudClass();
        studClass.setId(0);
        studClass.setMajor(this.mymajor.getText());
        studClass.setGrade( Integer.parseInt(grade.getText()));
        studClass.setName(this.myclass.getText());
        System.out.println("待存储的数据"+studClass);
        studClassDao.insert(studClass);
    }
}
