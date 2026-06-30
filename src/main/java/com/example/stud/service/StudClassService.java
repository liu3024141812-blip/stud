package com.example.stud.service;

import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;

import java.sql.SQLException;
import java.util.List;

/**
 * 班级业务层：负责参数校验、调用 DAO、捕获并包装异常。
 * Controller 不再直接持有 DAO，也无需 throws SQLException。
 */
public class StudClassService {

    private final StudClassDao studClassDao = new StudClassDao();

    public List<StudClass> findAll() {
        try {
            return studClassDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("查询班级列表失败", e);
        }
    }

    public int insert(StudClass studClass) {
        validate(studClass);
        try {
            return studClassDao.insert(studClass);
        } catch (SQLException e) {
            throw new ServiceException("新增班级失败", e);
        }
    }

    public int update(StudClass studClass) {
        if (studClass.getId() == null) {
            throw new ServiceException("修改班级失败：ID 不能为空");
        }
        validate(studClass);
        try {
            return studClassDao.update(studClass);
        } catch (SQLException e) {
            throw new ServiceException("修改班级失败", e);
        }
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new ServiceException("删除班级失败：ID 不能为空");
        }
        try {
            return studClassDao.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("删除班级失败", e);
        }
    }

    private void validate(StudClass studClass) {
        if (studClass == null) {
            throw new ServiceException("班级数据不能为空");
        }
        if (studClass.getName() == null || studClass.getName().isBlank()) {
            throw new ServiceException("班级名称不能为空");
        }
        if (studClass.getGrade() == null) {
            throw new ServiceException("年级不能为空");
        }
        if (studClass.getMajor() == null || studClass.getMajor().isBlank()) {
            throw new ServiceException("专业不能为空");
        }
    }
}
