package underwaterflappybird.codeclan.com.underwaterflappybird;

/**
 * Created by user on 02/05/2016.
 */
public class Diver {
    // times are in seconds
    // distances are in meters
    private float mDownwardSpeed;

    private static double speed;
    private final float mAcceleration = 100f;

    public Diver(float initialSpeed) {
        mDownwardSpeed = initialSpeed;
    }

    public float getSpeed() {
        return mDownwardSpeed;
    }

    private void updateAndCalculateSpeed(float timeframe) {
        mDownwardSpeed = mDownwardSpeed + (mAcceleration * timeframe);
    }

    public float move(float timeframe) {
        float distance = mDownwardSpeed * timeframe;
        updateAndCalculateSpeed(timeframe);
        return distance;
    }

    public void boost(float speedBoost) {
        mDownwardSpeed = mDownwardSpeed - speedBoost;
    }

    public void boost() {
//        mDownwardSpeed = mDownwardSpeed - 0.5f;
    }

}
