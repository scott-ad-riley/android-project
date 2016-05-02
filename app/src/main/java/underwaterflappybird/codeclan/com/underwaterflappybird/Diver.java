package underwaterflappybird.codeclan.com.underwaterflappybird;

/**
 * Created by user on 02/05/2016.
 */
public class Diver {
    // times are in seconds
    // distances are in meters
    private float mDownwardSpeed;
    private float mAcceleration;
    private static double speed;

    public Diver(float initialSpeed) {
        mDownwardSpeed = initialSpeed;
        mAcceleration = 9.81f;
    }

    public float getSpeed() {
        return mDownwardSpeed;
    }


    private void updateAndCalculateSpeed(float timeframe) {
        if (mDownwardSpeed < 0) {
            // we need to work out what our new speed is, after the acceleration pulls it downwards
            mDownwardSpeed = mDownwardSpeed - (mAcceleration * timeframe);
        } else {
            // we are travelling downwards, so we just add our new speed due to acceleration
            mDownwardSpeed = mDownwardSpeed + (mAcceleration * timeframe);
        }
    }

    public float move(float timeframe) {
        float distance = mDownwardSpeed * timeframe;
        updateAndCalculateSpeed(timeframe);
        return distance;
    }


    public void boost(float speedBoost) {
        // speedBoost needs to come in as like 5 m/s or something (5.0f)
        mDownwardSpeed = mDownwardSpeed - speedBoost;
    }
}
