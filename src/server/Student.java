package server;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String name;
	private int studentNumber;
	private boolean fullTime;
	private int maxCourseCount;
	
	private List<Integer> courseIds = new ArrayList<>();
	
	public Student(String name, int studentNumber) {
		this.name = name;
		this.studentNumber = studentNumber;
		setFullTime(true);
	}

	public void registerCourse(Course course) {
		if (alreadyRegistered(course.getCode())) {
			throw new IllegalStateException("Already registered for course");
		}
		
		if (courseIds.size() == maxCourseCount) {
			throw new IllegalStateException(String.format("Max course count for %s student is %d", (fullTime ? "full time" : "part time"), maxCourseCount));
		}
		
		courseIds.add(course.getCode());
	}

	private boolean alreadyRegistered(int code) {
		for (int id : courseIds) {
			if (id == code) {
				return true;
			}
		}
		return false;
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
		maxCourseCount = fullTime ? 4 : 2;
	}

}
