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
package org.openntf.junit.xsp.junit4;

import java.security.AccessController;
import java.security.PrivilegedAction;
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

	public static XSPTestSuite testClassesAsSuite(final Class<?>... testClasses) {
		List<XSPResult> results = AccessController.doPrivileged(new PrivilegedAction<List<XSPResult>>() {
			@Override
			public List<XSPResult> run() {
				return testClasses(testClasses);
			}
		});
		return XSPTestSuite.buildTestSuite(results);
	}

}
