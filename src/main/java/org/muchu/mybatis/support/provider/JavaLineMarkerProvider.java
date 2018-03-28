package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MybatisIcon;
import org.muchu.mybatis.support.util.JavaUtils;

import java.util.Collection;

/**
 * @author heber
 */
public class JavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        PsiNameIdentifierOwner psiNameIdentifierOwner = JavaUtils.process(element);
        if (psiNameIdentifierOwner != null) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(MybatisIcon.navigateToJavaIcon)
                            .setAlignment(GutterIconRenderer.Alignment.CENTER)
                            .setTarget(psiNameIdentifierOwner.getNameIdentifier())
                            .setTooltipTitle("Navigation to target in java ");
            result.add(builder.createLineMarkerInfo(element));
        }
    }
}
