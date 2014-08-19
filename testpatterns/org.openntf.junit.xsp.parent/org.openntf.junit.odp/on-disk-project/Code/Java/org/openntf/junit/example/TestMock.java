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
package org.openntf.junit.example;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestMock {

	@Test
	public void testMethodWithSuccess() {
		assertTrue("This test is always i.O.", true);
	}

	@Test
	public void testMethodWithFailure() {
		assertFalse("This test will fail", true);
	}
	//The method should generate an exception!
	@SuppressWarnings("null")
	@Test
	public void testMethodWithException() {
		String testString = null;
		testString.replace("someThing", "otherThing");
	}
	@Test
	@Ignore("Ignored")
	public void testIgnored() {
		assertFalse("Should not run",true);
	}
}
