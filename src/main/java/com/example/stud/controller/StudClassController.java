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

public class StudClassController {
    @FXML
    private TableColumn<StudClass, Integer> cid;

    @FXML
    private TableColumn<StudClass, Integer> grade;

    @FXML
    private TextField keywordField;

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

    private List<StudClass> studClasses = List.of();

    @FXML
    public void initialize() {
        this.initTable();
        this.query(null);
    }

    @FXML
    void delete(ActionEvent event) {
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
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
            this.query(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        StudClassInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            this.query(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        StudClassUpdateController controller = loader.getController();
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
            this.studClasses = studClassService.findAll();
            ObservableList<StudClass> observableList = FXCollections.observableList(this.studClasses);
            this.results.setItems(observableList);
            this.keywordField.clear();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String normalizedKeyword = this.keywordField.getText() == null ? "" : this.keywordField.getText().trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            this.results.setItems(FXCollections.observableList(this.studClasses));
            return;
        }
        List<StudClass> filtered = this.studClasses.stream()
                .filter(studClass -> contains(studClass.getGrade(), normalizedKeyword)
                        || contains(studClass.getMajor(), normalizedKeyword)
                        || contains(studClass.getName(), normalizedKeyword))
                .collect(Collectors.toList());
        this.results.setItems(FXCollections.observableList(filtered));
    }

    @FXML
    void select(ActionEvent event) {

    }

    private boolean contains(Object value, String keyword) {
        return value != null && value.toString().toLowerCase().contains(keyword);
    }

    private void initTable() {
        this.grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        this.cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.myclass.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.mymajor.setCellValueFactory(new PropertyValueFactory<>("major"));
    }
}
