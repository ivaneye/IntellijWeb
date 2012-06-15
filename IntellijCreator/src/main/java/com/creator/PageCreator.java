package com.creator;

import org.apache.velocity.VelocityContext;

import java.io.File;

/**
 * Create Page
 */
public abstract class PageCreator extends BaseCreator {
    /**
     * @parameter expression="${dir}" default-value="${view.dir}"
     * @required
     */
    private String dir;

    @Override
    public String getDir() {
        return dir;
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
    }
}
