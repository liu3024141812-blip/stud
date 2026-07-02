package com.example.stud.controller;

import com.example.stud.service.DashboardService;
import com.example.stud.service.ServiceException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;

// 渲染首页汇总、图表、优秀学生排行和预警数据。
public class DashboardController {

    @FXML
    private TableColumn<StarStudent, Double> averageScoreColumn;

    @FXML
    private Label averageScoreLabel;

    @FXML
    private Label classCountLabel;

    @FXML
    private TableColumn<StarStudent, Integer> creditColumn;

    @FXML
    private Label courseCountLabel;

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

    private final DashboardService dashboardService = new DashboardService();

    // 初始化首页所有统计区域。
    @FXML
    public void initialize() {
        try {
            initSummary();
            initScoreRangeChart();
            initPassStatusChart();
            initStarStudentTable();
            initWarningList();
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    // 跳转到成绩管理页面。
    @FXML
    void openScores() {
        AppContentController current = AppContentController.getCurrent();
        if (current != null) {
            current.openScorePage();
        }
    }

    // 跳转到学生管理页面。
    @FXML
    void openStudents() {
        AppContentController current = AppContentController.getCurrent();
        if (current != null) {
            current.openStudentPage();
        }
    }

    // 加载学生数、班级数、课程数和平均分。
    private void initSummary() {
        studentCountLabel.setText(String.valueOf(dashboardService.countStudents()));
        classCountLabel.setText(String.valueOf(dashboardService.countClasses()));
        courseCountLabel.setText(String.valueOf(dashboardService.countCourses()));
        averageScoreLabel.setText(String.valueOf(dashboardService.findAverageScore()));
    }

    // 加载成绩分布柱状图。
    private void initScoreRangeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : dashboardService.findScoreRanges().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        scoreRangeChart.getData().setAll(series);
    }

    // 加载及格和不及格占比 饼图。
    private void initPassStatusChart() {
        List<PieChart.Data> data = dashboardService.findPassStatus().entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                .toList();
        passStatusChart.setData(FXCollections.observableArrayList(data));
    }

    // 加载优秀学生排行表格。
    private void initStarStudentTable() {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentNoColumn.setCellValueFactory(new PropertyValueFactory<>("studentNo"));
        studentClassColumn.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
        averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        List<StarStudent> students = dashboardService.findTopStudents().stream()
                .map(row -> new StarStudent(
                        (String) row[0],
                        (String) row[1],
                        row[2] == null ? "未分配班级" : (String) row[2],
                        (Double) row[3],
                        (Integer) row[4]
                ))
                .toList();
        starStudentTable.setItems(FXCollections.observableArrayList(students));
    }

    // 加载低分预警列表。
    private void initWarningList() {
        warningList.setItems(FXCollections.observableArrayList(dashboardService.findWarnings()));
    }

    public static class StarStudent {
        private final String name;
        private final String studentNo;
        private final String studentClass;
        private final Double averageScore;
        private final Integer credits;

        // 保存优秀学生排行表格的一行数据。
        public StarStudent(String name, String studentNo, String studentClass, Double averageScore, Integer credits) {
            this.name = name;
            this.studentNo = studentNo;
            this.studentClass = studentClass;
            this.averageScore = averageScore;
            this.credits = credits;
        }

        // 获取学生姓名。
        public String getName() {
            return name;
        }

        // 获取学号。
        public String getStudentNo() {
            return studentNo;
        }

        // 获取班级名称。
        public String getStudentClass() {
            return studentClass;
        }

        // 获取平均分。
        public Double getAverageScore() {
            return averageScore;
        }

        // 获取学分。
        public Integer getCredits() {
            return credits;
        }
    }
}
