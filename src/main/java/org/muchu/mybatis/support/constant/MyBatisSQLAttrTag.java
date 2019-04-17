package org.muchu.mybatis.support.constant;

import com.intellij.psi.PsiMethod;

public enum MyBatisSQLAttrTag {

    ID("id"), RETURN_TYPE("returnType");

    private String value;

    MyBatisSQLAttrTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getAttrValue(PsiMethod psiMethod) {
        switch (this) {
            case ID:
                return psiMethod.getName();
            case RETURN_TYPE:
                if (psiMethod.getReturnType() != null && !psiMethod.getReturnType().equalsToText("void")) {
                    return psiMethod.getReturnType().getCanonicalText();
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
