package com.example.stud.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {

    @FXML
    private TableColumn<StarStudent, Double> averageScoreColumn;

    @FXML
    private Label classCountLabel;

    @FXML
    private TableColumn<StarStudent, Integer> creditColumn;

    @FXML
    private Label courseCountLabel;

    @FXML
    private Label averageScoreLabel;

    @FXML
    private PieChart passStatusChart;

    @FXML
    private BarChart<String, Number> scoreRangeChart;

    @FXML
    private TableColumn<StarStudent, String> studentClassColumn;

    @FXML
    private Label studentCountLabel;

    @FXML
    private TableColumn<StarStudent, String> studentNameColumn;

    @FXML
    private TableColumn<StarStudent, String> studentNoColumn;

    @FXML
    private TableView<StarStudent> starStudentTable;

    @FXML
    private ListView<String> warningList;

    @FXML
    public void initialize() {
        this.initScoreRangeChart();
        this.initPassStatusChart();
        this.initStarStudentTable();
        this.initWarningList();
    }

    @FXML
    void openScores() {
        AppContentController current = AppContentController.getCurrent();
        if (current != null) {
            current.openScorePage();
        }
    }

    @FXML
    void openStudents() {
        AppContentController current = AppContentController.getCurrent();
        if (current != null) {
            current.openStudentPage();
        }
    }

    private void initScoreRangeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("0-59", 3));
        series.getData().add(new XYChart.Data<>("60-69", 8));
        series.getData().add(new XYChart.Data<>("70-79", 18));
        series.getData().add(new XYChart.Data<>("80-89", 31));
        series.getData().add(new XYChart.Data<>("90-100", 14));
        this.scoreRangeChart.getData().setAll(series);
    }

    private void initPassStatusChart() {
        this.passStatusChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("及格", 92),
                new PieChart.Data("不及格", 8)
        ));
    }

    private void initStarStudentTable() {
        this.studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.studentNoColumn.setCellValueFactory(new PropertyValueFactory<>("studentNo"));
        this.studentClassColumn.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
        this.averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        this.creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        this.starStudentTable.setItems(FXCollections.observableArrayList(
                new StarStudent("张三", "2024010101", "2024级1班", 95.5, 22),
                new StarStudent("李四", "2024010102", "2024级1班", 92.0, 20),
                new StarStudent("王五", "2024010201", "2024级2班", 90.5, 18),
                new StarStudent("赵六", "2024010202", "2024级2班", 88.0, 18),
                new StarStudent("钱七", "2024010301", "2024级3班", 86.5, 16)
        ));
    }

    private void initWarningList() {
        this.warningList.setItems(FXCollections.observableArrayList(
                "成绩预警：王五《高等数学》58分",
                "课程提醒：《数据库原理》暂无成绩记录",
                "学生提醒：赵六暂未分配班级",
                "成绩预警：钱七《大学英语》55分"
        ));
    }

    public static class StarStudent {
        private final String name;
        private final String studentNo;
        private final String studentClass;
        private final Double averageScore;
        private final Integer credits;

        public StarStudent(String name, String studentNo, String studentClass, Double averageScore, Integer credits) {
            this.name = name;
            this.studentNo = studentNo;
            this.studentClass = studentClass;
            this.averageScore = averageScore;
            this.credits = credits;
        }

        public String getName() {
            return name;
        }

        public String getStudentNo() {
            return studentNo;
        }

        public String getStudentClass() {
            return studentClass;
        }

        public Double getAverageScore() {
            return averageScore;
        }

        public Integer getCredits() {
            return credits;
        }
    }
}
