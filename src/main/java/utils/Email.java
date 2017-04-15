package utils;

import org.testng.Assert;
import pages.Base;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Email{

    private String password;
    private String user;
    private String host;
    private Folder folder;
    private Store store;
    private Message message[];

    private String emailCode;
    private String emailText;

    public Email(String host, String user, String password) {
        try {
            this.host = host;
            this.user = user;
            this.password = password;

            // Get system settings
            Base.log(1, "Get system settings");
            Properties props = System.getProperties();
            props.put("mail.pop3.host", host);

            // Get session
            Base.log(1, "Get session");
            Session session = Session.getDefaultInstance(props, null);

            // Get store
            Base.log(1, "Get store");
            store = session.getStore("pop3");
            store.connect(host, user, password);

            openFolderAndGetMessages();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private Message[] openFolderAndGetMessages() {
        try {
            Base.log(1, "\n=== Start process for opening folder.");

            // Get folder
            Base.log(1, "> Get inbox folder");
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Get messages from Folder
            Base.log(1, "> Get messages from inbox folder");
            message = folder.getMessages();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message[] checkNewMessage() {
        Base.log(1, "\n=== Start process for checking New Message.");
        int counter = 30;
        while (message.length == 0 & counter > 0) {

            Base.log(1, "\n> Attempts left: " + counter + "\n-------------------");
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
            Base.log(1, "> New E-mail is found");
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
        Base.log(1, "Method is started");
        String input = getEmailTextFromNewMessage();
        if (emailText != null) {
            Pattern pattern = Pattern.compile("[\\d]{6}");
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            emailCode = matcher.group();
            Base.log(1, "> E-mail Validation code is: " + emailCode);
        }
        return emailCode;
    }


    private String getEmailTextFromNewMessage() {
        try {
            if (message.length > 0) {
                Base.log(1, "\n=== Start process for getting text from e-mail.");
                Base.log(1, "> There are found new messages. So get the newest one.");
                Message m = folder.getMessage(folder.getMessageCount());

                if (m.getSize() > 0) {
                    Base.log(1, "> Get Message is successfully.");
                } else {
                    Assert.fail("> Get Message is fail!");
                }

                if (m.isMimeType("text/plain")) {
                    Base.log(1, "> CONTENT-TYPE: " + m.getContentType());
                    emailText = String.valueOf(m.getContent());
                } else if (m.isMimeType("multipart/*")) {
                    Base.log(1, "> CONTENT-TYPE: " + m.getContentType());
                    Base.log(1, "> Get Multipart content.");
                    Multipart mp = (Multipart) m.getContent();

                    Base.log(1, "> Get Body Part from content.");
                    BodyPart bp = mp.getBodyPart(0);
                    emailText = String.valueOf(bp.getContent());
                }

            } else Base.log(1, "> There are no messages found.");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return emailText;
    }

    public void deleteAllMessage() {
        try {
            if (message.length > 0) {
                Base.log(1, "\n=== Start process of deleting all message.");
                Base.log(1, "> Number of messages: " + message.length);
                for (Message m : message) {
                    m.setFlag(Flags.Flag.DELETED, true);
                    System.out.println(m.getMessageNumber() + ": Deleted\nFROM: " + m.getFrom()[0] + "\nSubject: " + m.getSubject() + "\nSentDate" + m.getSentDate() + "\n");
                }
                // folder.setFlags(1, 1, new Flags(Flags.Flag.DELETED));
            } else Base.log(1, "> There is no messages found.");

            // close the store and folder objects
            Base.log(1, "> Close the store and folder objects");
            closeAll();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        try {
            Base.log(1, "\n=== Start closing process of all folders and stores.");
            // close the store and folder objects
            if(folder.isOpen()){
                folder.close(true);
                Base.log(1, "> Folder is closed.");
            }else Base.log(1, "> Folder is already closed.");

            if(store.isConnected()){
                store.close();
                Base.log(1, "> Store is closed.");
            }else Base.log(1, "> Store is already closed.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
