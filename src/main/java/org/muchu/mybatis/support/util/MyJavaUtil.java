package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;

import java.util.ArrayList;
import java.util.List;

public class MyJavaUtil {

    public static List<PsiIdentifier> process(XmlAttribute xmlAttribute, XmlTag parent) {
        List<PsiIdentifier> result = new ArrayList<>();
        if (xmlAttribute.getValue() != null)
            if ("namespace".equals(xmlAttribute.getName())) {
                Project project = xmlAttribute.getProject();
                JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
                PsiClass psiClass = javaPsiFacade.findClass(xmlAttribute.getValue(), GlobalSearchScope.allScope(project));
                if (psiClass != null) {
                    result.add(psiClass.getNameIdentifier());
                }
            } else {

            }
        return result;
    }
}
