package com.example.stud.controller;

import com.example.stud.entity.StudClass;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudClassService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public Runnable runnable = null;

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
            // 收集用户输入的数据，打包成一个实体类对象
            StudClass studClass = new StudClass();
            studClass.setId(0);
            studClass.setMajor(this.mymajor.getText());
            studClass.setGrade(Integer.parseInt(this.grade.getText()));
            studClass.setName(this.myclass.getText());
            System.out.println("待存储的数据" + studClass);
            // 调用业务层保存
            studClassService.insert(studClass);
            if (runnable != null) {
                runnable.run();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "年级必须是整数").showAndWait();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
