package org.openntf.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "ddehd", requiresDependencyResolution=ResolutionScope.COMPILE)
@Execute(goal="compile", phase=LifecyclePhase.COMPILE)	
public class HeadlessDesignerBuilder extends AbstractMojo {

	@Parameter(property = "ddehd.designerexec", defaultValue = "designer.exe")
	private String m_DesignerExec;
	@Parameter(property = "ddehd.notesdata")
	private String m_NotesData;
	@Parameter(property = "ddehd.targetdbname")
	private String m_TargetDBName;

	@Parameter(property = "ddehd.odpdirectory")
	private String m_ODPDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Starting DDE HeadlessDesigner Plugin");
		getLog().info("Designer Exec =" + m_DesignerExec);
		getLog().info("Notes Data    =" + m_NotesData);
		getLog().info("TargetDB Name =" + m_TargetDBName);
		getLog().info("ODP           =" + m_ODPDirectory);
		if (StringUtils.isEmpty(m_NotesData) || StringUtils.isEmpty(m_TargetDBName) || StringUtils.isEmpty(m_ODPDirectory)) {
			getLog().info("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata)");
			throw new MojoExecutionException("DDE HeadlessDesigner Plugin miss some configuration (ddehd.targetdbname, ddehd.notesdata, ddehd.odpdirectory)");
		}
		StringBuilder sbNotesData = new StringBuilder("=");
		sbNotesData.append(m_NotesData);
		sbNotesData.append("\\notes.ini");

		StringBuilder sbDesignerArgs = new StringBuilder("-Dcom.ibm.designer.cmd=\"");
		sbDesignerArgs.append("true,true,");
		sbDesignerArgs.append(m_TargetDBName);
		sbDesignerArgs.append(",");
		sbDesignerArgs.append("importandbuild,");
		sbDesignerArgs.append(m_ODPDirectory + "\\.project,");
		sbDesignerArgs.append(m_TargetDBName);
		sbDesignerArgs.append("\"");
		getLog().info("Designer call = "+ sbDesignerArgs.toString());
		ProcessBuilder pb = new ProcessBuilder(m_DesignerExec, sbNotesData.toString(),"-console", "-RPARAMS", "-vmargs", sbDesignerArgs.toString());
		try {
			Process process = pb.start();
			int result = process.waitFor();
			getLog().info("DDE HeadlessDesigner ended with: " + result);
		} catch (Exception ex) {
			throw new MojoExecutionException("DDE HeadlessDesignerPlugin reports an error: ", ex);
		}
	}

}
