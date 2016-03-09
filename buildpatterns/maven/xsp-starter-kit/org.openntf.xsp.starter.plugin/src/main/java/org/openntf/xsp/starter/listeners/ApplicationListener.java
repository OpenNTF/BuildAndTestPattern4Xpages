/*
 * © Copyright GBS Inc 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.openntf.xsp.starter.listeners;

import org.openntf.xsp.starter.Activator;

import com.ibm.xsp.application.ApplicationEx;

/**
 * @deprecated Use {@link ApplicationListener2} instead.
 */
@Deprecated
public class ApplicationListener extends AbstractListener implements com.ibm.xsp.application.events.ApplicationListener {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(ApplicationListener.class.getName() + " loaded");
	}

	public ApplicationListener() {
		_debugOut("created");
	}

	public void applicationCreated(ApplicationEx application) {
		_debugOut("applicationCreated triggered " + application.getApplicationId());
		// your code goes here
	}

	public void applicationDestroyed(ApplicationEx application) {
		_debugOut("applicationDestroyed triggered " + application.getApplicationId());
		// your code goes here
	}

}
