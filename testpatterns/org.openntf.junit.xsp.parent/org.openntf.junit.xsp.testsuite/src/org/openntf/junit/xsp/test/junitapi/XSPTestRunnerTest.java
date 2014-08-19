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
package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.junit4.report.XSPResult;
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
		assertEquals(1, xspResult.getIgnoreCount());
		assertNotNull(xspResult.getSystemOut());

		assertTrue("java.lang.NullPointerException".equals(xspResult.getErrorEntries().get(0).getErrorType()));
		assertTrue("This test will fail".equals(xspResult.getFailureEntrties().get(0).getFailureMessage()));
	}

	@Test
	public void testMultibleTestClasses() {
		List<XSPResult> testResults = XSPTestRunner.testClasses(TestMock.class, TestMock2.class);
		assertEquals(2, testResults.size());

	}

	@Test
	public void testWithXSPRunnerAndCheckIgnoreCount() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock.class);
		XSPResult xspResult2 = XSPTestRunner.testSingleClass(TestMock2.class);
		assertEquals(1, xspResult.getIgnoreCount());
		assertEquals(0, xspResult2.getIgnoreCount());
	}

	@Test
	public void testWithXSPRunnerAndMeasureTime() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock.class);
		assertTrue(xspResult.getTime() > Integer.MIN_VALUE);
	}

	@Test
	public void testWithXSPRunnerAndMeasureTimeMock2() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock2.class);
		assertTrue(xspResult.getTime() > 199);
	}

	@Test
	public void testSysoutOnMock2() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock2.class);
		assertTrue( xspResult.getSystemOut().contains("MOCK2SYSOUT"));
	}
	@Test
	public void testSysErrorOnMock2() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock2.class);
		assertTrue( xspResult.getSystemErr().contains("MOCK2SYSERR"));
	}

}
