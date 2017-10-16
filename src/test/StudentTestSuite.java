package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Course;
import server.CourseInteractor;
import server.CourseTable;
import server.StudentTable;
import server.University;

public class StudentTestSuite {
	
	private static University versity;
	
	@BeforeClass
	public static void setup() {
		versity = University.getInstance();
	}
	
	@Before
	public void prepare() {
		StudentTable.getInstance().clear();
		CourseTable.getInstance().clear();
		
		prepareDummyCourse();
	}
	
	@Test
	public void createStudent() {
		Student student = versity.createStudent("John Doe", 123);
		assertEquals(true, student != null);
	}
	
	@Test
	public void studentRegistersCourse() {
		Student student = versity.createStudent("John Doe", 123);
		CourseInteractor interactor = new CourseInteractor(versity);
		Course course = interactor.createCourse("cleark", "CS101", 101000, 27, true, 2, 1, false);
		student.registerCourse(course);
		List<Course> courses = student.currentCourses();
		
		boolean exist = false;
		for (Course c : courses) {
			if (c.getCode() == course.getCode()) {
				exist = true;
				break;
			}
		}
		
		assertEquals(true, exist);
	}
	
	@Test
	public void studentHasName() {
		Student student = versity.createStudent("John Doe", 123);
		assertEquals("John Doe", student.getName());
	}
	
	@Test
	public void studentHasStudentNumber() {
		Student student = versity.createStudent("John Doe", 123);
		assertEquals(123, student.getStudentNumber());
	}
	
	@Test
	public void studentIsFullTimeByDefault() {
		Student student = versity.createStudent("John Doe", 123);
		assertEquals(true, student.isFullTime());
	}
	
	@Test
	public void studentIsPartTime() {
		Student student = versity.createStudent("John Doe", 123);
		student.setFullTime(false);
		assertEquals(false, student.isFullTime());
	}
	
	private void prepareDummyCourse() {
		CourseInteractor courseInteractor = new CourseInteractor(versity);
		courseInteractor.createCourse("cleark", "CS101", 111110, 26, true, 2, 1, false);
		courseInteractor.createCourse("cleark", "CS102", 111111, 26, true, 2, 1, false);
		courseInteractor.createCourse("cleark", "CS103", 111112, 26, true, 2, 1, false);
		courseInteractor.createCourse("cleark", "CS104", 111113, 26, true, 2, 1, false);
		courseInteractor.createCourse("cleark", "CS105", 111114, 26, true, 2, 1, false);
	}
}
