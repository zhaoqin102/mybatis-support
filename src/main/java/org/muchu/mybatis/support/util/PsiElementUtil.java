package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

public class PsiElementUtil {


    public static PsiClass getPsiClass(PsiElement element) {
        PsiElement parent = element.getParent();
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        } else {
            PsiMethod psiMethod = (PsiMethod) parent;
            return psiMethod.getContainingClass();
        }
    }

    public static boolean isMap(PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        Project project = psiClass.getProject();
        PsiClass mapClass = JavaPsiFacade.getInstance(project).findClass("java.util.Map", GlobalSearchScope.allScope(project));
        return mapClass != null && (mapClass == psiClass || psiClass.isInheritor(mapClass, true));
    }
}
