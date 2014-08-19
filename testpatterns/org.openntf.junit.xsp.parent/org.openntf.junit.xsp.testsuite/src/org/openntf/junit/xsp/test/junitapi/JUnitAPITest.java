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
