package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.*;

import java.io.OutputStream;

import org.junit.Test;
import org.openntf.junit.xsp.junit4.TestSuiteXMLProducer;
import org.openntf.junit.xsp.junit4.XSPResult;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock;

public class TestSuiteXMLProducerTest {

	@Test
	public void getTestSuiteXMLProducer() {
		assertNotNull(TestSuiteXMLProducer.INSTANCE);
	}

	@Test
	public void buildTestSuiteXMLOutputStream() {
		XSPResult result = XSPTestRunner.testSingleClass(TestMock.class);
		OutputStream output = TestSuiteXMLProducer.INSTANCE.buildXMLStream(result);
		assertNotNull(output);
	}
}
