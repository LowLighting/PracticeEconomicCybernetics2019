package com.company;

import javax.swing.tree.DefaultTreeModel;

class CatalogTreeModel extends DefaultTreeModel {
    private static final long serialVersionUID = 1L;

    private BusTreeNode root;

    CatalogTreeModel(BusTreeNode root) {
        super(root);
        this.root = root;
    }

    @Override
    public BusTreeNode getRoot() {
        return root;
    }

    void insertNodeInto(BusTreeNode child, BusTreeNode parent, int i, boolean flag) {
        insertNodeInto(child, parent, i);
        parent.addNode(child);
    }
}
