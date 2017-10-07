package server;

public class CourseInteractor {
	University versity;
	
	public CourseInteractor(University versity) {
		this.versity = versity;
	}

	public Course createCourse(
			String user,
			String title,
			int code,
			int capsize, 
			boolean hasAFinal, 
			int numberOfAssignments, 
			int numberOfMidTerms, 
			boolean enforcePrereqs
		) {
		
		validateCode(code);
		validateGradeElems(hasAFinal, numberOfAssignments, numberOfMidTerms);
		validateCapsize(capsize);
		
		if (versity.hasCourseExist(code)) 
			throw new IllegalArgumentException("Course already exist");
		
		Course course = versity.createCourse(title, capsize);
		course.setCode(code);
		course.setHasAFinal(hasAFinal);
		course.setNumberOfAssignments(numberOfAssignments);
		course.setNumberOfMidterms(numberOfMidTerms);
		course.enforcePrereqs(enforcePrereqs);
		
		return course;
	}

	private void validateCapsize(int capsize) {
		if (capsize <= 25)
			throw new IllegalArgumentException("capsize must be greater than 25");
	}

	private void validateGradeElems(boolean hasAFinal, int numberOfAssignments, int numberOfMidTerms) {
		if (!hasAFinal && numberOfAssignments == 0 && numberOfMidTerms == 0)
			throw new IllegalArgumentException("There must be one grade element");
	}

	private void validateCode(int code) {
		String codeStr = String.valueOf(code);
		if (codeStr.startsWith("0") || codeStr.length() != 6)
			throw new IllegalArgumentException("Code must be of length 6 and first digit cannot be zero");
	}
}
