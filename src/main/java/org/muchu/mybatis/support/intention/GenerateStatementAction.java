package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.Mapper;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.constant.MyBatisSQLTag;
import org.muchu.mybatis.support.service.MyDomService;

public class GenerateStatementAction implements IntentionAction {
    private String name = "Create statement";

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return name;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return name;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        if (file instanceof PsiJavaFile) {
            CaretModel caretModel = editor.getCaretModel();
            int position = caretModel.getOffset();
            PsiElement element = file.findElementAt(position);
            if (element == null) {
                return false;
            }
            if (!(element.getContext() instanceof PsiMethod)) {
                return false;
            }
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (psiClass == null || !psiClass.isInterface()) {
                return false;
            }
            PsiMethod psiMethod = (PsiMethod) element.getContext();
            Statement statement = MyDomService.getInstance().getStatement(psiMethod, GlobalSearchScope.allScope(project));
            if (statement != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        CaretModel caretModel = editor.getCaretModel();
        int position = caretModel.getOffset();
        PsiElement element = file.findElementAt(position);
        if (element == null) {
            return;
        }
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (psiClass == null) {
            return;
        }
        Mapper mapper = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
        if (mapper == null) {
            HintManager.getInstance().showErrorHint(editor, "Please create mapper");
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
                    generateStatement(selectedValue, psiMethod, mapper.getXmlTag(), project);
                    return super.onChosen(selectedValue, finalChoice);
                }
            });
            listPopup.showInBestPositionFor(editor);
        }
    }

    private void generateStatement(MyBatisSQLTag myBatisSQLTag, PsiMethod psiMethod, XmlTag parent, Project project) {
        XmlTag childTag = myBatisSQLTag.createMyBatisTag(parent, psiMethod);
        WriteCommandAction.runWriteCommandAction(project, "create statement", null, () -> {
            parent.add(childTag);
            CodeStyleManager formatter = CodeStyleManager.getInstance(project);
            formatter.reformat(parent.getContainingFile());
        }, parent.getContainingFile());
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
