package com.example.stud.controller;

import com.example.stud.entity.Score;
import com.example.stud.service.ScoreService;
import com.example.stud.service.ServiceException;
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

public class ScoreController {

    @FXML
    private TableColumn<Score, String> studentName;

    @FXML
    private TableColumn<Score, String> courseName;

    @FXML
    private TableColumn<Score, Integer> id;

    @FXML
    private TableColumn<Score, Double> score;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private TextField keywordField;

    @FXML
    private TableView<Score> results;

    @FXML
    private Button searchButton;

    @FXML
    private Button updateButton;

    private final ScoreService scoreService = new ScoreService();

    private List<Score> scores = List.of();

    @FXML
    public void initialize() {
        this.initTable();
        this.query(null);
    }

    @FXML
    void delete(ActionEvent event) {
        Score selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "确定删除成绩：" + selectedItem.getStudentName() + " - " + selectedItem.getCourseName() + "？",
                ButtonType.OK,
                ButtonType.CANCEL
        );
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        try {
            scoreService.delete(selectedItem.getId());
            this.query(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/score-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        ScoreInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            this.query(null);
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
        stage.setScene(SceneUtil.createScene(root));
        ScoreUpdateController controller = loader.getController();
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
            this.scores = scoreService.findAll();
            ObservableList<Score> observableList = FXCollections.observableList(this.scores);
            this.results.setItems(observableList);
            this.keywordField.clear();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void search(ActionEvent event) {
        // 根据学生或课程关键字过滤已缓存的成绩列表。
        String normalizedKeyword = this.keywordField.getText() == null ? "" : this.keywordField.getText().trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            this.results.setItems(FXCollections.observableList(this.scores));
            return;
        }
        List<Score> filtered = this.scores.stream()
                .filter(score -> contains(score.getStudentName(), normalizedKeyword)
                        || contains(score.getCourseName(), normalizedKeyword))
                .collect(Collectors.toList());
        this.results.setItems(FXCollections.observableList(filtered));
    }

    private boolean contains(Object value, String keyword) {
        return value != null && value.toString().toLowerCase().contains(keyword);
    }

    private void initTable() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        this.courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        this.score.setCellValueFactory(new PropertyValueFactory<>("score"));
    }
}
