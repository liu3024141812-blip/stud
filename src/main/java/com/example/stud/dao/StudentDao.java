package com.example.stud.dao;

import com.example.stud.entity.Student;
import com.example.stud.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public int insert(Student student) throws SQLException {
        String sql = "insert into student (name, student_no, class_id, credits) values (?,?,?,?)";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getStudentNo());
            if (student.getClassId() != null) {
                statement.setInt(3, student.getClassId());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setInt(4, student.getCredits() == null ? 0 : student.getCredits());
            return statement.executeUpdate();
        }
    }

    public List<Student> findAll() throws SQLException {
        String sql = "select * from student";
        // 使用 try-with-resources 自动关闭学生查询的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                int classId = resultSet.getInt("class_id");
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setStudentNo(resultSet.getString("student_no"));
                student.setClassId(resultSet.wasNull() ? null : classId);
                student.setCredits(resultSet.getInt("credits"));
                students.add(student);
            }
            return students;
        }
    }

    public int delete(Integer id) throws SQLException {
        String sql = "delete from student where id = ?";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public int update(Student student) throws SQLException {
        String sql = "update student set name = ?, student_no = ?, class_id = ?, credits = ? where id = ?";
        // 使用 try-with-resources 自动关闭本次数据库操作的 JDBC 资源。
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getStudentNo());
            if (student.getClassId() != null) {
                statement.setInt(3, student.getClassId());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setInt(4, student.getCredits() == null ? 0 : student.getCredits());
            statement.setInt(5, student.getId());
            return statement.executeUpdate();
        }
    }
}
