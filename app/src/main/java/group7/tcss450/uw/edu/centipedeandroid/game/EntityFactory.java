package group7.tcss450.uw.edu.centipedeandroid.game;


import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 */

public class EntityFactory {

    private static final float SHRINK_VALUE = GameActivity.getBlockSize() / 6;
//    public static MetaEntity createCentipede(int theSize) {
//        MetaEntity centipede = new MetaEntity("why travis");
//        UUID[] segments = new UUID[theSize];
//        for (int i = 0; i < theSize; i++) {
//            segments[i] = createCentipedeSegment(centipede.entity).entity;
//        }
//        centipede.add(new Components.ParentComponent(segments));
//        return centipede;
//    }
//
//    public static MetaEntity createCentipede(UUID[] theSegments) {
//        MetaEntity centipede = new MetaEntity("whyyyy");
//        centipede.add(new Components.ParentComponent(theSegments));
//        return centipede;
//    }

//    public static MetaEntity createCentipedeSegment(UUID theParent) {
//        MetaEntity segment = new MetaEntity("y no default constructor");
//        segment.add(new Components.EntitySize());
//        segment.add(new Components.Health(1));
//        segment.add(new Components.Movable());
//        segment.add(new Components.SegmentComponent(theParent));
//        return segment;
//    }

    /**
     * Method that creates a board entity.
     *
     * @return A MetaEntity object of the board.
     */
    public static MetaEntity createGameBoard() {
        MetaEntity board = new MetaEntity("board");
        return board;
    }

    /**
     * Method that creates a a mushroom entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior.
     *
     * @return A MetaEntity object of the mushroom.
     */
    public static MetaEntity createMushroom() { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity("mushroom");
        mushroom.add(new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize()));
        mushroom.add(new Components.Health(4));
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(R.drawable.shroom));
        return mushroom;
    }

    /**
     * Method that creates a a mushroom entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior.
     *
     * @param p is the position of the mushroom
     * @return A MetaEntity object of the mushroom.
     */
    public static MetaEntity createMushroom(Components.Position p) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity("mushroom");
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize());
        mushroom.add(es);
        mushroom.add(new Components.Health(4));
