package org.openntf.junit.xsp.junit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.openntf.junit.xsp.junit4.TestEntry.TestStatus;

import com.ibm.commons.util.StringUtil;

public class XSPResult {

	private Map<String, TestEntry> m_TestEntries = new ConcurrentSkipListMap<String, TestEntry>();

	public int getRunCount() {
		return m_TestEntries.size();
	}

	public int getFailureCount() {
		return countByStatus(TestStatus.FAILURE);
	}

	public int getErrorCount() {
		return countByStatus(TestStatus.ERROR);
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

	public void startTest(Description description) {
		TestEntry entry = TestEntry.buildTestEntry(description.getClassName(), description.getMethodName());
		m_TestEntries.put(entry.getKey(), entry);

	}

	public void reportFailure(Failure failure) {
		TestEntry testEntry = getEntryByDescription(failure.getDescription());
		testEntry.failure(failure.getMessage(), failure.getTrace());

	}

	public void reportError(Failure failure) {
		TestEntry testEntry = getEntryByDescription(failure.getDescription());
		String exceptionMessage = getExceptionMessage(failure.getException());
		testEntry.error(exceptionMessage, failure.getTrace());

	}

	private String getExceptionMessage(Throwable exception) {
		if (StringUtil.isEmpty(exception.getMessage())) {
			return exception.toString();
		}
		return exception.getMessage();
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

}
