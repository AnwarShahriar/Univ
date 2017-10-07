package server;

public class Course {
	private String title;
	private int capsize;
	private int code;
	private boolean hasAFinal;
	private int numberOfAssignments;
	private int numberOfMidterms;
	private boolean enforcePrereqs;
	
	public Course(String title, int capsize) {
		this.title = title;
		this.capsize = capsize;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setHasAFinal(boolean hasAFinal) {
		this.hasAFinal = hasAFinal;
	}

	public void setNumberOfAssignments(int numberOfAssignments) {
		this.numberOfAssignments = numberOfAssignments;	
	}

	public void setNumberOfMidterms(int numberOfMidTerms) {
		this.numberOfMidterms = numberOfMidTerms;
	}

	public void enforcePrereqs(boolean enforcePrereqs) {
		this.enforcePrereqs = enforcePrereqs;
	}

	public int getCode() {
		return code;
	}

}
