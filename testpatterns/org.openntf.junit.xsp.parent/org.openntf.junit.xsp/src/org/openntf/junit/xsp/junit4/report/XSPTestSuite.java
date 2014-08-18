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
