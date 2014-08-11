package org.openntf.junit.xsp.junit4;

public class TestFailure {

	private final String m_FailureMessage;
	private final String m_FailurTrace;

	public TestFailure(String failureMessage, String failurTrace) {
		super();
		m_FailureMessage = failureMessage;
		m_FailurTrace = failurTrace;
	}

	public String getFailurTrace() {
		return m_FailurTrace;
	}

	public String getFailureMessage() {
		return m_FailureMessage;
	}

}