//        mushroom.add(new Components.Damage(new Random().nextInt(3))); // for now lets give the mushroom some random damage
        mushroom.add(p);
        mushroom.add(new Components.DamagedDrawable(new int[] {R.drawable.shroom, R.drawable.shroom3, R.drawable.shroom2, R.drawable.shroom1}));
        mushroom.add(new Components.HitBox(p.getX(), p.getY(), p.getX() + es.getEntityWidth(), p.getY() + es.getEntityHeight()));
        return mushroom;
    }

    public static MetaEntity createShip() {
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize() * 2,
                GameActivity.getBlockSize());
        Components.Position p = new Components.Position((GameActivity.mWidth / 2),
                (GameActivity.mHeight - GameActivity.mBlockSize));
        MetaEntity ship = new MetaEntity("ship",
                p,
                es,
                new Components.CAndroidDrawable(R.drawable.alienblaster),
                new Components.Touch(),
                new Components.Movable(),
                new Components.HitBox(
                        p.getX() + SHRINK_VALUE,
                        p.getY() + SHRINK_VALUE,
                        p.getX() + (es.getEntityWidth() - SHRINK_VALUE),
                        p.getY() + (es.getEntityHeight() - SHRINK_VALUE)));
        return ship;
    }

    /**
     * Method that creates a bullet entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior. Bullet spawn is controlled by where the ship is.
     *
     * @param theGameView is a reference to the game view.
     * @param x is the x position of the bullet.
     * @param y is the y position of the bullet.
     * @return A MetaEntity object of the bullet.
     */
    public static MetaEntity createBullet(GameView theGameView, float x, float y) {
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize() / 2,
                GameActivity.getBlockSize() / 2);
        MetaEntity bullet = new MetaEntity("bullet", es,
                new Components.CAndroidDrawable(R.drawable.fireball),
                new Components.Health(1),
                new Components.Movable(0, - 100),
                new Components.Position(x, y),
                new Components.Hazard(1),
                new Components.Shoot(),
                new Components.Team(Components.Team.Color.Blue),
                new Components.HitBox(x, y,
                        x + es.getEntityWidth(),
                        y + es.getEntityHeight()));
        return bullet;
    }

    public static MetaEntity createCentBody(float x, float y, UUID parent) {
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize());
        MetaEntity centBody = new MetaEntity("Centipede Body", es,

                new Components.Direction(true),
                new Components.Position(x,y),
                new Components.SegmentComponent(parent),
                new Components.Team(Components.Team.Color.Red),
                new Components.Health(1),
                new Components.HitBox(x + SHRINK_VALUE, y + SHRINK_VALUE,
                        x + es.getEntityWidth() - SHRINK_VALUE,
                        y + es.getEntityHeight() - SHRINK_VALUE));
        return centBody;
    }
    public static void splitCentipede(UUID[] theIDS, UUID theSeg) {
        int head = 0;
        int tail = 0;
        ArrayList<MetaEntity> newCentipedes = new ArrayList<>();
        for (int i = 0; i<theIDS.length; i++) {
            if (theIDS[i] == theSeg) {
                head = (i - 1);
                tail = (i + 1);
            }
        }
        UUID[] leftCent = new UUID[head];
        UUID[] rightCent = new UUID[tail];
        for (int j = 0; j < head; j++) {
            leftCent[j] = theIDS[j];
        }

        for (int k = tail; k < theIDS.length; k++) {
            rightCent[k] = theIDS[k];
        }
        EntityFactory.createCentipede(leftCent);
        EntityFactory.createCentipede(rightCent);
    }

    /**
     * Method that creates a centipede entity.
     * Entity is comprised of components that
     * control behavior. The centipede is made up of several segments.
     *
     * @param theGameView is a reference to the game view.
     * @param theSize is the number of segments in the centipede.
     * @return A MetaEntity object of the centipede.
     */
    public static MetaEntity createCentipede(GameView theGameView, int theSize) {
//        MetaEntity centipede = new MetaEntity();
        UUID[] ids = new UUID[theSize];
        int k = 0;
        MetaEntity centipede = new MetaEntity("Centipede");
        for (int i = 0; i<theSize; i++ ) {
            k += theGameView.mBlockSize;
            MetaEntity temp = EntityFactory.createCentBody((theGameView.mScreenSizeX)/2 - k, 0, centipede.entity);
            if (i == 0) {
                temp.add(new Components.Movable(theGameView.mBlockSize,0));
                temp.add(new Components.CAndroidDrawable(R.drawable.centipedehead));
            } else {
                Components.SegmentMovable sm = new Components.SegmentMovable();
                sm.dx = theGameView.mBlockSize;
                temp.add(new Components.CAndroidDrawable(R.drawable.centipede));
                temp.add(sm);
                temp.add(new Components.Movable(0,0));
            }
            ids[i] = temp.entity;
        }
        centipede.add(new Components.CentipedeID(ids));
        return centipede;
    }

    /**
     * Method that creates a centipede entity.
     * Entity is comprised of components that
     * control behavior. This centipede takes a list of
     * already created segements for a new centipede to be created.
     *
     * @param theIDS is a list of segement ids
     * @return A MetaEntity object of the centipede.
     */
    public static MetaEntity createCentipede(UUID[] theIDS) {
        MetaEntity centipede = new MetaEntity("centipede");
        centipede.add(new Components.CentipedeID(theIDS));
        return centipede;
    }
    /**
     * Method that creates a scorpian entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior. Scorpian moves in a horizontal line on the screen.
     *
     * @param theGameView is a reference to the game view.
     * @param x is the x position of the scorpian.
     * @param y is the y position of the scorpian.
     * @return A MetaEntity object of the scorpian.
     */
    public static MetaEntity createScorpian(GameView theGameView, float x, float y) {
        MetaEntity scorpian = new MetaEntity("scorpion");
        scorpian.add(new Components.EntitySize(GameActivity.getBlockSize() + 40,
                GameActivity.getBlockSize() + 40)); // POSSIBLE CHANGE SIZE OF THIS CREATURE CURRENT NUMBERS ARE TEST NUMBERS
        scorpian.add(new Components.CAndroidDrawable(R.drawable.scorpianeast));
        scorpian.add(new Components.Movable(1/2,0));
        scorpian.add(new Components.Position(x,y));
//        scorpian.add(new Components.Health());
        return scorpian;
    }
}
