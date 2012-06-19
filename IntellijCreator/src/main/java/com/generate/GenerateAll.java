package com.generate;

import com.creator.ControllerCreator;
import com.creator.IbatorCreator;
import com.creator.NewPageCreator;
import com.creator.ServiceCreator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Generate All
 *
 * @goal generateAll
 * @requiresProject true
 */
public class GenerateAll extends AbstractMojo {

    /**
     * 返回输入的Table名称
     *
     * @parameter expression="${name}"
     */
    private String name;

    /**
     * @parameter default-value="${project}"
     */
    protected MavenProject project;

    /**
     * @parameter default-value="${ibatis.java.dir}"
     */
    private String javaDir;

    /**
     * @parameter default-value="${ibatis.model.package}"
     */
    private String modelPackage;

    /**
     * @parameter default-value="${ibatis.dao.package}"
     */
    private String daoPackage;

    /**
     * @parameter default-value="${ibatis.resources.dir}"
     */
    private String resourcesDir;

    /**
     * @parameter default-value="${ibatis.resources.package}"
     */
    private String resourcesPackage;
    /**
     * @parameter expression="${dir}" default-value="${controller.dir}"
     * @required
     */
    private String controllerDir;
    /**
     * @parameter expression="${dir}" default-value="${service.dir}"
     * @required
     */
    private String serviceDir;

    /**
     * @parameter expression="${dir}" default-value="${view.dir}"
     * @required
     */
    private String viewDir;

    @Override
    public void execute() throws MojoExecutionException {
        while (name == null) {
            System.out.println("Please enter Generate name:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                name = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        IbatorCreator ic = new IbatorCreator();
        ic.setDaoPackage(daoPackage);
        ic.setJavaDir(javaDir);
        ic.setModelPackage(modelPackage);
        ic.setName(name);
        ic.setProject(project);
        ic.setResourcesDir(resourcesDir);
        ic.setResourcesPackage(resourcesPackage);
        ic.execute();

        ControllerCreator cc = new ControllerCreator();
        cc.setFlag(true);
        cc.setDir(controllerDir);
        cc.setProject(project);
        cc.setName(name);
        cc.execute();

        ServiceCreator sc = new ServiceCreator();
        sc.setFlag(true);
        sc.setName(name);
        sc.setProject(project);
        sc.setDir(serviceDir);
        sc.execute();

        NewPageCreator npc = new NewPageCreator();
        npc.setFlag(true);
        npc.setName(name);
        npc.setProject(project);
        npc.setDir(viewDir);
        npc.setJavaDir(javaDir);
        npc.setModelPackage(modelPackage);
        npc.execute();
    }

}
