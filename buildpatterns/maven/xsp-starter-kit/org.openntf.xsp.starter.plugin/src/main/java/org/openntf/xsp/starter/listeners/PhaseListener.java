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

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.openntf.xsp.starter.Activator;

public class PhaseListener extends AbstractListener implements javax.faces.event.PhaseListener {
	public static final long serialVersionUID = -6528380677556637393L;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(PhaseListener.class.getName() + " loaded");
	}

	public PhaseListener() {
		_debugOut("created");
	}

	private void doBeforeEveryPhase(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterEveryPhase(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeRestoreView(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterRestoreView(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeApplyRequest(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterApplyRequest(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeProcessValidations(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterProcessValidations(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeUpdateModel(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterUpdateModel(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeInvokeApplication(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterInvokeApplication(PhaseEvent event) {
		// Insert your code here
	}

	private void doBeforeRenderResponse(PhaseEvent event) {
		// Insert your code here
	}

	private void doAfterRenderResponse(PhaseEvent event) {
		// Insert your code here
	}

	public void afterPhase(PhaseEvent event) {
		PhaseId curId = event.getPhaseId();
		if (PhaseId.APPLY_REQUEST_VALUES.equals(curId)) {
			doAfterApplyRequest(event);
		} else if (PhaseId.INVOKE_APPLICATION.equals(curId)) {
			doAfterInvokeApplication(event);
		} else if (PhaseId.PROCESS_VALIDATIONS.equals(curId)) {
			doAfterProcessValidations(event);
		} else if (PhaseId.RENDER_RESPONSE.equals(curId)) {
			doAfterRenderResponse(event);
		} else if (PhaseId.RESTORE_VIEW.equals(curId)) {
			doAfterRestoreView(event);
		} else if (PhaseId.UPDATE_MODEL_VALUES.equals(curId)) {
			doAfterUpdateModel(event);
		}
		doAfterEveryPhase(event);
	}

	public void beforePhase(PhaseEvent event) {
		PhaseId curId = event.getPhaseId();
		if (PhaseId.APPLY_REQUEST_VALUES.equals(curId)) {
			doBeforeApplyRequest(event);
		} else if (PhaseId.INVOKE_APPLICATION.equals(curId)) {
			doBeforeInvokeApplication(event);
		} else if (PhaseId.PROCESS_VALIDATIONS.equals(curId)) {
			doBeforeProcessValidations(event);
		} else if (PhaseId.RENDER_RESPONSE.equals(curId)) {
			doBeforeRenderResponse(event);
		} else if (PhaseId.RESTORE_VIEW.equals(curId)) {
			doBeforeRestoreView(event);
		} else if (PhaseId.UPDATE_MODEL_VALUES.equals(curId)) {
			doBeforeUpdateModel(event);
		}
		doBeforeEveryPhase(event);
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
