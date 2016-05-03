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

    private float[] topColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private float[] bottomColor = { 0.23671875f, 0.26953125f, 0.22265625f, 1.0f };
    private final float[][][] mPanes;

//    private final ArrayList<float[]> mDiscarded;


    public CavernRenderer(Cavern cavern, int numberOfPanes) {
        mPanes  = new float[numberOfPanes][][];
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
            CavernPaneRenderer topCavernPaneRenderer = new CavernPaneRenderer(mPanes[i][0], mProgram, topColor);
            topCavernPaneRenderer.draw(mvpMatrix);
            CavernPaneRenderer bottomCavernPaneRenderer = new CavernPaneRenderer(mPanes[i][1], mProgram, bottomColor);
            bottomCavernPaneRenderer.draw(mvpMatrix);
        }
    }


    private static void rotate(int[] arr, int order) {
        if (arr == null || arr.length==0 || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        if(order > arr.length){
            order = order %arr.length;
        }

        //length of first part
        int a = arr.length - order;

        reverse(arr, 0, a-1);
        reverse(arr, a, arr.length-1);
        reverse(arr, 0, arr.length-1);

    }

    private static void reverse(int[] arr, int left, int right){
        if(arr == null || arr.length == 1)
            return;

        while(left < right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }



}
