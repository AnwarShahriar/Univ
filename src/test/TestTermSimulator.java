package test;

import server.common.TermEventListener;
import server.common.TermSimulator;

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
	
	public void twoWeeksPassesTillTermStarted() {
		listener.onTwoWeekPassedTillTermStarted();
	}
	
}
