package org.openntf.junit.xsp.junit4;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class XSPTestRunner {

	public static XSPResult testSingleClass(Class<?> testClass) {
		Request request = Request.aClass(testClass);
		Result result = new Result();
		XSPResult xspResult = new XSPResult();
		RunNotifier runNotifier = buildRunNotifier(xspResult, result);
		Runner testRunner = request.getRunner();
		testRunner.run(runNotifier);
		return xspResult;
	}

	private static RunNotifier buildRunNotifier(XSPResult xspResult, Result result) {
		RunNotifier runNotifier = new RunNotifier();
		XSPRunListener xspRunListener = new XSPRunListener(xspResult);
		runNotifier.addListener(xspRunListener);
		runNotifier.addListener(result.createListener());
		return runNotifier;

	}

	public static List<XSPResult> testClasses(Class<?>... testClasses) {
		List<XSPResult> testResults = new ArrayList<XSPResult>(testClasses.length);
		for (Class<?> test : testClasses) {
			XSPResult result = testSingleClass(test);
			testResults.add(result);
		}
		return testResults;
	}

}
