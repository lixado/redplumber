package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//UI for the map
public class FirstMap extends JFrame {
    public static ArrayList<Rectangle> nonSpecialCollisionObjects = new ArrayList<>();

    public static ArrayList<Rectangle> specialCollisionObjects = new ArrayList<>();

    public static Rectangle winningFlag;

    private JLabel background = new JLabel(new ImageIcon("assets/images/maps/map1.png"));

    public FirstMap(Controller c) {
        setResizable(false);
        setLayout(null);
        setTitle("Super Mario");
        background.setSize((int) (background.getWidth() * 3.125), (int) (background.getHeight() * 3.125));
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(c);

        background.setBounds(new Rectangle(new Point(0, 0), background.getPreferredSize()));
        background.setDoubleBuffered(true);
        background.setSize(new Dimension(400, 3000));
        this.setContentPane(background);

        //add non-special objects
        nonSpecialCollisionObjects.add(new Rectangle(1000, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(1100, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(1200, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(1900, 475, 100, 150));
        nonSpecialCollisionObjects.add(new Rectangle(1400, 525, 100, 100));
        nonSpecialCollisionObjects.add(new Rectangle(2300, 425, 100, 200));
        nonSpecialCollisionObjects.add(new Rectangle(2850, 425, 100, 200));
        nonSpecialCollisionObjects.add(new Rectangle(3450, 700, 100, 100));
        nonSpecialCollisionObjects.add(new Rectangle(3850, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(3950, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(4000, 225, 400, 50));
        nonSpecialCollisionObjects.add(new Rectangle(4300, 700, 150, 100));
        nonSpecialCollisionObjects.add(new Rectangle(4550, 225, 150, 50));
        nonSpecialCollisionObjects.add(new Rectangle(4700, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(5000, 425, 100, 50));
        nonSpecialCollisionObjects.add(new Rectangle(5900, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6050, 225, 150, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6450, 425, 100, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6400, 225, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6550, 225, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6700, 575, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(6750, 525, 50, 100));
        nonSpecialCollisionObjects.add(new Rectangle(6800, 475, 50, 150));
        nonSpecialCollisionObjects.add(new Rectangle(6850, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(7000, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(7050, 475, 50, 150));
        nonSpecialCollisionObjects.add(new Rectangle(7100, 525, 50, 100));
        nonSpecialCollisionObjects.add(new Rectangle(7150, 575, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(7400, 575, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(7450, 525, 50, 100));
        nonSpecialCollisionObjects.add(new Rectangle(7500, 475, 50, 150));
        nonSpecialCollisionObjects.add(new Rectangle(7550, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(7600, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(7650, 625, 100, 100));
        nonSpecialCollisionObjects.add(new Rectangle(7750, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(7800, 475, 50, 150));
        nonSpecialCollisionObjects.add(new Rectangle(7850, 525, 50, 100));
        nonSpecialCollisionObjects.add(new Rectangle(7900, 575, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(8150, 525, 100, 100));
        nonSpecialCollisionObjects.add(new Rectangle(8400, 425, 100, 50));
        nonSpecialCollisionObjects.add(new Rectangle(8550, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(9400, 225, 100, 400));
        nonSpecialCollisionObjects.add(new Rectangle(9350, 275, 50, 350));
        nonSpecialCollisionObjects.add(new Rectangle(9300, 325, 50, 300));
        nonSpecialCollisionObjects.add(new Rectangle(8500, 425, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(9250, 375, 50, 250));
        nonSpecialCollisionObjects.add(new Rectangle(9200, 425, 50, 200));
        nonSpecialCollisionObjects.add(new Rectangle(9150, 475, 50, 150));
        nonSpecialCollisionObjects.add(new Rectangle(9100, 525, 50, 100));
        nonSpecialCollisionObjects.add(new Rectangle(9050, 575, 50, 50));
        nonSpecialCollisionObjects.add(new Rectangle(8950, 525, 100, 100));

        //add special objects
        specialCollisionObjects.add(new Rectangle(800, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(1050, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(1150, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(1100, 225, 50, 50));
        specialCollisionObjects.add(new Rectangle(3900, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(4700, 225, 50, 50));
        specialCollisionObjects.add(new Rectangle(5300, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(5450, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(5450, 225, 50, 50));
        specialCollisionObjects.add(new Rectangle(5600, 425, 50, 50));
        specialCollisionObjects.add(new Rectangle(6450, 225, 50, 50));
        specialCollisionObjects.add(new Rectangle(6500, 225, 50, 50));
        specialCollisionObjects.add(new Rectangle(8500, 425, 50, 50));


        winningFlag = new Rectangle(9900, 80, 50, 500);

        this.setVisible(true);
    }

    //Sets new location of the background
    public void setbackgroundLocation(int x) {
        background.setBounds(new Rectangle(new Point(x, 0), background.getPreferredSize()));
    }
}