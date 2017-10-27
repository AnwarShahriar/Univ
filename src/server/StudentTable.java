package server;

import java.util.ArrayList;
import java.util.List;

public class StudentTable {
	
	private static final StudentTable INSTANCE = new StudentTable();
	
	List<Student> students = new ArrayList<>();
	
	private StudentTable() {}
	
	public static StudentTable getInstance() {
		return INSTANCE;
	}
	
	public void clear() {
		students.clear();
	}

	public void add(Student student) {
		students.add(student);
	}
	
	public Student findByStudentNumber(int studentNumber) {
		for (Student student : students) {
			if (student.getStudentNumber() == studentNumber) {
				return student;
			}
		}
		return null;
	}
	
	public Student findByEmailPassword(String email, String password) {
		for (Student student : students) {
			if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
				return student;
			}
		}
		return null;
	}
	
	public static void createFakeStudent() {
		Student student1 = University.getInstance().createStudent("Jim Gordon", 123123);
		student1.setEmail("jim@gotham");
		student1.setPassword("12345");
		
		Student student2 = University.getInstance().createStudent("Lee Thompkins", 231231);
		student2.setEmail("lee@gotham");
		student2.setPassword("12345");
	}
}
