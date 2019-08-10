package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MybatisIcon;
import org.muchu.mybatis.support.util.MyXmlUtil;

import java.util.Collection;

/**
 * @author heber
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        DomElement domElement = MyXmlUtil.process(element);
        if (domElement != null && domElement.getXmlElement() != null) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(MybatisIcon.NAVIGATE_TO_XML)
                            .setAlignment(GutterIconRenderer.Alignment.CENTER)
                            .setTarget(domElement.getXmlElement().findElementAt(0))
                            .setTooltipTitle("Navigation to target in mapper xml");
            if (element instanceof PsiNameIdentifierOwner) {
                PsiNameIdentifierOwner psiNameIdentifierOwner = (PsiNameIdentifierOwner) element;
                if (psiNameIdentifierOwner.getNameIdentifier() != null) {
                    result.add(builder.createLineMarkerInfo(psiNameIdentifierOwner.getNameIdentifier()));
                }
            }
        }
    }
}
