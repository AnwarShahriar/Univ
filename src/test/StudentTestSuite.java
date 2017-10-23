package test;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Course;
import server.CourseInteractor;
import server.CourseTable;
import server.Student;
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
		Course course = interactor.createCourse("cleark", "CS101", 101000, 27, true, 2, 1, false, false);
		student.selectCourse(course);
		student.registerCourse(course);
		List<Course> courses = student.currentCourses();
		assertEquals(course.getCode(), courses.get(0).getCode());
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
	
	@Test
	public void studentRegistersForACourseDoesNotThrowException() {
		Student student = new Student("John", 23);
		Course course = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(course);
		versity.registerStudentForCourse(student, course);
	}
	
	@Test(expected = IllegalStateException.class)
	public void duplicateCourseRegistrationThrowsException() {
		Student student = new Student("John", 23);
		Course course = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(course);
		versity.registerStudentForCourse(student, course);
		versity.registerStudentForCourse(student, course);
	}
	
	@Test(expected = IllegalStateException.class)
	public void courseRegistrationAttemptMoreThan4ThrowsExceptionForFullTimeStudent() {
		Student student = new Student("John", 23);
		Course course1 = CourseTable.getInstance().findCourseByCode(111110);
		Course course2 = CourseTable.getInstance().findCourseByCode(111111);
		Course course3 = CourseTable.getInstance().findCourseByCode(111112);
		Course course4 = CourseTable.getInstance().findCourseByCode(111113);
		Course course5 = CourseTable.getInstance().findCourseByCode(111114);
		
		student.selectCourse(course1);
		student.selectCourse(course2);
		student.selectCourse(course3);
		student.selectCourse(course4);
		student.selectCourse(course5);
		
		versity.registerStudentForCourse(student, course1);
		versity.registerStudentForCourse(student, course2);
		versity.registerStudentForCourse(student, course3);
		versity.registerStudentForCourse(student, course4);
		versity.registerStudentForCourse(student, course5);
	}
	
	@Test(expected = IllegalStateException.class)
	public void courseRegistrationAttemptMoreThan2ThrowsExceptionForPartTimeStudent() {
		Student student = new Student("John", 23);
		student.setFullTime(false);
		
		Course course1 = CourseTable.getInstance().findCourseByCode(111110);
		Course course2 = CourseTable.getInstance().findCourseByCode(111111);
		Course course3 = CourseTable.getInstance().findCourseByCode(111112);
		
		student.selectCourse(course1);
		student.selectCourse(course2);
		student.selectCourse(course3);
		
		versity.registerStudentForCourse(student, course1);
		versity.registerStudentForCourse(student, course2);
		versity.registerStudentForCourse(student, course3);
	}
	
	@Test
	public void studentWithSameStudentNumberHasSameHashCode() {
		Student student1 = new Student("John", 23);
		Student student2 = new Student("John", 23);
		assertEquals(student1.hashCode(), student2.hashCode());
	}
	
	@Test
	public void studentHasCompletedCourses() {
		Student student = new Student("John", 23);
		Course c1 = CourseTable.getInstance().findCourseByCode(111110);
		Course c2 = CourseTable.getInstance().findCourseByCode(111111);
		
		student.selectCourse(c1);
		student.selectCourse(c2);
		
		student.registerCourse(c1);
		student.registerCourse(c2);
		
		student.completedCourse(c1);
		student.completedCourse(c2);
		
		assertEquals(2, student.completedCourses().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void attemptsToCompleteCourseNotInRegisteredCourseListThrowsException() {
		Student student = new Student("John", 23);
		Course c1 = CourseTable.getInstance().findCourseByCode(111110);
		Course c2 = CourseTable.getInstance().findCourseByCode(111111);
		
		student.selectCourse(c1);
		student.selectCourse(c2);
		
		student.registerCourse(c1);
		student.completedCourse(c2);
	}
	
	@Test
	public void selectCourseSucceed() {
		Student student = new Student("John", 23);
		Course c1 = CourseTable.getInstance().findCourseByCode(111110);
		Course c2 = CourseTable.getInstance().findCourseByCode(111111);
		
		student.selectCourse(c1);
		student.selectCourse(c2);
		
		assertEquals(2, student.selectedCourses().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void attemptsToSelectAlreadyRegisteredCourseThrowsException() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		student.selectCourse(c);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void attemptsToSelectAlreadyCompletedCourseThrowsException() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		student.completedCourse(c);
		student.selectCourse(c);
	}
	
	@Test(expected = IllegalStateException.class)
	public void attemptsToRegisterCourseNotInSelectedCoursesThrowsException() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.registerCourse(c);
	}
	
	@Test
	public void completedCourseCannotBeOnRegisteresCourseList() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		student.completedCourse(c);
		assertEquals(0, student.currentCourses().size());
	}
	
	@Test
	public void registeredCourseCannotBeOnSelectedCourseList() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		assertEquals(0, student.selectedCourses().size());
	}
	
	@Test
	public void studentDropsCourse() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		assertEquals(true, student.dropCourse(c));
	}
	
	@Test
	public void studentCannotDropUnregisteredCourses() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		assertEquals(false, student.dropCourse(c));
	}
	
	@Test
	public void studentDeregisterCourse() {
		Student student = new Student("John", 23);
		Course c = CourseTable.getInstance().findCourseByCode(111110);
		student.selectCourse(c);
		student.registerCourse(c);
		assertEquals(true, student.deRegisterCourse(c));
	}
	
	private void prepareDummyCourse() {
		CourseInteractor courseInteractor = new CourseInteractor(versity);
		courseInteractor.createCourse("cleark", "CS101", 111110, 26, true, 2, 1, false, false);
		courseInteractor.createCourse("cleark", "CS102", 111111, 26, true, 2, 1, false, false);
		courseInteractor.createCourse("cleark", "CS103", 111112, 26, true, 2, 1, false, false);
		courseInteractor.createCourse("cleark", "CS104", 111113, 26, true, 2, 1, false, false);
		courseInteractor.createCourse("cleark", "CS105", 111114, 26, true, 2, 1, false, true);
	}
}
