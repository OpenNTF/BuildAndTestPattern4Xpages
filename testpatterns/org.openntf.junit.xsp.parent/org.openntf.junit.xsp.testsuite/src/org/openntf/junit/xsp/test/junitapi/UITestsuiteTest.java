package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.faces.FacesException;

import org.junit.Test;
import org.openntf.junit.xsp.component.UITestsuite;

public class UITestsuiteTest {

	@Test
	public void testSetListOfClassNames() {
		UITestsuite testsuite = new UITestsuite();
		testsuite.setTestClasses(Arrays.asList("org.openntf.junit.xsp.junitapi.tests.TestMock", "org.openntf.junit.xsp.junitapi.tests.TestMock2"));
		assertNotNull(testsuite.getTestClasses());
	}

	@Test
	public void testSetDownloadXMLName() {
		UITestsuite testsuite = new UITestsuite();
		testsuite.setDownloadName("TEST-All.xml");
		assertNotNull(testsuite.getDownloadName());
	}

	@Test
	public void testGetClassForName() throws ClassNotFoundException {
		UITestsuite testsuite = new UITestsuite();
		testsuite.setTestClasses(Arrays.asList("org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock", "org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock2"));
		Class<?>[] classes = testsuite.getAllTestClasses();
		assertEquals(2, classes.length);
	}

	@Test
	public void testGetClassForNameWithException() throws ClassNotFoundException {
		UITestsuite testsuite = new UITestsuite();
		testsuite.setTestClasses(Arrays.asList("org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock.NotFound", "org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock2"));
		try {
			testsuite.getAllTestClasses();
		} catch (Exception ex) {
			assertTrue(ex instanceof FacesException);
			return;
		}
		assertFalse("No excetion!", true);
	}

}
