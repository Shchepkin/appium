package ajaxMobileApp;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


public class Assertion {

    public void checkIsDisplayed(WebElement element) {
        try {
            Assert.assertTrue(element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

}
