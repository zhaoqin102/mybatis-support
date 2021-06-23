package org.muchu.mybatis.support.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;
import org.muchu.mybatis.support.util.MyJavaUtil;

public class MybatisCompletionContributor extends CompletionContributor {

  @Override
  public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
    PsiFile psiFile = parameters.getOriginalFile();
    if (!(psiFile instanceof XmlFile)) {
      return;
    }
    DomFileElement<Mapper> mapper = MyDomService.getInstance().getMapper((XmlFile) psiFile);
    if (mapper == null) {
      return;
    }
    PsiElement psiElement = parameters.getPosition();
    if (!(psiElement.getParent() instanceof XmlText)) {
      return;
    }
    XmlTag xmlTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
    if (xmlTag != null) {
      DomElement domElement = DomManager.getDomManager(xmlTag.getProject()).getDomElement(xmlTag);
      if (domElement instanceof Statement) {
        PsiClass psiClass = MyJavaUtil.findClass(mapper.getRootElement(), xmlTag.getProject());
        if (psiClass == null) {
          return;
        }
        Statement statement = (Statement) domElement;
        PsiMethod[] methodsByName = psiClass.findMethodsByName(statement.getId().getValue(), true);
        for (PsiMethod psiMethod : methodsByName) {
          PsiParameterList parameterList = psiMethod.getParameterList();
          if (parameterList.isEmpty()) {
            return;
          }
          for (int i = 0; i < parameterList.getParametersCount(); i++) {
            PsiParameter parameter = parameterList.getParameter(i);
            if (parameter != null) {
              PsiAnnotation annotation = parameter.getAnnotation("org.apache.ibatis.annotations.Param");
              String name = parameter.getName();
              if (annotation != null) {
                PsiAnnotationParameterList annotationParameterList = annotation.getParameterList();
                PsiNameValuePair[] attributes = annotationParameterList.getAttributes();
                for (PsiNameValuePair attribute : attributes) {
                  if (StringUtils.isNotBlank(attribute.getLiteralValue())) {
                    name = attribute.getLiteralValue();
                  }
                }
              }
              LookupElementBuilder builder =
                  LookupElementBuilder.create(parameter, name);
              result.addElement(builder);
            }
          }
        }
      }
    }
  }
}
