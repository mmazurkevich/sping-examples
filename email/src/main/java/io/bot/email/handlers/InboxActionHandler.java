package io.bot.email.handlers;

import com.vdurmont.emoji.EmojiParser;
import io.bot.email.model.Preferences;
import io.bot.email.model.Protocol;
import io.bot.email.model.SetupState;
import io.bot.email.model.Vendor;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.StrictMath.toIntExact;

public class InboxActionHandler extends AbstractHandler {
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasCallbackQuery()
                && update.getCallbackQuery().getData().equals("inbox")
                && preferences.getSetupState().equals(SetupState.SETUP_FINISHED);

    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        org.telegram.telegrambots.api.objects. Message message = update.hasMessage() ?
                                                update.getMessage() : update.getCallbackQuery().getMessage();
        return new EditMessageText()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText(getEmails(preferences));

    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("<< Back").setCallbackData("backInActions"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private String getEmails(Preferences preferences) {
        try {
            Store store = Session.getInstance(getEmailProperties(preferences))
                    .getStore();

            store.connect(preferences.getEmail(), preferences.getPassword());

            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = messages.length - 1; i > messages.length - 1 - 5; i--) {
                Message message = messages[i];
                stringBuilder.append(":email: ")
                        .append(((InternetAddress)message.getFrom()[0]).getAddress())
                        .append("\n")
                        .append("Subject: ")
                        .append(message.getSubject())
                        .append("\n")
                        .append("Date: ")
                        .append(message.getSentDate())
                        .append("\n");
            }
            emailFolder.close(false);
            store.close();
            return EmojiParser.parseToUnicode(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EmojiParser.parseToUnicode(":email: Empty folder");
    }

    private Properties getEmailProperties(Preferences preferences) {
        Properties properties = new Properties();
        properties.put("mail.host", getHost(preferences.getVendor(), preferences.getProtocol()));
        properties.put("mail.port", getPort(preferences.getProtocol()));
        properties.put("mail.user", preferences.getEmail());
        properties.put("mail.store.protocol", preferences.getProtocol().name().toLowerCase());
        return properties;
    }

    private String getHost(Vendor vendor, Protocol protocol) {
        switch (vendor) {
            case GMAIL:
                switch (protocol) {
                    case POP3:
                        return "pop.gmail.com";
                    case IMAPS:
                        return "imap.gmail.com";
                    default:
                        return "smtp.gmail.com";
                }
            case YANDEX:
                switch (protocol) {
                    case POP3:
                        return "pop.yandex.com";
                    case IMAPS:
                        return "imap.yandex.com";
                    default:
                        return "smtp.yandex.com";
                }
            default:
                return "";
        }
    }

    private String getPort(Protocol protocol) {
        switch (protocol) {
            case POP3:
                return "995";
            case IMAPS:
                return "993";
            default:
                return "465";
        }
    }
}
