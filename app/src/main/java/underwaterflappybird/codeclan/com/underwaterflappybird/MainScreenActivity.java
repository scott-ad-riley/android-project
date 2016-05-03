package underwaterflappybird.codeclan.com.underwaterflappybird;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Created by user on 02/05/2016.
 */
public class MainScreenActivity extends Activity {

    private GLSurfaceView mGLView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CanvasPlayLog:", "OpenGLES20Activity has loaded");
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    private class MyGLSurfaceView extends GLSurfaceView {

        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private float mPreviousX;
        private float mPreviousY;

        private final MainScreenRenderer mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            setEGLContextClientVersion(2);

            mRenderer = new MainScreenRenderer();
            // makes it full screen (removing the clock bar at the top)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRenderer(mRenderer);
//            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.

            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("WebGLLog", "X:" + x);
                    Log.d("WebGLLog", "Y:" + y);
                    Log.d("WebGLLog", "getHeight():" + getHeight());
                    Log.d("WebGLLog", "getWidth():" + getWidth());
                    // so we can capture a click handler
                    requestRender();
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2) {
                        dx = dx * -1 ;
                    }

                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2) {
                        dy = dy * -1 ;
                    }

//                    mRenderer.setAngle(
//                            mRenderer.getAngle() +
//                                    ((dx + dy) * TOUCH_SCALE_FACTOR));
//                    requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        }
    }

}
