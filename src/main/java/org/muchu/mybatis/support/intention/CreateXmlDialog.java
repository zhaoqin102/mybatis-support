package org.muchu.mybatis.support.intention;

import com.intellij.CommonBundle;
import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.ide.util.DirectoryUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
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

public class CreateXmlDialog extends DialogWrapper {
  private static final Logger LOG = Logger.getInstance(CreateXmlDialog.class);
  private final JLabel myInformationLabel = new JLabel("create mapper");
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
    myDirectoryComponent.setTextFieldPreferredWidth(40);
    setTitle(title);
    init();
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
      if (!errorString[0].isEmpty()) {
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
