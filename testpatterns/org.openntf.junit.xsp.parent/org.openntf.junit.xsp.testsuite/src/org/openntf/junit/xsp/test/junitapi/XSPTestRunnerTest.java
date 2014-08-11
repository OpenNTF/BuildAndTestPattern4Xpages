package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openntf.junit.xsp.junit4.XSPResult;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock2;

public class XSPTestRunnerTest {

	@Test
	public void testWithXSPTestRunner() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock.class);
		assertEquals(3, xspResult.getRunCount());
	}

	@Test
	public void testWithXSPTestRunnerAndAnalyseResult() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock.class);

		assertEquals(3, xspResult.getRunCount());
		assertEquals(1, xspResult.getFailureCount());
		assertEquals(1, xspResult.getErrorCount());
		assertTrue("java.lang.NullPointerException".equals(xspResult.getErrorEntries().get(0).getFailureMessage()));
		assertTrue("This test will fail".equals(xspResult.getFailureEntrties().get(0).getFailureMessage()));
	}

	@Test
	public void testMultibleTestClasses() {
		List<XSPResult> testResults = XSPTestRunner.testClasses(TestMock.class, TestMock2.class);
		assertEquals(2, testResults.size());
	}

}
