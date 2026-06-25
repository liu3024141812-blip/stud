package com.example.stud.entity;

public class StudClass {
    private Integer id;
    private Integer grade;
    private String major;
    private String name;

    @Override
    public String toString() {
        return "StudClass{" +
                "id=" + id +
                ", grade=" + grade +
                ", major='" + major + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
