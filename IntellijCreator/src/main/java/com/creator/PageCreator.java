package com.creator;

import com.creator.bean.Field;
import org.apache.velocity.VelocityContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Page
 */
public abstract class PageCreator extends BaseCreator {
    /**
     * @parameter expression="${dir}" default-value="${view.dir}"
     * @required
     */
    private String dir;

    /**
     * @parameter default-value="${ibatis.java.dir}"
     */
    private String javaDir;

    /**
     * @parameter default-value="${ibatis.model.package}"
     */
    private String modelPackage;

    /**
     * baseCreator中的name，此处用来获取ibator生成的bean
     */
    private String beanName;

    @Override
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setJavaDir(String javaDir) {
        this.javaDir = javaDir;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    @Override
    public String getRootPath() {
        return project.getBasedir()
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "webapp"
                + File.separator + "WEB-INF";
    }

    @Override
    public String getFileTypeName() {
        return "";
    }

    @Override
    public String getFileType() {
        return "html";
    }

    @Override
    protected void mergeContext(VelocityContext context) {
        super.mergeContext(context);
        mergeFieldInfo(context);
    }

    private void mergeFieldInfo(VelocityContext context) {
        String basePath = project.getBasedir().getAbsolutePath();
        basePath = basePath.replaceAll("\\\\", "/");
        modelPackage = modelPackage.replaceAll("\\.", "/");
        String beanPath = basePath + File.separator + ".." + javaDir + File.separator + modelPackage + File.separator + beanName + ".java";
        File file = new File(beanPath);
        List<Field> fields = new ArrayList<Field>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            Field field = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("@name")) {
                    field = new Field();
                    field.setName(line.substring(line.indexOf("@name") + 5).trim());
                }
                if (line.contains("@type")) {
                    field.setType(line.substring(line.indexOf("@type") + 5).trim());
                }
                if (line.contains("@length")) {
                    field.setLength(new Integer(line.substring(line.indexOf("@length") + 7).trim()));
                }
                if (line.contains("@comment")) {
                    field.setComment(line.substring(line.indexOf("@comment") + 8).trim());
                    fields.add(field);
                }
            }
            br.close();
            context.put("fields",fields);
            context.put("name",beanName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
