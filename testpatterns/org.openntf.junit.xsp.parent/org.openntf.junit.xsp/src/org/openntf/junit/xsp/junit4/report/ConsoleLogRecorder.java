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
package org.openntf.junit.xsp.junit4.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleLogRecorder {

	private final PrintStream m_SysOut;
	private final PrintStream m_ErrOut;
	private final ByteArrayOutputStream m_SysOutRecorder = new ByteArrayOutputStream();
	private final ByteArrayOutputStream m_SysErrorRecorder = new ByteArrayOutputStream();

	public ConsoleLogRecorder() {
		m_SysOut = System.out;
		m_ErrOut = System.err;
	}

	public void startRecording() {
		System.setOut(new PrintStream(m_SysOutRecorder));
		System.setErr(new PrintStream(m_SysErrorRecorder));
	}

	public void stopRecordingAndResetSystem() {
		System.setOut(m_SysOut);
		System.setErr(m_ErrOut);
	}

	public String getSystemOut() {
		return decodeBAOStoUTF8(m_SysOutRecorder);
	}

	public String getErrorOut() {
		return decodeBAOStoUTF8(m_SysErrorRecorder);
	}

	private String decodeBAOStoUTF8(ByteArrayOutputStream baos) {
		try {
			return baos.toString("UTF-8");
		} catch (Exception ex) {
			// Not relevant, the failover is good to handle this
		}
		return baos.toString();

	}
}
