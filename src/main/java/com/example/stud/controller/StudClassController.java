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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private Button updatebutton;

    private StudClassDao studClassDao = new StudClassDao();

    @FXML
    public void initialize() throws SQLException {
        System.out.println("自动运行");
        System.out.println("自动运行2");
        this.initTable();
        this.search(null);
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        System.out.println("将要删除");
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        if (selectedItem==null) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION,"没有选中待删除的数据");
            alert.showAndWait();
            return;

        }
        studClassDao.delete(selectedItem.getId());
        this.search(null);
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
            try {
                this.search(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        stage.showAndWait();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        StudClass selectedItem = this.results.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "未选择修改对象");
            alert.showAndWait();
            System.out.println("back");
            return;
        }
        System.out.println("要修改的数据"+selectedItem);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println("准备更新");
        StudClassUpdateController controller = loader.getController();
        controller.setItem(selectedItem);

        controller.onUpdate=()->{
            stage.close();
            try {
                this.search(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        stage.showAndWait();
    }

    @FXML
    void search(ActionEvent event) throws SQLException {
        List<StudClass> studClasses = studClassDao.findAll();
        ObservableList<StudClass> observableList = FXCollections.observableList(studClasses);
        this.results.setItems(observableList);
    }

    @FXML
    void select(ActionEvent event) throws SQLException {

    }

    private void initTable() {
        this.grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        this.cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.myclass.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.mymajor.setCellValueFactory(new PropertyValueFactory<>("major"));
    }
}
