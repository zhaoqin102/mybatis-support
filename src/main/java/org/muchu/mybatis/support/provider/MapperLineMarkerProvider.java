package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author heber
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        PsiElement parent = element.getParent();
        if (element instanceof PsiIdentifier && parent instanceof PsiMethod) {
        }
    }
}
