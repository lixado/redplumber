package model;

import controller.Controller;
import view.Direction;
import view.FirstMap;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static controller.Controller.*;

public class model {

    //Sets up sprites for mario and enemies,
    public static ImageIcon marioRight = new ImageIcon("assets/images/sprites/mario_right.png");
    public static ImageIcon marioLeft = new ImageIcon("assets/images/sprites/mario_left.png");
    public static ImageIcon marioCrouchRight = new ImageIcon("assets/images/sprites/mario_crouch_right.png");
    public static ImageIcon marioCrouchLeft = new ImageIcon("assets/images/sprites/mario_crouch_left.png");
    public static ImageIcon marioRunRight = new ImageIcon("assets/images/sprites/mario_run_right.png");
    public static ImageIcon marioRunLeft = new ImageIcon("assets/images/sprites/mario_run_left.png");
    public static ImageIcon marioHit = new ImageIcon("assets/images/sprites/mario_hit.png");

    //Bowser
    public static ImageIcon bowserRight =  new ImageIcon("assets/images/sprites/bowser_right.png");
    public static ImageIcon bowserLeft = new ImageIcon("assets/images/sprites/bowser_left.png");
    public static ImageIcon fireballLeft =  new ImageIcon("assets/images/sprites/fireball_left.png");
    public static ImageIcon fireballRight = new ImageIcon("assets/images/sprites/fireball_right.png");
    public static ImageIcon bowserVunerableRight = new ImageIcon("assets/images/sprites/bowser_right_vulnerable.png");
    public static ImageIcon bowserVunerableLeft = new ImageIcon("assets/images/sprites/bowser_left_vulnerable.png");

    //Username
    private static String username = "";

    public static String mapClip = "assets/sound/map1.wav";
    public static String menuClip = "assets/sound/menu.wav";
    public static String jumpClip = "assets/sound/jump.wav";
    public static String deathClip = "assets/sound/deathsound.wav";
    public static String stompClip = "assets/sound/stompenemy.wav";
    public static String shootClip = "assets/sound/shoot.wav";
    public static String levelClearClip = "assets/sound/levelclear.wav";
    public static String boostsClip = "assets/sound/powerup.wav";
    public static String blockHitClip = "assets/sound/blockhit.wav";
    public static String ButtonClip = "assets/sound/sm64_pause.wav";

    private static Clip soundClipLooping;

    //gravity / jump
    private static double GRAVITY = 0.5;

    private static final int MAX_VELOCITY = 16;

    public static final int groundY = 561;

    //variable to change picture when running
    private static int run = 0;

    //x position of map, used to control map viweing
    private static int mapPosX = 0;

    //ints to control map
    private static int xMax = 350;   //Max is 700, xMax decides when the map is going to move forwards instead of mario.
    private static int xMin = 50;    //Max is 0, xMin decides when the map is going to move backwards instead of mario.
    private static int xMaxAdd = 0;  //How much xMax is gonna expand when mario is moving forwards.
    private static int xMinAdd = 0;  //How much xMin is gonna expand when mario is moving backwards.

    //booleans for if fireflower has been picked up and shooting is not happening
    public static boolean shooting = false;
    public static boolean bowserShooting = false;
    static boolean fireflower = false;

    //Enemy arrays
    static ArrayList<Rectangle> enemyHitboxes;
    static ArrayList<JLabel> enemyArray;

    //Jlabels of characters
    public static JLabel marioFigure = new JLabel(new ImageIcon("assets/images/sprites/mario1.png"));
    public static JLabel bullet = new JLabel(new ImageIcon("assets/images/sprites/bullet.png"));
    public static JLabel bowser = new JLabel(bowserRight);
    public static JLabel fireball = new JLabel(fireballRight);

