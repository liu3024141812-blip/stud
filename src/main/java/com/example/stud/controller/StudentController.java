package com.example.stud.controller;

import com.example.stud.entity.Student;
import com.example.stud.service.ServiceException;
import com.example.stud.service.StudentService;
import com.example.stud.util.SceneUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private TextField keywordField;

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

    private List<Student> students = List.of();

    @FXML
    public void initialize() {
        this.initTable();
        this.query(null);
    }

    @FXML
    void delete(ActionEvent event) {
        Student selectedItem = this.results.getSelectionModel().getSelectedItem();
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
            this.query(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/student-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        StudentInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            this.query(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        Student selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/student-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        StudentUpdateController controller = loader.getController();
        controller.setItem(selectedItem);
        controller.onUpdate = () -> {
            stage.close();
            this.query(null);
        };
        stage.showAndWait();
    }

    @FXML
    void query(ActionEvent event) {
        try {
            this.students = studentService.findAll();
            ObservableList<Student> observableList = FXCollections.observableList(this.students);
            this.results.setItems(observableList);
            this.keywordField.clear();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void search(ActionEvent event) {
        // 根据搜索框关键字过滤已缓存的学生列表。
        String normalizedKeyword = this.keywordField.getText() == null ? "" : this.keywordField.getText().trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            this.results.setItems(FXCollections.observableList(this.students));
            return;
        }
        List<Student> filtered = this.students.stream()
                .filter(student -> contains(student.getName(), normalizedKeyword)
                        || contains(student.getStudentNo(), normalizedKeyword)
                        || contains(student.getClassId(), normalizedKeyword))
                .collect(Collectors.toList());
        this.results.setItems(FXCollections.observableList(filtered));
    }

    private boolean contains(Object value, String keyword) {
        return value != null && value.toString().toLowerCase().contains(keyword);
    }

    private void initTable() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.student_no.setCellValueFactory(new PropertyValueFactory<>("studentNo"));
        this.class_id.setCellValueFactory(new PropertyValueFactory<>("classId"));
        this.credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
    }
}
