package org.openntf.junit.xsp.test.junitapi;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.openntf.junit.xsp.junit4.TestSuiteXMLProducer;
import org.openntf.junit.xsp.junit4.XSPResult;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.test.junitapi.helpers.SimpleErrorHandler;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestSuiteXMLProducerTest {

	@Test
	public void getTestSuiteXMLProducer() {
		assertNotNull(TestSuiteXMLProducer.INSTANCE);
	}

	@Test
	public void buildTestSuiteXMLOutputStream() throws JAXBException {
		OutputStream output = buildXMLOutputStream();
		assertNotNull(output);
		// System.out.println(((ByteArrayOutputStream) output).toString());
	}

	@Test
	public void validateTestSuiteXMLOutputStream() throws JAXBException, ParserConfigurationException, SAXException, IOException {
		OutputStream output = buildXMLOutputStream();
		assertNotNull(output);
		SimpleErrorHandler errorHandler = new SimpleErrorHandler();
		Document document = parseXMLOutputStream(output, errorHandler);
		assertNotNull("No xml document found!", document);
		assertFalse("Error in xml validation", errorHandler.isError());
	}

	@Test
	public void validateTestSuiteXMLOutputStreamContent() throws JAXBException, ParserConfigurationException, SAXException, IOException {
		OutputStream output = buildXMLOutputStream();
		assertNotNull(output);
		SimpleErrorHandler errorHandler = new SimpleErrorHandler();
		Document document = parseXMLOutputStream(output, errorHandler);
		assertNotNull("No xml document found!", document);
		assertFalse("Error in xml validation", errorHandler.isError());
		NodeList testSuite = document.getElementsByTagName("testsuite");
		assertEquals(1, testSuite.getLength());
		NodeList testCases = ((Element) testSuite.item(0)).getElementsByTagName("testcase");
		assertEquals(3, testCases.getLength());

	}

	private OutputStream buildXMLOutputStream() throws JAXBException {
		XSPResult result = XSPTestRunner.testSingleClass(TestMock.class);
		OutputStream output = TestSuiteXMLProducer.INSTANCE.buildXMLStream(result);
		return output;
	}

	private Document parseXMLOutputStream(OutputStream output, SimpleErrorHandler errorHandler) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(errorHandler);
		Document document = builder.parse(new InputSource(new ByteArrayInputStream(((ByteArrayOutputStream) output).toByteArray())));
		return document;
	}

}
