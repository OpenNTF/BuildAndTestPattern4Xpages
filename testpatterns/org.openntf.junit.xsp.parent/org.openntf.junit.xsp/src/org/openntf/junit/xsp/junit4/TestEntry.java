package org.openntf.junit.xsp.junit4;

public class TestEntry {
	public enum TestStatus {
		START, SUCCESS, ERROR, FAILURE
	};

	private final String m_ClassName;
	private final String m_MethodName;
	private final long m_StartTime;
	private long m_EndTime;
	private String m_FailureMessage;
	private String m_FailurTrace;
	private TestStatus m_Status = TestStatus.START;

	public static TestEntry buildTestEntry(String className, String methodName) {
		return new TestEntry(className, methodName);
	}

	private TestEntry(String className, String methodName) {
		super();
		m_ClassName = className;
		m_MethodName = methodName;
		m_StartTime = System.currentTimeMillis();
	}

	public void failure(String reason, String trace) {
		m_FailureMessage = reason;
		m_FailurTrace = trace;
		m_Status = TestStatus.FAILURE;
	}

	public void error(String reason, String trace) {
		m_FailureMessage = reason;
		m_FailurTrace = trace;
		m_Status = TestStatus.ERROR;
	}

	public void finished() {
		if (m_Status == TestStatus.START) {
			m_Status = TestStatus.SUCCESS;
		}
		m_EndTime = System.currentTimeMillis();
	}

	public long testDuration() {
		if (m_Status == TestStatus.START) {
			return 0;
		}
		return m_EndTime - m_StartTime;
	}

	public String getClassName() {
		return m_ClassName;
	}

	public String getMethodName() {
		return m_MethodName;
	}

	public long getStartTime() {
		return m_StartTime;
	}

	public long getEndTime() {
		return m_EndTime;
	}

	public String getFailureMessage() {
		return m_FailureMessage;
	}

	public String getFailurTrace() {
		return m_FailurTrace;
	}

	public TestStatus getStatus() {
		return m_Status;
	}

	public String getKey() {
		return m_ClassName + "#" + m_MethodName;
	}

}
