package server.logic.handler;

import java.util.Date;

import server.logic.handler.model.Output;
import server.logic.tables.StudentTable;
import server.logic.tables.CourseTable;
import utilities.Config;

public class OutputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING=1;
    public static final int CLERK = 2;
    public static final int STUDENT = 3;
    public static final int CREATESTUDENT=4;
    public static final int CREATECOURSE=5;
    public static final int CLERKLOGIN=14;
    public static final int STUDENTLOGIN=15;

	public Output createStudent(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean email=strArray[0].contains("@");
        Object result="";
        if(strArray.length!=2 || email!=true){
        	output.setOutput("Your input should in this format:'username,password'");
        	output.setState(CREATESTUDENT);
        }else{
        	result=StudentTable.getInstance().createuser(strArray[0], strArray[1]);
        	if(result.equals(true)){
        		output.setOutput("Success!");
        	}else{
        		output.setOutput("The User Already Exists!");
        	}
        	output.setState(CLERK);
        }
		return output;
	}

	public Output createCourse(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean number=isInteger(strArray[0]);
        Object result="";
        if(strArray.length!=2 || number!=true){
        	output.setOutput("Your input should in this format: specify format here -> ");
        	output.setState(CREATECOURSE);
        }else{
        	result=CourseTable.getInstance().createtitle(strArray[0], strArray[1]);
        	if(result.equals(true)){
        		output.setOutput("Success!");
        	}else{
        		output.setOutput("The Course Already Exists!");
        	}
        	output.setState(CLERK);
        }
		return output;
	}

	public Output clerkLogin(String input) {
		Output output=new Output("",0);
		if(input.equalsIgnoreCase(Config.CLERK_PASSWORD)){
			output.setOutput("What can I do for you?Menu:Create Student/Course");
        	output.setState(CLERK);
		}else{
			output.setOutput("Wrong Password!Please Input The Password:");
        	output.setState(CLERKLOGIN);
		}
		return output;
	}

	public Output studentLogin(String input) {
		Output output=new Output("",0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean email=strArray[0].contains("@");
        int result=0;
        if(strArray.length!=2 || email!=true){
        	output.setOutput("Your input should in this format:'username,password'");
        	output.setState(STUDENTLOGIN);
        }else{
        	result=StudentTable.getInstance().checkUser(strArray[0], strArray[1]);
        	if(result==0){
        		output.setOutput("What can I do for you? Specify what can I do ----->");
            	output.setState(STUDENT);
        	}else if(result==1){
        		output.setOutput("Wrong Password!Please Input Username and Password:'username,password'");
            	output.setState(STUDENTLOGIN);
        	}else{
        		output.setOutput("Student Does Not Exist!Please The Username and Password:'username,password'");
            	output.setState(STUDENTLOGIN);
        	}
        }
		return output;
	}
	
	public static boolean isInteger(String value) {
		char[] ch = value.toCharArray();
		boolean isNumber=true;
		if(value.length()==13){
			for (int i = 0; i < ch.length; i++) {
				isNumber = Character.isDigit(ch[i]);
			}
		}else{
			isNumber=false;
		}
		return isNumber;
		 }
	
	public boolean isNumber(String value) {
		char[] ch = value.toCharArray();
		boolean isNumber=true;
			for (int i = 0; i < ch.length; i++) {
				isNumber = Character.isDigit(ch[i]);
			}
		return isNumber;
	}
}
