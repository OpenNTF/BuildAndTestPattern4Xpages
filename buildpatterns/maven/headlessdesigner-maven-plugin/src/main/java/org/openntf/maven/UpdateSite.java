package org.openntf.maven;

public class UpdateSite {

	public UpdateSite() {
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return m_Path;
	}
	public void setPath(String path) {
		m_Path = path;
	}

	public String getVersion() {
		return m_Version;
	}

	public void setVersion(String version) {
		m_Version = version;
	}

	public String getFeatureID() {
		return m_FeatureID;
	}

	public void setFeatureID(String featureID) {
		m_FeatureID = featureID;
	}

	private String m_Path;
	private String m_FeatureID;
	private String m_Version;

}
