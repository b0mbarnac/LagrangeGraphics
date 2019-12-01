package view;

import controller.PolynomLagrange;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class GraphicalInterface extends JFrame {
    Graphics gr;
    Graphics scrgr;
    int CENTER_X = 600;
    int CENTER_Y = 400;
    int CELL_SIZE = 100;
    int serifSize = 10;
    int currentX;
    int currentY;
    BufferedImage img;
    int x;
    int y;

    Point point = new Point(x, y);

    public GraphicalInterface(Graphics gr) {
        scrgr = gr;
        img = new BufferedImage(1200, 800,
                BufferedImage.TYPE_3BYTE_BGR);
        this.gr = img.getGraphics();
    }

    public double transformx(int x) {
        double w = (x - CENTER_X) * 1.0 / CELL_SIZE;
        return w;
    }

    public double transformy(int y) {
        double q = (CENTER_Y - y) * 1.0 / CELL_SIZE;
        return q;
    }

    public void drawX() {
        gr.drawLine(50, CENTER_Y,
                2 * CENTER_X - 50, CENTER_Y);
        gr.drawLine(2 * CENTER_X - 80,
                CENTER_Y - 15, 2 * CENTER_X - 50, CENTER_Y);
        gr.drawLine(2 * CENTER_X - 80, CENTER_Y + 15,
                2 * CENTER_X - 50, CENTER_Y);
        int countOfSerifs = (CENTER_X - 35) / CELL_SIZE;
        for (int i = 0; i < countOfSerifs; i++) {
            gr.drawLine(CENTER_X + i * CELL_SIZE,
                    CENTER_Y - serifSize,
                    CENTER_X + i * CELL_SIZE,
                    CENTER_Y + serifSize);
            gr.drawLine(CENTER_X - i * CELL_SIZE,
                    CENTER_Y - serifSize,
                    CENTER_X - i * CELL_SIZE, CENTER_Y + serifSize);
        }
    }

    public void drawY() {
        gr.drawLine(CENTER_X, 50, CENTER_X, 750);
        gr.drawLine(CENTER_X - 15, 80, CENTER_X, 50);
        gr.drawLine(CENTER_X + 15, 80, CENTER_X, 50);
        int countOfSerifs = (CENTER_Y - 35) / CELL_SIZE;
        for (int i = 0; i < countOfSerifs; i++) {
            gr.drawLine(CENTER_X - serifSize, CENTER_Y + i * CELL_SIZE,
                    CENTER_X + serifSize, CENTER_Y + i * CELL_SIZE);
            gr.drawLine(CENTER_X - serifSize, CENTER_Y - i * CELL_SIZE,
                    CENTER_X + serifSize, CENTER_Y - i * CELL_SIZE);
        }
    }

    public void stringpolynom(double[] uzly, double[] znach) {
        String s = "";
        if (uzly.length == 1 && znach.length == 1) {
            s = "f(x)=" + Double.toString(znach[0]);
            gr.drawString(s, 620, 600);
        } else {
            s = ConcoleView.convert(PolynomLagrange.polynomLagrange(uzly, znach));
            gr.drawString(s, 620, 600);
        }
    }

    public void drawgraphics(double[] uzly, double[] znach) {
        if (uzly.length == 1 && znach.length == 1) {
            gr.drawLine(0, decartToscreeny(znach[0]), decartToscreenx(uzly[0]), decartToscreeny(znach[0]));
            gr.drawLine(decartToscreenx(uzly[0]), decartToscreeny(znach[0]), img.getWidth(), decartToscreeny(znach[0]));
        } else if (uzly.length > 1 && znach.length > 1) {
            for (int i = 1; i < img.getWidth(); i++) {
                gr.drawLine(i, decartToscreeny(PolynomLagrange.inpoints(PolynomLagrange.polynomLagrange(uzly, znach), transformx(i))), i + 1, decartToscreeny(PolynomLagrange.inpoints(PolynomLagrange.polynomLagrange(uzly, znach), transformx(i + 1))));

            }
        }
    }

    public void all(double[] uzly, double[] znach, int x, int y) {
        resetImg();
        drawgraphics(uzly, znach);
        stringpolynom(uzly, znach);
        setXandY(x, y);
        drawAxis();
        drawToScreen();
    }

    public void paintpoint(double[] uzly, double[] znach) {
        for (int i = 0; i < uzly.length; i++) {
            gr.fillOval(decartToscreenx(uzly[i]) - 5, decartToscreeny(znach[i]) - 5, 10, 10);
        }
        drawToScreen();
    }

    public int decartToscreenx(double w) {
        int x1 = (int) (CELL_SIZE * w + CENTER_X);
        return x1;
    }

    public int decartToscreeny(double q) {
        int y1 = (int) (CENTER_Y - CELL_SIZE * q);
        return y1;
    }

    public void setXandY(int x, int y) {
        currentX = x;
        currentY = y;
        double newX = transformx(x);
        double newY = transformy(y);
        gr.drawString("x = " + newX +
                "   y = " + newY, 800, 700);
    }

    public void drawAxis() {
        drawX();
        drawY();

    }

    public void resetImg() {
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
        gr.setColor(Color.BLACK);
    }

    public void drawToScreen() {
        scrgr.drawImage(img, 0, 0,
                img.getWidth(), img.getHeight(), null);
    }

    public void changeCellSize(int a, int windowX, int windowY) {
        resetImg();
        double plotX = transformx(windowX);
        double plotY = transformy(windowY);
        if (a > 0) {
            CELL_SIZE += 5*(-1.);
        } else {
            if (CELL_SIZE > 7) {
                CELL_SIZE -= 5*(-1.);
            }
        }
        CENTER_X = windowX - (int) (plotX * CELL_SIZE);
        CENTER_Y = windowY + (int) (plotY * CELL_SIZE);
        drawAxis();
        drawToScreen();
    }

}

