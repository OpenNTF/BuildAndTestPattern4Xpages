package org.openntf.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "ddehd")
@Execute(goal = "ddehd")
public class HeadlessDesignerBuilder extends AbstractDesignerPlugin {
	private static final String UNINSTALL_TXT = "uninstall.txt";

	private static final String DISABLE_TXT = "disable.txt";

	private static final String BUILD_TXT = "build.txt";

	private static final String ENABLE_TXT = "enable.txt";

	private static final String INSTALL_TXT = "install.txt";

	private final Pattern NOTESPATTERN = Pattern.compile("(notes2.exe.*? )");

	/**
	 * Path to the designer.exe (e.g. C:\Program Files\IBM\Notes\Designer.exe)
	 */
	@Parameter(property = "ddehd.designerexec", defaultValue = "designer.exe")
	private String m_DesignerExec;
	/**
	 * Path to the notes data directory (e.g. C:\Program Files\IBM\Notes\Data)
	 */
	@Parameter(property = "ddehd.notesdata")
	private String m_NotesData;
	@Parameter(property = "ddehd.odpdirectory")
	private String m_ODPDirectory;

	/**
	 * Path to File with the build instructions for the headless designer. If
	 * this value is set, all other values like updateSites, odpdirectory and
	 * targetdbname will be ignored.
	 */
	@Parameter(property = "ddehd.filename")
	private String m_Filename;
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Starting DDE HeadlessDesigner Plugin");
		getLog().info("====================================");
		getLog().info(buildSetupOutput("Designer Exec", m_DesignerExec));
		getLog().info(buildSetupOutput("Notes Data", m_NotesData));
		getLog().info(buildSetupOutput("TargetDB Name", m_TargetDBName));
		getLog().info(buildSetupOutput("ODP", m_ODPDirectory));
		getLog().info(buildSetupOutput("Features", m_Features == null ? "<none>" : "" + m_Features.size()));
		getLog().info(buildSetupOutput("Target Directory", m_TargetDir == null ? "<no targetdir!" : "" + m_TargetDir.getAbsolutePath()));
		if (StringUtils.isEmpty(m_NotesData) || StringUtils.isEmpty(m_TargetDBName) || StringUtils.isEmpty(m_ODPDirectory)) {
			getLog().info("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata)");
			throw new MojoExecutionException("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata, ddehd.odpdirectory)");
		}
		if (StringUtils.isEmpty(m_Filename)) {
			installFeature();
			enableFeatures();
			buildApplication();
			disableFeatures();
			uninstallFeatures();
			moveNSFtoTargetDirectory();
		} else {
			customBuild();
		}
	}

	private void moveNSFtoTargetDirectory() throws MojoExecutionException {
		getLog().info(buildReportOutput("copyNSF", "Move "+m_TargetDBName));
		StringBuilder sbNotesData = new StringBuilder();
		sbNotesData.append(m_NotesData);
		sbNotesData.append("\\");
		sbNotesData.append(m_TargetDBName);
		File flDB = new File(sbNotesData.toString());
		File flTarget = new File(m_TargetDir, m_TargetDBName);
		if (flDB.exists()) {
			try {
				FileUtils.copyFile(flDB, flTarget);
			} catch (Exception ex) {
				throw new MojoExecutionException("Build failed during copy of " + m_TargetDBName + " to target directory", ex);

			}
		} else {
			throw new MojoExecutionException("Build failed, no " + m_TargetDBName + " found in " + m_NotesData);
		}
		getLog().info(buildReportOutput("copyNSF", "Move "+m_TargetDBName + " finished."));

	}

	private void executeDesigner(String fileName) throws MojoExecutionException {

		StringBuilder sbDesignerArgs = new StringBuilder("-Dcom.ibm.designer.cmd.file=\"");
		sbDesignerArgs.append(fileName);
		sbDesignerArgs.append("\"");

		getLog().debug("Designer call = " + sbDesignerArgs.toString());
		ProcessBuilder pb = new ProcessBuilder(m_DesignerExec, "-RPARAMS", "-console", "-vmargs", sbDesignerArgs.toString());

		try {
			Process process = pb.start();
			int result = process.waitFor();
			getLog().debug("DDE HeadlessDesigner ended with: " + result);
			boolean finished = false;
			int nCounter = 0;
			while (!finished || nCounter < 120) {
				finished = checkIfNotesHasFinished();
				nCounter++;
			}
			if (!finished) {
				throw new MojoExecutionException("DDE HeadlessDesignerPlugin not finished in 120 sec timeout");

			}
		} catch (Exception ex) {
			throw new MojoExecutionException("DDE HeadlessDesignerPlugin reports an error: ", ex);
		}
		getLog().debug("Designer execution ended");
	}

	private void customBuild() throws MojoExecutionException {
		executeDesigner(m_Filename);
	}

	private void installFeature() throws MojoExecutionException {
		if (m_Features != null && m_Features.size() > 0) {
			getLog().info(buildReportOutput("installFeatures", "Build File target/" + INSTALL_TXT));
			File fileDeployUS = createInstructionFile(INSTALL_TXT);
			try {
				PrintWriter pw = new PrintWriter(fileDeployUS);
				pw.println("config,true,true");
				for (Feature site : m_Features) {
					StringBuilder sb = new StringBuilder();
					sb.append("com.ibm.designer.domino.tools.userlessbuild.jobs.UpdateManagerJob,-command install -from ");
					sb.append(site.getUrl());
					sb.append(" -to file:/");
					sb.append(m_NotesData);
					sb.append("/workspace/applications");
					sb.append(" -featureId ");
					sb.append(site.getFeatureId());
					sb.append(" -version ");
					sb.append(site.getVersion());
					pw.println(sb.toString());
				}
				pw.println("exit,100");
				pw.close();
				getLog().info(buildReportOutput("installFeatures", "Build File target/" + INSTALL_TXT + " prepared."));
				executeDesigner(fileDeployUS.getAbsolutePath());

			} catch (Exception ex) {
				throw new MojoExecutionException("Headless Designer Plugin: Could not create " + INSTALL_TXT, ex);
			}
			getLog().info(buildReportOutput("installFeatures", "Executed"));
		} else {
			getLog().info(buildReportOutput("installFeatures", "SKIPPED"));
		}

	}

	private File createInstructionFile(String fileName) {
		if (!m_TargetDir.exists()) {
			m_TargetDir.mkdirs();
		}
		File fileDeployUS = new File(m_TargetDir, fileName);
		return fileDeployUS;
	}

	private void enableFeatures() throws MojoExecutionException {
		if (m_Features != null && m_Features.size() > 0) {
			getLog().info(buildReportOutput("enableFeatures", "Build File target/" + ENABLE_TXT));
			File fileActivate = createInstructionFile(ENABLE_TXT);
			try {
				PrintWriter pw = new PrintWriter(fileActivate);
				pw.println("config,true,true");
				for (Feature site : m_Features) {
					StringBuilder sb = new StringBuilder();
					sb.append("com.ibm.designer.domino.tools.userlessbuild.jobs.UpdateManagerJob,-command enable -to file:/");
					sb.append(m_NotesData);
					sb.append("/workspace/applications");
					sb.append(" -featureId ");
					sb.append(site.getFeatureId());
					sb.append(" -version ");
					sb.append(site.getVersion());
					pw.println(sb.toString());
				}
				pw.println("exit");
				pw.close();
				getLog().info(buildReportOutput("enableFeatures", "Build File target/" + ENABLE_TXT + " prepared."));
				executeDesigner(fileActivate.getAbsolutePath());

			} catch (Exception ex) {
				throw new MojoExecutionException("Headless Designer Plugin: Could not create" + ENABLE_TXT, ex);
			}
			getLog().info(buildReportOutput("enableFeatures", "Executed"));
		} else {
			getLog().info(buildReportOutput("enableFeatures", "SKIPPED"));
		}

	}

	private void buildApplication() throws MojoExecutionException {
		getLog().info(buildReportOutput("buildApplication", "Build File target/" + BUILD_TXT));
		File fileBuild = createInstructionFile(BUILD_TXT);
		try {
			PrintWriter pw = new PrintWriter(fileBuild);
			pw.println("config,true,true");
			pw.println("importandbuild," + m_ODPDirectory + "/.project," + m_TargetDBName);
			pw.println("wait," + m_TargetDBName + ",3");
			pw.println("clean");
			pw.println("exit,100");
			pw.close();
			getLog().info(buildReportOutput("buildApplication", "Build File target/" + BUILD_TXT + " prepared."));
			executeDesigner(fileBuild.getAbsolutePath());

		} catch (Exception ex) {
			throw new MojoExecutionException("Headless Designer Plugin: Could not create " + BUILD_TXT, ex);
		}
		getLog().info(buildReportOutput("buildApplication", "Executed"));
	}

	private void disableFeatures() throws MojoExecutionException {
		if (m_Features != null && m_Features.size() > 0) {
			getLog().info(buildReportOutput("disableFeatures", "Build File target/" + DISABLE_TXT));
			File fileDisable = createInstructionFile(DISABLE_TXT);
			try {
				PrintWriter pw = new PrintWriter(fileDisable);
				pw.println("config,true,true");
				for (Feature site : m_Features) {
					StringBuilder sb = new StringBuilder();
					sb.append("com.ibm.designer.domino.tools.userlessbuild.jobs.UpdateManagerJob,-command disable -to file:/");
					sb.append(m_NotesData);
					sb.append("/workspace/applications");
					sb.append(" -featureId ");
					sb.append(site.getFeatureId());
					sb.append(" -version ");
					sb.append(site.getVersion());

					pw.println(sb.toString());
				}
				pw.println("exit,100");
				pw.close();
				getLog().info(buildReportOutput("disableFeatures", "Build File target/" + DISABLE_TXT + " prepared."));
				executeDesigner(fileDisable.getAbsolutePath());

			} catch (Exception ex) {
				throw new MojoExecutionException("Headless Designer Plugin: Could not create " + DISABLE_TXT, ex);
			}
			getLog().info(buildReportOutput("disableFeatures", "Executed"));
		} else {
			getLog().info(buildReportOutput("disableFeatures", "SKIPPED"));
		}

	}

	private void uninstallFeatures() throws MojoExecutionException {
		if (m_Features != null && m_Features.size() > 0) {
			getLog().info(buildReportOutput("uninstallFeatures", "Build File target/" + UNINSTALL_TXT));
			File fileUninstall = createInstructionFile(UNINSTALL_TXT);
			try {
				PrintWriter pw = new PrintWriter(fileUninstall);
				pw.println("config,true,true");
				for (Feature site : m_Features) {
					StringBuilder sb = new StringBuilder();
					sb.append("com.ibm.designer.domino.tools.userlessbuild.jobs.UpdateManagerJob,-command uninstall -to file:/");
					sb.append(m_NotesData);
					sb.append("/workspace/applications");
					sb.append(" -featureId ");
					sb.append(site.getFeatureId());
					sb.append(" -version ");
					sb.append(site.getVersion());

					pw.println(sb.toString());
				}
				pw.println("exit,100");
				pw.close();
				getLog().info(buildReportOutput("uninstallFeatures", "Build File target/" + UNINSTALL_TXT + " prepared."));
				executeDesigner(fileUninstall.getAbsolutePath());

			} catch (Exception ex) {
				throw new MojoExecutionException("Headless Designer Plugin: Could not create " + UNINSTALL_TXT, ex);
			}
			getLog().info(buildReportOutput("uninstallFeatures", "Executed"));
		} else {
			getLog().info(buildReportOutput("uninstallFeatures", "SKIPPED"));
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
				getLog().debug("Waiting for Notes to complete building.");
				return false;
			}
		}
		return true;
	}

	public List<Feature> getFeatures() {
		return m_Features;
	}

	public void setFeatures(List<Feature> features) {
		m_Features = features;
	}
	
}
