package org.openntf.maven;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractDesignerPlugin extends AbstractMojo {

	/**
	 * Name of the target database like "crm.nsf"
	 */
	@Parameter(property = "ddehd.targetdbname")
	protected String m_TargetDBName;
	@Parameter(defaultValue = "${project.build.directory}")
	protected File m_TargetDir;
	/**
	 * Collection of updatesite / feature definitions, to prepare the headless
	 * designer with the right environment for building the applications
	 */
	@Parameter(property = "ddehd.features", alias = "features")
	protected List<Feature> m_Features;

	public AbstractDesignerPlugin() {
		super();
	}

	protected String buildReportOutput(String category, String action) {
		return String.format("- %-22s: %s", category, action);
	}

	protected String buildSetupOutput(String variableName, String value) {
		return String.format("%-22s= %s", variableName, value);
	
	}

}