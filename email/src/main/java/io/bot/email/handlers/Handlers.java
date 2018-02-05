package io.bot.email.handlers;

import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class Handlers {

    List<AbstractHandler> handlers;

    public Handlers() {
        handlers = new ArrayList<>();
        handlers.add(new UserStartupHandler());
        handlers.add(new EmailVendorHandler());
        handlers.add(new EmailProtocolHandler());
        handlers.add(new EmailAddressHandler());
        handlers.add(new EmailPasswordHandler());
    }

    public void handle(Update update) {
        handlers.stream()
                .filter(it -> it.accept(update))
                .forEach(it -> it.handle(update));
    }
}
