package org.openntf.junit.xsp.junit4;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "testsuites")
@XmlType(name = "testsuites", factoryMethod = "newJAXBInstance")
@XmlAccessorType(XmlAccessType.NONE)
public class XSPTestSuite {

	private int m_Tests;
	private int m_Failuers;
	private int m_Errors;

	private XSPTestSuite(int tests, int failuers, int errors) {
		m_Tests = tests;
		m_Failuers = failuers;
		m_Errors = errors;
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

	public static XSPTestSuite newJAXBInstance() {
		return new XSPTestSuite(0, 0, 0);

	}

	public static XSPTestSuite buildTestSuite(List<XSPResult> results) {
		int tests = 0;
		int errors = 0;
		int failures = 0;
		for (XSPResult result : results) {
			tests += result.getRunCount();
			errors += result.getErrorCount();
			failures += result.getFailureCount();
		}
		return new XSPTestSuite(tests, failures, errors);
	}

}
