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
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-12
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseCreator extends AbstractMojo {

    /**
     * 返回输入的Controller,Service,View名称
     *
     * @parameter expression="${name}"
     */
    private String name;

    /**
     * @parameter default-value="${project}"
     */
    protected MavenProject project;

    private static String overWriteFlag = "y";

    @Override
    public void execute() throws MojoExecutionException {

        String rootPath = getRootPath();
        File path = new File(rootPath + this.getDir());
        while (name == null) {
            System.out.println("Please enter " + (this.getFileTypeName().equals("") ? this.getFileType() : this.getFileTypeName()) + " name:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                name = br.readLine();
                name = mergeNameForPage(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (name.contains(".")) {
            String[] dirs = name.split("\\.");
            for (int i = 0; i < dirs.length - 1; i++) {
                String dir = dirs[i];
                path = new File(path, dir);
                path.mkdir();
            }
            name = dirs[dirs.length - 1];
        }
        String fullPath = path + File.separator + name + this.getFileTypeName() + "." + this.getFileType();
        exportFile(fullPath, false);
    }

    protected String mergeNameForPage(String name) {
        return name;
    }

    public void exportFile(String path, boolean flag) {
        try {
            Template t = initTemplate(this.getTemplateName());

            VelocityContext context = new VelocityContext();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            context.put("date", format.format(new Date()));
            context.put("name", name);
            context.put("flag", flag);
            context.put("author", System.getProperty("user.name").trim());
            context.put("package", getPackage(path));

            mergeContext(context);

            File file = new File(path);

            if(file.exists() && (!overWriteFlag.equals("a")|| !overWriteFlag.equals("an"))){
                System.out.println(name + this.getFileTypeName() + "." + this.getFileType() + " is existed!");
                System.out.println("will you overwrite it?(y/n/a/an default n)");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    overWriteFlag = br.readLine();
                    if(overWriteFlag == null || overWriteFlag.trim().equals("")){
                        overWriteFlag = "n";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(overWriteFlag.equalsIgnoreCase("y")|| overWriteFlag.equalsIgnoreCase("a")){
                PrintWriter writer = new PrintWriter(path, "UTF-8");
                t.merge(context, writer);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 供子类实现，添加更多的变量
     *
     * @param context
     */
    protected void mergeContext(VelocityContext context) {
    }

    private String getPackage(String path) {
        String rootPath = getRootPath();
        String packagePath = path.substring(rootPath.length() + 1, path.length() - (name + this.getFileTypeName() + "." + this.getFileType()).length() - 1);
        packagePath = packagePath.replaceAll("\\\\", "\\.");
        return packagePath;
    }

    /**
     * 返回项目的源代码路径
     *
     * @return
     */
    protected String getRootPath() {
        List<String> list = project.getCompileSourceRoots();
        return list.get(0);
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

    /**
     * 子类返回其从pom.xml文件中取得的路径
     * 例如：service.dir,controller.dir
     *
     * @return
     */
    public abstract String getDir();

    /**
     * 返回文件后缀名
     * 例如:Controller,Service,Domain
     *
     * @return
     */
    public abstract String getFileTypeName();

    /**
     * 返回文件类型
     * 例如：java,html,js
     *
     * @return
     */
    public abstract String getFileType();

    /**
     * 返回模板的名称
     *
     * @return
     */
    public abstract String getTemplateName();
}
