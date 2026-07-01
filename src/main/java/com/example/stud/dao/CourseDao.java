package com.example.stud.dao;

import com.example.stud.entity.Course;
import com.example.stud.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public int insert(Course course) throws SQLException {
        String sql = "insert into course (name, teacher, image, credits) values (?,?,?,?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getTeacher());
            statement.setString(3, course.getImage());
            statement.setInt(4, course.getCredits() == null ? 0 : course.getCredits());
            return statement.executeUpdate();
        }
    }

    public List<Course> findAll() throws SQLException {
        String sql = "select * from course";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setTeacher(resultSet.getString("teacher"));
                course.setImage(resultSet.getString("image"));
                course.setCredits(resultSet.getInt("credits"));
                // System.out.println("数据库：" + course);
                courses.add(course);
            }
            return courses;
        }
    }

    public int delete(Integer id) throws SQLException {
        String sql = "delete from course where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public int update(Course course) throws SQLException {
        String sql = "update course set name = ?, teacher = ?, image = ?, credits = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getTeacher());
            statement.setString(3, course.getImage());
            statement.setInt(4, course.getCredits() == null ? 0 : course.getCredits());
            statement.setInt(5, course.getId());
            return statement.executeUpdate();
        }
    }
}
