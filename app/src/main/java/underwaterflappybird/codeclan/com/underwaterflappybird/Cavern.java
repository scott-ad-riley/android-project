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
    }

    public float[] generatePane() {
        int min = 1;
        int max = 10;
        float height = (float) randInt(min, max) / 10;

        float[] result = new float[12];
        result[0] = mCurrentXCoord; // bottom left X
        result[1] = 1.0f - height; // bottom left Y
        result[2] = 0.0f;
        result[3] = mCurrentXCoord - mWidth; // bottom right X
        result[4] = 1.0f - height; // bottom right Y
        result[5] = 0.0f;
        result[6] = mCurrentXCoord - mWidth; // top right X
        result[7] = 1.0f; // top right Y
        result[8] = 0.0f;
        result[9] = mCurrentXCoord; // top left X
        result[10] = 1.0f; // top left Y
        result[11] = 0.0f;
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