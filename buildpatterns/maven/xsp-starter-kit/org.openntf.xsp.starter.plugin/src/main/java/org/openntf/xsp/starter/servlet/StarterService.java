package org.openntf.xsp.starter.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import com.ibm.designer.runtime.domino.adapter.ComponentModule;
import com.ibm.designer.runtime.domino.adapter.HttpService;
import com.ibm.designer.runtime.domino.adapter.LCDEnvironment;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletRequestAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletResponseAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpSessionAdapter;

public class StarterService extends HttpService {

	public StarterService(LCDEnvironment environment) {
		super(environment);
	}

	@Override
	public void getModules(List<ComponentModule> paramList) {

	}

	@Override
	public boolean doService(String paramString1, String paramString2, HttpSessionAdapter paramHttpSessionAdapter,
			HttpServletRequestAdapter paramHttpServletRequestAdapter, HttpServletResponseAdapter paramHttpServletResponseAdapter)
			throws ServletException, IOException {
		
		return false;
	}

}
