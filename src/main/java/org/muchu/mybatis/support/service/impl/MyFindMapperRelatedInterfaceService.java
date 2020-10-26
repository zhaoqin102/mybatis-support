package org.muchu.mybatis.support.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomManager;
import org.apache.commons.lang.StringUtils;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.util.MyJavaUtil;

import java.util.ArrayList;
import java.util.List;

public class MyFindMapperRelatedInterfaceService extends AbstractFindRelatedItemService {
    public static MyFindMapperRelatedInterfaceService INSTANCE = new MyFindMapperRelatedInterfaceService();

    private MyFindMapperRelatedInterfaceService() {
    }

    @Override
    public boolean isSupport(PsiElement psiElement) {
        if (!(psiElement instanceof XmlToken)) {
            return false;
        }
        XmlToken xmlToken = (XmlToken) psiElement;
        if (!StringUtils.equals(xmlToken.getText(), "mapper") || !(xmlToken.getParent() instanceof XmlTag)) {
            return false;
        }
        XmlToken prevSibling = (XmlToken) xmlToken.getPrevSibling();
        if (!(prevSibling.getTokenType() == XmlTokenType.XML_START_TAG_START)) {
            return false;
        }
        DomElement domElement = DomManager.getDomManager(xmlToken.getProject()).getDomElement((XmlTag) xmlToken.getParent());
        if (!(domElement instanceof Mapper)) {
            return false;
        }
        return true;
    }

    @Override
    public List<PsiElement> findRelatedItem(PsiElement psiElement) {
        List<PsiElement> result = new ArrayList<>();
        XmlToken xmlToken = (XmlToken) psiElement;
        Project project = xmlToken.getProject();
        DomManager domManager = DomManager.getDomManager(project);
        Mapper mapper = (Mapper) domManager.getDomElement((XmlTag) xmlToken.getParent());
        PsiClass psiClass = MyJavaUtil.findClass(mapper, project);
        if (psiClass == null) {
            return result;
        }
        result.add(psiClass.getNameIdentifier());
        return result;
    }
}
