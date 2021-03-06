package org.muchu.mybatis.support.description;

import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.MyBatisDomConstants;
import org.muchu.mybatis.support.constant.MyBatisDoctype;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.icon.MybatisIcon;

import javax.swing.*;

public class MapperDescription extends DomFileDescription<Mapper> {

  public MapperDescription() {
    super(Mapper.class, Mapper.TAG_NAME);
  }

  @Nullable
  @Override
  public Icon getFileIcon(int flags) {
    return MybatisIcon.MYBATIS_ICON;
  }

  @Override
  protected void initializeFileDescription() {
    registerNamespacePolicy(MyBatisDomConstants.MYBATIS_NAMESPACE_KEY, MyBatisDoctype.MYBATIS_DTD);
  }
}