package com.company;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class MStoke implements Stroke {
    private BasicStroke stroke;

    MStoke(float width) {
        this.stroke = new BasicStroke(width);
    }

    @Override
    public Shape createStrokedShape(Shape shape) {
        GeneralPath shapePath = new GeneralPath();

        float[] xy = new float[2];
        float[] dxy = new float[2];
        double bias = 0;

        for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i.next()) {
            int type = i.currentSegment(xy);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    shapePath.moveTo(xy[1], xy[0]);
                    break;
                case PathIterator.SEG_LINETO:
                    double x1 = dxy[0];
                    double y1 = dxy[1];
                    double x2 = xy[0];
                    double y2 = xy[1];
                    double dx = x2 - x1;
                    double dy = y2 - y1;
                    double length = Math.sqrt(dx * dx + dy * dy);
                    dx /= length;
                    dy /= length;
                    x1 += dx * bias;
                    y1 += dy * bias;
                    length -= bias;
                    bias = 0;
                    double step = 5;
                    if (!Double.isInfinite(length)) {
                        while (bias <= length) {
                            x1 += dx * step + dy * step;
                            y1 += -dy * step + dx * step;
                            shapePath.lineTo(x1, y1);
                            x1 += -dy * step;
                            y1 += +dx * step;
                            shapePath.lineTo(x1, y1);
                            x1 += dx * step;
                            y1 += dy * step;
                            shapePath.lineTo(x1, y1);
                            x1 += +dy * step;
                            y1 += -dx * step;
                            shapePath.lineTo(x1, y1);
                            bias += 3 * step;
                        }
                        bias -= length;
                    }
                    break;
            }
            dxy[0] = xy[0];
            dxy[1] = xy[1];
        }
        return stroke.createStrokedShape(shapePath);
    }
}