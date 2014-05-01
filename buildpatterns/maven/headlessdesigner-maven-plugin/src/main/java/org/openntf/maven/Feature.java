package org.openntf.maven;

import org.apache.maven.plugins.annotations.Parameter;

public class Feature {
	@Parameter(property = "ddehd.url", alias = "url")
	private String m_Url;
	@Parameter(property = "ddehd.featureId", alias = "featureId")
	private String m_FeatureId;
	@Parameter(property = "ddehd.version", alias = "version")
	private String m_Version;

	public String getURL() {
		return m_Url;
	}

	public void setURL(String uRL) {
		m_Url = uRL;
	}

	public String getFeatureId() {
		return m_FeatureId;
	}

	public void setFeatureId(String featureId) {
		m_FeatureId = featureId;
	}

	public String getVersion() {
		return m_Version;
	}

	public void setVersion(String version) {
		m_Version = version;
	}

}
