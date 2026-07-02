package com.example.stud.controller;

import com.example.stud.entity.Course;
import com.example.stud.service.CourseService;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseController {

    @FXML
    private TableColumn<Course, Integer> cid;

    @FXML
    private TableColumn<Course, String> cimage;

    @FXML
    private TableColumn<Course, String> cname;

    @FXML
    private TableColumn<Course, Integer> credits;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;

    @FXML
    private TextField keywordField;

    @FXML
    private TableView<Course> results;

    @FXML
    private Button searchButton;

    @FXML
    private TableColumn<Course, String> teacher;

    @FXML
    private Button updateButton;

    private final CourseService courseService = new CourseService();

    private List<Course> courses = List.of();

    @FXML
    public void initialize() {
        this.initTable();
        this.query(null);
    }

    @FXML
    void delete(ActionEvent event) {
        Course selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "确定删除课程：" + selectedItem.getName() + "？", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        try {
            courseService.delete(selectedItem.getId());
            this.query(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/course-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        CourseInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            this.query(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        Course selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/course-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(SceneUtil.createScene(root));
        CourseUpdateController controller = loader.getController();
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
            this.courses = courseService.findAll();
            ObservableList<Course> observableList = FXCollections.observableList(this.courses);
            this.results.setItems(observableList);
            this.keywordField.clear();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void search(ActionEvent event) {
        // 根据搜索框关键字过滤已缓存的课程列表。
        String normalizedKeyword = this.keywordField.getText() == null ? "" : this.keywordField.getText().trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            this.results.setItems(FXCollections.observableList(this.courses));
            return;
        }
        List<Course> filtered = this.courses.stream()
                .filter(course -> contains(course.getName(), normalizedKeyword)
                        || contains(course.getTeacher(), normalizedKeyword))
                .collect(Collectors.toList());
        this.results.setItems(FXCollections.observableList(filtered));
    }

    private boolean contains(Object value, String keyword) {
        return value != null && value.toString().toLowerCase().contains(keyword);
    }

    private void initTable() {
        this.cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.cimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        this.cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        this.credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
        this.cimage.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null || url.isBlank()) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                setText(null);
                imageView.setFitWidth(80);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
                imageView.setImage(null);
                setGraphic(imageView);
                Image img = new Image(url, 80, 50, true, true, true);
                img.progressProperty().addListener((obs, oldV, newV) -> {
                    if (newV.doubleValue() >= 1.0 && imageView.getImage() == null) {
                        imageView.setImage(img);
                    }
                });
                if (img.getProgress() >= 1.0) {
                    imageView.setImage(img);
                }
            }
        });
    }
}
