package server;

public class ProjectCourse extends Course {

	private int projectWeight;

	public ProjectCourse(String title, int capsize) {
		super(title, capsize);
	}
	
	public int weightOfProject() {
		return projectWeight;
	}

	public boolean hasProject() {
		return true;
	}
	
	@Override
	protected void generateWeight() {
		int remainWeightToAssign = 100;
		if (numberOfAssignments > 0) {
			int gradeElems = numberOfAssignments + numberOfMidterms + (hasAFinal ? 1 : 0) + 1;
			remainWeightToAssign = assignWeight(assignmentWeights, numberOfAssignments, gradeElems, remainWeightToAssign);
		}
		
		if (numberOfMidterms > 0) {
			int gradeElems = numberOfMidterms + (hasAFinal ? 1 : 0) + 1;
			remainWeightToAssign = assignWeight(midTermWeights, numberOfMidterms, gradeElems, remainWeightToAssign);
		}
		
		if (hasAFinal) {
			int gradeElems = (hasAFinal ? 1 : 0) + 1;
			int[] finalArr = new int[1];
			remainWeightToAssign = assignWeight(finalArr, 1, gradeElems, remainWeightToAssign);
			finalWeight = finalArr[0];
		}
		
		projectWeight = remainWeightToAssign;
	}
}
