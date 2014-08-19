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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "testsuites")
@XmlType(name = "testsuites", factoryMethod = "newJAXBInstance")
@XmlAccessorType(XmlAccessType.NONE)
public class XSPTestSuite {

	private int m_Tests;
	private int m_Failuers;
	private int m_Errors;
	private List<XSPResult> m_Result;

	private XSPTestSuite(List<XSPResult> results) {
		int tests = 0;
		int errors = 0;
		int failures = 0;
		for (XSPResult result : results) {
			tests += result.getRunCount();
			errors += result.getErrorCount();
			failures += result.getFailureCount();
		}
		m_Tests = tests;
		m_Failuers = failures;
		m_Errors = errors;
		m_Result = results;
	}

	@XmlAttribute(name = "tests")
	public int getTests() {
		return m_Tests;
	}

	@XmlAttribute(name = "failures")
	public int getFailures() {
		return m_Failuers;
	}

	@XmlAttribute(name = "errors")
	public int getErrros() {
		return m_Errors;
	}
	
	@XmlElement(name="testsuite")
	public List<XSPResult> getResults() {
		return m_Result;
	}

	public static XSPTestSuite newJAXBInstance() {
		return new XSPTestSuite( null);

	}

	public static XSPTestSuite buildTestSuite(List<XSPResult> results) {
		return new XSPTestSuite(results);
	}

}
