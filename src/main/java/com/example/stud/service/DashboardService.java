package com.example.stud.service;

import com.example.stud.dao.DashboardDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

// 提供首页统计数据，并把数据库异常转换为业务异常。
public class DashboardService {

    private final DashboardDao dashboardDao = new DashboardDao();

    // 查询学生总数。
    public int countStudents() {
        try {
            return dashboardDao.countStudents();
        } catch (SQLException e) {
            throw new ServiceException("查询学生总数失败", e);
        }
    }

    // 查询班级总数。
    public int countClasses() {
        try {
            return dashboardDao.countClasses();
        } catch (SQLException e) {
            throw new ServiceException("查询班级总数失败", e);
        }
    }

    // 查询课程总数。
    public int countCourses() {
        try {
            return dashboardDao.countCourses();
        } catch (SQLException e) {
            throw new ServiceException("查询课程总数失败", e);
        }
    }

    // 查询所有成绩总和。
    public double sumScores() {
        try {
            return dashboardDao.sumScores();
        } catch (SQLException e) {
            throw new ServiceException("查询成绩总和失败", e);
        }
    }

    // 查询平均分，保留一位小数。
    public double findAverageScore() {
        try {
            int scoreCount = dashboardDao.countScores();
            if (scoreCount == 0) {
                return 0;
            }
            return Math.round((dashboardDao.sumScores() / scoreCount) * 10.0) / 10.0;
        } catch (SQLException e) {
            throw new ServiceException("查询平均分失败", e);
        }
    }

    // 查询各分数段的成绩数量。
    public Map<String, Integer> findScoreRanges() {
        try {
            return dashboardDao.countScoreRanges();
        } catch (SQLException e) {
            throw new ServiceException("查询成绩分布失败", e);
        }
    }

    // 查询及格和不及格数量。
    public Map<String, Integer> findPassStatus() {
        try {
            return dashboardDao.countPassStatus();
        } catch (SQLException e) {
            throw new ServiceException("查询成绩状态失败", e);
        }
    }

    // 查询平均分最高的学生列表。
    public List<Object[]> findTopStudents() {
        try {
            return dashboardDao.findTopStudents();
        } catch (SQLException e) {
            throw new ServiceException("查询优秀学生失败", e);
        }
    }

    // 查询低分预警列表。
    public List<String> findWarnings() {
        try {
            List<String> warnings = dashboardDao.findWarnings();
            if (warnings.isEmpty()) {
                return List.of("暂无学业预警");
            }
            return warnings;
        } catch (SQLException e) {
            throw new ServiceException("查询学业预警失败", e);
        }
    }
}
