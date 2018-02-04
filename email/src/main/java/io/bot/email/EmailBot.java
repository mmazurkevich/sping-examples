package io.bot.email;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class EmailBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {

                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Let's set up Email settings. Please select your Email provider");
                rowInline.add(new InlineKeyboardButton().setText("Yandex").setCallbackData("yandex"));
                rowInline.add(new InlineKeyboardButton().setText("Gmail").setCallbackData("gmail"));
                rowInline.add(new InlineKeyboardButton().setText("MailRU").setCallbackData("mailru"));
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().endsWith("@gmail.com")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Please enter Email password");
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("yandex") || call_data.equals("gmail") || call_data.equals("mailru")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Please select Email protocol");
                rowInline.add(new InlineKeyboardButton().setText("SMTP").setCallbackData("smtp"));
                rowInline.add(new InlineKeyboardButton().setText("POP3").setCallbackData("pop3"));
                rowInline.add(new InlineKeyboardButton().setText("IMAPS").setCallbackData("imaps"));
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
//                try {
//                    execute(message); // Sending our message object to user
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
            } else if (call_data.equals("smtp") || call_data.equals("pop3") || call_data.equals("imaps")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Please enter your Email address");
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
    }

    @Override
    public String getBotToken() {
    }
}
