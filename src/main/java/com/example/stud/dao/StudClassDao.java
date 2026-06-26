package com.example.stud.dao;

import com.example.stud.entity.StudClass;
import com.example.stud.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DataAccessObject Mapper / Dao
public class StudClassDao {

    //    新增
    public int insert(StudClass studClass) throws SQLException {
        String sql="insert into stud_class (myclass,grade,mymajor) values (?,?,?)";

        Connection connection = DbUtil.getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1, studClass.getName());
        statement.setInt(2, studClass.getGrade());
        statement.setString(3, studClass.getMajor());

        int i=statement.executeUpdate();
        System.out.println(i);
        return i;
    }


    //    查询 select * from stud_class
    public List<StudClass> findAll() throws SQLException {
        String sql = "select *  from stud_class";
        Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<StudClass> studClasses = new ArrayList<>();
//        控制台看结果 Alt+Enter 映射结果集到实体类
        while (resultSet.next()) {
            String major = resultSet.getString("mymajor");
            String name = resultSet.getString("myclass");
            int id = resultSet.getInt("id");
            int grade = resultSet.getInt("grade");

            StudClass studClass = new StudClass();
            studClass.setGrade(grade);
            studClass.setId(id);
            studClass.setMajor(major);
            studClass.setName(name);
            System.out.println("数据库："+studClass);
            studClasses.add(studClass);
//            System.out.println("来自数据库的数据:"+major+","+name+", "+id+","+grade);
        }
        return studClasses;
    }

//    删除
    public int delete(Integer id) throws SQLException {
        String sql="delete from stud_class where id=?";
        Connection connection = DbUtil.getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setInt(1, id);
        int i=statement.executeUpdate();
        return i;

    }

//    修改
public int update(StudClass studClass) throws SQLException {
        String sql="update stud_class set myclass=?,mymajor=?,grade=? where id=?";
        Connection connection=DbUtil.getConnection();
        PreparedStatement  statement= connection.prepareStatement(sql);
    statement.setString(1,studClass.getName());
    statement.setString(2,studClass.getMajor());
    statement.setInt(3,studClass.getGrade());
    statement.setInt(4,studClass.getId());
    int i=statement.executeUpdate();
    return i;

}



}

