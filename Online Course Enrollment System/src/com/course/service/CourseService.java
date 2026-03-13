package com.course.service;

import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;
import com.course.exception.DuplicateEnrollmentException;
import com.course.model.Course;
import com.course.model.Student;

import java.util.ArrayList;
import java.io.*;

public class CourseService {

    ArrayList<Course> courseList = new ArrayList<>();
    ArrayList<Integer> enrolledStudents = new ArrayList<>();

    public void addCourse(Course c) {
        courseList.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseFullException, CourseNotFoundException, DuplicateEnrollmentException {

        Course course = null;

        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);

            if (c.getCourseId() == courseId) {
                course = c;
            }
        }

        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }

        if (enrolledStudents.contains(s.getStudentId())) {
            throw new DuplicateEnrollmentException("Student already enrolled");
        }

        if (course.getEnrolledStudents() >= course.getMaxSeats()) {
            throw new CourseFullException("Course is full");
        }

        enrolledStudents.add(s.getStudentId());
        course.setEnrolledStudents(course.getEnrolledStudents() + 1);

        System.out.println("Student enrolled successfully");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true));

            bw.write(courseId + " " + s.getStudentId() + " " + s.getStudentName());
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            System.out.println("File writing error");
        }
    }

    public void viewCourses() {

        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);
            c.displayCourse();
        }

        System.out.println("Enrollment Records from File:");

        try {
            BufferedReader br = new BufferedReader(new FileReader("courses.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("File reading error");
        }
    }
}