package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock;

public class JUnitAPITest {

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
		assertEquals(1, result.getIgnoreCount());
	}

}
