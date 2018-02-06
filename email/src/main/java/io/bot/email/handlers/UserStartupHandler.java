package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class UserStartupHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasMessage()
                && update.getMessage().getText().equals("/start")
                && preferences.getSetupState().equals(SetupState.FIRST_USER_SETUP);
        preferences.setSetupState(accepted ? SetupState.SELECT_EMAIL_VENDOR : preferences.getSetupState());
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {

        Chat chat = update.getMessage().getChat();
        preferences.setFirstName(chat.getFirstName());
        preferences.setLastName(chat.getLastName());

        System.out.println(update.getMessage().getChat().getFirstName());
        System.out.println(update.getMessage().getChat().getLastName());
        System.out.println(update.getMessage().getChatId());

        return new SendMessage() // Create a message object object
                .setChatId(update.getMessage().getChatId())
                .setReplyMarkup(getInlineKeyboard())
                .setText("Let's set up Email settings. Please select your Email provider");
    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Yandex").setCallbackData("yandex"));
        rowInline.add(new InlineKeyboardButton().setText("Gmail").setCallbackData("gmail"));
        rowInline.add(new InlineKeyboardButton().setText("MailRU").setCallbackData("mailru"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
