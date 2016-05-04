package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.opengl.GLES20;

/**
 * Created by user on 02/05/2016.
 */
public class CavernRenderer {

    private final int mProgram;
    private final Cavern mCavern;
    private final int mNumberOfPanes;
    private float mLastPaneHeight;

    private final String vertexShaderCode = "uniform mat4 uMVPMatrix;attribute vec4 vPosition;void main() {  gl_Position = uMVPMatrix * vPosition;}";
    private final String fragmentShaderCode = "precision mediump float;uniform vec4 vColor;void main() { gl_FragColor = vColor;}";

    private float[] topColor = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private float[] bottomColor = { 0.23671875f, 0.26953125f, 0.22265625f, 1.0f };
    private final float[][][] mPanes;

//    private final ArrayList<float[]> mDiscarded;


    public CavernRenderer(int numberOfPanes) {
        mNumberOfPanes = numberOfPanes;
        mPanes  = new float[mNumberOfPanes][][];
        mCavern = new Cavern(3.64f / numberOfPanes);
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
        mLastPaneHeight = 0.05f;
        // not sure why i need to do this in reverse
        for (int i = numberOfPanes - 1; i >= 0; i--) {
            mPanes[i] = mCavern.generatePane(mLastPaneHeight);
            mLastPaneHeight = mPanes[i][2][0];
        }
        mLastPaneHeight = mPanes[0][2][0];
    }

    public void draw(float[] mvpMatrix) {
        for (int i=0; i < mPanes.length; i++) {
            CavernPaneRenderer topCavernPaneRenderer = new CavernPaneRenderer(mPanes[i][0], mProgram, topColor);
            topCavernPaneRenderer.draw(mvpMatrix);
            CavernPaneRenderer bottomCavernPaneRenderer = new CavernPaneRenderer(mPanes[i][1], mProgram, bottomColor);
            bottomCavernPaneRenderer.draw(mvpMatrix);
        }
    }

    public void appendNewPane() {
        rotatePanes(1);
//        Log.d("UFBLogPane", "last:" + positionToString(mPanes[mNumberOfPanes - 1]));

//        for (int x = 0; x <= mPanes.length-1; x++){
//            mPanes[(x+1) % mPanes.length ] = mPanes[x];
//        }
        float [][] newPane = mCavern.generatePane(mLastPaneHeight);
        mPanes[mNumberOfPanes - 1] = newPane;
        mLastPaneHeight = newPane[2][0];
    }

    private void rotatePanes(int order) {
        if (mPanes == null || mPanes.length==0 || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        if(order > mPanes.length){
            order = order %mPanes.length;
        }

        //length of first part
        int a = mPanes.length - order;

        reverse(0, a-1);
        reverse(a, mPanes.length-1);
        reverse(0, mPanes.length-1);

        ///
//        if (mPanes == null || order < 0) {
//            throw new IllegalArgumentException("Illegal argument!");
//        }
//
//        for (int i = 0; i < order; i++) {
//            for (int j = mPanes.length - 1; j > 0; j--) {
//                float[][] temp = mPanes[j];
//                mPanes[j] = mPanes[j - 1];
//                mPanes[j - 1] = temp;
//            }
//        }
        ///
    }

    private void reverse(int left, int right){
        if(mPanes == null || mPanes.length == 1)
            return;

        while(left < right){
            float[][] temp = mPanes[left];
            mPanes[left] = mPanes[right];
            mPanes[right] = temp;
            left++;
            right--;
        }

    }

    private String positionToString(float[][] position) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < position[0].length; i++) {
            sb.append(position[0][i] + " ");
        }
        return sb.toString();
    }


    public float getPaneWidth() {
        return mCavern.getPaneWidth();
    }


    public Cavern getCavern() {
        return mCavern;
    }
}
