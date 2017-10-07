package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Course;
import server.CourseInteractor;
import server.University;

public class CourseTestSuite {
	private static University versity;
	private CourseInteractor interactor;
	
	@BeforeClass
	public static void setup() {
		versity = new University();
	}

	@Before
	public void prepare() {
		interactor = new CourseInteractor(versity);
	}
	
	@Test
	public void createCourse() {
		Course course = null;
		course = interactor.createCourse(
								"clerk", // user
								"CS", // title,
								110022, // code
								26, // capsize
								true, // hasAFinal
								2, // numberOfAssignments,
								1, // numberOfMidterms,
								true // enforcePrereqs)
								);
		
		assertEquals(true, course != null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCourseIdThrowsException() {
		interactor.createCourse(
								"clerk", // user
								"CS", // title,
								010022, // code
								26, // capsize
								true, // hasAFinal
								2, // numberOfAssignments,
								1, // numberOfMidterms,
								true // enforcePrereqs)
								);
	}
	
	

}
