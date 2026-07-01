package com.example.stud.controller;

import com.example.stud.entity.Course;
import com.example.stud.service.CourseService;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
    private TableView<Course> results;

    @FXML
    private Button searchButton;

    @FXML
    private TableColumn<Course, String> teacher;

    @FXML
    private Button updateButton;

    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        this.initTable();
        this.search(null);
    }

    @FXML
    void delete(ActionEvent event) {
        System.out.println("将要删除");
        Course selectedItem = this.results.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "没有选中待删除的数据").showAndWait();
            return;
        }
        try {
            courseService.delete(selectedItem.getId());
            this.search(null);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("----打开新增窗口--");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/course-insert.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        CourseInsertController controller = loader.getController();
        controller.runnable = () -> {
            stage.close();
            System.out.println("保存后关闭对话框");
            this.search(null);
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        Course selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "未选择修改对象").showAndWait();
            System.out.println("back");
            return;
        }
        System.out.println("要修改的数据" + selectedItem);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/course-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println("准备更新");
        CourseUpdateController controller = loader.getController();
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
            List<Course> courses = courseService.findAll();
            ObservableList<Course> observableList = FXCollections.observableList(courses);
            this.results.setItems(observableList);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private void initTable() {
        this.cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        this.credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
        // 图片列：默认空，加载完成后显示图片
        this.cimage.setCellValueFactory(new PropertyValueFactory<>("image"));
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
