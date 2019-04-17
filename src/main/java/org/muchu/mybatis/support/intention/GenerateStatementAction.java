package org.muchu.mybatis.support.intention;

import com.intellij.CommonBundle;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.IPopupChooserBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MyBatisSQLTag;
import org.muchu.mybatis.support.util.MyJavaUtil;
import org.muchu.mybatis.support.util.MyXmlUtil;

import java.util.Arrays;
import java.util.List;

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
            PsiElement element = MyJavaUtil.getPsiElement(editor, file);
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (psiClass == null || !psiClass.isInterface()) {
                return false;
            }
            return element.getContext() instanceof PsiMethod && MyXmlUtil.process(element.getContext()) == null;
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        PsiElement element = MyJavaUtil.getPsiElement(editor, file);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        DomElement domElement = MyXmlUtil.process(psiClass);
        if (domElement == null) {
            Messages.showMessageDialog(project, "Please create mapper", CommonBundle.getErrorTitle(), Messages.getErrorIcon());
            return;
        }
        PsiElement context = element.getContext();
        XmlElement xmlElement = domElement.getXmlElement();
        if (xmlElement instanceof XmlTag && context instanceof PsiMethod) {
            List<MyBatisSQLTag> myBatisTags = Arrays.asList(MyBatisSQLTag.values());
            IPopupChooserBuilder<MyBatisSQLTag> popupChooserBuilder = JBPopupFactory.getInstance()
                    .createPopupChooserBuilder(myBatisTags);
            JBPopup popup = popupChooserBuilder.setItemChosenCallback(myBatisTag -> {
                PsiMethod psiMethod = (PsiMethod) context;
                XmlTag parent = (XmlTag) xmlElement;
                generateStatement(myBatisTag, psiMethod, parent, project);
            }).createPopup();
            popup.showInBestPositionFor(editor);
        }
    }

    private void generateStatement(MyBatisSQLTag myBatisSQLTag, PsiMethod psiMethod, XmlTag parent, Project project) {
        XmlTag childTag = myBatisSQLTag.createMyBatisTag(parent, psiMethod);
        WriteCommandAction.runWriteCommandAction(project, () -> {
            parent.add(childTag);
        });
        CodeStyleManager formatter = CodeStyleManager.getInstance(project);
        formatter.reformat(parent.getContainingFile());
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
