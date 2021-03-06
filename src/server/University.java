package server;

import java.util.ArrayList;
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
		if (termState != TermState.CREATE_STUDENT_COURSE_STATE) {
			String errMsg = "Course cannot be created because course creation period is over";
			throw new IllegalStateException(errMsg);
		}
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
	
	public boolean studentExist(int studentNumber) {
		for (Student s : StudentTable.getInstance().students) {
			if (s.getStudentNumber() == studentNumber) {
				return true;
			}
		}
		return false;
	}

	public Student createStudent(String name, int studentNumber) {
		return createStudent(name, studentNumber, "", "");
	}
	
	public Student createStudent(String name, int studentNumber, String email, String password) {
		if (termState != TermState.CREATE_STUDENT_COURSE_STATE) {
			String errMsg = "Student cannot be created because student creation period is over";
			throw new IllegalStateException(errMsg);
		}
		if (studentExist(studentNumber)) {
			String errMsg = String.format("Student with student_number %d already exists", studentNumber);
			throw new IllegalArgumentException(errMsg);
		}
		Student student = new Student(name, studentNumber);
		student.setEmail(email);
		student.setPassword(password);
		StudentTable.getInstance().add(student);
		return student;
	}

	public void registerStudentForCourse(Student student, Course course) {
		if (termState != TermState.COURSE_REGISTRATION_STATE) {
			String errMsg = "Time for course registration in this term has been passed or not yet been started";
			throw new IllegalStateException(errMsg);
		}
		try {
			student.registerCourse(course);
			course.addStudent(student);
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
		TWO_WEEK_PASSED_AFTER_TERM_STARTED_STATE,
		TERM_END_STATE
	}

	public TermState getTermState() {
		return termState;
	}

	@Override
	public void onCreate() {
		termState = TermState.CREATE_STUDENT_COURSE_STATE;
		System.out.println(termState.name());
	}

	@Override
	public void onRegistrationPossible() {
		termState = TermState.COURSE_REGISTRATION_STATE;
		System.out.println(termState.name());
	}

	@Override
	public void onTermProperlyStarted() {
		termState = TermState.TERM_PROPERLY_STARTED_STATE;
		System.out.println(termState.name());
	}
	
	@Override
	public void onTwoWeekPassedTillTermStarted() {
		termState = TermState.TWO_WEEK_PASSED_AFTER_TERM_STARTED_STATE;
		System.out.println(termState.name());
	}

	@Override
	public void onTermEnded() {
		termState = TermState.TERM_END_STATE;
		System.out.println(termState.name());
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

	public List<Student> students() {
		return StudentTable.getInstance().students;
	}

	public void selectCourseForStudent(Student student, Course course) {
		student.selectCourse(course);
	}

	public void cancelCourse(Course course) {
		List<Student> students = course.students();
		for (Student s : students) {
			s.currentCourses().remove(course);
			s.selectedCourses().remove(course);
		}
		course.students().clear();
	}

	public void destroyCourse(Course course) {
		cancelCourse(course);
		CourseTable.getInstance().courses.remove(course);
	}

	public boolean deregisterCourse(Course course, Student student) {
		if (termState == TermState.TERM_PROPERLY_STARTED_STATE) {
			return student.deRegisterCourse(course);
		} else {
			String errMsg = "Course can be deregistered upto two weeks after term has started properly";
			throw new IllegalStateException(errMsg);
		}
	}

	public void deleteStudent(Student student) {
		StudentTable.getInstance().students.remove(student);
		
		List<Course> courses = new ArrayList<>();
		courses.addAll(student.currentCourses());
		courses.addAll(student.selectedCourses());
		
		for (Course c : courses) {
			c.students().remove(student);
		}
	}

	public boolean dropCourse(Student student, Course course) {
		if (termState != TermState.TWO_WEEK_PASSED_AFTER_TERM_STARTED_STATE) {
			String errMsg = "Cannot drop a course before two weeks has been passed since term started properly";
			throw new IllegalStateException(errMsg);
		}
		return student.dropCourse(course);
	}
}
