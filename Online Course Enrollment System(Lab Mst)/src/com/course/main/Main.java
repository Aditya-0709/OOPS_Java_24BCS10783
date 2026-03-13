package com.course.main;

import com.course.model.*;
import com.course.service.*;
import com.course.exception.*;

public class Main {

    public static void main(String[] args) {

        CourseService service = new CourseService();

        Course c1 = new Course(101, "Java", 2);
        Course c2 = new Course(102, "Python", 3);

        service.addCourse(c1);
        service.addCourse(c2);

        Student s1 = new Student(1, "Aditya");
        Student s2 = new Student(2, "Rahul");
        Student s3 = new Student(3, "Aman");

        try {

            service.enrollStudent(101, s1);
            service.enrollStudent(101, s2);
            service.enrollStudent(102, s3);

        }
        catch (CourseFullException e) {
            System.out.println(e.getMessage());
        }
        catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (DuplicateEnrollmentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Course Details:");
        service.viewCourses();
    }
}