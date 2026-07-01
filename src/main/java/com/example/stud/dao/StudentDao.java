package com.example.stud.dao;

import com.example.stud.entity.Student;
import com.example.stud.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//DataAccessObject Mapper / Dao
public class StudentDao {

    // 新增
    public int insert(Student student) throws SQLException {
        String sql = "insert into student (name, student_no, class_id, credits) values (?,?,?,?)";
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
            // 增删改写操作使用 statement.executeUpdate()
            return statement.executeUpdate();
        }
    }

    // 查询 select * from student
    public List<Student> findAll() throws SQLException {
        String sql = "select * from student";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Student> students = new ArrayList<>();
            // 映射结果集到实体类
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String studentNo = resultSet.getString("student_no");
                int classId = resultSet.getInt("class_id");
                int credits = resultSet.getInt("credits");

                Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setStudentNo(studentNo);
                student.setClassId(resultSet.wasNull() ? null : classId);
                student.setCredits(credits);
                // System.out.println("数据库：" + student);
                students.add(student);
            }
            return students;
        }
    }

    // 删除
    public int delete(Integer id) throws SQLException {
        String sql = "delete from student where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    // 修改
    public int update(Student student) throws SQLException {
        String sql = "update student set name = ?, student_no = ?, class_id = ?, credits = ? where id = ?";
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
    //测试
}
