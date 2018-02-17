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

    private static final String REG_EXP = "^i\\d+$";
    private int currentPage;
    private int messagesCount;
    private int pageSize = 5;

    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasCallbackQuery()
                && (update.getCallbackQuery().getData().equals("inbox")
                || update.getCallbackQuery().getData().matches(REG_EXP))
                && preferences.getSetupState().equals(SetupState.SETUP_FINISHED);

    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        org.telegram.telegrambots.api.objects.Message message = update.hasMessage() ?
                update.getMessage() : update.getCallbackQuery().getMessage();
        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("inbox")) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(update.getCallbackQuery().getData().replace("i", ""));
        }
        return new EditMessageText()
                .setMessageId(toIntExact(message.getMessageId()))
                .setChatId(message.getChatId())
                .setText(getEmails(preferences))
                .setReplyMarkup(getInlineKeyboard());

    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(getPagination());
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("<< Back").setCallbackData("backInActions"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private List<InlineKeyboardButton> getPagination() {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int pagesCount = (int) Math.ceil(messagesCount / (double) pageSize);
        rowInline.add(new InlineKeyboardButton().setText("<< 1").setCallbackData("i1"));
        int previousPage = currentPage - 1;
        if (previousPage > 1)
            rowInline.add(new InlineKeyboardButton().setText("< " + previousPage).setCallbackData("i" + previousPage));
        rowInline.add(new InlineKeyboardButton().setText("- " + currentPage + " -").setCallbackData("i" + currentPage));
        int futurePage = currentPage + 1;
        if (futurePage < pagesCount)
            rowInline.add(new InlineKeyboardButton().setText("" + futurePage + " >").setCallbackData("i" + futurePage));
        rowInline.add(new InlineKeyboardButton().setText("" + pagesCount + " >>").setCallbackData("i" + pagesCount));
        return rowInline;
    }

    private String getEmails(Preferences preferences) {
        try {
            Store store = Session.getInstance(getEmailProperties(preferences))
                    .getStore();

            store.connect(preferences.getEmail(), preferences.getPassword());

            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            messagesCount = messages.length;
            int fromValue = messagesCount - 1;
            if (currentPage != 1) {
                fromValue = messagesCount - (currentPage - 1) * pageSize - 1;
            }
            int toValue = messagesCount - currentPage * pageSize;
            toValue = toValue < 0 ? 0 : toValue;

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = fromValue; i >= toValue; i--) {
                Message message = messages[i];
                stringBuilder.append(":email: ")
                        .append(((InternetAddress) message.getFrom()[0]).getAddress())
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
