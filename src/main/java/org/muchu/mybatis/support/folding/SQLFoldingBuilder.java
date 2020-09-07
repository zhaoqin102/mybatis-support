package org.muchu.mybatis.support.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.model.Include;
import org.muchu.mybatis.support.dom.MyBatisRoot;
import org.muchu.mybatis.support.dom.model.Sql;
import org.muchu.mybatis.support.dom.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class SQLFoldingBuilder extends FoldingBuilderEx {


    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof XmlFile)) {
            return FoldingDescriptor.EMPTY;
        }
        XmlFile xmlFile = (XmlFile) root;
        DomFileElement<MyBatisRoot> domFileElement = DomManager.getDomManager(root.getProject()).getFileElement(xmlFile, MyBatisRoot.class);
        if (domFileElement == null) {
            return FoldingDescriptor.EMPTY;
        }
        MyBatisRoot myBatisRoot = domFileElement.getRootElement();
        FoldingGroup foldingGroup = FoldingGroup.newGroup("mybatis");
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        List<Statement> statements = myBatisRoot.getStatements();
        for (Statement statement : statements) {
            List<Include> includes = statement.getIncludes();
            for (Include include : includes) {
                if (include.getRefId() == null || include.getXmlElement() == null || include.getXmlElement().getNode() == null) {
                    continue;
                }
                List<Sql> sqlList = myBatisRoot.getSQL();
                for (Sql sql : sqlList) {
                    if (sql.getId() == null || StringUtils.isBlank(sql.getId().getStringValue())) {
                        continue;
                    }
                    if (StringUtils.equals(sql.getId().getStringValue(), include.getRefId().getStringValue())) {
                        FoldingDescriptor foldingDescriptor = new FoldingDescriptor(include.getXmlElement().getNode(), include.getXmlElement().getTextRange());
                        String retTxt = "...";
                        if (StringUtils.isNotBlank(sql.getValue())) {
                            retTxt = sql.getValue().replace("\n", "\\\\n");
                        }
                        if (retTxt.length() > include.getXmlElement().getTextLength()) {
                            retTxt = retTxt.substring(0, include.getXmlElement().getTextLength()) + "...";
                        }
                        foldingDescriptor.setPlaceholderText(retTxt);
                        descriptors.add(foldingDescriptor);
                        break;
                    }
                }
            }
        }
        return descriptors.toArray(FoldingDescriptor.EMPTY);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        if (!(psi instanceof XmlTag)) {
            return null;
        }
        XmlTag xmlTag = (XmlTag) psi;
        DomElement domElement = DomManager.getDomManager(xmlTag.getProject()).getDomElement(xmlTag);
        if (!(domElement instanceof Include)) {
            return null;
        }
        Include include = (Include) domElement;
        if (include.getRefId() == null || StringUtils.isBlank(include.getRefId().getStringValue())) {
            return null;
        }
        MyBatisRoot myBatisRoot = include.getParentOfType(MyBatisRoot.class, false);
        if (myBatisRoot == null) {
            return null;
        }
        List<Sql> sqlList = myBatisRoot.getSQL();
        for (Sql sql : sqlList) {
            if (sql.getId() == null || StringUtils.isBlank(sql.getId().getStringValue())) {
                continue;
            }
            if (StringUtils.equals(sql.getId().getStringValue(), include.getRefId().getStringValue())) {
                return sql.getValue() == null ? "..." : sql.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}
