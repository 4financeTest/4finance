package core;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class helps to connect and work with gmail account
 */
public class MailHelper {

    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "465";
    Store store;

    public MailHelper (String login, String password) throws MessagingException{
        this.store = connectToMail(login, password);
    }

    /**
     * Specify mail service settings for connect
     *
     * @return properties
     */
    public Properties createMailProperties() {
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.host", HOST);
        mailServerProperties.put("mail.smtp.socketFactory.port", PORT);
        mailServerProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.port", PORT);
        return mailServerProperties;
    }

    /**
     * Method creates mail session
     *
     * @return session
     */
    public Session createMailSession() {
        Session session = Session.getDefaultInstance(createMailProperties(), null);
        return session;
    }

    /**
     * Method creates a store to work in
     * @param login mail login
     * @param password mail password
     * @return - store
     * @throws MessagingException
     */
    public Store connectToMail(String login, String password) throws MessagingException{
        Store store = createMailSession().getStore("imaps");
        store.connect(HOST, login, password);
        return store;
    }

    /**
     * Method marks all messages in folder as Deleted
     *
     * @param folder folder to work with
     * @throws MessagingException
     */
    public void clearAllMails(String folder) throws MessagingException{
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_WRITE);

        int messageCount = inbox.getMessageCount();
        Message[] messages = inbox.getMessages();

        for (int i = 0; i < messageCount; i++) {
            messages[i].setFlag(Flags.Flag.DELETED, true);
        }
        inbox.close(true);
    }

    /**
     * Method returms las received mail message
     *
     * @param folder folder to look
     * @return message
     * @throws MessagingException
     * @throws IOException
     */
    public String getLastMail(String folder) throws MessagingException, IOException {
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        Multipart multipart = (Multipart) messages[0].getContent();
        return multipart.getBodyPart(0).getContent().toString();
    }

    /**
     * Method returns password recovery link from mail body
     *
     * @param folder folder to work with
     * @return URL for password recovery
     * @throws MessagingException
     * @throws IOException
     */
    public String getRecoveryLinkFromMessage(String folder) throws MessagingException, IOException {
        Pattern pattern = Pattern.compile("https://[a-zA-z./?=0-9-]*");
        Matcher matcher = pattern.matcher(getLastMail(folder));
        matcher.find();
        return matcher.group();
    }
}