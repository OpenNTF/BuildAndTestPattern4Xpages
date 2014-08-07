package org.openntf.junit.xsp.testsuite.junitapi;

import static org.junit.Assert.*;

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
}
