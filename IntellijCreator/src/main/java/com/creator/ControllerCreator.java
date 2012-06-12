package com.creator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Create Controller
 *
 * @goal controller
 * @requiresProject true
 */
public class ControllerCreator extends BaseCreator {
    /**
     * @parameter expression="${dir}" default-value="${controller.dir}"
     * @required
     */
    private String dir;

    @Override
    public String getDir() {
        return dir;
    }

    @Override
    public String getFileName() {
        return "Controller";
    }

    @Override
    public String getFileType() {
        return "java";
    }

    @Override
    public String getTemplateName() {
        return "ControllerTemplate.vm";
    }
}
