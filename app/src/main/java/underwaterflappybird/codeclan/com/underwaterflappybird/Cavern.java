package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.util.Log;

/**
 * Created by user on 02/05/2016.
 */
public class Cavern {
//    private float mCurrentTop;
//    private float mCurrentBottom;
//    private float mMaxTop;
    private float mCurrentXCoord;
    private final float mWidth;

    private float mRoughness;

    private final int mScaler = 1;
    private final float mMiddlePanel = 1.1f;


    // when we want to be able to generate panes in the cavern
    // we need to setup with a default X of 0
    public Cavern(float paneWidth) {
        mWidth = paneWidth;
        mCurrentXCoord = 1.82f;
        mRoughness = 0.15f;
    }

    public Cavern(int numberOfPanes, float roughness) {
        this(numberOfPanes / 1.82f);
        mRoughness = roughness;
    }


    public float generateRandomValue(float prevValue) {
        // get a random number to determine direction
        // if it's above 0.5, we go up, if below, we go down
        float direction = (float) Math.random();

        // get a random number to determine distance
        float distance = (float) Math.random();

        // this gets reduced to the range that roughness will allow
        float result = distance * mRoughness;

        // now we check it isn't out of the bounds of the view
        if (direction > 0.5f) {
            result = prevValue + result;
        } else if (direction < 0.5f) {
            result = prevValue - result;
        }
        if (result > 0.9f) {
            result = 0.9f;
        }
        if (result < 0.1f) {
            result = 0.1f;
        }
        return result;
    }

    public float[][] generatePane(float prevHeight) {
        float[][] result = new float[3][12];
        float value = generateRandomValue(prevHeight);
//        Log.d("UFBLogValue", "" + value);
//        float value = (float) Math.random();
//        value *= 10;
//        if (value > prevHeight + 0.2) value = prevHeight;
//        if (value < prevHeight - 0.2) value = 0;
//
//        int min = ((int) prevHeight * 100) - 1;
//        int max = ((int) prevHeight * 100) + 1;
//        Log.d("UFBLogHeights", "min:" + min + " max:" + max);
//        float height = (float) randInt(min, max) / 100;
        result[0][0] = mCurrentXCoord; // bottom left X
        result[0][1] = 1.0f - value; // bottom left Y
        result[0][2] = 0.0f;
        result[0][3] = mCurrentXCoord - mWidth; // bottom right X
        result[0][4] = 1.0f - value; // bottom right Y
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
        result[1][7] = 1.0f - (mMiddlePanel + value); // top right Y
        result[1][8] = 0.0f;
        result[1][9] = mCurrentXCoord; // top left X
        result[1][10] = 1.0f - (mMiddlePanel + value); // top left Y
        result[1][11] = 0.0f;

        mCurrentXCoord -= mWidth;
        result[2][0] = value;
        return result;
    }

    public float getPaneWidth() {
        return mWidth;
    }
}


//        { // example coords for a cavern ceiling square in top left on landscape
//                1.82f, 0.9f, 0.0f,
//                1.65f, 0.9f, 0.0f,
//                1.65f, 1.0f, 0.0f,
//                1.82f, 1.0f, 0.0f  };