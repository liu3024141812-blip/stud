package com.example.stud.controller;

import com.example.stud.entity.Student;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudentService;
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

public class StudentController {

    @FXML
    private TableColumn<Student, Integer> class_id;

    @FXML
    private TableColumn<Student, Integer> credits;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private Button insertButton;

    @FXML
    private TableColumn<Student, String> name;

    @FXML
    private TableView<Student> results;

    @FXML
    private Button searchButton;

    @FXML
    private TableColumn<Student, String> student_no;

    @FXML
    private Button updateButton;

    private final StudentService studentService = new StudentService();

    @FXML
    public void initialize() {
        this.initTable();
        this.search(null);
    }

    @FXML
    void delete(ActionEvent event) {
        System.out.println("将要删除");
        Student selectedItem = this.results.getSelectionModel().getSelectedItem();
        // System.out.println(selectedItem);
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "确定删除学生：" + selectedItem.getName() + "？", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        try {
            studentService.delete(selectedItem.getId());
            this.search(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("----打开新增窗口--");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/student-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        StudentInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            System.out.println("保存后关闭对话框");
            this.search(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        Student selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            System.out.println("back");
            return;
        }
        // System.out.println("要修改的数据" + selectedItem);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/student-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println("准备更新");
        StudentUpdateController controller = loader.getController();
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
            List<Student> students = studentService.findAll();
            ObservableList<Student> observableList = FXCollections.observableList(students);
            this.results.setItems(observableList);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private void initTable() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.student_no.setCellValueFactory(new PropertyValueFactory<>("studentNo"));
        this.class_id.setCellValueFactory(new PropertyValueFactory<>("classId"));
        this.credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
    }
}
