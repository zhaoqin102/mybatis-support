package org.muchu.mybatis.support.constant;

import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

public enum MyBatisSQLAttrTag {

    ID("id"), RESULT_TYPE("resultType");

    private String value;

    private static PsiType listType;

    MyBatisSQLAttrTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getAttrValue(PsiMethod psiMethod) {
        if (listType == null) {
            listType = PsiClassType.getTypeByName("java.util.List", psiMethod.getProject(), GlobalSearchScope.allScope(psiMethod.getProject()));
        }
        switch (this) {
            case ID:
                return psiMethod.getName();
            case RESULT_TYPE:
                if (psiMethod.getReturnType() != null && !PsiType.VOID.equals(psiMethod.getReturnType())) {
                    PsiType returnType = psiMethod.getReturnType();
                    if (returnType instanceof PsiClassType) {
                        PsiClassType returnClassType = ((PsiClassType) returnType);
                        if (returnClassType.isAssignableFrom(listType)) {
                            if (returnClassType.getParameterCount() > 0) {
                                PsiType[] parameters = returnClassType.getParameters();
                                return parameters[0].getCanonicalText();
                            } else {
                                return returnType.getCanonicalText();
                            }
                        } else {
                            return returnType.getCanonicalText();
                        }
                    } else if (returnType instanceof PsiArrayType) {
                        return ((PsiArrayType) returnType).getComponentType().getCanonicalText();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
