package org.openntf.junit.xsp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class JUnitXSPActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	@Override
	public void start(BundleContext context) throws Exception {
		JUnitXSPActivator.context = context;

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		JUnitXSPActivator.context = null;

	}

}
