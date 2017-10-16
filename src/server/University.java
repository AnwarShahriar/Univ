package server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utilities.Trace;

public class University {
	private static final University INSTANCE = new University();
	Logger logger = Trace.getInstance().getLogger(this);
	
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

	public void registerStudentForCourse(Student student, Course course) {
		try {
			student.registerCourse(course);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}

}
