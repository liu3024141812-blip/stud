package com.example.stud.controller;

import com.example.stud.entity.StudClass;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudClassService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @FXML
    private Button updatebutton;

    private final StudClassService studClassService = new StudClassService();

    @FXML
    public void initialize() {

        this.initTable();
        this.search(null);
    }

    @FXML
    void delete(ActionEvent event) {
        System.out.println("将要删除");
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
        // System.out.println(selectedItem);
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "确定删除班级：" + selectedItem.getName() + "？", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        try {
            studClassService.delete(selectedItem.getId());
            this.search(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("----打开新增窗口--");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        //自动搜索
        StudClassInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            System.out.println("保存后关闭对话框");
            this.search(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            System.out.println("back");
            return;
        }
        // System.out.println("要修改的数据" + selectedItem);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println("准备更新");
        StudClassUpdateController controller = loader.getController();
        controller.setItem(selectedItem);

        controller.onUpdate = () -> {
            stage.close();
            this.search(null);
        };
        stage.showAndWait();
    }

    @FXML
    void search(ActionEvent event) {
        try {
            List<StudClass> studClasses = studClassService.findAll();
            ObservableList<StudClass> observableList = FXCollections.observableList(studClasses);
            this.results.setItems(observableList);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void select(ActionEvent event) {

    }

    private void initTable() {
        this.grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        this.cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.myclass.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.mymajor.setCellValueFactory(new PropertyValueFactory<>("major"));
    }
}
