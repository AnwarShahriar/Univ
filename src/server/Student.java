package server;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String name;
	private int studentNumber;
	private boolean fullTime;
	private int maxCourseCount;
	
	private List<Course> completedCourses = new ArrayList<>();
	private List<Course> registeredCourses = new ArrayList<>();
	
	public Student(String name, int studentNumber) {
		this.name = name;
		this.studentNumber = studentNumber;
		setFullTime(true);
	}

	public void registerCourse(Course course) {
		if (alreadyRegistered(course)) {
			throw new IllegalStateException("Already registered for course");
		}
		
		if (registeredCourses.size() == maxCourseCount) {
			throw new IllegalStateException(String.format("Max course count for %s student is %d", (fullTime ? "full time" : "part time"), maxCourseCount));
		}
		
		registeredCourses.add(course);
	}

	private boolean alreadyRegistered(Course course) {
		return registeredCourses.contains(course);
	}

	public List<Course> currentCourses() {
		return registeredCourses;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentNumber != other.studentNumber)
			return false;
		return true;
	}

	public void completedCourse(Course course) {
		if (!registeredCourses.contains(course)) { 
			String errMsg = String.format("Course %s is not registered by Student %d", course.title, studentNumber);
			throw new IllegalArgumentException(errMsg);
		}
		completedCourses.add(course);
	}

	public List<Course> completedCourses() {
		return completedCourses;
	}
	
}
