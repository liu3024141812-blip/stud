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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
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


    private  StudClassDao studClassDao = new StudClassDao();

    @FXML
    //删除
    void delete(ActionEvent event) throws SQLException {
        //获取选中的id
 StudClass selectedItem =this.results.getSelectionModel().getSelectedItem();
 //选中
 if(selectedItem!=null){
     studClassDao.delete(selectedItem.getId());
     //自动点击搜索刷新页面
     try {
         this.search(null);
     }catch (SQLException e){
         throw new RuntimeException(e);
     }

 }
    }
//进入增加界面
    @FXML
    void insert(ActionEvent event) throws IOException {
        System.out.println("new window");
        //fxml文件从硬盘加载到内存 成为一幅画
       FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-insert.fxml"));
//内存中的xml根节点
        Parent root= loader.load();
        //stage是fx的窗口window  开启一个新窗口
        Stage stage = new Stage();
        //scene相当于相框，将上述fxml文件加载到窗口
        stage.setScene(new Scene(root));
        StudClassInsertController controller= loader.getController();
        //帮用户点关闭
        controller.runnable=()->{
            stage.close();
            System.out.println("保存后关闭对话框");

            //帮用户点搜索
          try {
            this.search(null);
          }catch (SQLException e){
              throw new RuntimeException(e);
          }
        };
        //展示
        stage.showAndWait();

    }

    @FXML
    void update(ActionEvent event) throws IOException {
        System.out.println("进入修改界面");
   FXMLLoader loader= new  FXMLLoader(this.getClass().getResource("/com/example/stud/stud-class-update.fxml"));
        Parent root= loader.load();
        //stage是fx的窗口window  开启一个新窗口
        Stage stage = new Stage();
        //scene相当于相框，将上述fxml文件加载到窗口
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    //刷新
    @FXML
    void search(ActionEvent event) throws SQLException {
        initTable();
        List<StudClass> studClasses = studClassDao.findAll();
        ObservableList<StudClass> observableList = FXCollections.observableList(studClasses);
        results.setItems(observableList);


    }
    @FXML
    void select(ActionEvent event) throws SQLException {

    }


    private void initTable() {
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        myclass.setCellValueFactory(new PropertyValueFactory<>("name"));
        mymajor.setCellValueFactory(new PropertyValueFactory<>("major"));
    }
}
