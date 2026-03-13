package com.course.model;

public class Student {
    int studentId;
    String studentName;
    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
    public int getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void displayStudent() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
    }
}
