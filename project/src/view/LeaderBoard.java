package view;

import controller.Controller;
import model.model;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class LeaderBoard extends JFrame {
    private static JTextPane texteditor;
    private JLabel backgroundImage = new JLabel(new ImageIcon("assets/images/sources/Marioleaderboard.png"));
    public static JButton Reset_scores;
    public static JButton Go_back;

    public LeaderBoard(Controller c) {
        setLayout(null);
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //start an instance of the objects
        Reset_scores = new JButton();
        texteditor = new JTextPane();
        Go_back = new JButton();


        //set up reset scores button settings
        Reset_scores.setBounds(550, 0, 150, 50);

        Reset_scores.setText("Reset ALL Scores");


        //set up go back button settings
        Go_back.setBounds(450, 0, 100, 50);

        Go_back.setText("Menu");


        //set up texteditor that includes scores
        texteditor.setFont(new Font("Cool", Font.BOLD, 15));


        //set attributes for texteditor
        StyledDocument document = texteditor.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        document.setParagraphAttributes(0, document.getLength(), center, false);

        //read scores
        //read scores
        texteditor.setText(model.getDataFromFile("assets/Scores.txt"));
        if (model.getDataFromFile("assets/Scores.txt").isEmpty()) {
            texteditor.setText("No scores yet");
        }
        texteditor.setForeground(Color.DARK_GRAY);
        texteditor.setBounds(100, 100, 500, 500);
        texteditor.setVisible(true);
        texteditor.setBackground(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), 200));
        texteditor.setEditable(false);


        //set background
        this.setContentPane(backgroundImage);


        //add all objects
        this.add(texteditor);

        this.add(Reset_scores);

        this.add(Go_back);


        //add objects to actionlistener
        Go_back.addActionListener(c);

        Reset_scores.addActionListener(c);


        this.setVisible(true);

    }

}
