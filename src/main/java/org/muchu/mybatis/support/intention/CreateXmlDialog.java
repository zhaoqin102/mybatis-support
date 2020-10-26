package org.muchu.mybatis.support.intention;

import com.intellij.CommonBundle;
import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.ide.util.DirectoryUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.refactoring.util.RefactoringMessageUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.ui.DirectoryEditorComboWithBrowseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CreateXmlDialog extends DialogWrapper {
    private final JLabel myInformationLabel = new JLabel(CodeInsightBundle.message("dialog.create.class.label", "xml"));
    private final DirectoryEditorComboWithBrowseButton myDirectoryComponent;
    private final JLabel myPackageLabel = new JLabel("Destination folder");
    private final JTextField myTfClassName = new MyTextField();
    private final Project myProject;
    private PsiDirectory myTargetDirectory;
    private final String myXmlName;
    @NonNls
    private static final String RECENTS_KEY = "CreateXmlDialog.RecentsKey";

    public CreateXmlDialog(@NotNull Project project,
                           @NotNull String title,
                           @NotNull String targetXmlName,
                           @Nullable PsiDirectory psiDirectory) {
        super(project, true);
        myXmlName = targetXmlName;
        myProject = project;
        myDirectoryComponent = new DirectoryEditorComboWithBrowseButton(null, psiDirectory, project, RECENTS_KEY);
        myDirectoryComponent.addActionListener(e -> {
            FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
            FileChooser.chooseFile(fileChooserDescriptor, project, psiDirectory != null ? psiDirectory.getVirtualFile() : null, virtualFile -> {
                if (virtualFile.isDirectory()) {
                    myDirectoryComponent.setText(virtualFile.getCanonicalPath());
                    myTargetDirectory = PsiManager.getInstance(project).findDirectory(virtualFile);
                }
            });

        });
        myDirectoryComponent.setTextFieldPreferredWidth(40);
        init();
        setTitle(title);
        myTfClassName.setText(myXmlName);
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return myTfClassName;
    }

    @Override
    protected JComponent createCenterPanel() {
        return new JPanel(new BorderLayout());
    }

    @Override
    protected JComponent createNorthPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbConstraints = new GridBagConstraints();

        gbConstraints.insets = JBUI.insets(4, 8);
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.anchor = GridBagConstraints.WEST;

        gbConstraints.weightx = 0;
        gbConstraints.gridwidth = 1;
        panel.add(myInformationLabel, gbConstraints);
        gbConstraints.insets = JBUI.insets(4, 8);
        gbConstraints.gridx = 1;
        gbConstraints.weightx = 1;
        gbConstraints.gridwidth = 1;
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.anchor = GridBagConstraints.WEST;
        panel.add(myTfClassName, gbConstraints);

        getOKAction().setEnabled(StringUtil.isNotEmpty(myXmlName));

        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        gbConstraints.weightx = 0;
        gbConstraints.gridwidth = 1;
        panel.add(myPackageLabel, gbConstraints);

        gbConstraints.gridx = 1;
        gbConstraints.weightx = 1;

        new AnAction() {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                myDirectoryComponent.getButton().doClick();
            }
        }.registerCustomShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK)), myDirectoryComponent.getChildComponent());

        JPanel _panel = new JPanel(new BorderLayout());
        _panel.add(myDirectoryComponent, BorderLayout.CENTER);
        panel.add(_panel, gbConstraints);
        return panel;
    }

    public PsiDirectory getTargetDirectory() {
        return myTargetDirectory;
    }

    private static class MyTextField extends JTextField {
        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            FontMetrics fontMetrics = getFontMetrics(getFont());
            size.width = fontMetrics.charWidth('a') * 40;
            return size;
        }
    }

    @Override
    protected void doOKAction() {
        final String[] errorString = new String[1];
        CommandProcessor.getInstance().executeCommand(myProject, () -> {
            try {
                ApplicationManager.getApplication().runWriteAction(() -> {
                    if (myTargetDirectory == null) {
                        myTargetDirectory = DirectoryUtil.mkdirs(PsiManager.getInstance(myProject), myDirectoryComponent.getText());
                        errorString[0] = RefactoringMessageUtil.checkCanCreateFile(myTargetDirectory, getXmlName() + ".xml");
                    }
                });
            } catch (IncorrectOperationException e) {
                errorString[0] = e.getMessage();
            }
        }, CodeInsightBundle.message("create.directory.command"), null);
        if (errorString[0] != null) {
            if (errorString[0].length() > 0) {
                Messages.showMessageDialog(myProject, errorString[0], CommonBundle.getErrorTitle(), Messages.getErrorIcon());
            }
            return;
        }
        super.doOKAction();
    }

    @NotNull
    public String getXmlName() {
        return myTfClassName.getText();
    }

}
