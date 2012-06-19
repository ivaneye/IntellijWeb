package com.creator;

/**
 * Create NewPage
 *
 * @goal newpage
 * @requiresProject true
 */
public class NewPageCreator extends PageCreator {

    @Override
    public String getTemplateName() {
        return "NewPageTemplate.vm";
    }

    @Override
    public String mergeNameForPage(String name) {
        String bName = name;
        if(bName.contains(".")){
            String[] t = bName.split("\\.");
            bName = t[t.length - 1];
        }
        setBeanName(bName);
        return name.toLowerCase() + ".new";
    }
}
