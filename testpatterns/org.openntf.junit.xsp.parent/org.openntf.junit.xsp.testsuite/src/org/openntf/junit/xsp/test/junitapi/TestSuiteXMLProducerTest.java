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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.openntf.junit.xsp.junit4.TestSuiteXMLProducer;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.junit4.report.XSPResult;
import org.openntf.junit.xsp.junit4.report.XSPTestSuite;
import org.openntf.junit.xsp.test.junitapi.helpers.SimpleErrorHandler;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock;
import org.openntf.junit.xsp.testsuite.junitapi.tests.TestMock2;
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

	@Test
	public void validateTestSuiteXMLOutputStreamAgainstXSD() throws JAXBException, SAXException {
		OutputStream out = buildXMLOutputStream();
		InputStream isXSD = SimpleErrorHandler.class.getResourceAsStream("junit-4.xsd");

		assertNotNull(out);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(isXSD));
		Validator validator = schema.newValidator();
		boolean nofailure = false;
		try {
			validator.validate(new StreamSource((new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray()))));
			nofailure = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		assertTrue(nofailure);
		
	}
	
	@Test
	public void getXSDForValidation() throws IOException {
		InputStream is = SimpleErrorHandler.class.getResourceAsStream("junit-4.xsd");
		assertNotNull(is);
		is.close();
	}

	@Test
	public void getXSPTestSuite() {
		XSPTestSuite testSuite = XSPTestRunner.testClassesAsSuite(TestMock.class, TestMock2.class);
		assertNotNull(testSuite);
	}

	@Test
	public void getXSPTestSuiteAndCheckResult() {
		XSPTestSuite testSuite = XSPTestRunner.testClassesAsSuite(TestMock.class, TestMock2.class);
		assertNotNull(testSuite);
		assertEquals(6, testSuite.getTests());
		assertEquals(2, testSuite.getFailures());
		assertEquals(1, testSuite.getErrros());
	}

	@Test
	public void buildTestSuitesXMLOutputStream() throws JAXBException {
		XSPTestSuite testSuite = XSPTestRunner.testClassesAsSuite(TestMock.class, TestMock2.class);
		OutputStream out = TestSuiteXMLProducer.INSTANCE.buildXMLStream(testSuite);
		assertNotNull(out);
		System.out.println(out);
	}

	@Test
	public void validateTestSouresXMLOutputStreamAgainstXSD() throws JAXBException, SAXException {
		InputStream isXSD = SimpleErrorHandler.class.getResourceAsStream("junit-4.xsd");
		XSPTestSuite testSuite = XSPTestRunner.testClassesAsSuite(TestMock.class, TestMock2.class);
		OutputStream out = TestSuiteXMLProducer.INSTANCE.buildXMLStream(testSuite);

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(isXSD));
		Validator validator = schema.newValidator();
		boolean nofailure = false;
		try {
			validator.validate(new StreamSource((new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray()))));
			nofailure = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		assertTrue(nofailure);
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
