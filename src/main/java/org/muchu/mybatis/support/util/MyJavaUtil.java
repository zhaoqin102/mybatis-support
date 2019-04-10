package org.muchu.mybatis.support.util;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.apache.commons.lang.StringUtils;
import org.muchu.mybatis.support.constant.MySQLAttrTag;
import org.muchu.mybatis.support.constant.MyTag;

import java.util.Objects;

/**
 * @author heber
 */
public class MyJavaUtil {

    public static PsiNameIdentifierOwner process(PsiElement element) {
        if (element instanceof XmlTag) {
            XmlTag xmlTag = (XmlTag) element;
            if (Objects.equals(MyTag.MAPPER.getValue(), xmlTag.getName())) {
                return processMapper(xmlTag);
            } else if (MyTag.isCRUDStatement(xmlTag.getName())) {
                return processCRUDStatement(xmlTag);
            }
        }
        return null;
    }

    public static PsiElement getPsiElement(Editor editor, PsiFile file) {
        CaretModel caretModel = editor.getCaretModel();
        int position = caretModel.getOffset();
        return file.findElementAt(position);
    }

    private static PsiClass processMapper(XmlTag xmlTag) {
        XmlAttribute namespace = xmlTag.getAttribute("namespace");
        if (namespace != null && StringUtils.isNotBlank(namespace.getValue())) {
            Project project = xmlTag.getProject();
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
            return javaPsiFacade.findClass(namespace.getValue(), GlobalSearchScope.allScope(project));
        }
        return null;
    }

    private static PsiMethod processCRUDStatement(XmlTag xmlTag) {
        XmlTag parentTag = xmlTag.getParentTag();
        XmlAttribute id = xmlTag.getAttribute(MySQLAttrTag.ID.getValue());
        if (parentTag != null && id != null && StringUtils.isNotBlank(id.getValue())) {
            PsiClass psiClass = processMapper(parentTag);
            if (psiClass != null) {
                PsiMethod[] methods = psiClass.getMethods();
                for (PsiMethod method : methods) {
                    if (Objects.equals(method.getName(), id.getValue())) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
}
