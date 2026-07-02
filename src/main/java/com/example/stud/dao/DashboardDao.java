package com.example.stud.dao;

import com.example.stud.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 查询首页统计、图表和预警所需的数据。
public class DashboardDao {

    // 统计学生总数。
    public int countStudents() throws SQLException {
        return count("student");
    }

    // 统计班级总数。
    public int countClasses() throws SQLException {
        return count("stud_class");
    }

    // 统计课程总数。
    public int countCourses() throws SQLException {
        return count("course");
    }

    // 统计成绩记录总数。
    public int countScores() throws SQLException {
        return count("score");
    }

    // 统计所有成绩分数总和。
    public double sumScores() throws SQLException {
        String sql = "select coalesce(sum(score), 0) as total_score from score";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getDouble("total_score");
        }
    }

    // 统计每个分数段的成绩数量。
    public Map<String, Integer> countScoreRanges() throws SQLException {
        Map<String, Integer> ranges = new LinkedHashMap<>();
        ranges.put("0-59", countScoreRange(0, 60));
        ranges.put("60-69", countScoreRange(60, 70));
        ranges.put("70-79", countScoreRange(70, 80));
        ranges.put("80-89", countScoreRange(80, 90));
        ranges.put("90-100", countScoreRange(90, 101));
        return ranges;
    }

    // 统计及格和不及格数量。
    public Map<String, Integer> countPassStatus() throws SQLException {
        Map<String, Integer> status = new LinkedHashMap<>();
        status.put("及格", countScoreRange(60, 101));
        status.put("不及格", countScoreRange(0, 60));
        return status;
    }

    // 查询平均分最高的前五名学生。
    public List<Object[]> findTopStudents() throws SQLException {
        String sql = """
                select s.name, s.student_no, concat(c.grade, '级', c.myclass) as class_name,
                       round(avg(sc.score), 2) as average_score, s.credits
                from student s
                join score sc on s.id = sc.student_id
                left join stud_class c on s.class_id = c.id
                group by s.id, s.name, s.student_no, c.grade, c.myclass, s.credits
                order by average_score desc
                limit 5
                """;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Object[]> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(new Object[]{
                        resultSet.getString("name"),
                        resultSet.getString("student_no"),
                        resultSet.getString("class_name"),
                        resultSet.getDouble("average_score"),
                        resultSet.getInt("credits")
                });
            }
            return students;
        }
    }

    // 查询不及格成绩生成预警文本。
    public List<String> findWarnings() throws SQLException {
        String sql = """
                select concat('成绩预警：', s.name, '《', c.name, '》', sc.score, '分') as warning_text
                from score sc
                join student s on sc.student_id = s.id
                join course c on sc.course_id = c.id
                where sc.score < 60
                order by sc.score
                limit 6
                """;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<String> warnings = new ArrayList<>();
            while (resultSet.next()) {
                warnings.add(resultSet.getString("warning_text"));
            }
            return warnings;
        }
    }

    // 按表名统计记录总数。
    private int count(String tableName) throws SQLException {
        String sql = "select count(*) as total from " + tableName;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    // 统计指定分数区间内的成绩数量。
    private int countScoreRange(int min, int max) throws SQLException {
        String sql = "select count(*) as total from score where score >= ? and score < ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, min);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("total");
            }
        }
    }
}
