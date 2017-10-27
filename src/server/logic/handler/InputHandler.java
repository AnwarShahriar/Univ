package server.logic.handler;

import java.util.List;

import server.Course;
import server.CourseTable;
import server.Student;
import server.StudentTable;
import server.University;
import server.logic.handler.model.Output;
import server.logic.handler.model.ServerOutput;

public class InputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING=1;
    public static final int CLERK = 2;
    public static final int STUDENT = 3;
    public static final int CREATESTUDENT=4;
    public static final int CREATECOURSE=5;
    public static final int CANCELCOURSE=6;
    public static final int DELETESTUDENT=7;
    public static final int CLERKLOGIN=14;
    public static final int STUDENTLOGIN=15;
    public static final int REGISTERCOURSE=16;
    public static final int DROPCOURSE=17;
    public static final int TAKECOURSE=18;
    
    private static final String CLERK_MENU = "\nPlease select from the Menu:\nCreate Student/Course\nCancel Course\nDelete Student\nMain Menu\nLog Out";
    private static final String STUDENT_MENU = "\nPlease select from the Menu:\nRegister Course\nDrop Course\nTake Course\nMain Menu\nLog Out";
    
    OutputHandler outputHandler=new OutputHandler();

    public static int loggedInStudentNumber = 0;

	public ServerOutput processInput(String input, int state) {
		 String output = "";
		 Output o = new Output("",0);
		 ServerOutput oo = new ServerOutput(output,o.getState());
	        if (state == WAITING) {
	        	output = "Who Are you?Clerk or Student?";
	            state = FINISHWAITING;
	            oo.setOutput(output);
	            oo.setState(state);
	         }else if (state == FINISHWAITING) {
	            if (input.equalsIgnoreCase("clerk")) {
	            	output="Please Input The Password:";
	            	state=CLERKLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if (input.equalsIgnoreCase("student")) {
	            	output="Please Input Email and Password:'email,password'";
	            	state=STUDENTLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else{
	            	output = "Who Are you?Clerk or Student?";
	            	state = FINISHWAITING;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }
	        }else if(state==CLERKLOGIN){
	        	o=outputHandler.clerkLogin(input);
        		output=o.getOutput();
        		state=o.getState();
        		oo.setOutput(output);
	            oo.setState(state);
	        }else if(state==STUDENTLOGIN){
	        	o=outputHandler.studentLogin(input);
        		output=o.getOutput();
        		state=o.getState();
        		oo.setOutput(output);
	            oo.setState(state);
	        }else if (state==CLERK){
	        	if (input.equalsIgnoreCase("create student")) {
	            	output = "Please Input Student Info: 'Name, Student Number, Email, Password'";
	            	state=CREATESTUDENT;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if (input.equalsIgnoreCase("create course")) {
	            	output = "Please Input Course Info as given format: " + 
	            			 "\n" +
	            			 "'Title, Code, Capcity Size, Has Final(True/False), Assignment Count, MidTerm Count, Enforce Prerequisites(True/False), Has Project(True/False)'";
	            	state=CREATECOURSE;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("cancel course")){
	            	output = "Please Input Course Code to cancel: ";
	            	state=CANCELCOURSE;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("delete student")){
	            	output = "Please Input Student Number to delete: ";
	            	state=DELETESTUDENT;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("main menu")){
	        		output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	            	output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	            }
	        }else if (state==STUDENT){
	        	if (input.equalsIgnoreCase("register course")) {
	        		Student student = StudentTable.getInstance().findByStudentNumber(loggedInStudentNumber);
	        		List<Course> courses = student.selectedCourses();
	        		if (courses.size() == 0) {
	        			output = "\n=> There is no selected course to register\n" + STUDENT_MENU;
		            	state=STUDENT;
	        		} else {
	        			output = "\nPlease input Course Code from the selected courses below to register:\n"; 
	        			for (Course course: courses) {
	        				output += String.format("Title: %s, Code: %d\n", course.getTitle(), course.getCode());
	        			}
	        			state=REGISTERCOURSE;
	        		}
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if (input.equalsIgnoreCase("drop course")) {
	            	Student student = StudentTable.getInstance().findByStudentNumber(loggedInStudentNumber);
	        		List<Course> courses = student.currentCourses();
	        		if (courses.size() == 0) {
	        			output = "\n=> There is no registered course to drop\n" + STUDENT_MENU;
		            	state=STUDENT;
	        		} else {
	        			output = "\nPlease input Course Code from the registered courses below to drop:\n"; 
	        			for (Course course: courses) {
	        				output += String.format("Title: %s, Code: %d\n", course.getTitle(), course.getCode());
	        			}
	        			state=DROPCOURSE;
	        		}
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("take course")){
	        		List<Course> courses = University.getInstance().courses();
	        		if (courses.size() == 0) {
	        			output = "\n=> There is no course to select\n" + STUDENT_MENU;
		            	state=STUDENT;
	        		} else {
	        			output = "\nPlease input Course Code from the courses below to select:\n"; 
	        			for (Course course: courses) {
	        				output += String.format("Title: %s, Code: %d\n", course.getTitle(), course.getCode());
	        			}
	        			state=TAKECOURSE;
	        		}
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("main menu")){
	        		output = STUDENT_MENU;
	                state = STUDENT;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	            	output = STUDENT_MENU;
	                state = STUDENT;
	                oo.setOutput(output);
		            oo.setState(state);
	            }
	        
	        }else if(state==CREATESTUDENT){
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.createStudent(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        }else if(state==CREATECOURSE){
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.createCourse(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        } else if (state == CANCELCOURSE) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.cancelCourse(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        } else if (state == DELETESTUDENT) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = CLERK_MENU;
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.deleteStudent(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        } else if (state == REGISTERCOURSE) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = STUDENT_MENU;
	                state = STUDENT;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.registerCourse(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        } else if (state == DROPCOURSE) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = STUDENT_MENU;
	                state = STUDENT;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.dropCourse(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        } else if (state == TAKECOURSE) {
	        	if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else if(input.equalsIgnoreCase("main menu")){
	        		output = STUDENT_MENU;
	                state = STUDENT;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	        		o=outputHandler.takeCourse(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	        	}
	        }
	        return oo;
	}


}
