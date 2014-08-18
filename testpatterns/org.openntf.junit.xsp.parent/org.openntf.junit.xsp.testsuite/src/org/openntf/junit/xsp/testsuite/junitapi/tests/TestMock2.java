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
		System.err.println("MOCK2SYSERR");
		System.out.println("MOCK2SYSOUT");
		assertFalse(true);
	}

	@Test
	public void checkEquals() throws InterruptedException {
		Thread.sleep(201);
		assertEquals(10, 10);
	}
}
