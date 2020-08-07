package org.muchu.mybatis.support.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomManager;
import org.apache.commons.lang.StringUtils;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.bean.Statement;
import org.muchu.mybatis.support.constant.MyBatisSQLTag;
import org.muchu.mybatis.support.util.MyJavaUtil;

import java.util.ArrayList;
import java.util.List;

public class MyFindStatementRelatedMethodService extends AbstractFindRelatedItemService {
    public static MyFindStatementRelatedMethodService INSTANCE = new MyFindStatementRelatedMethodService();

    private MyFindStatementRelatedMethodService() {
    }

    @Override
    public boolean isSupport(PsiElement psiElement) {
        if (!(psiElement instanceof XmlToken)) {
            return false;
        }
        XmlToken xmlToken = (XmlToken) psiElement;
        if (!MyBatisSQLTag.isCRUDStatement(xmlToken.getText()) || !(xmlToken.getParent() instanceof XmlTag)) {
            return false;
        }
        XmlToken prevSibling = (XmlToken) xmlToken.getPrevSibling();
        if (!(prevSibling.getTokenType() == XmlTokenType.XML_START_TAG_START)) {
            return false;
        }
        DomElement domElement = DomManager.getDomManager(xmlToken.getProject()).getDomElement((XmlTag) xmlToken.getParent());
        if (!(domElement instanceof Statement)) {
            return false;
        }
        return true;
    }

    @Override
    public List<PsiElement> findRelatedItem(PsiElement psiElement) {
        List<PsiElement> result = new ArrayList<>();
        XmlToken xmlToken = (XmlToken) psiElement;
        Project project = xmlToken.getProject();
        Statement statement = (Statement) DomManager.getDomManager(project).getDomElement((XmlTag) xmlToken.getParent());
        if (statement == null) {
            return result;
        }
        String valueOfId = statement.getId().getValue();
        if (StringUtils.isBlank(valueOfId)) {
            return result;
        }
        Mapper mapper = (Mapper) statement.getParent();
        PsiClass psiClass = MyJavaUtil.findClass(mapper, project);
        if (psiClass == null) {
            return result;
        }
        PsiMethod[] methodsByName = psiClass.findMethodsByName(valueOfId, true);
        for (PsiMethod psiMethod : methodsByName) {
            result.add(psiMethod.getNameIdentifier());
        }
        return result;
    }
}
