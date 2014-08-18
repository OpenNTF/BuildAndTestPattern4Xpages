package org.openntf.junit.xsp.junit4;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openntf.junit.xsp.junit4.report.ConsoleLogRecorder;
import org.openntf.junit.xsp.junit4.report.XSPResult;
import org.openntf.junit.xsp.junit4.report.XSPTestSuite;

public class XSPTestRunner {

	public static XSPResult testSingleClass(Class<?> testClass) {
		ConsoleLogRecorder recorder = new ConsoleLogRecorder();

		recorder.startRecording();
		XSPResult xspResult = new XSPResult();
		Request request = Request.aClass(testClass);
		Result result = new Result();
		RunNotifier runNotifier = buildRunNotifier(xspResult, result);
		Runner testRunner = request.getRunner();
		testRunner.run(runNotifier);
		recorder.stopRecordingAndResetSystem();

		xspResult.applyResult(result, recorder);
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

	public static XSPTestSuite testClassesAsSuite(Class<?>... testClasses) {
		List<XSPResult> results = testClasses(testClasses);
		return XSPTestSuite.buildTestSuite(results);
	}

}