    //Hitboxes
    static Rectangle marioHitbox = new Rectangle(20, groundY, 44, 63);
    static Rectangle bowserHitbox = new Rectangle(8400, groundY - 10, 75, 75);
    static ArrayList<Rectangle> boostsHitboxes;
    static Rectangle bulletHitbox = new Rectangle(20 + marioFigure.getWidth(), groundY - (marioFigure.getHeight() / 2), 44, 18);
    static Rectangle fireballHitbox = new Rectangle(-1000, groundY - (bowser.getHeight() / 2), 75, 25);

    //boosts
    static ArrayList<JLabel> boosts;
    //0 = gun 1 = coin 3 = used
    static java.util.List<Integer> gunOrCoinInt = new ArrayList<>();

    //direction of goomba, bowser and lastdirection
    private static Direction goombaDirection = Direction.RIGHT;
    private static Direction bowserDirection = Direction.RIGHT;

    //Points
    static int points = 10000;
    private static int tick = 0;
    private static int seconds = 0;

    //for iframe
    static int iframe = 0;
    private static int oneSecond = 0;

    public static int lives = 3;
    private static int numberOfLives = 3;
    static int bowserLives = 5;
    static boolean bowserVunerabel = false;

    //one second for bowser
    private static int oneSecondBowser = 0;
    private static int number_of_seconds = 0;
    private static int random_number = 0;

    //score;
    private static String showPoints;
    private static String showName;
    private static String showTime;

    private static int bulletspeed = 20;
    private static int fireballspeed = 20;

    public static double marioVerticalVelocity = 0;
    public static Direction upOrDown = Direction.STOP;
    public static boolean collidingOnFeet = false;
    static boolean collision = false;

    //Mario speed
    private static int marioSpeed = 5;

    //Array for goombas starting position
    private static int[] posXArray = {230, 900, 2550, 3050, 3700, 4550, 5150, 6200, 8350, 8600};


