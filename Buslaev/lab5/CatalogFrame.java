package com.company;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import java.util.List;

public class CatalogFrame extends JFrame {

    static BusNode addResult = null;
    private JTable infoPanel;
    private JTree tree;
    private CatalogTableModel tableModel;
    private CatalogTreeModel treeModel;

    private CatalogFrame() {
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu2 = new JMenu();
        JMenuItem jMenuItem1 = new JMenuItem();
        JMenuItem jMenuItem2 = new JMenuItem();
        jMenu2.setText("Меню");
        jMenuItem1.setText("Add");
        jMenuItem2.setText("Delete");
        jMenuItem1.addActionListener(e -> openAddDialog());
        jMenuItem2.addActionListener(e -> removeItem());
        jMenu2.add(jMenuItem1);
        jMenu2.add(jMenuItem2);
        jMenuBar1.add(jMenu2);
        setJMenuBar(jMenuBar1);
        tableModel = new CatalogTableModel();
        infoPanel = new JTable(tableModel);
        treeModel = new CatalogTreeModel(new BusTreeNode("Timetable"));
        tree = new JTree(treeModel);
        tree.addTreeSelectionListener(e -> {
            BusTreeNode node = (BusTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            List<BusNode> busNodes = node.getAllLeaves();
            tableModel = new CatalogTableModel(busNodes);
            infoPanel.setModel(tableModel);
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true,
                new JScrollPane(tree),
                new JScrollPane(infoPanel));
        splitPane.setDividerLocation(250);
        getContentPane().add(splitPane);
        setBounds(100, 100, 640, 480);
        setTitle("Bus station");
        setVisible(true);
        BusNode[] nodes = new BusNode[]{
                new BusNode("658", "Витебск - Минск", "12:00", "5a"),
                new BusNode("43", "Санкт-Петербург - Минск", "05:00", "7"),
                new BusNode("333", "Витебск - Москва", "14:10", "8"),
                new BusNode("522", "Брест - Львов", "17:00", "1"),
                new BusNode("56", "Минск - Борисов", "14:00", "1"),
                new BusNode("323", "Витебск - Минск", "19:00", "7"),
                new BusNode("322", "Минск - Червень", "11:00", "8")};
        for (BusNode n : nodes) {
            BusTreeNode where, insert, root = treeModel.getRoot();
            try {
                insert = new BusTreeNode(n.getTime(), n);
                if ((where = findNode(root, n.getRoute())) != null) {
                    treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
                } else {
                    treeModel.insertNodeInto(new BusTreeNode(n.getRoute()),
                            root,
                            root.getChildCount(),
                            false);
                    where = findNode(root, n.getRoute());
                    assert where != null;
                    treeModel.insertNodeInto(insert, where, where.getChildCount(), false);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    private void openAddDialog() {
        Dialog addForm = new Dialog(this);
        addForm.setVisible(true);
    }

    void addNewItem() {
        BusTreeNode node, insert, root = treeModel.getRoot();

        if (addResult != null) {
            try {
                insert = new BusTreeNode(addResult.getTime(), addResult);
                if ((node = findNode(root, addResult.getRoute())) != null) {
                    treeModel.insertNodeInto(insert, node, node.getChildCount(), true);
                } else {
                    treeModel.insertNodeInto(new BusTreeNode(addResult.getRoute()),
                            root,
                            root.getChildCount(),
                            false);
                    node = findNode(root, addResult.getRoute());
                    assert node != null;
                    treeModel.insertNodeInto(insert, node, node.getChildCount(), true);
                }
            } catch (Exception e) {
                addResult = null;
                return;
            }
        }
        addResult = null;
    }

    private void removeItem() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            BusTreeNode currentNode = (BusTreeNode) (currentSelection.getLastPathComponent());
            BusTreeNode parent = (BusTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                parent.deleteNode(currentNode);
                List<BusNode> busNodes = parent.getAllLeaves();
                tableModel = new CatalogTableModel(busNodes);
                infoPanel.setModel(tableModel);
            }
        }
    }

    private BusTreeNode findNode(BusTreeNode root, String i) {
        Enumeration e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            BusTreeNode node = (BusTreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(i)) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CatalogFrame frame = new CatalogFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
