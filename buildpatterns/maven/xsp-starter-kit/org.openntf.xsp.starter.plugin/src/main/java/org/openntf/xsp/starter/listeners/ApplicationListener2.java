package org.openntf.xsp.starter.listeners;

import org.openntf.xsp.starter.Activator;

import com.ibm.xsp.application.ApplicationEx;

public class ApplicationListener2 extends AbstractListener implements com.ibm.xsp.application.events.ApplicationListener2 {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(ApplicationListener2.class.getName() + " loaded");
	}
	
	public ApplicationListener2() {
		_debugOut("created");
	}
	
	@Override
	public void applicationCreated(ApplicationEx application) {
		_debugOut("applicationCreated triggered " + application.getApplicationId());
		// your code goes here
	}

	@Override
	public void applicationDestroyed(ApplicationEx application) {
		_debugOut("applicationDestroyed triggered " + application.getApplicationId());
		// your code goes here
	}

	@Override
	public void applicationRefreshed(ApplicationEx application) {
		_debugOut("applicationRefreshed triggered " + application.getApplicationId());
		// your code goes here
	}

}
