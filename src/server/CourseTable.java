package server;

import java.util.ArrayList;
import java.util.List;

public class CourseTable {
	
	private static final CourseTable INSTANCE = new CourseTable();
	
	List<Course> courses = new ArrayList<>();
	
	private CourseTable() {}
	
	public static CourseTable getInstance() {
		return INSTANCE;
	}
	
	public void add(Course course) {
		courses.add(course);
	}

	public List<Course> getCourses(List<Integer> courseIds) {
		List<Course> res = new ArrayList<>();
		for (int id : courseIds) {
			for (Course c : courses) {
				if (c.getCode() == id) {
					res.add(c);
				}
			}
		}
		return res;
	}
}
