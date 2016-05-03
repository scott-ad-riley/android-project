package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 02/05/2016.
 */
public class CavernRenderer {

    private final int mProgram;

    private final String vertexShaderCode = "uniform mat4 uMVPMatrix;attribute vec4 vPosition;void main() {  gl_Position = uMVPMatrix * vPosition;}";
    private final String fragmentShaderCode = "precision mediump float;uniform vec4 vColor;void main() { gl_FragColor = vColor;}";


    private final float[][] mPanes;

    public CavernRenderer(Cavern cavern, int numberOfPanes) {
        mPanes  = new float[numberOfPanes][];
        int vertexShader = MainScreenRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);

        int fragmentShader = MainScreenRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

        for (int i = 0; i < numberOfPanes; i++) {
            mPanes[i] = cavern.generatePane();
        }
    }

    public void draw(float[] mvpMatrix) {
        for (int i=0; i < mPanes.length; i++) {
            CavernPaneRenderer cavernPaneRenderer = new CavernPaneRenderer(mPanes[i], mProgram);
            cavernPaneRenderer.draw(mvpMatrix);
        }
    }



}
