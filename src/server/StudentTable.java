package server;

import java.util.ArrayList;
import java.util.List;

import test.Student;

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
}
