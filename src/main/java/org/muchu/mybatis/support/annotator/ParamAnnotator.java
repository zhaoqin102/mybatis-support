package org.muchu.mybatis.support.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.util.PsiElementUtil;

public class ParamAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiParameter) {
            PsiParameter psiParameter = (PsiParameter) element;
            if (psiParameter.getParent() == null || psiParameter.getIdentifyingElement() == null
                    || psiParameter.getParent().getParent() == null) {
                return;
            }
            PsiClass psiClass = PsiTypesUtil.getPsiClass(psiParameter.getType());
            if (psiClass == null || PsiElementUtil.isMap(psiClass)) {
                return;
            }
            PsiMethod psiMethod = (PsiMethod) psiParameter.getParent().getParent();
            if (psiMethod.getContainingClass() == null || !psiMethod.getContainingClass().isInterface()) {
                return;
            }
            PsiAnnotation annotation = psiParameter.getAnnotation("org.apache.ibatis.annotations.Param");
            TextRange textRange = psiParameter.getIdentifyingElement().getTextRange();
            if (annotation == null && textRange != null) {
                TextRange range = new TextRange(textRange.getStartOffset(),
                        textRange.getEndOffset());
                holder.createWarningAnnotation(range, "con't find param annotation");
            }
        }
    }
}