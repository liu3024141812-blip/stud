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
        String sql = "insert into score (student_id, student_name, course_id, course_name, score) values (?,?,?,?,?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, score.getStudentId());
            statement.setString(2, score.getStudentName());
            statement.setInt(3, score.getCourseId());
            statement.setString(4, score.getCourseName());
            statement.setBigDecimal(5, score.getScore());
            return statement.executeUpdate();
        }
    }

    public List<Score> findAll() throws SQLException {
        String sql = "select * from score";
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
                score.setScore(resultSet.getBigDecimal("score"));
                scores.add(score);
            }
            return scores;
        }
    }

    public int delete(Integer id) throws SQLException {
        String sql = "delete from score where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public int update(Score score) throws SQLException {
        String sql = "update score set student_id=?, student_name=?, course_id=?, course_name=?, score=? where id=?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, score.getStudentId());
            statement.setString(2, score.getStudentName());
            statement.setInt(3, score.getCourseId());
            statement.setString(4, score.getCourseName());
            statement.setBigDecimal(5, score.getScore());
            statement.setInt(6, score.getId());
            return statement.executeUpdate();
        }
    }
}
