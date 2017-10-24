package server.common;

public interface TermEventListener {
	void onCreate();
	void onRegistrationPossible();
	void onTermProperlyStarted();
	void onTwoWeekPassedTillTermStarted();
	void onTermEnded();
}
