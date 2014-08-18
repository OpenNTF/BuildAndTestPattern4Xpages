package org.openntf.junit.xsp.junit4;

import java.io.ByteArrayOutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.openntf.junit.xsp.junit4.report.XSPResult;
import org.openntf.junit.xsp.junit4.report.XSPTestSuite;

public enum TestSuiteXMLProducer {
	INSTANCE;

	public ByteArrayOutputStream buildXMLStream(final XSPResult result) throws JAXBException {
		return AccessController.doPrivileged(new PrivilegedAction<ByteArrayOutputStream>() {

			@Override
			public ByteArrayOutputStream run() {
				try {
					JAXBContext context = JAXBContext.newInstance(XSPResult.class);
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					marshaller.marshal(result, out);
					return out;
				} catch (Exception ex) {
					throw new RuntimeException("Error in build XMLStream.", ex);
				}
			}
		});

	}

	public ByteArrayOutputStream buildXMLStream(final XSPTestSuite testSuite) throws JAXBException {

		return AccessController.doPrivileged(new PrivilegedAction<ByteArrayOutputStream>() {

			@Override
			public ByteArrayOutputStream run() {
				try {
					JAXBContext context = JAXBContext.newInstance(XSPTestSuite.class);
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					marshaller.marshal(testSuite, out);
					return out;
				} catch (Exception ex) {
					throw new RuntimeException("Error in build XMLStream.", ex);
				}
			}
		});

	}

}
