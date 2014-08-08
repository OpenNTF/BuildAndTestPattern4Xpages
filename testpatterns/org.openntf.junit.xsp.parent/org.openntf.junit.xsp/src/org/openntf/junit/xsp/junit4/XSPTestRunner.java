package org.openntf.junit.xsp.junit4;

import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class XSPTestRunner {

	public static XSPResult testSingleClass(Class<?> testClass) {
		Request request = Request.aClass(testClass);
		Result result = new Result();
		XSPResult xspResult = new XSPResult();
		Runner testRunner = request.getRunner();
		RunNotifier runNotifier = new RunNotifier();
		XSPRunListener xspRunListener = new XSPRunListener(xspResult);
		runNotifier.addListener(xspRunListener);
		runNotifier.addListener(result.createListener());
		testRunner.run(runNotifier);
		return xspResult;
	}

}
