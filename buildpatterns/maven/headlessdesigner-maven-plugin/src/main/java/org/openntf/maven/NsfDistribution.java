package org.openntf.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "nsf")
@Execute(goal = "nsf", phase = LifecyclePhase.PACKAGE)
public class NsfDistribution extends AbstractMojo {
	@Parameter(property = "ddehd.notesdata")
	private String m_NotesData;
	@Parameter(defaultValue = "${project.build.outputDirectory}")
	private File m_OutputDir;

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Starting NSF Distribution");
		getLog().info("Notes Data    =" + m_NotesData);
		getLog().info("OutputDir     =" + m_OutputDir);
		if (StringUtils.isEmpty(m_NotesData) || m_OutputDir == null) {
			getLog().info("NSF Distribution miss some configuration (ddehd.notesdata)");
			throw new MojoExecutionException("NSF Distribution miss some configuration (ddehd.notesdata)");
		}
	}

}
