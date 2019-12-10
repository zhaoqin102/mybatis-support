package org.muchu.mybatis.support.util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

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
}
