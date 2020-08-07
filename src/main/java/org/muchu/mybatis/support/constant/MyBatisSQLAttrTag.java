package org.muchu.mybatis.support.constant;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.Optional;

import static com.intellij.psi.CommonClassNames.JAVA_UTIL_COLLECTION;
import static com.intellij.psi.CommonClassNames.JAVA_UTIL_MAP;

public enum MyBatisSQLAttrTag {

    ID("id"), RESULT_TYPE("resultType");

    private String value;

    MyBatisSQLAttrTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getAttrValue(PsiMethod psiMethod) {
        Project project = psiMethod.getProject();
        PsiType collectionType = Optional.ofNullable(JavaPsiFacade.getInstance(project).findClass(JAVA_UTIL_COLLECTION, GlobalSearchScope.allScope(project)))
                .map(psiClass -> PsiElementFactory.getInstance(project).createType(psiClass))
                .orElse(null);

        PsiType mapType = Optional.ofNullable(JavaPsiFacade.getInstance(project).findClass(JAVA_UTIL_MAP, GlobalSearchScope.allScope(project)))
                .map(psiClass -> PsiElementFactory.getInstance(project).createType(psiClass))
                .orElse(null);
        switch (this) {
            case ID:
                return psiMethod.getName();
            case RESULT_TYPE:
                PsiType returnType = psiMethod.getReturnType();
                if (returnType == null) {
                    return null;
                }
                if (returnType instanceof PsiClassType) {
                    PsiClassType psiClassType = ((PsiClassType) returnType);
                    if (collectionType != null && collectionType.isAssignableFrom(returnType) && psiClassType.getParameterCount() == 1) {
                        return psiClassType.getParameters()[0].getCanonicalText();
                    } else if (mapType != null && mapType.isAssignableFrom(returnType)) {
                        PsiClass resolve = psiClassType.resolve();
                        if (resolve != null) {
                            return resolve.getQualifiedName();
                        }
                    } else {
                        return psiClassType.getCanonicalText();
                    }
                } else if (returnType instanceof PsiArrayType) {
                    PsiArrayType psiArrayType = (PsiArrayType) returnType;
                    return psiArrayType.getComponentType().getCanonicalText();
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
