/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author fpm.dmitroviIO
 */

public class VertLine extends Canvas {
    
    private int h = 5;
    private Color c = Color.BLUE;

    public VertLine() {}
    
    VertLine(int h, Color c) {
        this.h = h;
        this.c = c;
    }
    public VertLine(int h) {
        this.h = h;
    }
    public VertLine(Color c) {
        this.c = c;
    }
    
    public void paint(Graphics g) {
        g.setColor(this.c);
        int ox = 15;
        int oy = 0;
        g.drawLine(ox, oy, ox, this.h);
    }
    
    public void setHeight(int h) {
        this.h = h;
    }
    
    public void setColor(Color c) {
        this.c = c;
    }
}
