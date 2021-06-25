package org.muchu.mybatis.support.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.service.FindRelatedItemService;
import org.muchu.mybatis.support.service.factory.MyFindRelatedItemServiceFactory;
import org.muchu.mybatis.support.util.PsiElementUtil;

import java.util.List;

public class ParamAnnotator implements Annotator {

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (!(element instanceof PsiIdentifier)) {
      return;
    }
    if (!(element.getParent() instanceof PsiParameter)) {
      return;
    }
    PsiParameter psiParameter = (PsiParameter) element.getParent();
    PsiClass parameterType = PsiTypesUtil.getPsiClass(psiParameter.getType());
    if (parameterType == null || PsiElementUtil.isMap(parameterType)) {
      return;
    }
    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    if (psiClass == null || !psiClass.isInterface()) {
      return;
    }
    FindRelatedItemService findRelatedItemService = MyFindRelatedItemServiceFactory.getFindRelatedItemService(psiClass.getNameIdentifier());
    List<PsiElement> relatedItem = findRelatedItemService.findRelatedItem(psiClass.getNameIdentifier());
    if (CollectionUtils.isEmpty(relatedItem)) {
      return;
    }
    PsiAnnotation annotation = psiParameter.getAnnotation("org.apache.ibatis.annotations.Param");
    if (annotation == null) {
      holder.newAnnotation(HighlightSeverity.WARNING, "Cannot find param annotation").create();
    }
  }
}