    //Functions which make out the game.
    //reset scoreboard
    public static void resetScoreboard() {
        String path = "assets/Scores.txt";
        FileWriter file;
        try {
            file = new FileWriter(path, false);
            file.write("");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //lagre
    private static void saveToFile(String text) {
        String path = "assets/Scores.txt";
        FileWriter file;
        try {
            file = new FileWriter(path, true);
            file.append(text);
            file.append("\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get data from file
    public static String getDataFromFile(String filepath) {
        StringBuilder s = new StringBuilder();

        FileReader f;
        try {
            f = new FileReader(filepath);
            BufferedReader b = new BufferedReader(f);
            String line = b.readLine();

            //If the text document is empty, returns a string to the leaderboard.
            if (line == null) {
                return "";
            }

            // Hvis linje er null har vi nådd enden av dokumentet
            while (line != null) {
                s.append(line);
                s.append("\n");
                line = b.readLine();
            }
            f.close();
        } catch (IOException e) {
            System.out.println("Canceled");
            e.printStackTrace();
        }

        return s.toString();
    }

    //Changes images while the player is moving
    private static int running(JLabel figure, ImageIcon running, ImageIcon still, int run) {
        if (run < 8) {
            figure.setIcon(running);
        }
        if (run > 8) {
            figure.setIcon(still);
            if (run == 16) {
                run = 0;
            }
        }
        run++;

        return run;
    }

    // Plays sound from the specified filepath. File must be a wavefile with the file extension ".wav". Plays 1 time
    public static void playSound(String filepath) {
        //Finds file
        Clip soundClip;
        File soundFile = new File(filepath);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
            soundClip = AudioSystem.getClip();
            soundClip.open(audio);
            soundClip.start();
            soundClip.loop(0);
            if (soundClip.getFramePosition() == soundClip.getFrameLength()) {
                soundClip.close();
            }

            //Error-handling
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Plays sound from the specified filepath. File must be a wavefile with the file extension ".wav". The file loops for count number of times. If count is -1, the sound loops forever.
    public static void playLoopingSound(String filepath) {
        //Finds file
        File soundFile = new File(filepath);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
            soundClipLooping = AudioSystem.getClip();
            soundClipLooping.open(audio);
            soundClipLooping.start();
            soundClipLooping.loop(-1);


            //Error-handling
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void stopLoopingSound() {
        if (soundClipLooping != null) {
            soundClipLooping.stop();
            soundClipLooping.close();
        }
    }


    private static String generateUsername() {

        return "Player" + (int) (Math.random() * (1000 - 10) * 10);
    }

    public static FirstMap startGame(Controller c) {
        try {
            username = JOptionPane.showInputDialog("Input your name below");
            if (username.isEmpty()) {
                username = generateUsername();
            }
            JOptionPane.showMessageDialog(null, "How to play:\nUse the arrow keys to move to the sides, up to jump and down to crouch.\nYou have 3 lives, if you loose all of them or fall in a hole, it's game over.\nIf you get the fireflower-powerup, you can use the spacebar to shoot.\nIf you lose a life, you will lose the ability to shoot.\nIf you get hit, you have 1 second invincibility.\nGood luck!");
        } catch (Exception e) {
            System.out.println("Cancelled");
            return null;
        }


        if (!mute)
        {
            stopLoopingSound();
            playLoopingSound(mapClip);
        }

        enemyArray = generateEnemies();
        enemyHitboxes = generateHitboxes();

        //load map
        FirstMap first_map = new FirstMap(c);

        //add characters to the map
        first_map.add(marioFigure);
        bowser.setBounds(bowserHitbox);
        first_map.add(bowser);

        for (int i = 0; i < enemyArray.size(); i++) {
            first_map.add(enemyArray.get(i));
            enemyArray.get(i).setLocation(posXArray[i], groundY + 12);
            enemyHitboxes.get(i).setBounds(posXArray[i], groundY + 12, 44, 44);
        }

        first_map.add(bullet);
        first_map.add(fireball);
        fireball.setBounds(-100, -100, 75, 25);
        bullet.setBounds(-100, -100, 44, 18);

        //boosts
        boosts = generateBoosts();
        boostsHitboxes = generateHitboxesforBoosts();
        coverBlocks = coverBlocksForBoosts();

        for (int j = 0; j < FirstMap.specialCollisionObjects.size(); j++) {
            first_map.add(boosts.get(j));
            first_map.add(coverBlocks.get(j));
        }

        marioFigure.setDoubleBuffered(true);
        marioFigure.setLocation(20, groundY);

        //Show map
        first_map.setVisible(true);

        return first_map;
    }

    public static void stopGame(Controller c) {

        stopLoopingSound();
        fireflower = false;
        currentDirection = Direction.STOP;
        c.gameTimer.stop();
        c.firstMap.dispose();
        c.ui.setVisible(true);
        if (!mute) {
            playLoopingSound(menuClip);
        }

    }

    //Resets all variables for restarting the game on the fly
    public static void resetVariables(Controller c) {

        //x position of map, used to control map viweing
        mapPosX = 0;

        holeFalling = false;

        //ints to control map
        xMax = 350;   //Max is 700, xMax decides when the map is going to move forwards instead of mario.
        xMin = 50;    //Max is 0, xMin decides when the map is going to move backwards instead of mario.
        xMaxAdd = 0;  //How much xMax is gonna expand when mario is moving forwards.
        xMinAdd = 0;  //How much xMin is gonna expand when mario is moving backwards.
        marioVerticalVelocity = 0;
        enemyArray = null;
        enemyHitboxes = null;

        //variable to change picture when running
        run = 0;

        showPoints = null;
        showTime = null;
        showName = null;
        points = 10000;
        seconds = 0;
        lives = numberOfLives;
        bowserLives = 5;
        bowserVunerabel = false;
        random_number = (int) (Math.random() * 4 + 8);

        oneSecondBowser = 0;
        number_of_seconds = 0;

        //direction of goomba
        goombaDirection = Direction.RIGHT;

        c.right = true;
        jumping = false;
        falling = false;


        fireflower = false;
        shooting = false;
        bulletspeed = 20;

        iframe = 0;
        oneSecond = 0;

        gunOrCoinInt.clear();
    }


    //move mario
    public static void moveMario(Controller c) {

        //Stops mario from leaving the screen to the left
        if (marioFigure.getX()<=0){
            currentDirection=Direction.STOP;
            marioFigure.setLocation(10, marioFigure.getY());
        }

        //if mario is going right
        if (currentDirection == Direction.RIGHT) {
            //change pictures for running, so character seems like he is moving
            run = running(marioFigure, marioRunRight, marioRight, run);

            //set new location
            marioFigure.setLocation((marioFigure.getX() + marioSpeed), marioFigure.getY());

        }

        if (currentDirection == Direction.LEFT) {

            //change pictures for running, so character seems like he is moving
            run = running(marioFigure, marioRunLeft, marioLeft, run);

            //set new location
            marioFigure.setLocation((marioFigure.getX() - marioSpeed), marioFigure.getY());
        }

        //set location for hitbox
        marioHitbox.setLocation(marioFigure.getX(), marioFigure.getY());
        //move map
        c.firstMap.setbackgroundLocation(mapPosX);

        //move mario
        marioFigure.setBounds(new Rectangle(new Point(marioFigure.getX(), marioFigure.getY()), marioFigure.getPreferredSize()));
    }

    //move map
    public static void moveMap() {
        if (currentDirection == Direction.RIGHT && marioFigure.getX() >= xMax + xMaxAdd) {

            mapPosX -= marioSpeed;                                                            //How fast mario moves
            xMaxAdd += marioSpeed;                                                              //xMax starts at 500, when mario is moving MinX is added with i, to update Maximum position.
            xMinAdd += marioSpeed;                                                              //xMin starts at 50, when mario is moving MinX is added with k, to update Minimum position.
        }

        if (currentDirection == Direction.LEFT && marioFigure.getX() <= xMin + xMinAdd && mapPosX != 0) {
            mapPosX += marioSpeed;                                                            //How fast mario moves
            xMaxAdd -= marioSpeed;                                                              //xMax starts at 500, when mario is moving MinX is added with i, to update Maximum position.
            xMinAdd -= marioSpeed;                                                              //xMin starts at 50, when mario is moving MinX is added with k, to update Minimum position.
        }
    }

    //Generates an array of JLabels which shows enemies.
    private static ArrayList<JLabel> generateEnemies() {
        ArrayList<JLabel> enemies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            JLabel goomba = new JLabel(new ImageIcon("assets/images/sprites/goomba.png"));
            goomba.setDoubleBuffered(true);
            enemies.add(goomba);
        }
        return enemies;
    }

    //Generates an array of rectangles, which will be hitboxes for the enemies.
    private static ArrayList<Rectangle> generateHitboxes() {
        ArrayList<Rectangle> enemyHitboxes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Rectangle goombaHitbox = new Rectangle(posXArray[i], groundY + 12, 44, 44);
            enemyHitboxes.add(goombaHitbox);
        }
        return enemyHitboxes;
    }

    //Move goomba
    public static void moveGoomba() {

        for (int i = 0; i < enemyHitboxes.size(); i++) {

            if (enemyArray.get(i).getX() < posXArray[i] - 30) {
                goombaDirection = Direction.LEFT;
            }
            if (enemyArray.get(i).getX() > posXArray[i] + 270) {
                goombaDirection = Direction.RIGHT;
            }

            if (goombaDirection.equals(Direction.RIGHT)) {
                enemyArray.get(i).setLocation((enemyArray.get(i).getX() - 2), enemyArray.get(i).getY());
            }
            if (goombaDirection.equals(Direction.LEFT)) {
                enemyArray.get(i).setLocation((enemyArray.get(i).getX() + 2), enemyArray.get(i).getY());
            }

            enemyHitboxes.set(i, new Rectangle(enemyArray.get(i).getX(), enemyArray.get(i).getY(), 44, 63));
            enemyArray.get(i).setBounds(enemyHitboxes.get(i));
        }
    }

    //Move bowser
    public static void moveBowser() {
        oneSecondBowser++;
        //make bowser be defensless so u can jump on him and dmg him
        if (oneSecondBowser >= 60) {
            number_of_seconds++;
            oneSecondBowser = 0;

        }
        if (number_of_seconds >= random_number) {
            bowserVunerabel = true;
        }
        if (number_of_seconds >= random_number + 2) {
            bowserVunerabel = false;
            number_of_seconds = 0;
            random_number = (int) (Math.round(Math.random() * 4) + 8);
        }

        if (bowserVunerabel) {
            if (bowserDirection == Direction.RIGHT)
            {
                bowser.setIcon(bowserVunerableRight);
            }
            else {
                bowser.setIcon(bowserVunerableLeft);
            }

        }
        if (!bowserVunerabel) {
            if (bowser.getX() < 8250) {
                bowserDirection = Direction.RIGHT;
            } else if (bowser.getX() > 8875) {
                bowserDirection = Direction.LEFT;
            }

            if (bowserDirection == Direction.RIGHT) {
                bowserHitbox.setLocation(bowser.getX() + 2, bowser.getY());

                bowser.setIcon(bowserRight);
            }

            else if (bowserDirection ==Direction.LEFT) {
                bowserHitbox.setLocation(bowser.getX() - 2, bowser.getY());

                bowser.setIcon(bowserLeft);
            }

            if (marioFigure.getX() < 8875 && marioFigure.getX() > 8250)
            {
                shootFireball();
            }
        }

        bowser.setBounds(bowserHitbox);
    }

    //jump
    public static void jump() {
        marioVerticalVelocity -= GRAVITY;
        int intMarioVelocity = (int) marioVerticalVelocity;
        if (marioVerticalVelocity > 0) {
            marioFigure.setLocation(marioFigure.getX(), marioFigure.getY() - intMarioVelocity);
            upOrDown = Direction.UP;
        } else {
            jumping = false;
            upOrDown = Direction.STOP;
        }
    }

    //fall
    public static void gravity() {
        upOrDown = Direction.DOWN;
        marioVerticalVelocity += GRAVITY;
        int intMarioVelocity = (int) marioVerticalVelocity;

        if (marioVerticalVelocity > MAX_VELOCITY) {
            marioVerticalVelocity = MAX_VELOCITY;
        }
        if (marioVerticalVelocity <= MAX_VELOCITY) {
            marioFigure.setLocation(marioFigure.getX(), marioFigure.getY() + intMarioVelocity);
        }
    }

    //Triggers if the user presses spacebar, and readies the bullet jlabel for shooting left or right, depending on the players orientation.
    public static void shoot(Controller c) {
        if (!shooting && fireflower) {

            if (!mute) {
                //Plays bullet sound
                playSound(shootClip);
            }

            //Sets the position of the bullet to the middle of the player, and shows the bullet image
            bullet.setBounds(marioFigure.getX(), marioFigure.getY() + (marioFigure.getHeight() / 2), 44, 18);
            bullet.setLocation(bullet.getX(), bullet.getY());

            //Sets the speed of the bullet, depending on the orientation of the player. Bulletspeed is used when moving the bullet in moveBullet().
            bulletspeed = 20;
            bullet.setIcon(new ImageIcon("assets/images/sprites/bullet_right.png"));

            if (!c.right) {
                bulletspeed = -20;
                bullet.setIcon(new ImageIcon("assets/images/sprites/bullet_left.png"));
            }
            shooting = true;

        }
    }

    private static void shootFireball() {
        if (!bowserShooting && bowserLives!=0) {
            //Plays bullet sound
            if (!mute) {
                playSound(shootClip);
            }

            //Sets the position of the bullet to the middle of the player, and shows the bullet image
            fireball.setBounds(bowser.getX(), bowser.getY() + (bowser.getHeight() / 3), 75, 25);
            fireball.setLocation(fireball.getX(), fireball.getY());

            //Sets the speed of the bullet, depending on the orientation of the player. Bulletspeed is used when moving the bullet in moveBullet().
            fireballspeed = -20;
            fireball.setIcon(fireballLeft);

            if (bowserDirection.equals(Direction.RIGHT)) {
                fireballspeed = 20;
                fireball.setIcon(fireballRight);
            }
            bowserShooting = true;
        }
    }

    //Moves the fireball and hitbox
    public static void moveFireball() {

        fireball.setBounds(fireball.getX() + fireballspeed, fireball.getY(), 75, 25);
        fireball.setLocation(fireball.getX(), fireball.getY());
        fireballHitbox.setBounds(fireball.getX() + fireballspeed, fireball.getY(), 75, 25);
        fireballHitbox.setLocation(fireball.getX(), fireball.getY());

        //Checks if the bullet is outside the screen, and stops shooting if so. It works for both shooting to the right and left.
        if ((fireball.getX() > marioFigure.getX() + 700) || (fireballspeed == -20 && fireball.getX() < marioFigure.getX() - 700)) {
            //the shooting variable is checked every tick in gameTimer, so this will stop the shot.
            bowserShooting = false;
            fireball.setLocation(-100, -100);
            fireballHitbox.setLocation(-100, -100);
        }
    }

    //Moves the bullet and hitbox, until they are out of the screen. This function is called every tick in gameTimer.
    public static void moveBullet() {

        bullet.setBounds(bullet.getX() + bulletspeed, bullet.getY(), 44, 18);
        bullet.setLocation(bullet.getX(), bullet.getY());
        bulletHitbox.setBounds(bullet.getX() + bulletspeed, bullet.getY(), 44, 18);
        bulletHitbox.setLocation(bullet.getX(), bullet.getY());

        //Checks if the bullet is outside the screen, and stops shooting if so. It works for both shooting to the right and left.
        if ((bullet.getX() > marioFigure.getX() + 350) || (bulletspeed == -20 && bullet.getX() < marioFigure.getX() - 350)) {
            //the shooting variable is checked every tick in gameTimer, so this will stop the shot.
            shooting = false;
            bullet.setLocation(-100, -100);
            bulletHitbox.setLocation(-100, -100);
        }
    }


    //generate the boosts
    private static ArrayList<JLabel> generateBoosts() {
        ArrayList<JLabel> boosts = new ArrayList<>();

        for (int i = 0; i < FirstMap.specialCollisionObjects.size(); i++) {
            //special skill
            int gunOrCoin = (int) (Math.random() * 100);

            JLabel boost = new JLabel();

            if (gunOrCoin <= 30 && !noGunMode) {

                gunOrCoinInt.add(0);
                boost.setIcon(new ImageIcon("assets/images/sprites/fireflower.png"));
                //gun
            } else {
                gunOrCoinInt.add(1);
                boost.setIcon(new ImageIcon("assets/images/sprites/coin.png"));
                //coin
            }

            boost.setDoubleBuffered(true);
            boost.setVisible(true);
            boosts.add(boost);
        }
        return boosts;
    }


    //Generates an array of rectangles, which will be hitboxes for the enemies.
    private static ArrayList<Rectangle> generateHitboxesforBoosts() {
        ArrayList<Rectangle> Hitboxes = new ArrayList<>();
        for (int i = 0; i < FirstMap.specialCollisionObjects.size(); i++) {
            Rectangle Hitbox = new Rectangle(-100, -100, 50, 50);
            Hitboxes.add(Hitbox);
        }
        return Hitboxes;
    }

    private static ArrayList<JLabel> coverBlocksForBoosts() {
        ArrayList<JLabel> blocks = new ArrayList<>();

        for (int i = 0; i < FirstMap.specialCollisionObjects.size(); i++) {
            JLabel block = new JLabel();
            block.setIcon(new ImageIcon("assets/images/sprites/block_hit.png"));
            block.setBounds(-1000 + i, -1000 + i, 50, 50);
            block.setLocation(-1000 + i, -1000 + i);
            block.setDoubleBuffered(true);
            block.setVisible(true);
            blocks.add(block);
        }
        return blocks;
    }

    public static void pointSystem(Controller c) {
        //points system
        points--;
        tick++;
        if (tick >= 60) {
            tick = 0;
            seconds++;
        }
        showPoints = "Points: " + points + " ";
        showName = "Player: " + username + " ";
        showTime = String.format("Time: %02d:%02d", seconds / 60, seconds % 60);

        c.firstMap.setTitle(showPoints + "              " + showName + "              " + showTime + "              " + "Lifes:   " + lives);
    }

    public static void checkIfDead(Controller c) {
        if (lives == 0) {
            stopLoopingSound();
            if (!mute) {
                playSound(deathClip);
            }
            JOptionPane.showMessageDialog(null, "You died, game over!");
            stopGame(c);
        }
    }

    public static void marioWins(Controller c) {
        //check if mario wins
        if (marioHitbox.intersects(FirstMap.winningFlag)) {

            if (!mute) {
                //play music once
                stopLoopingSound();
                playSound(levelClearClip);
            }

            JOptionPane.showMessageDialog(null, "You won!");

            //mario win animation

            currentDirection = Direction.STOP;
            saveToFile(showName + showTime + " " + showPoints);

            stopGame(c);
        }
    }

    public static void marioUnderMap(Controller c) {

        if (marioFigure.getY() > groundY && collidingOnFeet) {
            //kill mario
            JOptionPane.showMessageDialog(null, "You died, game over!");
            stopGame(c);

        } else if (marioFigure.getY() > groundY && !collision && !holeFalling) {

            marioFigure.setLocation(marioFigure.getX(), groundY);
        }
    }

    public static void iframe() {
        //iframe
        if (iframe == 1)
        {
            marioFigure.setIcon(marioHit);
            oneSecond++;
        }
        if (oneSecond >= 60) {
            iframe = 0;
            oneSecond = 0;

            if (currentDirection == Direction.LEFT)
            {
                marioFigure.setIcon(marioLeft);
            }
            else {
                marioFigure.setIcon(marioRight);
            }
        }
    }

    public static void changeSpeed() {
        int input;
        try {
            input = (int) Double.parseDouble(JOptionPane.showInputDialog(null, "Current speed is: " + marioSpeed + "\n" + "Change Speed:"));
            if (!(input <= 0)) {
                marioSpeed = input;
            }
        } catch (Exception e) {
            System.out.println("Canceled by user / No input / input containing characters");
        }
    }

    public static void changeGravity() {
        double input;
        try {
            input = Double.parseDouble(JOptionPane.showInputDialog(null, "Current gravity is: " + GRAVITY + "\n" + "Change Gravity:"));
            if (!(input <= 0)) {
                GRAVITY = input;
            }
        } catch (Exception e) {
            System.out.println("Canceled by user / No input / input containing characters");
        }
    }

    public static void changeLives() {
        int input;
        try {
            input = (int) Double.parseDouble(JOptionPane.showInputDialog(null, "Current number of lives is: " + numberOfLives + "\n" + "Change number of lives:"));
            if (!(input <= 0)) {
                numberOfLives = input;
            }
        } catch (Exception e) {
            System.out.println("Canceled by user / No input / input containing characters");
        }
    }

    //update hitbox
    public static void updateHitbox() {
        marioHitbox.setBounds(new Rectangle(new Point(marioFigure.getX(), marioFigure.getY()), marioFigure.getPreferredSize()));
    }
}