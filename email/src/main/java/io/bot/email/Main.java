package io.bot.email;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.mail.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void check(String host, String user,
                             String password) {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            Folder defaultFolder = store.getDefaultFolder();

            //create the folder object and open it
            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

//            // search for all "unseen" messages
//            Flags seen = new Flags(Flags.Flag.SEEN);
//            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
//            Message[] search = emailFolder.search(unseenFlagTerm);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = messages.length - 1; i > 0; i--) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Date: " + message.getSentDate());
                String s = message.getFrom()[0].toString();
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
                if (m.find()) {
                    System.out.println("From: " + m.group());
                }
//                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new EmailBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        check(host, username, password);

    }

}
