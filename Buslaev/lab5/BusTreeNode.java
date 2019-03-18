package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class BusTreeNode extends DefaultMutableTreeNode {

    private String nodeName;
    private BusNode nodeValue = null;
    private List<BusTreeNode> nodes = new ArrayList<>();
    private boolean LeafNode = false;

    BusTreeNode(String name) {
        nodeName = name;
    }

    BusTreeNode(String name, BusNode value) {
        nodeName = name;
        nodeValue = value;
        LeafNode = true;
    }

    void addNode(BusTreeNode node) {
        nodes.add(node);
    }

    void deleteNode(BusTreeNode node) {
        for (int i = 0; i < nodes.size(); ++i)
            if (nodes.get(i).toString().compareToIgnoreCase(node.toString()) == 0) {
                nodes.remove(i);
            }
    }

    List<BusNode> getAllLeaves() {
        List<BusNode> leaves = new ArrayList<>();
        Deque<BusTreeNode> deque = new ArrayDeque<>();

        deque.push(this);
        BusTreeNode temp;
        while (!deque.isEmpty()) {
            temp = deque.removeFirst();
            if (temp.LeafNode) {
                leaves.add(temp.getNodeValue());
            } else {
                for (int i = 0; i < temp.nodes.size(); ++i) {
                    deque.push(temp.nodes.get(i));
                }
            }
        }

        return leaves;
    }

    private BusNode getNodeValue() {
        return nodeValue;
    }

    @Override
    public String toString() {
        return nodeName;
    }
}
