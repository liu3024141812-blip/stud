package com.example.stud.controller;

import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private Button searchbutton;

    @FXML
    private Button insertbutton;

    @FXML
    private Button deletebutton;


    private  StudClassDao studClassDao = new StudClassDao();

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("new window");
        //fxml文件从硬盘加载到内存 成为一幅画
       FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-insert.fxml"));
//内存中的xml根节点
        Parent root= loader.load();
        //stage是fx的窗口window  开启一个新窗口
        Stage stage = new Stage();
        //scene相当于相框，将上述fxml文件加载到窗口
        stage.setScene(new Scene(root));
        //展示
        stage.showAndWait();

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
