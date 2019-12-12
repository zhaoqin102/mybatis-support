package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.constant.MyBatisTag;
import org.muchu.mybatis.support.constant.MybatisIcon;
import org.muchu.mybatis.support.util.MyJavaUtil;

import java.util.Collection;
import java.util.List;

public class JavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (!(element instanceof XmlAttribute) || !(element.getContainingFile() instanceof XmlFile)) {
            return;
        }
        XmlAttribute xmlAttribute = (XmlAttribute) element;
        if (xmlAttribute.getParent() == null || xmlAttribute.getValueElement() == null
                || !MyBatisTag.isValue(xmlAttribute.getParent().getName(), xmlAttribute.getName())) {
            return;
        }
        DomManager manager = DomManager.getDomManager(xmlAttribute.getProject());
        DomFileElement<Mapper> fileElement = manager.getFileElement((XmlFile) xmlAttribute.getContainingFile(), Mapper.class);
        if (fileElement == null) {
            return;
        }
        List<PsiIdentifier> resultList = MyJavaUtil.process(fileElement.getRootElement(), xmlAttribute);
        if (resultList.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(MybatisIcon.NAVIGATE_TO_JAVA).
                            setTargets(resultList).
                            setTooltipText("Navigate to mybatis file");
            result.add(builder.createLineMarkerInfo(xmlAttribute.getValueElement().getFirstChild()));
        }
    }

}
