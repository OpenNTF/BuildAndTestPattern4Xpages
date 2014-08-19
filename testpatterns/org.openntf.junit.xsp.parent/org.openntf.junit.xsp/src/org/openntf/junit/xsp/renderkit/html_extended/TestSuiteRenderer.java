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
package org.openntf.junit.xsp.renderkit.html_extended;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.openntf.junit.xsp.component.UITestsuite;
import org.openntf.junit.xsp.junit4.XSPTestRunner;
import org.openntf.junit.xsp.junit4.report.TestEntry;
import org.openntf.junit.xsp.junit4.report.TestEntry.TestStatus;
import org.openntf.junit.xsp.junit4.report.XSPResult;
import org.openntf.junit.xsp.junit4.report.XSPTestSuite;

import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.component.UIScriptCollector;
import com.ibm.xsp.renderkit.FacesRenderer;

public class TestSuiteRenderer extends FacesRenderer {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (component instanceof UITestsuite && component.isRendered()) {
			UIScriptCollector sc = UIScriptCollector.find();
			sc.addScriptOnce(buildJSCode());
			UITestsuite testsuite = (UITestsuite) component;
			XSPTestSuite testResult = runTests(testsuite);
			buildResultTable(context, testsuite, testResult);
		}
	}

	private XSPTestSuite runTests(UITestsuite testsuite) {
		try {
			return XSPTestRunner.testClassesAsSuite(testsuite.getAllTestClasses());
		} catch (ClassNotFoundException e) {
			throw new FacesException("Errro during runTests", e);
		}
	}

	private void buildResultTable(FacesContext context, UITestsuite testsuite, XSPTestSuite testResult) throws IOException {
		ResponseWriter rw = context.getResponseWriter();
		String id = testsuite.getClientId(context);
		rw.startElement("div", testsuite);
		rw.writeAttribute("id", id, null);
		writeSummary(context, rw, id, testsuite, testResult);
		writeResults(context, rw, id, testsuite, testResult);
		rw.endElement("div");
	}

	private void writeSummary(FacesContext context, ResponseWriter rw, String id, UITestsuite testsuite, XSPTestSuite testResult) throws IOException {
		rw.startElement("table", testsuite);
		rw.writeAttribute("id", id + "_summary", null);
		rw.writeAttribute("class", "lotusTable", null);
		rw.startElement("tr", testsuite);
		rw.writeAttribute("class", "lotusFirst", null);

		writeTD(rw, testsuite, "lotusFirstCell", "Tests: " + testResult.getTests(), "120px");
		writeIconTD(rw, TestStatus.ERROR, "");
		writeTD(rw, testsuite, null, "Errors: " + testResult.getErrros(), "120px");
		writeIconTD(rw, TestStatus.FAILURE, "");
		writeTD(rw, testsuite, null, "Failures: " + testResult.getFailures(), "120px");
		if (testResult.getErrros() + testResult.getFailures() > 0) {
			writeTDRED(rw, testsuite, "lotusLastCell");
		} else {
			writeTDGREEN(rw, testsuite, "lotusLastCell");
		}
		rw.endElement("tr");
		rw.endElement("table");
	}

	private void writeTD(ResponseWriter rw, UITestsuite testsuite, String uiClass, String content, String width) throws IOException {
		rw.startElement("td", testsuite);
		if (!StringUtil.isEmpty(uiClass)) {
			rw.writeAttribute("class", uiClass, null);
		}
		if (!StringUtil.isEmpty(width)) {
			rw.writeAttribute("width", width, null);
		}
		rw.writeText(content, null);
		rw.endElement("td");

	}

	private void writeTD(ResponseWriter rw, UITestsuite testsuite, String uiClass, String content) throws IOException {
		writeTD(rw, testsuite, uiClass, content, null);
	}

	private void writeTDRED(ResponseWriter rw, UITestsuite testsuite, String uiClass) throws IOException {
		rw.startElement("td", testsuite);
		if (!StringUtil.isEmpty(uiClass)) {
			rw.writeAttribute("class", uiClass, null);
		}
		rw.writeAttribute("bgcolor", "red", null);
		rw.endElement("td");
	}

	private void writeTDGREEN(ResponseWriter rw, UITestsuite testsuite, String uiClass) throws IOException {
		rw.startElement("td", testsuite);
		if (!StringUtil.isEmpty(uiClass)) {
			rw.writeAttribute("class", uiClass, null);
		}
		rw.writeAttribute("bgcolor", "green", null);
		rw.endElement("td");
	}

	private void writeResults(FacesContext context, ResponseWriter rw, String id, UITestsuite testsuite, XSPTestSuite testResult) throws IOException {
		int nCounter = 0;
		for (XSPResult testcase : testResult.getResults()) {
			String idSub = id + "_tc_" + nCounter;
			writeResultSummary(rw, idSub + "_details", testsuite, testcase, testResult);
			writeDetailResults(context, rw, idSub + "_details", testcase);
			writeSystemOut(context, rw, idSub + "_sysout", testcase);
			nCounter++;
		}
	}

	private void writeResultSummary(ResponseWriter rw, String id, UITestsuite testsuite, XSPResult testcase, XSPTestSuite testResult) throws IOException {
		rw.startElement("table", null);
		rw.writeAttribute("style", "margin-top:44px;", null);
		rw.writeAttribute("class", "lotusTable", null);
		rw.startElement("tr", null);
		rw.writeAttribute("class", "lotusFirst", null);

		writeIconTD(rw, getTestStatus(testcase), "lotusFirstCell");
		rw.startElement("td", null);
		rw.startElement("h2", null);
		rw.writeAttribute("class", "lotusTitle", null);
		rw.startElement("a", null);
		rw.writeAttribute("onclick", buildOnClickCall(id), null);
		rw.writeText(testcase.getTestClassName(), null);
		rw.endElement("a");
		rw.endElement("h2");
		rw.endElement("td");

		writeTD(rw, testsuite, "lotusAlignRight lotusLastCell", "(Tests: " + testcase.getRunCount() + ", Errors: " + testcase.getErrorCount() + ", Failures: " + testcase.getFailureCount()
				+ ", Time: " + testcase.getTime() + " ms)");
		rw.endElement("tr");
		rw.endElement("table");
	}

	private TestStatus getTestStatus(XSPResult testcase) {
		if (testcase.getErrorCount() > 0) {
			return TestStatus.ERROR;
		}
		if (testcase.getFailureCount() > 0) {
			return TestStatus.FAILURE;
		}
		// TODO Auto-generated method stub
		return TestStatus.SUCCESS;
	}

	private void writeDetailResults(FacesContext context, ResponseWriter rw, String idSub, XSPResult testcase) throws IOException {
		rw.startElement("div", null);
		if (getTestStatus(testcase) == TestStatus.SUCCESS) {
			rw.writeAttribute("style", "margin-left:20px;display:none", null);
		} else {
			rw.writeAttribute("style", "margin-left:20px", null);			
		}
		rw.writeAttribute("id", idSub, null);
		rw.startElement("table", null);
		rw.writeAttribute("id", idSub + "_table", null);
		rw.writeAttribute("class", "lotusTable", null);

		int nCounter = 0;
		for (TestEntry entry : testcase.getTestEntries()) {
			String entryID = idSub + "_" + nCounter;
			rw.startElement("tr", null);
			writeIconTD(rw, entry.getStatus(), "lotusFirstCell");
			writeTD(rw, null, "", entry.getMethodName());
			writeTD(rw, null, "lotusAlignRight lotusLastCell", buildDurationString(entry));
			rw.endElement("tr");
			writeFailureInfos(rw, entryID, entry);
			nCounter++;
		}
		rw.endElement("table");
		rw.endElement("div");

	}

	private void writeFailureInfos(ResponseWriter rw, String entryID, TestEntry entry) throws IOException {
		if (entry.getStatus() != TestStatus.SUCCESS) {
			rw.startElement("tr", null);
			rw.writeAttribute("class", "lotusDetails", null);
			rw.startElement("td", null);
			rw.writeAttribute("width", "20px", null);
			rw.endElement("td");
			rw.startElement("td", null);
			rw.writeAttribute("colspan", "2", null);
			rw.startElement("p", null);
			if (entry.getStatus() == TestStatus.ERROR) {
				rw.writeText(entry.getErrorType() + " / " + entry.getErrorMessage(), null);
			} else {
				rw.writeText(entry.getFailureType() + " / " + entry.getFailureMessage(), null);
			}
			rw.endElement("p");
			rw.startElement("a", null);
			rw.writeAttribute("onclick", buildOnClickCall(entryID), null);
			rw.writeText("Details (show / hide)", null);
			rw.endElement("a");
			rw.startElement("br", null);
			rw.endElement("br");
			rw.startElement("div", null);
			rw.writeAttribute("id", entryID, null);
			rw.writeAttribute("style", "display:none", null);
			if (entry.getStatus() == TestStatus.ERROR) {
				rw.write(entry.getErrorTrace().replace(System.getProperty("line.separator"), "<br/>\n"));
			} else {
				rw.write(entry.getFailureTrace().replace(System.getProperty("line.separator"), "<br/>\n"));
			}
			rw.endElement("div");
			rw.endElement("td");
			rw.endElement("tr");
		}
	}

	private String buildOnClickCall(String entryID) {
		return "junitDetailToggle('" + entryID + "')";
	}

	private String buildDurationString(TestEntry entry) {
		return entry.getTestDuration() + " ms";
	}

	private void writeIconTD(ResponseWriter rw, TestStatus status, String cssClass) throws IOException {
		rw.startElement("td", null);
		rw.writeAttribute("width", "20px", null);
		if (!StringUtil.isEmpty(cssClass)) {
			rw.writeAttribute("class", cssClass, null);

		}
		rw.startElement("img", null);
		rw.writeAttribute("src", status.getICONUrl(), null);
		rw.endElement("img");
		rw.endElement("td");
	}

	private void writeSystemOut(FacesContext context, ResponseWriter rw, String idSub, XSPResult testcase) throws IOException {
		rw.startElement("div", null);
		rw.writeAttribute("style", "margin-left:20px", null);
		rw.startElement("a", null);
		rw.writeAttribute("onclick", buildOnClickCall(idSub), null);
		rw.writeText("SystemOut / SytemError (show / hide)", null);
		rw.endElement("a");
		rw.startElement("br", null);
		rw.endElement("br");
		rw.startElement("div", null);
		rw.writeAttribute("style", "display:none;margin-left:5px", null);
		rw.writeAttribute("id", idSub, null);
		rw.write("System.Out</br>");
		rw.write(testcase.getSystemOut().replace(System.getProperty("line.separator"), "<br/>\n"));
		rw.write("<br/><br/>System.Err</br>");
		rw.write(testcase.getSystemErr().replace(System.getProperty("line.separator"), "<br/>\n"));
		rw.endElement("div");
		rw.endElement("div");

	}

	private String buildJSCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("function junitDetailToggle( id ) {");
		sb.append("var div = document.getElementById(id);");
		sb.append("if (div.style.display !== 'none') {");
		sb.append("div.style.display = 'none';");
		sb.append("} else {");
		sb.append("    div.style.display = 'block';");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
}
