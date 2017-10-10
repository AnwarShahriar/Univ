package server;

import java.util.ArrayList;
import java.util.List;

import test.Student;

public class University {
	private static final University INSTANCE = new University();
	
	private List<Course> courses = new ArrayList<>();
	
	private University() {}
	
	public static University getInstance() {
		return INSTANCE;
	}
	
	public Course createCourse(String title, int capsize) {
		Course course = new Course(title, capsize);
		CourseTable.getInstance().add(course);
		return course;
	}

	public boolean hasCourseExist(int code) {
		for (Course c : courses) {
			if (c.getCode() == code) {
				return true;
			}
		}
		return false;
	}

	public Student createStudent(String name, int studentNumber) {
		Student student = new Student(name, studentNumber);
		StudentTable.getInstance().add(student);
		return student;
	}

}
