package org.openntf.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "nsf-dist")
@Execute(goal = "nsf-dist", phase = LifecyclePhase.PACKAGE)
public class NsfDistribution extends AbstractDesignerPlugin {

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Starting NSF Distribution");
		getLog().info("TargetDBName    =" + m_TargetDBName);
		getLog().info("OutputDir     =" + m_TargetDir);
		if (StringUtils.isEmpty(m_TargetDBName) || m_TargetDBName == null) {
			getLog().info("NSF Distribution miss some configuration (ddehd.notesdata)");
			throw new MojoExecutionException("NSF Distribution miss some configuration (ddehd.notesdata)");
		}
	}

}
