package server.logic.tables;

import org.apache.log4j.Logger;
import utilities.Trace;

public class CourseTable {
	private Logger logger = Trace.getInstance().getLogger("opreation_file");
	// Create a course list here
	
    private static class CourseListHolder {
        private static final CourseTable INSTANCE = new CourseTable();
    }
    
    private CourseTable(){
    	// INSERT pre recorded courses here
    	logger.info(String.format("Operation:Initialize CourseTable;TitleTable: %s", null));
    };
    
    public static final CourseTable getInstance() {
        return CourseListHolder.INSTANCE;
    }
	
    public Object createtitle(String string, String string2) {		
		return true;
	}
	
}
