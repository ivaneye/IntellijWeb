/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.ibator.generator.ibatis3.xmlmapper.elements;

import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;

/**
 * 
 * @author Jeff Butler
 *
 */
public class DeleteByExampleElementGenerator extends AbstractXmlElementGenerator {

    public DeleteByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        String fqjt = introspectedTable.getExampleType();

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getDeleteByExampleStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "parameterType", fqjt)); //$NON-NLS-1$

        ibatorContext.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());

        if (ibatorContext.getPlugins().sqlMapDeleteByExampleElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
