package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DragAndDrop extends JComponent implements DragGestureListener, DragSourceListener, DropTargetListener, MouseListener, MouseMotionListener {
    private ArrayList<Cissoid> leafs = new ArrayList<Cissoid>();
    private Cissoid beingDragged;
    private boolean dragMode;
    private final int width=200;
    private final int height=200;
    private final int param=145;
    private static final int lineWidth = 3;
    private static final BasicStroke lineStyle = new BasicStroke(lineWidth);
    private static final Border normalBorder = new BevelBorder(BevelBorder.LOWERED);
    private static final Border dropBorder = new BevelBorder(BevelBorder.RAISED);

    private DragAndDrop() {
        setBorder(normalBorder);

        addMouseListener(this);
        addMouseMotionListener(this);

        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY_OR_MOVE, this);

        DropTarget dropTarget = new DropTarget(this, this);
        this.setDropTarget(dropTarget);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new MyStroke(1));

        for (Cissoid s : leafs) {
            g2.draw(s);
        }
        g2.setStroke(lineStyle);
    }

    private void setDragMode(boolean dragMode)
    {
        this.dragMode = dragMode;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e)
    {
        if (dragMode)
            return;
        Cissoid currentScribble = new Cissoid(e.getX(), e.getY(), e.getX(), e.getY(), width, height, param);
        leafs.add(currentScribble);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void dragGestureRecognized(DragGestureEvent e)
    {
        if (!dragMode)
            return;

        MouseEvent inputEvent = (MouseEvent) e.getTriggerEvent();
        int x = inputEvent.getX();
        int y = inputEvent.getY();

        Rectangle r = new Rectangle(x - lineWidth, y - lineWidth, lineWidth * 2, lineWidth * 2);
        for (Cissoid s : leafs) {
            if (s.intersects(r)) {
                beingDragged = s;

                Cissoid dragScribble = (Cissoid) s.clone();
                dragScribble.translate(-x, -y);

                Cursor cursor;
                switch (e.getDragAction()) {
                    case DnDConstants.ACTION_COPY:
                        cursor = DragSource.DefaultCopyDrop;
                        break;
                    case DnDConstants.ACTION_MOVE:
                        cursor = DragSource.DefaultMoveDrop;
                        break;
                    default:
                        return;
                }
                e.startDrag(cursor, dragScribble, this);
                return;
            }
        }
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {

    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragExit(DragSourceEvent dse) {

    }

    public void dragDropEnd(DragSourceDropEvent e)
    {
        if (!e.getDropSuccess())
            return;
        int action = e.getDropAction();
        if (action == DnDConstants.ACTION_MOVE)
        {
            leafs.remove(beingDragged);
            beingDragged = null;
            repaint();
        }
    }

    public void dragEnter(DropTargetDragEvent e)
    {
        if (e.isDataFlavorSupported(Cissoid.decDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            this.setBorder(dropBorder);
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    public void dragExit(DropTargetEvent e)
    {
        this.setBorder(normalBorder);
    }

    public void drop(DropTargetDropEvent e)
    {
        this.setBorder(normalBorder);

        if (e.isDataFlavorSupported(Cissoid.decDataFlavor) || e.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        } else
        {
            e.rejectDrop();
            return;
        }

        Transferable t = e.getTransferable();
        Cissoid droppedScribble;
        try {
            droppedScribble = (Cissoid) t.getTransferData(Cissoid.decDataFlavor);
        } catch (Exception ex) {
            try {
                String s = (String) t.getTransferData(DataFlavor.stringFlavor);
                droppedScribble = Cissoid.getFromString(s);
            } catch (Exception ex2) {
                e.dropComplete(false);
                return;
            }
        }

        Point p = e.getLocation();
        droppedScribble.translate(p.getX(), p.getY());
        leafs.add(droppedScribble);
        repaint();
        e.dropComplete(true);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("DragAndDrop");
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        final DragAndDrop scribblePane = new DragAndDrop();
        frame.getContentPane().add(scribblePane, BorderLayout.CENTER);
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu2 = new JMenu();
        jMenu2.setText("Меню");
        JMenuItem jMenuItem1 = new JMenuItem();
        JMenuItem jMenuItem2 = new JMenuItem();
        jMenuItem1.setText("Draw");
        jMenuItem2.setText("Drag");
        jMenu2.add(jMenuItem1);
        jMenu2.add(jMenuItem2);
        jMenuBar1.add(jMenu2);
        frame.setJMenuBar(jMenuBar1);
        jMenuItem1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                scribblePane.setDragMode(false);
            }
        });
        jMenuItem2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                scribblePane.setDragMode(true);
            }
        });
        scribblePane.setDragMode(false);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
