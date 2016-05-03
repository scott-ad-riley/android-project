package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.util.Log;

import java.util.Random;

/**
 * Created by user on 02/05/2016.
 */
public class Cavern {
    private float mCurrentTop;
    private float mCurrentBottom;
    private float mCurrentXCoord;
    private float mMaxTop;
    private float mMaxBottom;
    private float mWidth;

    // when we want to be able to generate panes in the cavern
    // we need to setup with a default X of 0
    // we also need to accept a default per pane (based on how many we want to draw)
    public Cavern(float initialTop, float initialBottom) {
        mWidth = 0.02f;
        mCurrentTop = initialTop;
        mCurrentBottom = initialBottom;
        mCurrentXCoord = 1.82f; // this needs to be the leftmost value for the window
        mMaxBottom = 50 / 100;
    }

    public float[][] generatePane() {
        float[][] result = new float[2][12];
        int min = 1;
        int max = 50;
        float height = (float) randInt(min, max) / 100;

        result[0][0] = mCurrentXCoord; // bottom left X
        result[0][1] = 1.0f - height; // bottom left Y
        result[0][2] = 0.0f;
        result[0][3] = mCurrentXCoord - mWidth; // bottom right X
        result[0][4] = 1.0f - height; // bottom right Y
        result[0][5] = 0.0f;
        result[0][6] = mCurrentXCoord - mWidth; // top right X
        result[0][7] = 1.0f; // top right Y
        result[0][8] = 0.0f;
        result[0][9] = mCurrentXCoord; // top left X
        result[0][10] = 1.0f; // top left Y
        result[0][11] = 0.0f;

        result[1][0] = mCurrentXCoord; // bottom left X
        result[1][1] = -1.0f; // bottom left Y
        result[1][2] = 0.0f;
        result[1][3] = mCurrentXCoord - mWidth; // bottom right X
        result[1][4] = -1.0f; // bottom right Y
        result[1][5] = 0.0f;
        result[1][6] = mCurrentXCoord - mWidth; // top right X
        result[1][7] = -1.0f + (mMaxBottom + height); // top right Y
        result[1][8] = 0.0f;
        result[1][9] = mCurrentXCoord; // top left X
        result[1][10] = -1.0f + (mMaxBottom + height); // top left Y
        result[1][11] = 0.0f;

        mCurrentXCoord -= mWidth;

        return result;
    }

    private int randInt(int min, int max) {
        Random rand;

        rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}


//        { // example coords for a cavern ceiling square in top left on landscape
//                1.82f, 0.9f, 0.0f,
//                1.65f, 0.9f, 0.0f,
//                1.65f, 1.0f, 0.0f,
//                1.82f, 1.0f, 0.0f  };