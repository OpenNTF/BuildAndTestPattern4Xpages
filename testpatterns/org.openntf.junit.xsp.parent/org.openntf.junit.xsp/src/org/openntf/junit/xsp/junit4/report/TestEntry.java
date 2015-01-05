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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "testcase", factoryMethod = "newJAXBInstance")
@XmlAccessorType(XmlAccessType.NONE)
public class TestEntry {
	public enum TestStatus {
		START("/icons/vwicn009.gif"), SUCCESS("/icons/vwicn037.gif"), ERROR("/icons/vwicn091.gif"), FAILURE("/icons/vwicn038.gif");

		private final String m_IconURL;

		private TestStatus(String iconURL) {
			m_IconURL = iconURL;
		}

		public Object getICONUrl() {
			return m_IconURL;
		}
	}

	private final String m_ClassName;
	private final String m_MethodName;
	private final long m_StartTime;
	private long m_EndTime;
	private TestFailure m_Failure;
	private TestFailure m_Error;
	private TestStatus m_Status = TestStatus.START;

	public static TestEntry newJAXBInstance() {
		return new TestEntry("", "");

	}

	public static TestEntry buildTestEntry(String className, String methodName) {
		return new TestEntry(className, methodName);
	}

	private TestEntry(String className, String methodName) {
		super();
		m_ClassName = className;
		m_MethodName = methodName;
		m_StartTime = System.currentTimeMillis();
	}

	public void failure(TestFailure failure) {
		m_Failure = failure;
		m_Status = TestStatus.FAILURE;
	}

	public void error(TestFailure failure) {
		m_Error = failure;
		m_Status = TestStatus.ERROR;
	}

	public void finished() {
		if (m_Status == TestStatus.START) {
			m_Status = TestStatus.SUCCESS;
		}
		m_EndTime = System.currentTimeMillis();
	}

	@XmlAttribute(name = "time")
	public long getTestDuration() {
		return testDuration();
	}

	public long testDuration() {
		if (m_Status == TestStatus.START) {
			return 0;
		}
		return m_EndTime - m_StartTime;
	}

	@XmlAttribute(name = "classname")
	public String getClassName() {
		return m_ClassName;
	}

	@XmlAttribute(name = "name")
	public String getMethodName() {
		return m_MethodName;
	}

	public long getStartTime() {
		return m_StartTime;
	}

	public long getEndTime() {
		return m_EndTime;
	}

	@XmlElement(name = "failure")
	public TestFailure getTestFailure() {
		return m_Failure;
	}

	@XmlElement(name = "error")
	public TestFailure getErrorFailure() {
		return m_Error;
	}

	public String getFailureMessage() {
		return m_Failure != null ? m_Failure.getFailureMessage() : "";
	}

	public String getFailureTrace() {
		return m_Failure != null ? m_Failure.getFailureTrace() : "";
	}

	public String getFailureType() {
		return m_Failure != null ? m_Failure.getFailureType() : "";
	}

	public String getErrorMessage() {
		return m_Error != null ? m_Error.getFailureMessage() : "";
	}

	public String getErrorTrace() {
		return m_Error != null ? m_Error.getFailureTrace() : "";
	}

	public String getErrorType() {
		return m_Error != null ? m_Error.getFailureType() : "";
	}

	public TestStatus getStatus() {
		return m_Status;
	}

	public String getKey() {
		return m_ClassName + "#" + m_MethodName;
	}

}
