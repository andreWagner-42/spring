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
    private int y = 100;
    private int size = 50;  // width and height
    private double ySpeed = 0; // m/s
    private double yAcceleration = 0;
    private double yDisplacement = 0; // displacement compared to the last position
    private double mass = 1; // kg
    private double gravity = -9.81; // N/m
    private double gravitationalForce = mass*gravity; // N
    private double springLenght = 1; // m
    private double springConstant = -100; // N/m
    private double deflection = 0; // m
    private double springForce = deflection*springConstant; // N
    private double resultantForce = gravitationalForce+springForce; // N


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
        /*
        ySpeed += yAcceleration / 2 * UPDATE_INTERVAL;
        yAcceleration = springConstant * y;
        yDisplacement = ySpeed * UPDATE_INTERVAL + yAcceleration / 2 * UPDATE_INTERVAL * UPDATE_INTERVAL;
        y += yDisplacement;
        */
        y += 1; // testing
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