
package com.wakacommerce.core.web.catalog.taglib.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import com.wakacommerce.core.web.catalog.taglib.CategoryBreadCrumbTag;

import java.util.ArrayList;
import java.util.List;

public class CategoryBreadcrumbTei extends TagExtraInfo {

    @Override
    public VariableInfo[] getVariableInfo(TagData tagData) {
        List<VariableInfo> infos = new ArrayList<VariableInfo>(2);

        String variableName = tagData.getAttributeString("var");
        infos.add(new VariableInfo(variableName, String.class.getName(), true, VariableInfo.NESTED));

        variableName = tagData.getAttributeString("categoryId");

        if (variableName != null) {
            variableName = CategoryBreadCrumbTag.toVariableName(variableName);
            infos.add(new VariableInfo(variableName, String.class.getName(), true, VariableInfo.NESTED));
        }

        return infos.toArray(new VariableInfo[infos.size()]);
    }
}
