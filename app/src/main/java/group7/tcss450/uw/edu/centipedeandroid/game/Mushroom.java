package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import group7.tcss450.uw.edu.centipedeandroid.R;

/**
 * Created by addis on 2/16/2017.
 */

public class Mushroom {

    private float mXCoord;

    private float mYCoord;

    private int mBlock;

    private Context mContext;

    private int mHP;

    private Bitmap mBitmap;

    private int mWidth;

    private int mHeight;

    private Bitmap[] mShrooms;

    private RectF mRectf;

    public Mushroom(float xPos, float yPos, int blocksize, Context context) {
        mXCoord = xPos;
        mYCoord = yPos;
        mBlock = blocksize;
        mContext = context;
        mWidth = blocksize;
        mHeight = blocksize;
        mHP = 4;
        mShrooms = new Bitmap[4];
        mRectf = new RectF();
        createShroomBitmaps();
        setRectf();
        mBitmap = Bitmap.createScaledBitmap(mShrooms[mHP-1], mWidth, mHeight, false);
    }

    public int getHP() {
        return mHP;
    }

    private void createShroomBitmaps() {
        Bitmap shroom = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shroom);
        Bitmap shroom3HP = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shroom3);
        Bitmap shroom2HP = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shroom2);
        Bitmap shroom1HP = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shroom1);
        mShrooms[0] = shroom1HP;
        mShrooms[1] = shroom2HP;
        mShrooms[2] = shroom3HP;
        mShrooms[3] = shroom;
        ;
    }

    public void setShroomHP() {
        mHP--;
        mBitmap = Bitmap.createScaledBitmap(mShrooms[mHP-1], mWidth, mHeight, false);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public float getXCoord() {
        return mXCoord;
    }

    public float getYCoord() {
        return mYCoord;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public RectF getRectF() {
        return mRectf;
    }

    public void setRectf() {
        mRectf.left = mXCoord;
        mRectf.right = mXCoord + mWidth;
        mRectf.top = mYCoord;
        mRectf.bottom = mYCoord + mHeight;
    }
    public void update() {

    }
}
