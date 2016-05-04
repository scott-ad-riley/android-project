package underwaterflappybird.codeclan.com.underwaterflappybird;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DiverTests {
    @Test
    public void getNewDistanceAfterSecond() {
        Diver diver = new Diver(0);
        diver.move(1.0f);
        float expectedSpeed = 9.81f;
        Assert.assertEquals(expectedSpeed, diver.getSpeed(), 0.02);
    }

    @Test
    public void getNewDistanceAfterTapping() {
        Diver diver = new Diver(0);
        diver.boost(5.0f);
        float expectedSpeed = -5.0f;
        Assert.assertEquals(expectedSpeed, diver.getSpeed(), 0.02);
    }

    @Test
    public void getNewDistanceAfterMovingThenTapping() {
        Diver diver = new Diver(0);
        diver.move(1.0f);
        diver.boost(5.0f);
        float expectedSpeed = 4.81f;
        Assert.assertEquals(expectedSpeed, diver.getSpeed(), 0.02);
    }


}