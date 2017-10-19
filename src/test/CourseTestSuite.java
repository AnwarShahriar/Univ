package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Course;
import server.CourseInteractor;
import server.ProjectCourse;
import server.University;

public class CourseTestSuite {
	private static University versity;
	private CourseInteractor interactor;
	
	@BeforeClass
	public static void setup() {
		versity = University.getInstance();
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
								true, // enforcePrereqs)
								false // isProjectCourse
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
								true, // enforcePrereqs)
								false // isProjectCourse
								);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void capSizeLowerOrEqualThan25ThrowsException() {
		interactor.createCourse(
								"clerk", // user
								"CS", // title,
								110022, // code
								23, // capsize
								true, // hasAFinal
								2, // numberOfAssignments,
								1, // numberOfMidterms,
								true, // enforcePrereqs)
								false // isProjectCourse
								);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noGradeElementsThrowsException() {
		interactor.createCourse(
								"clerk", // user
								"CS", // title,
								110022, // code
								23, // capsize
								false, // hasAFinal
								0, // numberOfAssignments,
								0, // numberOfMidterms,
								true, // enforcePrereqs)
								false // isProjectCourse
								);
	}
	
	@Test
	public void courseHasProject() {
		ProjectCourse course = (ProjectCourse) interactor.createCourse(
								"clerk", // user
								"CS", // title,
								110022, // code
								26, // capsize
								false, // hasAFinal
								0, // numberOfAssignments,
								0, // numberOfMidterms,
								true, // enforcePrereqs)
								true // isProjectCourse
								);
		assertEquals(true, course.hasProject());
	}
	
	@Test
	public void totalWeightOfGradeElemsEqualsTo100() {
		Course course = null;
		course = interactor.createCourse(
									"clerk", // user
									"CS", // title,
									110022, // code
									26, // capsize
									true, // hasAFinal
									2, // numberOfAssignments,
									2, // numberOfMidterms,
									true, // enforcePrereqs)
									false // isProjectCourse
									);
		int totalWeight = 0;
		for (int i = 1; i <= 2; i++) {
			totalWeight += course.weightOfAssignment(i);
		}
		for (int i = 1; i <= 2; i++) {
			totalWeight += course.weightOfMidterm(i);
		}
		totalWeight += course.weightOfFinal();
		
		assertEquals(100, totalWeight);
	}
	
	@Test
	public void totalWeightOfGradeElemsInProjectCourseEqualsTo100() {
		ProjectCourse course = null;
		course = (ProjectCourse) interactor.createCourse(
									"clerk", // user
									"CS", // title,
									110022, // code
									26, // capsize
									true, // hasAFinal
									2, // numberOfAssignments,
									2, // numberOfMidterms,
									true, // enforcePrereqs)
									true // isProjectCourse
									);
		int totalWeight = 0;
		for (int i = 1; i <= 2; i++) {
			totalWeight += course.weightOfAssignment(i);
		}
		for (int i = 1; i <= 2; i++) {
			totalWeight += course.weightOfMidterm(i);
		}
		totalWeight += course.weightOfFinal();
		totalWeight += course.weightOfProject();
		
		assertEquals(100, totalWeight);
	}

}
