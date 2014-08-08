package org.openntf.junit.xsp.junit4;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

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
