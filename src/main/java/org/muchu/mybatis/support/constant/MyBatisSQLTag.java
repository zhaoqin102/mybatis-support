package org.muchu.mybatis.support.constant;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum MyBatisSQLTag {

    SELECT("select", "\nselect * from\n", MyBatisSQLAttrTag.ID, MyBatisSQLAttrTag.RESULT_TYPE),
    UPDATE("update", "\nupdate\n", MyBatisSQLAttrTag.ID, MyBatisSQLAttrTag.RESULT_TYPE),
    DELETE("delete", "\ndelete from\n", MyBatisSQLAttrTag.ID, MyBatisSQLAttrTag.RESULT_TYPE),
    INSERT("insert", "\ninsert into\n", MyBatisSQLAttrTag.ID, MyBatisSQLAttrTag.RESULT_TYPE);

    private String value;

    private List<MyBatisSQLAttrTag> attrTagList;

    private String bodyText;

    MyBatisSQLTag(String value, String bodyText, MyBatisSQLAttrTag... attrTagList) {
        this.value = value;
        this.bodyText = bodyText;
        this.attrTagList = Arrays.asList(attrTagList);
    }

    public String getValue() {
        return value;
    }

    public static boolean isCRUDStatement(String value) {
        if (Objects.equals(INSERT.getValue(), value)) {
            return true;
        }
        if (Objects.equals(SELECT.getValue(), value)) {
            return true;
        }
        if (Objects.equals(UPDATE.getValue(), value)) {
            return true;
        }
        return Objects.equals(DELETE.getValue(), value);
    }

    public XmlTag createMyBatisTag(XmlTag parent, PsiMethod psiMethod) {
        XmlTag childTag = parent.createChildTag(value, null, bodyText, false);
        setAttributes(childTag, psiMethod);
        return childTag;
    }

    private void setAttributes(XmlTag childTag, PsiMethod psiMethod) {
        for (MyBatisSQLAttrTag myBatisSQLAttrTag : attrTagList) {
            String attrValue = myBatisSQLAttrTag.getAttrValue(psiMethod);
            if (StringUtil.isNotEmpty(attrValue)) {
                childTag.setAttribute(myBatisSQLAttrTag.getValue(), attrValue);
            }
        }
    }
}
