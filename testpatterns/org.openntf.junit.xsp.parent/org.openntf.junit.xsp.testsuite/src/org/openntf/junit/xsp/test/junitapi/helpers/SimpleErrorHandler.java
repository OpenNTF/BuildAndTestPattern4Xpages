package org.openntf.junit.xsp.test.junitapi.helpers;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {

	private boolean m_Error = false;
	@Override
	public void error(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
		m_Error = true;

	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
		m_Error = true;
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
		m_Error = true;
	}

	public boolean isError() {
		return m_Error;
	}

}
