package assertion;

import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import readers.Log;

public class Assertions {
    private static SoftAssert softAssert = new SoftAssert();

    public Assertions assertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
        return this;
    }

    public Assertions assertFalse(boolean condition, String message) {
        softAssert.assertFalse(condition, message);
        return this;
    }

    public Assertions assertEquals(String actual, String expected, String message) {
        softAssert.assertEquals(actual, expected, message);
        return this;
    }
    public Assertions assertNotEquals(String actual, String expected, String message) {
        softAssert.assertNotEquals(actual, expected, message);
        return this;
    }
    public static void assertAll(ITestResult result) {
        try {
            softAssert.assertAll();
        }
        catch (AssertionError e)
        {
            Log.error("Assertion failed:", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }

}
