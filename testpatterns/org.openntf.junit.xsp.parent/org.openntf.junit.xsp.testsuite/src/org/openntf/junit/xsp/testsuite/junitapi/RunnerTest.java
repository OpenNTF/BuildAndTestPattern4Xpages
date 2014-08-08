package org.openntf.junit.xsp.testsuite.junitapi;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openntf.junit.xsp.junit4.XSPResult;
import org.openntf.junit.xsp.junit4.XSPRunListener;
import org.openntf.junit.xsp.junit4.XSPTestRunner;

public class RunnerTest {

	@Test
	public void testWithRequestFromClassAndSimpleResultObject() {
		Request request = Request.aClass(TestMock.class);
		Result result = new Result();
		Runner testRunner = request.getRunner();
		RunNotifier runNotifier = new RunNotifier();
		runNotifier.addListener(result.createListener());
		testRunner.run(runNotifier);
		assertEquals(3, result.getRunCount());
		assertEquals(2, result.getFailureCount());
	}

	@Test
	public void testWithRequesFromClassAndRunListener() {
		Request request = Request.aClass(TestMock.class);
		Result result = new Result();
		XSPResult xspResult = new XSPResult();
		Runner testRunner = request.getRunner();
		RunNotifier runNotifier = new RunNotifier();
		XSPRunListener xspRunListener = new XSPRunListener(xspResult);
		runNotifier.addListener(xspRunListener);
		runNotifier.addListener(result.createListener());
		testRunner.run(runNotifier);

		assertEquals(3, xspResult.getRunCount());
		assertEquals(1, xspResult.getFailureCount());
		assertEquals(1, xspResult.getErrorCount());
		System.out.println(xspResult.getErrorEntries().get(0).getFailureMessage());
		System.out.println(xspResult.getErrorEntries().get(0).testDuration());
		assertTrue("java.lang.NullPointerException".equals(xspResult.getErrorEntries().get(0).getFailureMessage()));
		assertTrue("This test will fail".equals(xspResult.getFailureEntrties().get(0).getFailureMessage()));
	}

	@Test
	public void testWithXSPTestRunner() {
		XSPResult xspResult = XSPTestRunner.testSingleClass(TestMock.class);
		assertEquals(3, xspResult.getRunCount());
	}

}
