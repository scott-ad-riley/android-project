package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 02/05/2016.
 */
public class MainScreenRenderer implements GLSurfaceView.Renderer {

    private DiverRenderer mDiver;


    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float[] mRotationMatrix = new float[16];

    private int mScreenHeight = 1196;
    private int mScreenWidth = 800;

    public volatile float mAngle;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        mDiver = new DiverRenderer(new Diver(0));
        GLES20.glViewport(0, 0, mScreenWidth, mScreenHeight);

        // maybe setup his initial position in here?
    }

    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Set the camera position (View matrix)
        // in the below method
        // changing arg 2: breaks it
        // changing arg 3: clips the left side of the viewport
        // changing arg 4: clips the top of the viewport
        // changing arg 5: seems to scale, going down to -2 makes it disappear, -4 makes it smaller
        // changing arg 6: clips the viewport on either side (+ive is right, -ive is left)
        // changing arg 7: same as above, top and bottom
        // changing arg 8: nothing
        // changing arg 9: some sort of rotation
        // changing arg 10: nothing
        // changing arg 11: nothing
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Create a rotation transformation for the triangle
        // the following two lines generate an angle from the system clock
        long time = SystemClock.uptimeMillis() % 4000L;
        mAngle = 0.090f * ((int) time);

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mDiver.draw(scratch);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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
