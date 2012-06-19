package com.creator;

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

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String getFileTypeName() {
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
