package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.constant.MyBatisSQLTag;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Select;
import org.muchu.mybatis.support.service.MyDomService;

public class GenerateStatementAction extends PsiElementBaseIntentionAction {

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return "Create statement";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (element.getContainingFile() instanceof PsiJavaFile) {
            if (!(element.getContext() instanceof PsiMethod)) {
                return false;
            }
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (psiClass != null) {
                Mapper mapper = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
                return mapper != null;
            }
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (psiClass == null) {
            return;
        }
        PsiElement context = element.getContext();
        if (context instanceof PsiMethod) {
            ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<MyBatisSQLTag>(null, MyBatisSQLTag.values()) {

                @NotNull
                @Override
                public String getTextFor(MyBatisSQLTag value) {
                    return value.getValue() + " statement";
                }

                @Nullable
                @Override
                public PopupStep onChosen(MyBatisSQLTag selectedValue, boolean finalChoice) {
                    PsiMethod psiMethod = (PsiMethod) context;
                    Mapper mapper = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
                    generateStatement(selectedValue, psiMethod, mapper, project);
                    return super.onChosen(selectedValue, finalChoice);
                }
            });
            listPopup.showInBestPositionFor(editor);
        }
    }

    private void generateStatement(MyBatisSQLTag myBatisSQLTag, PsiMethod psiMethod, Mapper mapper, Project project) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            Select select = mapper.addSelect();
            select.getId().setStringValue(psiMethod.getName());
            select.setValue("\nselect * from \n");
//            XmlTag childTag = myBatisSQLTag.createMyBatisTag(mapper.getXmlTag(), psiMethod);
//            PsiElement psiElement = mapper.getXmlTag().add(childTag);
//            CodeStyleManager formatter = CodeStyleManager.getInstance(project);
//            formatter.reformat(mapper.getXmlTag().getContainingFile());
//            ASTNode node = psiElement.getNode();
//            ASTNode xmlName = node.findChildByType(XmlTokenType.XML_NAME);
//            if (xmlName != null) {
//                ((Navigatable) xmlName.getPsi().getNavigationElement()).navigate(true);
//                Editor selectedTextEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
//                if (selectedTextEditor != null) {
//                    selectedTextEditor.getCaretModel()
//                            .moveCaretRelatively(2, 0, false, false, false);
//                }
//            }
        });
    }

}
