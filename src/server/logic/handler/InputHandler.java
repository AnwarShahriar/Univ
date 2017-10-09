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
    public static final int CLERKLOGIN=14;
    public static final int STUDENTLOGIN=15;
    
    OutputHandler outputHandler=new OutputHandler();


	public ServerOutput processInput(String input, int state) {
		 String output = "";
		 Output o = new Output("",0);
		 ServerOutput oo = new ServerOutput(output,o.getState());
	        if (state == WAITING) {
	        	output = "Who Are you?Clerk or User?";
	            state = FINISHWAITING;
	            oo.setOutput(output);
	            oo.setState(state);
	         }else if (state == FINISHWAITING) {
	            if (input.equalsIgnoreCase("clerk")) {
	            	output="Please Input The Password:";
	            	state=CLERKLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if (input.equalsIgnoreCase("user")) {
	            	output="Please Input Username and Password:'username,password'";
	            	state=STUDENTLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else{
	            	output = "Who Are you?Clerk or User?";
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
	            	output = "Please Input Student Info:";
	            	state=CREATESTUDENT;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if (input.equalsIgnoreCase("create title")) {
	            	output = "Please Input Title Info:'ISBN,title'";
	            	state=CREATECOURSE;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAITING;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("main menu")){
	        		output = "Please select from the menu.Menu:Create Student/Course";
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	            	output = "Please select from the menu.Menu:Create Student/Course";
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
	        		output = "What can I do for you?Menu:Create Student/Course";
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
	        		output = "What can I do for you?Menu:Create Student/Course";
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
	        }
	        return oo;
	}


}
