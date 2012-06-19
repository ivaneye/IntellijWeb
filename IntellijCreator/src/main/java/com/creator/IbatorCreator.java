package com.creator;

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
 * Run Ibator
 *
 * @goal ibator
 * @requiresProject true
 */
public class IbatorCreator extends AbstractMojo {

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

    public void setName(String name) {
        if(name.contains(".")){
            String[] t = name.split("\\.");
            name = t[t.length - 1];
        }
        this.name = name;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public void setJavaDir(String javaDir) {
        this.javaDir = javaDir;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public void setResourcesDir(String resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public void setResourcesPackage(String resourcesPackage) {
        this.resourcesPackage = resourcesPackage;
    }

    @Override
    public void execute() throws MojoExecutionException {

        while (name == null) {
            System.out.println("Please enter Table name:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                name = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        generateIbator(name);
    }

    public void generateIbator(String tableName) {
        try {
            Template t = initTemplate("ibatorConfig.vm");

            VelocityContext context = new VelocityContext();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            context.put("date", format.format(new Date()));
            name = name.toLowerCase();
            String n = name.charAt(0) + "";
            name = name.replaceFirst(n,n.toUpperCase());
            context.put("name", name);
            context.put("author", System.getProperty("user.name").trim());
            String basePath = project.getBasedir().getAbsolutePath();
            basePath = basePath.replaceAll("\\\\","/");
            context.put("javaDir", basePath + "/.." + javaDir);
            context.put("modelPackage", modelPackage);
            context.put("daoPackage", daoPackage);
            context.put("resourcesDir", basePath + "/.." + resourcesDir);
            context.put("resourcesPackage", resourcesPackage);

            String outputFile = project.getBuild().getOutputDirectory()
                    + File.separator
                    + name + ".xml";

            PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
            t.merge(context, writer);
            writer.flush();
            writer.close();
            runScript(outputFile);
//            new File(outputFile).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Java运行cmd命令
     *
     * @param outputFile
     */
    private void runScript(String outputFile) {
        String basePath = project.getBasedir().getAbsolutePath();
        String ibatorJarPath = "D:\\ProgramCodes\\IntellijWeb\\ibator-core\\target\\ibator-core-1.2.2.jar";
//                basePath + File.separator + "template" + File.separator + "ibator.jar";
        Runtime rt = Runtime.getRuntime();
        String cmd = "java -cp "
                + ibatorJarPath
                + " org.apache.ibatis.ibator.api.IbatorRunner -configfile  "
                + outputFile + "\n";
        try {
            rt.exec(cmd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化Velocity
     *
     * @return
     * @throws Exception
     */
    protected Template initTemplate(String templateName) throws Exception {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        String basePath = project.getBasedir().getAbsolutePath();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath + File.separator + "template");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init(p);
        return ve.getTemplate(templateName, "utf-8");
    }
}
