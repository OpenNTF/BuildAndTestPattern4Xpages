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
