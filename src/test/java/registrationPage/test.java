package registrationPage;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.*;


public class test {
    private Email email;

    @BeforeClass
    public void setup(){
        Reporter.log("Create objects of pages", true);
        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
    }

    @Test(priority = 1, enabled = false)
    public void get_Email_Text() {
        Reporter.log("===== Start get_Email_Text test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        String emailText = email.getEmailTextFromNewMessage();
        System.out.println("------------------------------------------\n" + emailText);
    }

    @Test(priority = 2, enabled = false)
    public void get_Code() {
        Reporter.log("===== Start get_Code test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        String emailCode = email.getValidationCode();
    }

    @Test(priority = 0)
    public void checkEmail() {
        Reporter.log("===== Start checkEmail test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.checkNewMessage();
        String emailCode = email.getValidationCode();
        System.out.println("> ====== emailCode: " + emailCode);
        System.out.println("> ====== emailText: " + email.emailText);
        email.deleteAllMessage();

    }

    @Test(priority = 2, enabled = false)
    public void delete_all_message() {
        Reporter.log("===== Start delete_all_message test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.deleteAllMessage();
        System.out.println("done\n");
    }

    @AfterClass
    public void endSuit() {
        Reporter.log("===== Close all folders and stores", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.closeAll();
    }
}
