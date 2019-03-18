package com.company;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CatalogTableModel implements TableModel {
    private Set<TableModelListener> list = new HashSet<>();
    private List<BusNode> infoNodes = new ArrayList<>();

    private static final String[] columnNames = new String[]{"Number", "Route", "Time", "Platform"};
    private static final Class<?>[] columnTypes = new Class[]{String.class, String.class, String.class, String.class};

    CatalogTableModel() {
    }

    CatalogTableModel(List<BusNode> al) {
        setInfoNodes(al);
    }

    private void setInfoNodes(List<BusNode> busNodes) {
        infoNodes = busNodes;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        list.add(l);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return infoNodes.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BusNode busNode = infoNodes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return busNode.getNumber();
            case 1:
                return busNode.getRoute();
            case 2:
                return busNode.getTime();
            case 3:
                return busNode.getPlatform();
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        list.remove(l);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}
