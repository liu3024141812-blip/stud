package com.example.stud.dao;

import com.example.stud.entity.Score;
import com.example.stud.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDao {

    public int insert(Score score) throws SQLException {
        String sql = "insert into score (student_id, course_id, score) values (?,?,?)";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, score.getStudentId());
            statement.setInt(2, score.getCourseId());
            statement.setDouble(3, score.getScore());
            return statement.executeUpdate();
        }
    }

    public boolean existsByStudentAndCourse(Integer studentId, Integer courseId) throws SQLException {
        String sql = "select 1 from score where student_id = ? and course_id = ? limit 1";
        // 使用 try-with-resources 自动关闭成绩重复检查的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public List<Score> findAll() throws SQLException {
        String sql = """
                select sc.id, sc.student_id, s.name as student_name, sc.course_id, c.name as course_name, sc.score
                from score sc
                join student s on sc.student_id = s.id
                join course c on sc.course_id = c.id
                order by sc.id
                """;
        // 使用 try-with-resources 自动关闭成绩查询的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Score> scores = new ArrayList<>();
            while (resultSet.next()) {
                Score score = new Score();
                score.setId(resultSet.getInt("id"));
                score.setStudentId(resultSet.getInt("student_id"));
                score.setStudentName(resultSet.getString("student_name"));
                score.setCourseId(resultSet.getInt("course_id"));
                score.setCourseName(resultSet.getString("course_name"));
                score.setScore(resultSet.getDouble("score"));
                scores.add(score);
            }
            return scores;
        }
    }

    public int delete(Integer id) throws SQLException {
        String sql = "delete from score where id = ?";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public int update(Score score) throws SQLException {
        String sql = "update score set student_id=?, course_id=?, score=? where id=?";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, score.getStudentId());
            statement.setInt(2, score.getCourseId());
            statement.setDouble(3, score.getScore());
            statement.setInt(4, score.getId());
            return statement.executeUpdate();
        }
    }
}
