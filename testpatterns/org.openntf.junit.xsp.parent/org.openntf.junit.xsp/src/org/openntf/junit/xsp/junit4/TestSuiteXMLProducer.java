package org.openntf.junit.xsp.junit4;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public enum TestSuiteXMLProducer {
	INSTANCE;

	public OutputStream buildXMLStream(XSPResult result) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(XSPResult.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		OutputStream out = new ByteArrayOutputStream();
		marshaller.marshal(result, out);
		return out;

	}

	public OutputStream buildXMLStream(XSPTestSuite testSuite) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(XSPTestSuite.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		OutputStream out = new ByteArrayOutputStream();
		marshaller.marshal(testSuite, out);
		return out;
	}

}
