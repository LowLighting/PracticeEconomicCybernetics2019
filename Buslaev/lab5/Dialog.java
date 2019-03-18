package com.company;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Dialog extends JFrame {

    private CatalogFrame frame;
    private JTextField numberTextField, routeTextField, departTextField, platformTextField;
    private JButton AddButton;

    Dialog(CatalogFrame temp) {
        frame = temp;
        initComponents();
        setTitle("Add bus");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {
        JLabel numberLabel = new JLabel();
        JLabel routeLabel = new JLabel();
        JLabel departLabel = new JLabel();
        JLabel platformLabel = new JLabel();
        numberTextField = new JTextField();
        routeTextField = new JTextField();
        departTextField = new JTextField();
        platformTextField = new JTextField();
        AddButton = new JButton();
        JButton cancelButton = new JButton();
        numberLabel.setText("Number:");
        routeLabel.setText("Route:");
        departLabel.setText("Time:");
        platformLabel.setText("Platform:");
        routeTextField.addCaretListener(this::routeTextFieldCaretUpdate);
        departTextField.addCaretListener(this::departTextFieldCaretUpdate);
        AddButton.setText("Add");
        AddButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                AddButtonMouseClicked(evt);
            }
        });
        cancelButton.setText("Cancel");
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(numberLabel)
                                                        .addComponent(routeLabel)
                                                        .addComponent(departLabel)
                                                        .addComponent(platformLabel))
                                                .addGap(28, 28, 28)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(routeTextField, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(departTextField, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(platformTextField, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(AddButton)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(cancelButton))
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18))))
                                                .addGap(0, 4, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(numberLabel)
                                        .addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(routeLabel)
                                        .addComponent(routeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(departLabel)
                                        .addComponent(departTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(platformLabel)
                                        .addComponent(platformTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddButton)
                                        .addComponent(cancelButton))
                                .addContainerGap())
        );
        pack();
    }

    private void routeTextFieldCaretUpdate(CaretEvent evt) {
        checkOkClose();
    }

    private void departTextFieldCaretUpdate(CaretEvent evt) {
        checkOkClose();
    }

    private void cancelButtonMouseClicked(MouseEvent evt) {
        dispose();
    }

    private void AddButtonMouseClicked(MouseEvent evt) {
        if (AddButton.isEnabled()) {
            CatalogFrame.addResult = new BusNode(numberTextField.getText(),
                                                    routeTextField.getText(),
                                                    departTextField.getText(),
                                                    platformTextField.getText()
                                                            .isEmpty()
                                                            ? "-1" : this.platformTextField.getText());
            frame.addNewItem();
            dispose();
        }
    }

    private void checkOkClose() {
        if (!numberTextField.getText().isEmpty() &&
                !routeTextField.getText().isEmpty() &&
                !departTextField.getText().isEmpty()) {
            AddButton.setEnabled(true);
        }
    }
}
