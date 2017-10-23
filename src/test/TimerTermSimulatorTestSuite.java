package test;

import static org.junit.Assert.*;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import server.TimerTermSimulator;
import server.common.TermEventListener;

public class TimerTermSimulatorTestSuite {

	@Test
	public void timerEventOccursInProperOrder() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		
		TimerTermSimulator simulator = new TimerTermSimulator(new TermEventListener() {
			int order = 0;
			
			@Override
			public void onCreate() {
				if (order != 0) {
					fail();
				}
				order += 1;
			}
			
			@Override
			public void onRegistrationPossible() {
				if (order != 1) {
					fail();
				}
				order += 2;
			}
			
			@Override
			public void onTermProperlyStarted() {
				if (order != 3) {
					fail();
				}
				order += 3;
			}
			
			@Override
			public void onTermEnded() {
				if (order != 6) { 
					fail();
				}
				latch.countDown();
			}
			
		}, 10); // time is in milliseconds
		
		simulator.start();
		
		latch.await();
	}

}
