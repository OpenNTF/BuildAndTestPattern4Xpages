package org.openntf.junit.xsp.junit4.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openntf.junit.xsp.junit4.report.TestEntry.TestStatus;

@XmlRootElement(name = "testsuite")
@XmlType(name = "testsuite")
@XmlAccessorType(XmlAccessType.NONE)
public class XSPResult {

	private Map<String, TestEntry> m_TestEntries = new ConcurrentSkipListMap<String, TestEntry>();
	private String m_ClassName;
	private int m_Ignored = 0;
	private long m_EndTime = Integer.MIN_VALUE;
	private long m_StartTime = System.currentTimeMillis();
	private String m_SystemOut;
	private String m_SystemErr;

	@XmlAttribute(name = "tests")
	public int getRunCount() {
		return m_TestEntries.size();
	}

	@XmlAttribute(name = "failures")
	public int getFailureCount() {
		return countByStatus(TestStatus.FAILURE);
	}

	@XmlAttribute(name = "errors")
	public int getErrorCount() {
		return countByStatus(TestStatus.ERROR);
	}

	@XmlAttribute(name = "name")
	public String getTestClassName() {
		return m_ClassName;
	}

	private int countByStatus(TestStatus status) {
		int count = 0;
		for (TestEntry entry : m_TestEntries.values()) {
			if (entry.getStatus() == status) {
				count++;
			}
		}
		return count;
	}

	@XmlElement(name = "testcase")
	public List<TestEntry> getTestEntries() {
		return new ArrayList<TestEntry>(m_TestEntries.values());
	}

	public void startTest(Description description) {
		TestEntry entry = TestEntry.buildTestEntry(description.getClassName(), description.getMethodName());
		m_TestEntries.put(entry.getKey(), entry);
		if (m_ClassName == null) {
			m_ClassName = description.getClassName();
		}
	}

	public void reportFailure(Failure failure) {
		TestEntry testEntry = getEntryByDescription(failure.getDescription());
		testEntry.failure(TestFailure.buildFailure(failure));

	}

	public void reportError(Failure failure) {
		TestEntry testEntry = getEntryByDescription(failure.getDescription());
		testEntry.error(TestFailure.buildError(failure));

	}

	public void endTest(Description description) {
		TestEntry testEntry = getEntryByDescription(description);
		testEntry.finished();
	}

	private TestEntry getEntryByDescription(Description description) {
		return m_TestEntries.get(description.getClassName() + "#" + description.getMethodName());
	}

	public List<TestEntry> getErrorEntries() {
		List<TestEntry> errorEntries = getEntriesByTestStatus(TestStatus.ERROR);
		return errorEntries;
	}

	private List<TestEntry> getEntriesByTestStatus(TestStatus status) {
		List<TestEntry> errorEntries = new ArrayList<TestEntry>();
		for (TestEntry entry : m_TestEntries.values()) {
			if (entry.getStatus() == status) {
				errorEntries.add(entry);
			}
		}
		return errorEntries;
	}

	public List<TestEntry> getFailureEntrties() {
		List<TestEntry> failureEntries = getEntriesByTestStatus(TestStatus.FAILURE);
		return failureEntries;
	}

	public int getIgnoreCount() {
		return m_Ignored;
	}

	public void applyResult(Result result, ConsoleLogRecorder recorder) {
		m_Ignored = result.getIgnoreCount();
		m_EndTime = System.currentTimeMillis();
		m_SystemOut = recorder.getSystemOut();
		m_SystemErr = recorder.getErrorOut();
	}

	@XmlAttribute(name = "time")
	public long getTime() {
		return m_EndTime - m_StartTime;
	}

	public String getSystemOut() {
		return m_SystemOut;
	}

	public String getSystemErr() {
		return m_SystemErr;
	}

}
