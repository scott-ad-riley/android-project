package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 02/05/2016.
 */
public class MainScreenRenderer implements GLSurfaceView.Renderer {

    private DiverRenderer mDiver;
    private CavernRenderer mCavern;


    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPCavernMatrix = new float[16];
    private final float[] mMVPDiverMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mCavernViewMatrix = new float[16];
    private final float[] mDiverViewMatrix= new float[16];

    private float[] mRotationMatrix = new float[16];

    private int mScreenHeight = 1196;
    private int mScreenWidth = 800;

    public volatile float mTranslateValue;
    private float mInitialTime;

    private float mLastFrameTime;

    private float mDiverDistance;

    private float mCumulativePaneWidth;
    private int mPaneCounter;

    float mInitialDiverCoords[] = {
            -0.05f,  0.05f, 0.0f,  // top right x, y, z
            -0.05f, -0.05f, 0.0f,  // bottom right x, y, z
            0.05f, -0.05f, 0.0f,  // bottom left x, y, z
            0.05f,  0.05f, 0.0f };  // top left x, y, z
    private float mPaneWidth;


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        mDiver = new DiverRenderer(new Diver(0), mInitialDiverCoords);
        mCavern = new CavernRenderer(150);
        GLES20.glViewport(0, 0, mScreenWidth, mScreenHeight);
        mInitialTime = SystemClock.uptimeMillis();
        mLastFrameTime = mInitialTime;
        mDiverDistance = 0;
        mPaneWidth = mCavern.getPaneWidth();
        mCumulativePaneWidth = 0;
    }

    public void onDrawFrame(GL10 unused) {
        float[] scratchCavern = new float[16];
        float[] scratchDiver = new float[16];
        float newTime = SystemClock.uptimeMillis();
        float frameTime = newTime - mLastFrameTime;
        float totalTime = newTime - mInitialTime;
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Set the camera position (View matrix)
        // in the below method
        // changing arg 2: breaks it
        // changing arg 3: rotates left side towards user and right side away
        // changing arg 4: same as above but top and bottom
        // changing arg 5: seems to scale, going down to -2 makes it disappear, -4 makes it smaller
        // changing arg 6: clips the viewport on either side (+ive is right, -ive is left)
        // changing arg 7: same as above, top and bottom
        // changing arg 8: nothing
        // changing arg 9: some sort of rotation
        // changing arg 10: nothing
        // changing arg 11: nothing
        Matrix.setLookAtM(mCavernViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.setLookAtM(mDiverViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        mLastFrameTime = newTime;

        mTranslateValue = 0.0006f * ((int) totalTime);

        if (mTranslateValue > mCumulativePaneWidth) {
            mPaneCounter++;
//            Log.d("UFBLog", "new pane" + mPaneCounter);
            mCavern.appendNewPane();
            mCumulativePaneWidth += mPaneWidth;
        }

        Matrix.translateM(mCavernViewMatrix, 0, mTranslateValue, 0, 0); // translation to the left
        // I need to call .move() on my diver
        float distance = mDiver.moveDiver(frameTime / 1000) / - 100;
        mDiverDistance += distance;
        Matrix.translateM(mDiverViewMatrix, 0, 0, mDiverDistance, 0); // translation downward

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPCavernMatrix, 0, mProjectionMatrix, 0, mCavernViewMatrix, 0);
        Matrix.multiplyMM(mMVPDiverMatrix, 0, mProjectionMatrix, 0, mDiverViewMatrix, 0);


        // Create a rotation transformation for the triangle
        // the following two lines generate an angle from the system clock

//        mAngle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, -1.0f);



        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratchCavern, 0, mMVPCavernMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(scratchDiver, 0, mMVPDiverMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mDiver.draw(scratchDiver);
        // Draw Cavern
        mCavern.draw(scratchCavern);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void resetTimer() {
        mInitialTime = SystemClock.uptimeMillis();
    }

    public void boostDiver() {
        float boost = 60f;
        mDiver.boostDiver(boost);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}




//    static float cavernCoords[] = {
//            1.82f, 0.9f, 0.0f,
//            1.65f, 0.9f, 0.0f,
//            1.65f, 1.0f, 0.0f,
//            1.82f, 1.0f, 0.0f };