package server.common;

public abstract class TermSimulator {
	protected TermEventListener listener;
	
	public TermSimulator(TermEventListener listener) {
		this.listener = listener;
	}
}
