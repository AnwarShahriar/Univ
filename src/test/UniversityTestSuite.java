package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.University;
import server.University.TermState;
import server.common.TermEventListener;
import server.common.TermSimulator;

public class UniversityTestSuite {
	University versity;
	TestTermSimulator simulator;
	
	@Before
	public void setup() {
		versity = University.getInstance();
		simulator = new TestTermSimulator(versity);
	}
	
	@Test
	public void termIsCreated() {
		simulator.termCreated();
		assertEquals(TermState.CREATE_STUDENT_COURSE_STATE, versity.getTermState());
	}
	
	@Test
	public void termIsReadyForRegistration() {
		simulator.termAllowCourseRegistration();
		assertEquals(TermState.COURSE_REGISTRATION_STATE, versity.getTermState());
	}
	
	@Test
	public void termStartedProperly() {
		simulator.startTerm();
		assertEquals(TermState.TERM_PROPERLY_STARTED_STATE, versity.getTermState());
	}
	
	@Test
	public void termEnded() {
		simulator.endTerm();
		assertEquals(TermState.TERM_END_STATE, versity.getTermState());
	}
	
	public class TestTermSimulator extends TermSimulator {

		public TestTermSimulator(TermEventListener listener) {
			super(listener);
		}

		public void endTerm() {
			listener.onTermEnded();
		}

		public void startTerm() {
			listener.onTermProperlyStarted();
		}

		public void termAllowCourseRegistration() {
			listener.onRegistrationPossible();
		}

		public void termCreated() {
			listener.onCreate();
		}
		
	}

}
