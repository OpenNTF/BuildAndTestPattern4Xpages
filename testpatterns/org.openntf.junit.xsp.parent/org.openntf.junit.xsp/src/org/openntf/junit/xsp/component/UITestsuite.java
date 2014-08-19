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
package org.openntf.junit.xsp.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.openntf.junit.xsp.junit4.TestSuiteXMLProducer;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.junit4.report.XSPTestSuite;

import com.ibm.xsp.component.FacesAjaxComponent;
import com.ibm.xsp.util.StateHolderUtil;
import com.ibm.xsp.webapp.XspHttpServletResponse;

public class UITestsuite extends UIComponentBase implements FacesAjaxComponent {

	public static final String COMPONENT_TYPE = "org.openntf.junit.xsp.component.testsuite"; //$NON-NLS-1$
	public static final String COMPONENT_FAMILY = "org.openntf.junit.xsp.component.testsuite"; //$NON-NLS-1$
	public static final String RENDERER_TYPE = "org.openntf.junit.xsp.component.testsuite"; //$NON-NLS-1$

	private Collection<String> m_TestClasses;
	private String m_DownloadName;

	public UITestsuite() {
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public void setTestClasses(Collection<String> testClasses) {
		m_TestClasses = testClasses;

	}

	public Collection<String> getTestClasses() {
		return m_TestClasses;
	}

	public void setDownloadName(String downloadName) {
		m_DownloadName = downloadName;
	}

	public String getDownloadName() {
		return m_DownloadName;
	}

	@Override
	public boolean handles(FacesContext context) {
		String requestPath = context.getExternalContext().getRequestPathInfo();
		return requestPath.equalsIgnoreCase(getDownloadName());
	}

	@Override
	public void processAjaxRequest(FacesContext context) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) context.getExternalContext().getResponse();

		// Disable the XPages response buffer as this will collide with the
		// engine one
		// We mark it as committed and use its delegate instead
		if (httpResponse instanceof XspHttpServletResponse) {
			XspHttpServletResponse r = (XspHttpServletResponse) httpResponse;
			r.setCommitted(true);
			httpResponse = r.getDelegate();
		}
		try {
			XSPTestSuite testsuite = XSPTestRunner.testClassesAsSuite();
			ByteArrayOutputStream out = TestSuiteXMLProducer.INSTANCE.buildXMLStream(testsuite);
			httpResponse.setContentType("application/xml");
			httpResponse.addHeader("Content-disposition", "filename=\"" + getDownloadName() + "\"");
			out.writeTo(httpResponse.getOutputStream());
			out.close();
			httpResponse.getOutputStream().close();
		} catch (Exception ex) {
			throw new FacesException("Error generationg file.", ex);
		}

	}

	public Class<?>[] getAllTestClasses() throws ClassNotFoundException {
		return AccessController.doPrivileged(new PrivilegedAction<Class<?>[]>() {

			@Override
			public Class<?>[] run() {
				List<Class<?>> classes = new ArrayList<Class<?>>();
				for (String className : getTestClasses()) {
					try {
						Class<?> testClass =Thread.currentThread().getContextClassLoader().loadClass(className);
						//Class<?> testClass = Class.forName(className);
						classes.add(testClass);
					} catch (Exception ex) {
						throw new FacesException(className + " not found!");
					}
				}
				return classes.toArray(new Class<?>[classes.size()]);
			}
		});
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] saveObject = new Object[3];
		saveObject[0] = super.saveState(context);
		saveObject[1] = m_DownloadName;
		saveObject[2] = StateHolderUtil.saveList(context, new ArrayList<String>(m_TestClasses),false);
		return saveObject;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] restoreObject = (Object[]) state;
		super.restoreState(context, restoreObject[0]);
		m_DownloadName = (String) restoreObject[1];
		m_TestClasses = StateHolderUtil.restoreList(context, this, restoreObject[2]);
	}
}
