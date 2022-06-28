package icu.jnet.mccreate;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;

public class EmailHandler {

    private final String host, user, password;
    private final int port;

    public EmailHandler(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String searchActivationCode(String regEmail, int timeout) {
        for(int i = 0; i < timeout; i += 4) {
            waitMill(4000);

            String code = searchActivationCode(regEmail);
            if(code != null) {
                return code;
            }
        }
        return null;
    }

    public String searchActivationCode(String regEmail) {
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            // Get a Store object that implements the specified protocol
            Store store = session.getStore("imaps");
            // Connect to the current host using the specified username and password
            store.connect(host, port, user, password);

            // Create a Folder object corresponding to the given name in read only mode
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for(int idx = messages.length - 1; idx >= Math.max(messages.length - 10, 0); idx--) {
                Message message = messages[idx];
                String s = String.valueOf(message.getContent());

                if(message.getFrom()[0].toString().contains("de.mcdonalds.com")) {
                    if(s.contains("redirect.html")) {
                        if(message.getRecipients(Message.RecipientType.TO)[0].toString().equals(regEmail)) {
                            message.setFlag(Flags.Flag.DELETED, true);
                            return s.split("redirect.html")[1].split("ac=")[1].split("/")[0];
                        } else {
                            message.setFlag(Flags.Flag.SEEN, false);
                        }
                    } else {
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                }
            }
            inbox.close(true);
            store.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void waitMill(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
