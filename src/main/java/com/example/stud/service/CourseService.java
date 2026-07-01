package com.example.stud.service;

import com.example.stud.dao.CourseDao;
import com.example.stud.entity.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseService {

    private final CourseDao courseDao = new CourseDao();

    public List<Course> findAll() {
        try {
            return courseDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("查询课程列表失败", e);
        }
    }

    public int insert(Course course) {
        validate(course);
        try {
            return courseDao.insert(course);
        } catch (SQLException e) {
            throw new ServiceException("新增课程失败", e);
        }
    }

    public int update(Course course) {
        if (course.getId() == null) {
            throw new ServiceException("修改课程失败：ID 不能为空");
        }
        validate(course);
        try {
            return courseDao.update(course);
        } catch (SQLException e) {
            throw new ServiceException("修改课程失败", e);
        }
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new ServiceException("删除课程失败：ID 不能为空");
        }
        try {
            return courseDao.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("删除课程失败", e);
        }
    }

    private void validate(Course course) {
        if (course == null) {
            throw new ServiceException("课程数据不能为空");
        }
        if (course.getName() == null || course.getName().isBlank()) {
            throw new ServiceException("课程名称不能为空");
        }
        if (course.getTeacher() == null || course.getTeacher().isBlank()) {
            throw new ServiceException("教师名称不能为空");
        }
        if (course.getImage() == null || course.getImage().isBlank()) {
            throw new ServiceException("课程图片URL不能为空");
        }
    }
}
