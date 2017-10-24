package server;

import java.util.List;

import org.apache.log4j.Logger;

import server.common.TermEventListener;
import utilities.Trace;

public class University implements TermEventListener {
	private static final University INSTANCE = new University();
	
	private Logger logger = Trace.getInstance().getLogger(this);
	
	private TermState termState = TermState.NONE;
	private int universityCourseCount = 25; // Default course count 25
	private int passRate = 70; // Default pass rate is 70
	
	private University() {}
	
	public static University getInstance() {
		return INSTANCE;
	}
	
	public Course createCourse(
			String user, 
			String title, 
			int code, 
			int capsize, 
			boolean hasAFinal, 
			int numberOfAssignments, 
			int numberOfMidTerms, 
			boolean enforcePrereqs, 
			boolean isProjectCourse) {
		if (hasCourseExist(code)) {
			String errMsg = String.format("Course with code %d already exists", code);
			throw new IllegalArgumentException(errMsg);
		}
		CourseInteractor interactor = new CourseInteractor(this);
		Course course = interactor.createCourse(user, title, code, capsize, hasAFinal, numberOfAssignments, numberOfMidTerms, enforcePrereqs, isProjectCourse);
		CourseTable.getInstance().add(course);
		return course;
	}

	public boolean hasCourseExist(int code) {
		for (Course c : CourseTable.getInstance().courses) {
			if (c.getCode() == code) {
				return true;
			}
		}
		return false;
	}

	public Student createStudent(String name, int studentNumber) {
		Student student = new Student(name, studentNumber);
		StudentTable.getInstance().add(student);
		return student;
	}

	public void registerStudentForCourse(Student student, Course course) {
		try {
			student.registerCourse(course);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	public enum TermState {
		NONE,
		CREATE_STUDENT_COURSE_STATE,
		COURSE_REGISTRATION_STATE,
		TERM_PROPERLY_STARTED_STATE,
		TERM_END_STATE
	}

	public TermState getTermState() {
		return termState;
	}

	@Override
	public void onCreate() {
		termState = TermState.CREATE_STUDENT_COURSE_STATE;
	}

	@Override
	public void onRegistrationPossible() {
		termState = TermState.COURSE_REGISTRATION_STATE;
	}

	@Override
	public void onTermProperlyStarted() {
		termState = TermState.TERM_PROPERLY_STARTED_STATE;
	}

	@Override
	public void onTermEnded() {
		termState = TermState.TERM_END_STATE;
	}

	public void universityCourseCount(int count) {
		if (count < 1 || count > 25) {
			String errMsg = "University course count must be between 1 and 25";
			throw new IllegalArgumentException(errMsg);
		}
		this.universityCourseCount = count;
	}

	public int getUniversityCourseCount() {
		return universityCourseCount;
	}

	public void passRate(int passRate) {
		if (passRate < 0 || passRate > 100) {
			String errMsg = "Pass rate must be between 0 and 100";
			throw new IllegalArgumentException(errMsg);
		}
		this.passRate = passRate;
	}
	
	public int getPassRate() {
		return passRate;
	}

	public List<Course> courses() {
		return CourseTable.getInstance().courses;
	}

}
