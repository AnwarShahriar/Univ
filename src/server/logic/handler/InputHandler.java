package server.logic.handler;

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
    
    private static final String CLERK_MENU = "\nPlease select from the Menu:\nCreate Student/Course\nCancel Course\nDelete Student";
    
    OutputHandler outputHandler=new OutputHandler();


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
	        	
	        	// Implement Student related feature
	        
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
	        }
	        return oo;
	}


}
