package underwaterflappybird.codeclan.com.underwaterflappybird;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by user on 04/05/2016.
 */
public class CavernTests {

    @Test
    public void generateRandomValueInRoughness() {
        float roughness = 0.02f;
        Cavern cavern = new Cavern(150, roughness);
        float previous1 = 0.6f;
        float result1 = cavern.generateRandomValue(0.6f);
        assertTrue("First number is out of range: " + result1, result1 < previous1 + roughness && result1 > previous1 - roughness);
        float previous2 = 0.3f;
        float result2 = cavern.generateRandomValue(0.3f);
        assertTrue("Second number is out of range: " + result2, result2 < previous2 + roughness && result2 > previous2 - roughness);
        float previous3 = 0.7f;
        float result3 = cavern.generateRandomValue(0.7f);
        assertTrue("Third number is out of range: " + result3, result3 < previous3 + roughness && result3 > previous3 - roughness);
        float previous4 = 0.4f;
        float result4 = cavern.generateRandomValue(0.4f);
        assertTrue("Fourth number is out of range: " + result4, result4 < previous4 + roughness && result4 > previous4 - roughness);
    }

    @Test
    public void generateRandomValueInBounds() {
        float roughness = 0.02f;
        Cavern cavern = new Cavern(150, roughness);
        float previous1 = 0.8f;
        float result1 = cavern.generateRandomValue(0.8f);
        assertTrue("First number is out of bounds: " + result1, result1 <= 0.9f && result1 > previous1 - roughness );
        float previous2 = 0.89f;
        float result2 = cavern.generateRandomValue(0.89f);
        assertTrue("Second number is out of bounds: " + result2, result2 <= 0.9f && result2 > previous2 - roughness );
        float previous3 = 0.17f;
        float result3 = cavern.generateRandomValue(0.17f);
        assertTrue("Third number is out of bounds: " + result3, result3 >= 0.1f && result3 < previous3 + roughness );
        float previous4 = 0.23f;
        float result4 = cavern.generateRandomValue(0.23f);
        assertTrue("Fourth number is out of bounds: " + result4, result4 >= 0.1f && result4 < previous4 + roughness );
    }

}
