package org.openntf.junit.xsp.junit4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.junit.runner.notification.Failure;

import com.ibm.commons.util.StringUtil;

@XmlType(name = "failure", factoryMethod = "newJAXBInstance")
public class TestFailure {

	private final String m_FailureMessage;
	private final String m_FailureTrace;
	private final String m_FailureType;

	public static TestFailure newJAXBInstance() {
		return new TestFailure("", "", "");
	}

	public static TestFailure buildFailure(Failure failure) {

		String type = failure.getException().getClass().getCanonicalName();
		String message = failure.getMessage();
		String trace = failure.getTrace();
		return new TestFailure(message, trace, type);
	}

	public static TestFailure buildError(Failure failure) {
		String type = failure.getException().getClass().getCanonicalName();
		String message = getExceptionMessage(failure.getException());
		String trace = failure.getTrace();
		return new TestFailure(message, trace, type);

	}

	private static String getExceptionMessage(Throwable exception) {
		if (StringUtil.isEmpty(exception.getMessage())) {
			return exception.toString();
		}
		return exception.getMessage();
	}

	public TestFailure(String failureMessage, String failurTrace, String type) {
		super();
		m_FailureMessage = failureMessage;
		m_FailureTrace = failurTrace;
		m_FailureType = type;
	}

	@XmlValue
	public String getFailureTrace() {
		return m_FailureTrace;
	}

	@XmlAttribute(name = "message")
	public String getFailureMessage() {
		return m_FailureMessage;
	}

	@XmlAttribute(name = "type")
	public String getFailureType() {
		return m_FailureType;
	}

}
