package testAjaxMobileApp.authorizationPage;

import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertTrue;


public class alliureTest {

    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("start");
    }

    @Test
    public void firstSimpleTest() {
        assertTrue("Result not equals to 4", 2 * 2 == 4);
    }

    @Test
    public void secondSimpleTest() {
        assertTrue("Result not equals to 6", 2 * 3 == 4);
    }

    @Test
    public void thirdSimpleTest() {
        assertTrue("Result not equals to 8", 2 * 4 == 8);
    }
}
