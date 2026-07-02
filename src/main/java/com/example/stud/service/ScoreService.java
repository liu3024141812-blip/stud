package com.example.stud.service;

import com.example.stud.dao.ScoreDao;
import com.example.stud.entity.Score;

import java.sql.SQLException;
import java.util.List;

public class ScoreService {

    private final ScoreDao scoreDao = new ScoreDao();

    public List<Score> findAll() {
        try {
            return scoreDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("查询成绩列表失败", e);
        }
    }

    public int insert(Score score) {
        validate(score);
        try {
            if (scoreDao.existsByStudentAndCourse(score.getStudentId(), score.getCourseId())) {
                throw new ServiceException("该学生这门课已有成绩，请使用修改功能");
            }
            return scoreDao.insert(score);
        } catch (SQLException e) {
            throw new ServiceException("新增成绩失败", e);
        }
    }

    public int update(Score score) {
        if (score.getId() == null) {
            throw new ServiceException("修改成绩失败：ID不能为空");
        }
        validate(score);
        try {
            return scoreDao.update(score);
        } catch (SQLException e) {
            throw new ServiceException("修改成绩失败", e);
        }
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new ServiceException("删除成绩失败：ID不能为空");
        }
        try {
            return scoreDao.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("删除成绩失败", e);
        }
    }

    private void validate(Score score) {
        if (score == null) {
            throw new ServiceException("成绩数据不能为空");
        }
        if (score.getStudentId() == null) {
            throw new ServiceException("学生ID不能为空");
        }
        if (score.getCourseId() == null) {
            throw new ServiceException("课程ID不能为空");
        }
        double value = score.getScore();
        if (value < 0 || value > 100) {
            throw new ServiceException("考试成绩必须在 0-100 之间");
        }
    }
}
