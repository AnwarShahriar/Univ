package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Course {
	protected String title;
	protected int capsize;
	protected int code;
	protected boolean hasAFinal;
	protected int numberOfAssignments;
	protected int numberOfMidterms;
	protected boolean enforcePrereqs;
	
	protected int[] assignmentWeights;
	protected int[] midTermWeights;
	protected int finalWeight;
	
	List<Student> students = new ArrayList<>();
	List<Integer> preRequisites = new ArrayList<>();
	
	public Course(String title, int capsize) {
		this.title = title;
		this.capsize = capsize;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setHasAFinal(boolean hasAFinal) {
		this.hasAFinal = hasAFinal;
		generateWeight();
	}

	public void setNumberOfAssignments(int numberOfAssignments) {
		this.numberOfAssignments = numberOfAssignments;
		assignmentWeights = new int[numberOfAssignments];
		generateWeight();
	}

	public void setNumberOfMidterms(int numberOfMidTerms) {
		this.numberOfMidterms = numberOfMidTerms;
		midTermWeights = new int[numberOfMidTerms];
		generateWeight();
	}

	public void enforcePrereqs(boolean enforcePrereqs) {
		this.enforcePrereqs = enforcePrereqs;
	}
	
	public List<Student> students() {
		return students;
	}
	
	public List<Integer> preRequisites() {
		return preRequisites;
	}
	
	public int weightOfAssignment(int assignmentNumber) {
		return assignmentWeights[assignmentNumber - 1];
	}
	
	public int weightOfMidterm(int midtermNumber) {
		return midTermWeights[midtermNumber - 1];
	}
	
	public int weightOfFinal() {
		return finalWeight;
	}
	
	public boolean hasProject() {
		return false;
	}

	public int getCode() {
		return code;
	}
	
	public boolean isFull() {
		return students.size() == capsize;
	}
	
	protected void generateWeight() {
		int remainWeightToAssign = 100;
		if (numberOfAssignments > 0) {
			int gradeElems = numberOfAssignments + numberOfMidterms + (hasAFinal ? 1 : 0);
			remainWeightToAssign = assignWeight(assignmentWeights, numberOfAssignments, gradeElems, remainWeightToAssign);
		}
		
		if (numberOfMidterms > 0) {
			int gradeElems = numberOfMidterms + (hasAFinal ? 1 : 0);
			remainWeightToAssign = assignWeight(midTermWeights, numberOfMidterms, gradeElems, remainWeightToAssign);
		}
		
		if (hasAFinal) {
			finalWeight = remainWeightToAssign;
		}
	}
	
	protected int assignWeight(int[] weightArr, int elemCount, int remainGradeElemCount, int totalWeight) {
		double totalGradeElems = remainGradeElemCount;
		
		double assignmentPercentage = (totalWeight / totalGradeElems) * elemCount;
		int totalAllotedAssignmentWeight = 0;
		for (int i = 0; i < elemCount; i++) {
			int weight = (int) ThreadLocalRandom.current().nextDouble(1, assignmentPercentage / elemCount);
			weightArr[i] = weight;
			totalAllotedAssignmentWeight += weight;
			
			if (i == elemCount - 1) {
				int extraWeight = (int) assignmentPercentage - totalAllotedAssignmentWeight;
				totalAllotedAssignmentWeight += extraWeight;
				weightArr[i] = weight + extraWeight;
			}
		}
		totalWeight -= totalAllotedAssignmentWeight;
		
		return totalWeight;
	}

	public boolean addStudent(Student student) {
		if (students.contains(student) || isFull()) return false;
		return students.add(student);
	}

	public boolean removeStudent(Student student) {
		return students.remove(student);
	}

	public boolean addPreRequisite(int courseCode) {
		return preRequisites.add(courseCode);
	}

}
