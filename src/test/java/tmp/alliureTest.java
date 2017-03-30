package tmp;

import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertTrue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Reporter;
import pages.Base;
import utils.Setup;


public class alliureTest extends Base{
    private Setup s = new Setup();


    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("start");
    }

    @Test
    public void firstSimpleTest() {

        Reporter.log("level 1", 1, true);
        Reporter.log("level 2", 2, true);
        Reporter.log("level 3", 3, true);
        Reporter.log("level 4", 4, true);
        log("Method is started");
        log(2,"Method is started");
        log(3,"Method is started");
        log(4,"Method is started");
        log(5,"Method is started");

//        assertTrue("Result not equals to 4", 2 * 2 == 4);
        String regularExpression = "[\\d]{6}";
        String inputString1 = "just text";
        String inputString2 = "sdf123456";
        String inputString3 = "003";
        String inputString4 = "sadas0000004";
        String inputString6 = "000006";
        Reporter.log("Browser Opened", true);

        Pattern pattern = Pattern.compile(regularExpression);
        Matcher match1 = pattern.matcher(inputString1);
        Matcher match2 = pattern.matcher(inputString2);
        Matcher match3 = pattern.matcher(inputString3);
        Matcher match4 = pattern.matcher(inputString4);
        Matcher match6 = pattern.matcher(inputString6);
        log(1,"== create objects of pages");
        System.out.println(match1.matches());
        System.out.println(match2.matches());
        System.out.println(match3.matches());
        System.out.println(match4.matches());
        System.out.println(match6.matches());
    }

    @Test(enabled = false)
    public void secondSimpleTest() {
        assertTrue("Result not equals to 6", 2 * 3 == 4);
    }

    @Test(enabled = false)
    public void thirdSimpleTest() {
        assertTrue("Result not equals to 8", 2 * 4 == 8);
    }
}
