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
}
