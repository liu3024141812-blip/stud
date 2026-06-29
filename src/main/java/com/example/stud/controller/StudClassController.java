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

    // 生命周期函数，自动运行
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
        if (selectedItem != null) {
            studClassDao.delete(selectedItem.getId());
        }
        this.search(null);
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("----打开新增窗口--");
        // fxml文件从硬盘加载到内存，成为一幅画
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-insert.fxml"));
        // 内存中的xml文件根节点
        Parent root = loader.load();
        // stage是JavaFX中的窗口，scene相当于窗口中的画面
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        StudClassInsertController controller = loader.getController();
        controller.runnable = () -> {
            // 帮助用户点击关闭按钮
            stage.close();
            System.out.println("保存后关闭对话框");
            // 帮助用户点击搜索按钮，刷新列表数据
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-update.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println("准备更新");
        stage.showAndWait();
    }

    @FXML
    void search(ActionEvent event) throws SQLException {
        // this.initTable();
        // 做假数据显示
        // StudClass studClass = new StudClass();
        // studClass.setId(1);
        // studClass.setName("3班");
        // studClass.setMajor("计科");
        // studClass.setGrade(23);
        // List studClasses = new ArrayList<>();
        // studClasses.add(studClass);
        // StudClassDao studClassDao = new StudClassDao();
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
