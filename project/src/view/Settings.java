package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Settings extends JFrame {


    private String[] buttonNames = {"Turn on sound", "Turn off sound", "Change Gravity", "Change Speed", "Change lives ", "Credits", "No Gun Mode", "Gun Allowed", ""};
    public ArrayList<JButton> listOfButtons = new ArrayList<>();
    private JLabel backgroundImage = new JLabel(new ImageIcon("assets/images/maps/setting.png"));
    public JTextPane texteditor;
    public static JButton go_back;

    private JPanel settings = new JPanel();

    public Settings(Controller c) {
        setResizable(false);
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(backgroundImage);
        setLayout(null);
        setTitle("Settings");


        go_back = new JButton();

        go_back.setBounds(600, 0, 100, 50);

        go_back.setText("Menu");

        settings.setSize(500, 500);
        settings.setBackground(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), 200));
        settings.setOpaque(false);
        settings.setLayout(new GridLayout(6, 1));
        settings.setBounds(67, 100, 550, 450);

        texteditor = new JTextPane();
        texteditor.setBounds(75, 400, 547, 150);
        texteditor.setOpaque(false);
        texteditor.setFont(new Font("Cool", Font.PLAIN, 15));
        texteditor.setForeground(Color.WHITE);


        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton();
            settings.add(button);
            button.setName(buttonNames[i]);
            button.setText(buttonNames[i]);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Cool", Font.PLAIN, 28));
            listOfButtons.add(button);
            listOfButtons.get(i).addActionListener(c);
        }

        add(settings);
        add(texteditor);
        add(go_back);


        go_back.addActionListener(c);


        this.setVisible(true);

    }

}
