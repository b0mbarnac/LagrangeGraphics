package view;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Window extends JFrame {
    ArrayList<Double> uzly1=new ArrayList<Double>();
    ArrayList<Double> znach1=new ArrayList<Double>();

    public Window() {
        this.setBounds(350, 150, 1200, 800);
        this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        GraphicalInterface animator = new GraphicalInterface(this.getGraphics());
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    uzly1.add(animator.transformx(e.getX()));
                    znach1.add(animator.transformy(e.getY()));
                Double[] uzly2 = uzly1.toArray(new Double[uzly1.size()]);
                Double[] znach2 = znach1.toArray(new Double[znach1.size()]);
                double[] uzly = Stream.of(uzly2).mapToDouble(Double::doubleValue).toArray();
                double[] znach = Stream.of(znach2).mapToDouble(Double::doubleValue).toArray();
                animator.all(uzly, znach, e.getX(), e.getY());
                animator.paintpoint(uzly, znach);
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        });
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                animator.changeCellSize(e.getWheelRotation(),
                        e.getX(), e.getY());
            }
        });
    }
}
