package model;

import controller.Controller;
import view.Direction;
import view.FirstMap;

import java.awt.*;

import static controller.Controller.*;
import static model.model.*;

public class CollisionFunction
{
    public static void checkCollision(Controller c)
    {
        collision = false;
        collidingOnFeet = false;

        for (int i = 0; i < FirstMap.specialCollisionObjects.size(); i++)
        {

            if (marioHitbox.intersects(FirstMap.specialCollisionObjects.get(i)))
            {

                //head
                if (upOrDown == Direction.UP && marioFigure.getY() > FirstMap.specialCollisionObjects.get(i).y)
                {
                    if(!mute) {
                        playSound(blockHitClip);
                    }

                    marioFigure.setLocation(marioFigure.getX(), FirstMap.specialCollisionObjects.get(i).y + FirstMap.specialCollisionObjects.get(i).height);

                    currentDirection = Direction.STOP;

                    upOrDown = Direction.STOP;

                    jumping = false;

                    //boosts

                    if (gunOrCoinInt.get(i) != 3)
                    {
                        boostsHitboxes.set(i, new Rectangle(FirstMap.specialCollisionObjects.get(i).x, FirstMap.specialCollisionObjects.get(i).y - 50, 50, 50));

                        boosts.get(i).setBounds(boostsHitboxes.get(i));

                        boosts.get(i).setLocation(FirstMap.specialCollisionObjects.get(i).x, (FirstMap.specialCollisionObjects.get(i).y - 50));


                        //change i block to other jlabel
                        coverBlocks.get(i).setLocation(FirstMap.specialCollisionObjects.get(i).x, FirstMap.specialCollisionObjects.get(i).y);

                    }
                }

                //feet
                else if ((upOrDown == Direction.STOP || upOrDown == Direction.DOWN) && ((marioFigure.getY()  < FirstMap.specialCollisionObjects.get(i).y-54) || (marioFigure.getY()  < FirstMap.specialCollisionObjects.get(i).y)))
                {

                    upOrDown = Direction.STOP;

                    jumping = false;

                    falling = false;

                    collidingOnFeet = true;
                    //Resets marios position if clipping into blocks occurs.
                    marioFigure.setLocation(marioFigure.getX(), FirstMap.specialCollisionObjects.get(i).y - 62);
                }

                //if collision from sides
                else if (currentDirection == Direction.RIGHT && marioFigure.getX() < FirstMap.specialCollisionObjects.get(i).x && !collidingOnFeet)
                {
                    currentDirection = Direction.STOP;
                }
                else if (currentDirection == Direction.LEFT && marioFigure.getX() > FirstMap.specialCollisionObjects.get(i).x && !collidingOnFeet)
                {
                    currentDirection = Direction.STOP;
                }


                collision = true;
            }
        }

        for (int i = 0; i < FirstMap.nonSpecialCollisionObjects.size(); i++) {
            if (marioHitbox.intersects(FirstMap.nonSpecialCollisionObjects.get(i)))
            {
                //head
                //if going up and mario is below block that collides
                if (upOrDown == Direction.UP && marioFigure.getY() > FirstMap.nonSpecialCollisionObjects.get(i).y)
                {
                    if(!mute) {
                        playSound(blockHitClip);
                    }
                    //move mario to position below block
                    marioFigure.setLocation(marioFigure.getX(), FirstMap.nonSpecialCollisionObjects.get(i).y + FirstMap.nonSpecialCollisionObjects.get(i).height);

                    //stop mario direction
                    currentDirection = Direction.STOP;

                    //not going up anymore
                    upOrDown = Direction.STOP;

                    //deactivate jumping
                    jumping = false;

                }

                //feet
                //if going down or stadining still and mario is above the block that collides
                else if ((upOrDown == Direction.STOP || upOrDown == Direction.DOWN) && ((marioFigure.getY()  < FirstMap.nonSpecialCollisionObjects.get(i).y-54) || (marioFigure.getY()  < FirstMap.nonSpecialCollisionObjects.get(i).y)))
                {

                    //not going down anymore
                    upOrDown = Direction.STOP;

                    //stop jumping
                    jumping = false;

                    //stop falling
                    falling = false;

                    //feet are touching a block
                    collidingOnFeet = true;

                    //Resets marios position if clipping into blocks occurs.
                    marioFigure.setLocation(marioFigure.getX(), FirstMap.nonSpecialCollisionObjects.get(i).y - 62);
                }

                //if collision from sides
                else if (currentDirection == Direction.RIGHT && marioFigure.getX() < FirstMap.nonSpecialCollisionObjects.get(i).x && !collidingOnFeet)
                {
                    currentDirection = Direction.STOP;
                }
                else if (currentDirection == Direction.LEFT && marioFigure.getX() > FirstMap.nonSpecialCollisionObjects.get(i).x && !collidingOnFeet)
                {
                    currentDirection = Direction.STOP;
                }


                collision = true;
            }
        }


        for (int i = 0; i < enemyArray.size(); i++)
        {

            //bullet inteacts with goomba
            if (bulletHitbox.intersects(enemyHitboxes.get(i)))
            {
                enemyArray.get(i).setLocation(enemyArray.get(i).getX(),-300);
                if(!mute) {
                    playSound("assets/sound/stompenemy.wav");
                }
                enemyHitboxes.get(i).setLocation(enemyArray.get(i).getX(),enemyArray.get(i).getY());

                //make bullet disapear
                bullet.setLocation(marioFigure.getX() + 800, bullet.getY());
                bulletHitbox.setLocation(bullet.getX(), bullet.getY());


                points += 500;

            }

            //mario interact with goombas
            if (marioHitbox.intersects(enemyHitboxes.get(i)))
            {

                if (iframe == 0)
                {
                    //if mario is over target
                    if ((marioFigure.getY() + 40) <= enemyHitboxes.get(i).y)
                    {
                        if(!mute) {
                            playSound("assets/sound/stompenemy.wav");
                        }

                        enemyArray.get(i).setLocation(enemyArray.get(i).getX(),-300);
                        points += 500;

                        //jump again
                        c.action = Direction.UP;
                        upOrDown = Direction.STOP;
                        jumping = false;
                        falling = false;
                    }

                    //if mario gets hit from right
                    else if (marioFigure.getX() < enemyHitboxes.get(i).x)
                    {
                        fireflower = false;

                        marioFigure.setLocation(marioFigure.getX() - 10, marioFigure.getY());

                        marioFigure.setLocation(marioFigure.getX() - 20, marioFigure.getY());

                        iframe = 1;

                        points -= 100;

                        lives--;

                    }
                    //if mario gets hit from left
                    else if (marioFigure.getX() > enemyHitboxes.get(i).x)
                    {
                        fireflower = false;

                        marioFigure.setLocation(marioFigure.getX() + 10, marioFigure.getY());

                        marioFigure.setLocation(marioFigure.getX() + 20, marioFigure.getY());

                        iframe = 1;

                        points -= 100;

                        lives--;

                    }
                }
            }
        }

        //boosts
        for (int i = 0; i < boostsHitboxes.size(); i++)
        {
            if (marioHitbox.intersects(boostsHitboxes.get(i)))
            {
                if(!mute) {
                    playSound(boostsClip);
                }

                if (gunOrCoinInt.get(i) == 0)
                {
                    //give a gun to mario
                    if (fireflower) {
                        points+=2000;
                    }

                    fireflower = true;

                    boosts.get(i).setLocation(-100,-100);
                    boostsHitboxes.get(i).setLocation(-100,-100);

                    gunOrCoinInt.set(i, 3);
                }
                else {
                    boosts.get(i).setLocation(-100,-100);
                    boostsHitboxes.get(i).setLocation(-100,-100);

                    points += 1000;

                    gunOrCoinInt.set(i, 3);
                }
            }
        }

        //bowser collision with mario
        if (marioHitbox.intersects(bowserHitbox))
        {
            if (iframe == 0) {
                //if mario is over target
                if ((marioFigure.getY() + 50) < bowserHitbox.y && bowserVunerabel) {
                    if (!mute) {
                        playSound("assets/sound/stompenemy.wav");
                    }

                    bowserLives--;

                    //kill bowser
                    if (bowserLives == 0) {
                        bowser.setLocation(bowserHitbox.x, -300);
                        bowserHitbox.setLocation(bowserHitbox.x, -300);
                        points += 5000;
                    }

                    //jump again
                    c.action = Direction.UP;
                    upOrDown = Direction.STOP;
                    jumping = false;
                    falling = false;
                }

                //if mario gets hit from right
                else if (marioFigure.getX() < bowserHitbox.x) {
                    fireflower = false;

                    marioFigure.setLocation(marioFigure.getX() - 10, marioFigure.getY());

                    marioFigure.setLocation(marioFigure.getX() - 20, marioFigure.getY());

                    iframe = 1;

                    points -= 100;

                    lives--;

                }
                //if mario gets hit from left
                else {
                    fireflower = false;

                    marioFigure.setLocation(marioFigure.getX() + 10, marioFigure.getY());

                    marioFigure.setLocation(marioFigure.getX() + 20, marioFigure.getY());

                    iframe = 1;

                    points -= 100;

                    lives--;
                }
            }
        }

        if (bulletHitbox.intersects(bowserHitbox))
        {
            bowserLives--;

            //kill bowser
            if (bowserLives == 0)
            {
                bowser.setLocation(bowserHitbox.x, -300);
                bowserHitbox.setLocation(bowserHitbox.x, -300);
                points += 5000;
            }

            //make bullet disapear
            bullet.setLocation(marioFigure.getX() + 800, bullet.getY());
            bulletHitbox.setLocation(bullet.getX(), bullet.getY());
        }

        //check if fireball hits mario

        if (fireballHitbox.intersects(marioHitbox))
        {
            if (iframe == 0) {

                if (marioFigure.getX() < fireball.getX()) {
                    marioFigure.setLocation(marioFigure.getX() - 10, marioFigure.getY());

                    marioFigure.setLocation(marioFigure.getX() - 20, marioFigure.getY());

                    iframe = 1;

                    points -= 100;

                    lives--;


                } else {
                    marioFigure.setLocation(marioFigure.getX() + 10, marioFigure.getY());

                    marioFigure.setLocation(marioFigure.getX() + 20, marioFigure.getY());

                    iframe = 1;

                    points -= 100;

                    lives--;
                }
            }

        }

    }
}
