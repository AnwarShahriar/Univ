package server;

import java.util.ArrayList;
import java.util.List;

public class University {
	
	private List<Course> courses = new ArrayList<>();

	public Course createCourse(String title, int capsize) {
		return new Course(title, capsize);
	}

	public boolean hasCourseExist(int code) {
		for (Course c : courses) {
			if (c.getCode() == code) {
				return true;
			}
		}
		return false;
	}

}
