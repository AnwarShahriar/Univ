package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.StudentTable;
import server.University;

public class StudentTestSuite {
	
	private static University versity;
	
	@BeforeClass
	public static void setup() {
		versity = University.getInstance();
	}
	
	@Before
	public void prepare() {
		StudentTable.getInstance().clear();
	}
	
	@Test
	public void createStudent() {
		Student student = versity.createStudent("John Doe", 123);
		assertEquals(true, student != null);
	}
}
