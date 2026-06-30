package com.example.stud.service;

import com.example.stud.dao.StudentDao;
import com.example.stud.entity.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * 学生业务层：负责参数校验、调用 DAO、捕获并包装异常。
 * Controller 不再直接持有 DAO，也无需 throws SQLException。
 */
public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    public List<Student> findAll() {
        try {
            return studentDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("查询学生列表失败", e);
        }
    }

    public int insert(Student student) {
        validate(student);
        try {
            return studentDao.insert(student);
        } catch (SQLException e) {
            throw new ServiceException("新增学生失败", e);
        }
    }

    public int update(Student student) {
        if (student.getId() == null) {
            throw new ServiceException("修改学生失败：ID 不能为空");
        }
        validate(student);
        try {
            return studentDao.update(student);
        } catch (SQLException e) {
            throw new ServiceException("修改学生失败", e);
        }
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new ServiceException("删除学生失败：ID 不能为空");
        }
        try {
            return studentDao.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("删除学生失败", e);
        }
    }

    private void validate(Student student) {
        if (student == null) {
            throw new ServiceException("学生数据不能为空");
        }
        if (student.getName() == null || student.getName().isBlank()) {
            throw new ServiceException("学生姓名不能为空");
        }
        if (student.getStudentNo() == null || student.getStudentNo().isBlank()) {
            throw new ServiceException("学号不能为空");
        }
    }
}
