package org.muchu.mybatis.support.icon;

import com.intellij.icons.AllIcons;
import com.intellij.ui.IconManager;

import javax.swing.*;

/**
 * @author heber
 */
public class MybatisIcon {

  private static final IconManager iconManager = IconManager.getInstance();

  public static final Icon NAVIGATE_TO_XML = AllIcons.Gutter.ImplementedMethod;

  public static final Icon NAVIGATE_TO_JAVA = AllIcons.Gutter.ImplementingMethod;

  public static final Icon MYBATIS_ICON = iconManager.getIcon("/images/mybatis.png", MybatisIcon.class.getClassLoader());

}
