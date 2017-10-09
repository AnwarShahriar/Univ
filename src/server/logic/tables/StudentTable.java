package server.logic.tables;

import org.apache.log4j.Logger;
import utilities.Trace;

public class StudentTable {
	private Logger logger = Trace.getInstance().getLogger("opreation_file");
	// Create a student list here
	
    private static class StudentListHolder {
        private static final StudentTable INSTANCE = new StudentTable();
    }
    
    private StudentTable(){
    	//set up the default list with some instances
    	String[] passwordList=new String[]{"Zhibo","Yu","Michelle","Kevin","Sun"};
    	String[] usernameList=new String[]{"Zhibo@carleton.ca","Yu@carleton.ca","Michelle@carleton.ca","Kevin@carleton.ca","Sun@carleton.ca"};
    	for(int i=0;i<usernameList.length;i++){
			// Create new student and add it to a student list
		}
    	logger.info(String.format("Operation:Initialize StudentTable;StudentTable: %s", null));
    }
    
    public static final StudentTable getInstance() {
        return StudentListHolder.INSTANCE;
    }
	
    public Object createuser(String string, String string2) {		
		return true;	
	}
    
    public int checkUser(String string, String string2) {
		return 0;
	}
	
}
