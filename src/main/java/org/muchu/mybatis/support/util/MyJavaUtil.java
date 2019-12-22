package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomManager;
import org.apache.commons.lang.StringUtils;
import org.muchu.mybatis.support.bean.CommonAttribute;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.constant.MyBatisTag;

import java.util.ArrayList;
import java.util.List;

public class MyJavaUtil {

    public static List<PsiIdentifier> process(Mapper mapper, XmlAttribute xmlAttribute) {
        List<PsiIdentifier> result = new ArrayList<>();
        if (mapper.getNameSpace() == null || StringUtils.isBlank(mapper.getNameSpace().getValue())) {
            return result;
        }
        Project project = xmlAttribute.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        PsiClass psiClass = javaPsiFacade.findClass(mapper.getNameSpace().getValue(), GlobalSearchScope.allScope(project));
        if (psiClass == null) {
            return result;
        }
        if (StringUtils.equals(xmlAttribute.getName(), MyBatisTag.MAPPER.getMajorAttribute())) {
            result.add(psiClass.getNameIdentifier());
            return result;
        }
        DomManager manager = DomManager.getDomManager(xmlAttribute.getProject());
        DomElement domElement = manager.getDomElement(xmlAttribute.getParent());
        if (!(domElement instanceof CommonAttribute)) {
            return result;
        }
        CommonAttribute commonAttribute = (CommonAttribute) domElement;
        PsiMethod[] psiMethods = psiClass.findMethodsByName(xmlAttribute.getValue(), false);
        for (PsiMethod psiMethod : psiMethods) {
            PsiType returnType = psiMethod.getReturnType();
            PsiParameterList parameterList = psiMethod.getParameterList();
            result.add(psiMethod.getNameIdentifier());
        }
        return result;
    }
}
