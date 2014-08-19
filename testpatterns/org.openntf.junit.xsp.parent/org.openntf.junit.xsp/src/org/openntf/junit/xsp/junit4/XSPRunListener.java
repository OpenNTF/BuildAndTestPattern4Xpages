/**
 * Copyright WebGate Consulting AG, 2014
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
package org.openntf.junit.xsp.junit4;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.openntf.junit.xsp.junit4.report.XSPResult;

public class XSPRunListener extends org.junit.runner.notification.RunListener {

	private final XSPResult m_XSPResult;
	public XSPRunListener(XSPResult xspResult) {
		m_XSPResult = xspResult;
	}

	@Override
	public void testStarted(Description description) throws Exception {
		m_XSPResult.startTest(description);
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		if (failure.getException() instanceof AssertionError) {
			m_XSPResult.reportFailure(failure);
		} else {
			m_XSPResult.reportError(failure);
		}

	}

	@Override
	public void testFinished(Description description) throws Exception {
		m_XSPResult.endTest(description);
	}
}
