package com.example.stud.controller;

import com.example.stud.entity.Score;
import com.example.stud.service.ScoreService;
import com.example.stud.service.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ScoreController {

    @FXML
    private TableColumn<Score, String> studentName;

    @FXML
    private TableColumn<Score, String> courseName;

    @FXML
    private TableColumn<Score, Integer> id;

    @FXML
    private TableColumn<Score, String> score;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private TableView<Score> results;

    @FXML
    private Button searchButton;

    @FXML
    private Button updateButton;

    private final ScoreService scoreService = new ScoreService();

    @FXML
    public void initialize() {
        this.initTable();
        this.search(null);
    }

    @FXML
    void delete(ActionEvent event) {
        Score selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        try {
            scoreService.delete(selectedItem.getId());
            this.search(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/score-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        ScoreInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            this.search(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        Score selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/score-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        ScoreUpdateController controller = loader.getController();
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
            List<Score> scores = scoreService.findAll();
            ObservableList<Score> observableList = FXCollections.observableList(scores);
            this.results.setItems(observableList);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private void initTable() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        this.courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        this.score.setCellValueFactory(new PropertyValueFactory<>("score"));
    }
}
