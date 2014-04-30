package org.openntf.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "ddehd")
@Execute(goal = "ddehd")
public class HeadlessDesignerBuilder extends AbstractMojo {
	private final Pattern NOTESPATTERN = Pattern.compile("(notes2.exe.*? )");

	@Parameter(property = "ddehd.designerexec", defaultValue = "designer.exe")
	private String m_DesignerExec;
	@Parameter(property = "ddehd.notesdata")
	private String m_NotesData;
	@Parameter(property = "ddehd.targetdbname")
	private String m_TargetDBName;

	@Parameter(property = "ddehd.odpdirectory")
	private String m_ODPDirectory;

	@Parameter(property = "ddehd.filename")
	private String m_Filename;
	@Parameter(defaultValue = "${project.build.outputDirectory}")
	private File m_OutputDir;

	@Parameter(property = "ddehd.updateSites", alias = "updateSites")
	private List<UpdateSite> m_UpdateSites;

	public List<UpdateSite> getUpdateSites() {
		return m_UpdateSites;
	}

	public void setUpdateSites(List<UpdateSite> updateSites) {
		m_UpdateSites = updateSites;
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Starting DDE HeadlessDesigner Plugin");
		getLog().info("Designer Exec =" + m_DesignerExec);
		getLog().info("Notes Data    =" + m_NotesData);
		getLog().info("TargetDB Name =" + m_TargetDBName);
		getLog().info("ODP           =" + m_ODPDirectory);
		getLog().info("Updatesite    =" + m_UpdateSites);
		if (StringUtils.isEmpty(m_NotesData) || StringUtils.isEmpty(m_TargetDBName) || StringUtils.isEmpty(m_ODPDirectory)) {
			getLog().info("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata)");
			throw new MojoExecutionException("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata, ddehd.odpdirectory)");
		}
		StringBuilder sbNotesData = new StringBuilder("=");
		sbNotesData.append(m_NotesData);
		sbNotesData.append("\\notes.ini");

		StringBuilder sbDesignerArgs = new StringBuilder("-Dcom.ibm.designer.cmd.file=\"");
		/*
		 * sbDesignerArgs.append("true,true,");
		 * sbDesignerArgs.append(m_TargetDBName); sbDesignerArgs.append(",");
		 * sbDesignerArgs.append("importandbuild,");
		 * sbDesignerArgs.append(m_ODPDirectory + "\\.project,");
		 * sbDesignerArgs.append(m_TargetDBName);
		 */
		sbDesignerArgs.append(m_Filename);
		sbDesignerArgs.append("\"");

		getLog().info("Designer call = " + sbDesignerArgs.toString());
		ProcessBuilder pb = new ProcessBuilder(m_DesignerExec, "-RPARAMS", "-console", "-vmargs", sbDesignerArgs.toString());

		try {
			// Process process = Runtime.getRuntime().exec(m_DesignerExec +
			// " -console -RPARAMS -vmargs "+sbDesignerArgs.toString());
			Process process = pb.start();
			int result = process.waitFor();
			getLog().info("DDE HeadlessDesigner ended with: " + result);
			boolean finished = false;
			int nCounter = 0;
			while (!finished || nCounter < 60) {
				finished = checkIfNotesHasFinished();
				nCounter++;
			}
			if (!finished) {
				throw new MojoExecutionException("DDE HeadlessDesignerPlugin not finished in 60 sec timeout");

			}
		} catch (Exception ex) {
			throw new MojoExecutionException("DDE HeadlessDesignerPlugin reports an error: ", ex);
		}
	}

	private boolean checkIfNotesHasFinished() throws IOException, InterruptedException {

		Process process = Runtime.getRuntime().exec("cmd.exe /c tasklist /fi \"IMAGENAME eq notes2.exe\"");
		process.waitFor();
		final InputStream is = process.getInputStream();
		final InputStreamReader isr = new InputStreamReader(is);
		final BufferedReader buff = new BufferedReader(isr);
		String line = new String();
		while ((line = buff.readLine()) != null) {
			Matcher iMapSuccessMatcher = NOTESPATTERN.matcher(line);
			if (iMapSuccessMatcher.find()) {
				Thread.sleep(1000);
				getLog().info("Waiting for Notes to complete building.");
				return false;
			}
		}
		return true;
	}

}
