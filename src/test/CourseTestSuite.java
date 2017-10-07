package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import server.Course;
import server.CourseInteractor;
import server.University;

public class CourseTestSuite {
	private static University versity;
	
	@BeforeClass
	public static void setup() {
		versity = new University();
	}

	@Test
	public void createCourse() {
		CourseInteractor interactor = new CourseInteractor(versity);
		Course course = null;
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(true, course != null);
	}

}
