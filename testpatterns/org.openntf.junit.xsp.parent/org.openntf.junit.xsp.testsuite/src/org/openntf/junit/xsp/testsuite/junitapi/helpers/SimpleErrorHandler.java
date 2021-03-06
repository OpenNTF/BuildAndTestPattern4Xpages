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
package org.openntf.junit.xsp.testsuite.junitapi.helpers;

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
