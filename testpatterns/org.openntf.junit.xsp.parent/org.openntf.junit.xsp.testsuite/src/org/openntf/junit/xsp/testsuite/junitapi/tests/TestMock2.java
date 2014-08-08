package org.openntf.junit.xsp.testsuite.junitapi.tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestMock2 {

	@Test
	public void checkTrue() {
		assertTrue(true);
	}

	@Test
	public void checkFalse() {
		assertFalse(true);
	}

	@Test
	public void checkEquals() {
		assertEquals(10, 10);
	}
}
