package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.Course;
import server.CourseInteractor;
import server.CourseTable;
import server.Student;
import server.StudentTable;
import server.University;
import server.University.TermState;
import server.common.TermEventListener;
import server.common.TermSimulator;

public class UniversityTestSuite {
	University versity;
	TestTermSimulator simulator;
	CourseInteractor interactor;
	
	@Before
	public void setup() {
		versity = University.getInstance();
		simulator = new TestTermSimulator(versity);
		interactor = new CourseInteractor(versity);
		CourseTable.getInstance().clear();
		StudentTable.getInstance().clear();
		simulator.termCreated();
	}
	
	@Test
	public void termIsCreated() {
		simulator.termCreated();
		assertEquals(TermState.CREATE_STUDENT_COURSE_STATE, versity.getTermState());
	}
	
	@Test
	public void termIsReadyForRegistration() {
		simulator.termAllowCourseRegistration();
		assertEquals(TermState.COURSE_REGISTRATION_STATE, versity.getTermState());
	}
	
	@Test
	public void termStartedProperly() {
		simulator.startTerm();
		assertEquals(TermState.TERM_PROPERLY_STARTED_STATE, versity.getTermState());
	}
	
	@Test
	public void termEnded() {
		simulator.endTerm();
		assertEquals(TermState.TERM_END_STATE, versity.getTermState());
	}
	
	@Test
	public void testUniversityCourseCount() {
		versity.universityCourseCount(20);
		assertEquals(20, versity.getUniversityCourseCount());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void universityCourseCountGreaterThan_25_ThrowsException() {
		versity.universityCourseCount(30);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void universityCourseCountSmallerThan_1_ThrowsException() {
		versity.universityCourseCount(0);
	}
	
	@Test
	public void testUniversityPassRate() {
		versity.passRate(65);
		assertEquals(65, versity.getPassRate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void universityPassRateGreaterThan_100_ThrowsException() {
		versity.passRate(101);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void universityPassRateSmallerThan_0_ThrowsException() {
		versity.passRate(-1);
	}
	
	@Test
	public void testUniversityCourseListHasCourses() {
		versity.createCourse(
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
		assertEquals(1, versity.courses().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void attemptToCreateDuplicateCourseWithSameCodeThrowsException() {
		versity.createCourse(
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
		versity.createCourse(
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
	}
	
	@Test
	public void testUniversityStudentListHasStudents() {
		versity.createStudent("John", 1);
		versity.createStudent("Noah", 2);
		assertEquals(2, versity.students().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void attemptToCreateDuplicateStudentWithSameNumberThrowsException() {
		versity.createStudent("John", 1);
		versity.createStudent("Noah", 1);
	}
	
	@Test(expected = IllegalStateException.class)
	public void courseCannotBeCreatedAfterTermCourseCreationWindowIsPassed() {
		simulator.startTerm();
		versity.createCourse(
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
	}
	
	@Test(expected = IllegalStateException.class)
	public void studentCannotBeCreatedAfterTermStudentCreationWindowIsPassed() {
		simulator.startTerm();
		versity.createStudent("John", 1);
	}
	
	@Test
	public void selectCourseForStudentSucceeds() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);

		assertEquals(course, student.selectedCourses().get(0));
	}
	
	@Test(expected = IllegalStateException.class)
	public void courseRegistrationFailsUnlessTermStateForRegistrationInProgress() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		versity.registerStudentForCourse(student, course);
	}

	@Test
	public void courseRegistrationSucceedsWhenCourseRegistrationIsAllowedInTerm() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		
		simulator.termAllowCourseRegistration();
		versity.registerStudentForCourse(student, course);
		
		assertEquals(student, course.students().get(0));
	}
	
	@Test
	public void cancelCourseSucceeds() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		
		simulator.termAllowCourseRegistration();
		versity.registerStudentForCourse(student, course);
		
		versity.cancelCourse(course);
		boolean cancelled = 
				!course.students().contains(student) && 
				!student.currentCourses().contains(course) && 
				!student.selectedCourses().contains(course);
		
		assertEquals(true, cancelled);
	}
	
	@Test
	public void destroyCourseSucceeds() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		
		simulator.termAllowCourseRegistration();
		versity.registerStudentForCourse(student, course);
		
		versity.destroyCourse(course);
		boolean destroyed = 
				!versity.courses().contains(course) && 
				!student.currentCourses().contains(course) && 
				!student.selectedCourses().contains(course);
		
		
		assertEquals(true, destroyed);
	}
	
	@Test
	public void studentDeregistrationSucceedsBeforeTwoWeekPassedAfterTermStarted() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		
		simulator.termAllowCourseRegistration();
		versity.registerStudentForCourse(student, course);
		
		simulator.startTerm();
		versity.deregisterCourse(course, student);
		
		assertEquals(true, !student.currentCourses().contains(course));
	}
	
	@Test(expected = IllegalStateException.class)
	public void studentDeregistrationFailsAfterTwoWeekPassedTillTermStarted() {
		Course course = versity.createCourse("clerk", // user
				"CS", // title,
				110022, // code
				26, // capsize
				true, // hasAFinal
				2, // numberOfAssignments,
				1, // numberOfMidterms,
				true, // enforcePrereqs)
				false // isProjectCourse
		);
		Student student = versity.createStudent("John", 1);
		versity.selectCourseForStudent(student, course);
		
		simulator.termAllowCourseRegistration();
		versity.registerStudentForCourse(student, course);
		
		simulator.twoWeeksPassesTillTermStarted();
		versity.deregisterCourse(course, student);
	}
}
