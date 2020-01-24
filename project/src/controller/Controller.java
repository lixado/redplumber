package controller;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static model.model.*;
import static model.CollisionFunction.*;

public class Controller implements ActionListener, KeyListener {

    public static boolean mute = false;

    //Different UI elements
    public UI ui;
    public FirstMap firstMap;
    private LeaderBoard showLeaderboard;
    private Settings showSettings;

    //direction of mario
    public static Direction currentDirection = Direction.STOP;
    public Direction action = Direction.STOP;

    //timer, used to do something every 16 ms which corresponds to ~ 60 fps
    public Timer gameTimer;

    //If mario is right of left, default is right
    public boolean right = true;

    //booleans for jumping and falling
    public static boolean jumping = false;
    public static boolean falling = false;

    private static boolean settings = false;

    public static ArrayList<JLabel> coverBlocks;

    //falling
    public static boolean holeFalling = false;

    //no gun mode
    public static boolean noGunMode = false;
    
    //use this controller, and start a UI(main menu)
    public Controller() {
        ui = new UI(this);
    }

    //if any action is performed
    @Override
    public void actionPerformed(ActionEvent e) {
        //run if setting is pressed
        if (e.getSource().equals(UI.listOfButtons.get(2))) {
            ui.setVisible(false);
            showSettings = new Settings(this);
            settings = true;
            if (!mute)
            {
                playSound("assets/sound/sm64_pause.wav");
            }
        }

        if (settings) {
            //Mute/unmute
            if (e.getSource().equals(showSettings.listOfButtons.get(0))) {
                if (mute) {
                    mute = false;
                playLoopingSound("assets/sound/menu.wav");
            }
                if(!mute) {
                    playSound("assets/sound/sm64_pause.wav");
                }
            }
            if (e.getSource().equals(showSettings.listOfButtons.get(1))) {
                mute = true;
                stopLoopingSound();
            }
            //Change mario gravity
            if (e.getSource().equals(showSettings.listOfButtons.get(2))) {
                changeGravity();
                if (!mute)
                {
                    playSound("assets/sound/sm64_pause.wav");
                }
            }
            //Change mario speed
            if (e.getSource().equals(showSettings.listOfButtons.get(3))) {
                changeSpeed();
                if (!mute)
                {
                    playSound("assets/sound/sm64_pause.wav");
                }
            }
            if (e.getSource().equals(showSettings.listOfButtons.get(4))) {
                changeLives();
                if (!mute)
                {
                    playSound("assets/sound//sm64_pause.wav");
                }
            }
            if (e.getSource().equals(showSettings.listOfButtons.get(5))) {
                showSettings.texteditor.setText("CREDITS: "+getDataFromFile("assets/Credits.txt"));
                if (!mute)
                {
                    playSound("assets/sound//sm64_pause.wav");
                }
            }
            if (e.getSource().equals(showSettings.listOfButtons.get(6))) {
                noGunMode = true;
                if (!mute)
                {
                    playSound("assets/sound/sm64_pause.wav");
                }
            }
            if (e.getSource().equals(showSettings.listOfButtons.get(7))) {
                noGunMode = false;
                if (!mute)
                {
                    playSound("assets/sound//sm64_pause.wav");
                }
            }
            //if go back button
            if (e.getSource().equals(Settings.go_back)) {
                showSettings.dispose();
                ui.setVisible(true);
                if (!mute)
                {
                    playSound("assets/sound//sm64_pause.wav");
                }
            }
        }

        //run if leaderboard is pressed
        if (e.getSource().equals(UI.listOfButtons.get(1))) {
            ui.setVisible(false);
            showLeaderboard = new LeaderBoard(this);
            if (!mute)
            {
                playSound("assets/sound//sm64_pause.wav");
            }
        }

        //if pressed rest scores button
        if (e.getSource().equals(LeaderBoard.Reset_scores)) {
            //reset score
            resetScoreboard();

            //refresh
            showLeaderboard.dispose();
            showLeaderboard = new LeaderBoard(this);
            if (!mute)
            {
                playSound("assets/sound//sm64_pause.wav");
            }
        }

        //if go back button
        if (e.getSource().equals(LeaderBoard.Go_back)) {
            ui.setVisible(true);

            showLeaderboard.dispose();
            if (!mute)
            {
                playSound("assets/sound//sm64_pause.wav");
            }
        }


        //run if start game is pressed
        if (e.getSource().equals(UI.listOfButtons.get(0))) {
            resetVariables(this);

            //load map
            firstMap = startGame(this);

            if(firstMap!=null) {
                //make a timer and start it
                gameTimer = new Timer(16, this);
                gameTimer.start();

                //disable main menu
                ui.setVisible(false);
            }

        }

        //Exits the game
        if (e.getSource().equals(UI.listOfButtons.get(3))) {
            System.exit(0);
            if (!mute)
            {
                playSound("assets/sound//sm64_pause.wav");
            }
        }

        //if timer is activated
        if (e.getSource().equals(gameTimer)) {
            //linux lag fix
            Toolkit.getDefaultToolkit().sync();


            //check collision
            checkCollision(this);
            moveMario(this);
            moveMap();
            moveGoomba();
            moveBowser();

            if (shooting) {
                moveBullet();
            }
            if (bowserShooting) {
                moveFireball();
            }

            iframe();

            //check if died
            checkIfDead(this);
            pointSystem(this);

            //Jumping mechanics
            if (action.equals(Direction.UP) && upOrDown == Direction.STOP && !jumping && !falling) {
                if(!mute) {
                    playSound(jumpClip);
                }
                jumping = true;
                marioVerticalVelocity = 16;
                upOrDown = Direction.UP;
                action = Direction.STOP;
            }

            if (jumping) {
                jump();
            }

            //gravity
            if (marioFigure.getY() <= groundY && !collidingOnFeet && !jumping && upOrDown == Direction.STOP) {
                upOrDown = Direction.DOWN;
                marioVerticalVelocity = 0;
                falling = true;
            }
            if (marioFigure.getY() == groundY || collidingOnFeet) {
                falling = false;
                upOrDown = Direction.STOP;
            }


            //if mario is in a hole
            if ((marioFigure.getX() < 3550 && marioFigure.getX() > 3450) || (marioFigure.getX() < 4450 && marioFigure.getX() > 4300) || (marioFigure.getX() <= 7750 && marioFigure.getX() >= 7650)) {
                if (marioFigure.getY() >= 561) {
                    marioFigure.setLocation(marioFigure.getX(), marioFigure.getY() + 5);

                    upOrDown = Direction.DOWN;
                    marioVerticalVelocity = 0;

                    falling = true;
                    holeFalling = true;

                }
            }

            //Kills mario if mario is underneath the ground and falling in hole
            if (marioFigure.getY() > 600 && holeFalling) {
                lives = 0;
            }
            if (marioFigure.getY() > groundY && !holeFalling) {
                marioFigure.setLocation(marioFigure.getX(), groundY);
            }


            if (falling || holeFalling) {
                gravity();
            }

            //Updates marios hitbox
            updateHitbox();

            //Checks if mario is under map

            //check if under map if not falling in a hole

            marioUnderMap(this);

            //Checks if mario wins
            marioWins(this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //if left key pressed
        if (e.getKeyCode() == KeyEvent.VK_LEFT && currentDirection != Direction.RIGHT && currentDirection != Direction.LEFT) {

            //direction of mario to use in crouching
            right = false;

            //set direction left
            currentDirection = Direction.LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stopGame(this);
        }
        //if right key pressed
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentDirection != Direction.LEFT && currentDirection != Direction.RIGHT) {

            //direction of mario to use in crouching
            right = true;

            //set direction right
            currentDirection = Direction.RIGHT;
        }
        //if up key pressed
        else if (e.getKeyCode() == KeyEvent.VK_UP && currentDirection != Direction.DOWN && currentDirection != Direction.UP) {

            //set direction up
            action = Direction.UP;
        }
        //if down key pressed
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentDirection != Direction.UP && currentDirection != Direction.DOWN) {

            //if mario was looking left
            if (!right) {
                //crouch looking left
                marioFigure.setIcon(marioCrouchLeft);
            }
            //if mario was looking right
            else {
                //crouch looking right
                marioFigure.setIcon(marioCrouchRight);
            }

            //set direction down
            currentDirection = Direction.DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot(this);
        }
    }

    //if a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        //if left key realesed
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            //stop mario
            currentDirection = Direction.STOP;

            //make him look left
            marioFigure.setIcon(marioLeft);

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {

            //stop mario
            action = Direction.STOP;
            //make him look right
        }
        //if right key realesed
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            //stop mario
            currentDirection = Direction.STOP;
            //make him look right
            marioFigure.setIcon(marioRight);
        }

        //if down key realesed
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            //stop mario
            currentDirection = Direction.STOP;
        }
    }

}