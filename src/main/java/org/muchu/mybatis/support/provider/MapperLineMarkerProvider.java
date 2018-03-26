package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomFileIndex;
import com.intellij.util.xml.DomService;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MybatisIcon;
import org.muchu.mybatis.support.model.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author heber
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) element;
            if (psiClass.isInterface()) {
                Project project = element.getProject();
                Processor<DomFileElement<Mapper>> processor = new CommonProcessors.FindProcessor<DomFileElement<Mapper>>() {
                    @Override
                    protected boolean accept(DomFileElement<Mapper> domFileElement) {
                        Mapper mapper = domFileElement.getRootElement();
                        System.out.println(psiClass.getQualifiedName());
                        System.out.println(mapper.getNamespace().getRawText());
                        if (Objects.equals(psiClass.getQualifiedName(), mapper.getNamespace().getRawText())) {
                            return true;
                        }
                        return false;
                    }
                };
                List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, project, GlobalSearchScope.allScope(project));

                for (DomFileElement<Mapper> fileElement : fileElements) {
                    boolean process = processor.process(fileElement);
                    if (process) {
                        System.out.println("find mapper");
                        NavigationGutterIconBuilder<PsiElement> builder =
                                NavigationGutterIconBuilder.create(MybatisIcon.navigateToDeclarationIcon)
                                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                                        .setTarget(fileElement.getXmlTag())
                                        .setTooltipTitle("Navigation to target in mapper xml");
                        result.add(builder.createLineMarkerInfo(Objects.requireNonNull(((PsiNameIdentifierOwner) element).getNameIdentifier())));
                    }
                }
            }
        }
    }
}
