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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Create Controller
 *
 * @goal controller
 * @requiresProject true
 */
public class ControllerCreator extends AbstractMojo {
    /**
     * @parameter expression="${name}"
     */
    private String controllerName;

    /**
     * @parameter expression="${dir}" default-value="${controller.dir}"
     * @required
     */
    private String controllerDir;

    /**
     * @parameter default-value="${project}"
     */
    private MavenProject project;

    private boolean flag = false;

    @Override
    public void execute() throws MojoExecutionException {

        String rootPath = getRootPath();
        File path = new File(rootPath + controllerDir);
        while (controllerName == null) {
            System.out.println("Please enter controller name:");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            try {
                controllerName = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (controllerName.contains(".")) {
            String[] dirs = controllerName.split("\\.");
            for (int i = 0; i < dirs.length - 1; i++) {
                String dir = dirs[i];
                path = new File(path, dir);
                path.mkdir();
            }
            controllerName = dirs[dirs.length - 1];
        }
        String fullPath = path + File.separator + controllerName + "Controller.java";
        exportFile(fullPath);
    }

    public void exportFile(String path) {
        try {
            Template t = initTemplate("ControllerTemplate.vm");

            VelocityContext context = new VelocityContext();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            context.put("date", format.format(new Date()));
            context.put("name", controllerName);
            context.put("flag", flag);
            context.put("author", System.getProperty("user.name").trim());
            context.put("package", getPackage(path));

            PrintWriter writer = new PrintWriter(path, "UTF-8");
            t.merge(context, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getPackage(String path) {
        String rootPath = getRootPath();
        String packagePath = path.substring(rootPath.length() + 1, path.length() - (controllerName + "Controller.java").length() - 1);
        packagePath = packagePath.replaceAll("\\\\", "\\.");
        return packagePath;
    }

    /**
     * 初始化Velocity
     *
     * @return
     * @throws Exception
     */
    private Template initTemplate(String templateName) throws Exception {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        String basePath = project.getBasedir().getAbsolutePath();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath + File.separator + "template");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init(p);
        return ve.getTemplate(templateName, "utf-8");
    }

    /**
     * 返回项目的源代码路径
     *
     * @return
     */
    private String getRootPath() {
        List<String> list = project.getCompileSourceRoots();
        return list.get(0);
    }

}
