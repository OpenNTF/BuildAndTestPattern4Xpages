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
