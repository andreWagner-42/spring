package com.company;

import javax.swing.*;
import java.awt.*;    // Using AWT's Graphics and Color

public class Spring extends JFrame {
    // Define named-constants
    private static final int CANVAS_WIDTH = 640;
    private static final int CANVAS_HEIGHT = 480;
    private static final int UPDATE_INTERVAL = 10; // milliseconds

    private DrawCanvas canvas;  // the drawing canvas (an inner class extends JPanel)

    // Attributes of moving object
    private int x = 100;     // top-left (x, y)
    private int y = 200;
    private int size = 50;  // width and height
    private double springLenght = 1;
    private int mass = 1; // kg
    private double gravity = 9.81; /// N/kg
    private int springConstant = -100; // N/metres
    private double position = 1; // metres
    private double deflection = position-springLenght; // metres
    private double ySpeed = 0; // m/s
    private double yAcceleration = 0; // m/s^^2
    private double gravitationalForce = gravity*mass; // N
    private double springForce = springConstant*deflection; // N
    private double resultantForce = gravitationalForce+springForce; // N
    private double update_interval = (double) UPDATE_INTERVAL/1000; // seconds
    // Constructor to setup the GUI components and event handlers
    public Spring() {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Spring");
        this.setVisible(true);

        // Create a new thread to run update at regular interval
        new Timer(UPDATE_INTERVAL, actionEvent -> {
            update();   // update the (x, y) position
            Toolkit.getDefaultToolkit().sync();
            repaint();  // Refresh the JFrame. Called back paintComponent()
        }).start();
    }

    // Update the (x, y) position of the moving object
    public void update() {
        deflection = position-springLenght;
        springForce = deflection*springConstant;
        resultantForce = gravitationalForce+springForce;
        yAcceleration = resultantForce/mass;
        ySpeed += yAcceleration/2*update_interval;
        position += ySpeed*update_interval;
        y = (int)(position*200);
    }

    // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // paint parent's background
            setBackground(Color.DARK_GRAY);
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, size, size);  // draw a circle
        }
    }

    public static void main(String[] args) {
        // Run GUI codes in Event-Dispatching thread for thread safety
        // Let the constructor do the job
        SwingUtilities.invokeLater(Spring::new);
    }
}
