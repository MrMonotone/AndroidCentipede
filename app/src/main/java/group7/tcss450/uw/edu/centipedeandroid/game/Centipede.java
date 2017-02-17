package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

import group7.tcss450.uw.edu.centipedeandroid.R;
/**
 * Centipede class that works as a linked list of nodes which are centipede bodies.
 * Class can add new nodes to the list and update the locations of the nodes. Also handles the
 * bitmap images for the nodes.
 *
 * Created by addison on 1/24/2017.
 */

class Centipede {


    /** Starting position for centipede*/
    private final static int CENTIPEDE_Y = -100;

    /** Height divisor for block size */
    private final static int HEIGHT_DIVISOR = 15;

    /** Starting x position for centipede */
    private final static int STARTING_X = 2;

    /** Size of each square in the boards grid*/
    private int mBlockSize;

    /** Size of Y axis of screen */
    private int mScreenY;

    /** X axis size of screen */
    private int mScreenX;

    /** Size of the centipede */
    private int mSize;

    /** Head node of the centipede */
    private CentipedeBody mHead;

    private ArrayList<Centipede> mCentipedes;

    private ArrayList<Bitmap> mBitmaps;

    /**
     * Centipede constructor that takes in a centipedebody
     * and makes it the new head of a new centipede.
     * @param head a centipedebody.
     */
    public Centipede(CentipedeBody head) {
        mHead = head;
        mCentipedes = new ArrayList<>();
        CentipedeBody temp = mHead;
        while (temp != null) {
            mSize++;
            temp = temp.getNext();
        }
    }

    /**
     * Constructor for the centipede class.
     *
     * @param context  The context of the parent constructing the ship.
     * @param screenX  The X dimensions of the parent activity.
     * @param screenY  The Y dimensions of the parent activity.
     * @param block    The size of a block on the screen.
     */
    Centipede(Context context, int screenX, int screenY, int block) {
        mScreenY = screenY;
        mScreenX = screenX;
        mCentipedes = new ArrayList<>();
        mSize = 0;
        mHead = null;
        mBlockSize = block;
        boolean isStart = true;
        createBitmaps(context);
        if (isStart) {
            createCentipede();
            isStart = false;
        }
    }

    public void createBitmaps(Context theContext) {
        mBitmaps = new ArrayList<>();
        Bitmap bodyBitmap = BitmapFactory.decodeResource(theContext.getResources(), R.drawable.centipede);
        Bitmap headSouth = BitmapFactory.decodeResource(theContext.getResources(), R.drawable.centipedeheadsouth);  // fix these drawable names!!!!!!!!!
        Bitmap headEast = BitmapFactory.decodeResource(theContext.getResources(), R.drawable.centipedeheadeast);
        Bitmap headWest = BitmapFactory.decodeResource(theContext.getResources(), R.drawable.westhead);
        mBitmaps.add(bodyBitmap);
        mBitmaps.add(headSouth);
        mBitmaps.add(headEast);
        mBitmaps.add(headWest);
    }

    /**
     * Method that creates the centipede bodies.
     */
    private void createCentipede() {
        int k = 0;
        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                addNode(mBitmaps,mScreenX/STARTING_X , -(mScreenY/HEIGHT_DIVISOR)-k, mScreenX, mScreenY, true);
            } else {
                addNode(mBitmaps,mScreenX/STARTING_X , -(mScreenY/HEIGHT_DIVISOR)-k, mScreenX, mScreenY, false);
            }
            k+=mHead.getHeight();
        }
    }

    public Bitmap getHeadBitmap(int i) {
        return mBitmaps.get(i);
    }

    /**
     * Method that adds a node to the list and checks if the
     * list is null and assigns the first node as the head
     * , otherwise it links the other nodes together that are being
     * added.
     *
     * @param bitmap is the bitmap picture of the node.
     * @param xPos is the x position of the node.
     * @param yPos is the y position of the node.
     */
    private void addNode(ArrayList<Bitmap> bitmap, int xPos, int yPos, int screenX, int screenY, boolean isHead) {
        CentipedeBody newNode = new CentipedeBody(bitmap, xPos, yPos, screenX, screenY, mBlockSize, isHead);
        if (mHead == null) {
            mHead = newNode;
            mCentipedes.add(this);
        } else {
            CentipedeBody temp = mHead;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
        }
        mSize++;
    }

    /**
     *  Method that splits the current centipede where ever the cur node is.
     *
     * @param theCur current node pointed at.
     * @param thePrev the previous node in the list.
     */
    public void splitCentipede(CentipedeBody theCur, CentipedeBody thePrev) {
            if (theCur.getNext() != null) {
                theCur.getNext().setHead(true);
                theCur.getNext().moveDown(theCur.getNext().getYCoord());
                theCur.getNext().setDir(!theCur.getEast());
                theCur.getNext().setRectf();
                if (thePrev != null) {
                    thePrev.setNext(null);
                }

            }
        Centipede newCent = new Centipede(theCur.getNext());
        mCentipedes.add(newCent);
    }
    /**
     * Getter method that returns the head node of the centipede.
     *
     * @return a node that is the head of the centipede.
     */
    CentipedeBody getHead() {
        return mHead;
    }

    /**
     *  Getter that returns the size of the centipede.
     * @return an int of the size of the centipede.
     */
    int getSize() {
        return this.mSize;
    }

    public ArrayList<Centipede> getCentipedes() {
        return mCentipedes;
    }
    /**
     *  Setter that sets the size of the centipede.
     */
    void setSize() {
        if (this.mSize >= 1) {
            this.mSize--;
        }
    }

    /**
     * Method that updates the head node in each centipede and makes each following
     * node take the position of the one ahead of it. Only head nodes use update().
     */
    void update() {
        for (int i = 0; i < mCentipedes.size(); i++) {
            CentipedeBody temp = mCentipedes.get(i).getHead();
            CentipedeBody prev = null;
            float newY = 0;
            float newX = 0;
            float yDir = 0;
            float xDir = 0;
           // int speed = 0;
            while (temp != null) {
                if (temp.getVisible()) {
                    if (prev == null  || temp.isHead()) {
                       // speed = temp.getSpeed();
                        newX = temp.getXCoord();
                        newY = temp.getYCoord();
                        xDir = newX;
                        yDir = newY;
                        temp.update();
                    } else {
                        xDir = temp.getXCoord();
                        yDir = temp.getYCoord();
                        temp.setDirCoord(newX, newY);
                        temp.setRectf();
                    }
                }
                newX = xDir;
                newY = yDir;
                prev = temp;
                temp = temp.getNext();
            }
        }
    }
}
