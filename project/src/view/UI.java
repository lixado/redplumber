package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static controller.Controller.mute;
import static model.model.*;

public class UI extends JFrame {

    private static String[] buttonNames = {"Start Game", "Leaderboard", "Settings", "Exit Game"};
    public static ArrayList<JButton> listOfButtons = new ArrayList<>();
    private static JPanel buttonPanel = new JPanel();
    private JLabel backgroundImage = new JLabel(new ImageIcon("assets/images/maps/menu.png"));


    public UI(Controller c) {
        setResizable(false);
        setLayout(null);
        setTitle("Super Mario");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBounds(10, 200, 700, 200);
        buttonPanel.setOpaque(false);

        Font font = new Font("Arial", Font.PLAIN, 24);
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton();
            buttonPanel.add(button);
            button.setName(buttonNames[i]);
            button.setText(buttonNames[i]);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(font);
            listOfButtons.add(button);
            listOfButtons.get(i).addActionListener(c);
        }

        //Adds for the UI
        this.setContentPane(backgroundImage);
        this.add(buttonPanel);


        //Plays background music, -1 means that it will play forever (or until its stopped with stopSound())
        if (!mute) {
            playLoopingSound(menuClip);
        }


        this.setVisible(true);

    }

}
