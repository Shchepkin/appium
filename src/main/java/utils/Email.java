package utils;

import org.testng.Assert;
import org.testng.Reporter;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Email {

    private String password;
    private String user;
    private String host;
    private Folder folder;
    private Store store;
    private Message message[];

    public String emailCode;
    public String emailText;

    public Email(String host, String user, String password) {
        try {
            this.host = host;
            this.user = user;
            this.password = password;

            // Get system settings
            Reporter.log("Get system settings", true);
            Properties props = System.getProperties();
            props.put("mail.pop3.host", host);

            // Get session
            Reporter.log("Get session", true);
            Session session = Session.getDefaultInstance(props, null);

            // Get store
            Reporter.log("Get store", true);
            store = session.getStore("pop3");
            store.connect(host, user, password);

            openFolderAndGetMessages();


        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public Message[] openFolderAndGetMessages() {
        try {
            Reporter.log("\n=== Start process for opening folder.", true);

            // Get folder
            Reporter.log("> Get inbox folder", true);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Get messages from Folder
            Reporter.log("> Get messages from inbox folder", true);
            message = folder.getMessages();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message[] checkNewMessage() {
        Reporter.log("\n=== Start process for checking New Message.", true);
        int counter = 30;
        while (message.length == 0 & counter > 0) {

            Reporter.log("\n> Attempts left: " + counter + "\n-------------------", true);
            openFolderAndGetMessages();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter--;
        }
        if (counter <= 0) {
            Assert.fail("> Timeout error! New E-mail is NOT found!");
        } else {
            Reporter.log("> New E-mail is found", true);
            try {
                folder = store.getFolder("INBOX");
                folder.open(Folder.READ_WRITE);
                message = folder.getMessages();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public String getValidationCode() {
        Reporter.log("\n=== Start process for getting Validation code.", true);
        String input = getEmailTextFromNewMessage();
        if (emailText != null) {
            Pattern pattern = Pattern.compile("[\\d]{6}");
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            emailCode = matcher.group();
            Reporter.log("> E-mail Validation code is: " + emailCode, true);
        }
        return emailCode;
    }


    public String getEmailTextFromNewMessage() {
        try {
            if (message.length > 0) {
                Reporter.log("\n=== Start process for getting text from e-mail.", true);
                Reporter.log("> There are found new messages. So get the newest one.", true);
                Message m = folder.getMessage(folder.getMessageCount());

                if (m.getSize() > 0) {
                    Reporter.log("> Get Message is successfully.", true);
                } else {
                    Assert.fail("> Get Message is fail!");
                }

                if (m.isMimeType("text/plain")) {
                    Reporter.log("> CONTENT-TYPE: " + m.getContentType(), true);
                    emailText = String.valueOf(m.getContent());
                } else if (m.isMimeType("multipart/*")) {
                    Reporter.log("> CONTENT-TYPE: " + m.getContentType(), true);
                    Reporter.log("> Get Multipart content.", true);
                    Multipart mp = (Multipart) m.getContent();

                    Reporter.log("> Get Body Part from content.", true);
                    BodyPart bp = mp.getBodyPart(0);
                    emailText = String.valueOf(bp.getContent());
                }

            } else Reporter.log("> There are no messages found.", true);

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailText;
    }

    public void deleteAllMessage() {
        try {
            if (message.length > 0) {
                Reporter.log("\n=== Start process of deleting all message.", true);
                Reporter.log("> Number of messages: " + message.length, true);
                for (Message m : message) {
                    m.setFlag(Flags.Flag.DELETED, true);
                    System.out.println(m.getMessageNumber() + ": Deleted\nFROM: " + m.getFrom()[0] + "\nSubject: " + m.getSubject() + "\nSentDate" + m.getSentDate() + "\n");
                }
                // folder.setFlags(1, 1, new Flags(Flags.Flag.DELETED), true);
            } else Reporter.log("> There is no messages found.", true);

            // close the store and folder objects
            Reporter.log("> Close the store and folder objects", true);
            closeAll();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        try {
            Reporter.log("\n=== Start closing process of all folders and stores.", true);
            // close the store and folder objects
            if(folder.isOpen()){
                folder.close(true);
                Reporter.log("> Folder is closed.", true);
            }else Reporter.log("> Folder is already closed.", true);

            if(store.isConnected()){
                store.close();
                Reporter.log("> Store is closed.", true);
            }else Reporter.log("> Store is already closed.", true);

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
