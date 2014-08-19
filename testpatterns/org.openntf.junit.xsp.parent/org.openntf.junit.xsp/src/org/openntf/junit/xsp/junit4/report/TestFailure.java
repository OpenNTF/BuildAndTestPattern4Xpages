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
package org.openntf.junit.xsp.junit4.report;

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
