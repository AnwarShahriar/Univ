package test;

import java.util.ArrayList;
import java.util.List;

import server.Course;
import server.CourseTable;

public class Student {
	private String name;
	private int studentNumber;
	private boolean fullTime;
	
	private List<Integer> courseIds = new ArrayList<>();
	
	public Student(String name, int studentNumber) {
		this.name = name;
		this.studentNumber = studentNumber;
		fullTime = true;
	}

	public void registerCourse(Course course) {
		courseIds.add(course.getCode());
	}

	public List<Course> currentCourses() {
		return CourseTable.getInstance().getCourses(courseIds);
	}

	public String getName() {
		return name;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public boolean isFullTime() {
		return fullTime;
	}
	
	public void setFullTime(boolean fullTime) {
		this.fullTime = fullTime;
	}

}
