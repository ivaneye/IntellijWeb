package com.creator;

/**
 * Create Service
 *
 * @execute phase="compile"
 * @goal service
 * @requiresProject true
 * @requiresDependencyResolution compile+runtime
 * @requiresDependencyCollection
 */
public class ServiceCreator extends BaseCreator {

    /**
     * @parameter expression="${dir}" default-value="${service.dir}"
     *
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
        return "Service";
    }

    @Override
    public String getFileType() {
        return "java";
    }

    @Override
    public String getTemplateName() {
        return "ServiceTemplate.vm";
    }
}
