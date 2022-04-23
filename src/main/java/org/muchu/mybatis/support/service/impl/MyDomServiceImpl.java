package org.muchu.mybatis.support.service.impl;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.DomService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.model.Foreach;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;
import org.muchu.mybatis.support.util.MyJavaUtil;
import org.muchu.mybatis.support.util.PsiElementUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyDomServiceImpl implements MyDomService {

  @Override
  public @NotNull List<Mapper> getMapper(@NotNull PsiClass psiClass, @Nullable GlobalSearchScope scope) {
    if (!psiClass.isInterface()) {
      return new ArrayList<>();
    }
    Project project = psiClass.getProject();
    List<DomFileElement<Mapper>> mappers = DomService.getInstance().getFileElements(Mapper.class, project, scope);
    return mappers.stream()
        .map(DomFileElement::getRootElement)
        .filter(mapper ->
            StringUtils.equals(psiClass.getQualifiedName(),
                mapper.getNamespace().getValue()))
        .collect(Collectors.toList());
  }

  @Nullable
  @Override
  public Statement getStatement(@NotNull PsiMethod psiMethod, @Nullable GlobalSearchScope scope) {
    PsiClass psiClass = psiMethod.getContainingClass();
    if (psiClass == null) {
      return null;
    }
    List<Mapper> mappers = getMapper(psiClass, scope);
    return mappers.stream().map(Mapper::getStatements).flatMap(Collection::stream)
        .filter(statement -> StringUtils.equals(psiMethod.getName(), statement.getId().getValue()))
        .findFirst().orElse(null);
  }

  @Override
  public @Nullable DomFileElement<Mapper> getMapper(XmlFile xmlFile) {
    return DomManager.getDomManager(xmlFile.getProject()).getFileElement(xmlFile, Mapper.class);
  }

  @Override
  public @NotNull List<LookupElementBuilder> getLookupElement(PsiElement psiElement) {
    List<LookupElementBuilder> lookupElementBuilders = new ArrayList<>();
    DomFileElement<Mapper> domFileElement = DomManager.getDomManager(psiElement.getProject())
        .getFileElement((XmlFile) psiElement.getContainingFile(), Mapper.class);
    Mapper mapper = domFileElement.getRootElement();
    XmlTag xmlTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
    if (xmlTag == null) {
      return lookupElementBuilders;
    }
    PsiClass psiClass = MyJavaUtil.findClass(mapper, xmlTag.getProject());
    if (psiClass == null) {
      return lookupElementBuilders;
    }
    DomElement domElement = DomManager.getDomManager(xmlTag.getProject()).getDomElement(xmlTag);
    if (domElement instanceof Statement) {
      List<LookupElementBuilder> builders = findLookupElement(psiClass, (Statement) domElement);
      lookupElementBuilders.addAll(builders);
    } else if (domElement instanceof Foreach) {
      List<LookupElementBuilder> builders = findLookupElement((Foreach) domElement);
      lookupElementBuilders.addAll(builders);
    }
    return lookupElementBuilders;
  }

  List<LookupElementBuilder> findLookupElement(PsiClass psiClass, Statement statement) {
    List<LookupElementBuilder> lookupElementBuilders = new ArrayList<>();
    PsiMethod[] methodsByName = psiClass.findMethodsByName(statement.getId().getValue(), true);
    for (PsiMethod psiMethod : methodsByName) {
      PsiParameterList parameterList = psiMethod.getParameterList();
      if (parameterList.isEmpty()) {
        continue;
      }
      for (int i = 0; i < parameterList.getParametersCount(); i++) {
        PsiParameter parameter = parameterList.getParameter(i);
        if (parameter != null) {
          PsiClass parameterType = PsiTypesUtil.getPsiClass(parameter.getType());
          if (PsiElementUtil.isMap(parameterType)) {
            continue;
          }
          String name = getName(parameter);
          LookupElementBuilder builder =
              LookupElementBuilder.create(parameter, name);
          lookupElementBuilders.add(builder);
        }
      }
    }
    return lookupElementBuilders;
  }

  List<LookupElementBuilder> findLookupElement(Foreach foreach) {
    List<LookupElementBuilder> lookupElementBuilders = new ArrayList<>();
    String collectionName = foreach.getCollection().getStringValue();
    if (StringUtils.isBlank(collectionName)) {
      return lookupElementBuilders;
    }
    if (StringUtils.isBlank(foreach.getItem().getStringValue())) {
      return lookupElementBuilders;
    }
    LookupElementBuilder builder =
        LookupElementBuilder.create(foreach.getItem(), foreach.getItem().getStringValue());
    lookupElementBuilders.add(builder);
    return lookupElementBuilders;
  }

  public String getName(PsiParameter parameter) {
    PsiAnnotation annotation = parameter.getAnnotation("org.apache.ibatis.annotations.Param");
    if (annotation == null) {
      return parameter.getName();
    }
    PsiAnnotationParameterList annotationParameterList = annotation.getParameterList();
    PsiNameValuePair[] attributes = annotationParameterList.getAttributes();
    for (PsiNameValuePair attribute : attributes) {
      if (StringUtils.isNotBlank(attribute.getLiteralValue())) {
        return attribute.getLiteralValue();
      }
    }
    return null;
  }
}
