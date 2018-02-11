package io.bot.email.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.bot.email.model.Preferences;
import io.bot.email.model.Protocol;
import io.bot.email.model.SetupState;
import io.bot.email.model.Vendor;
import org.bson.Document;

import java.lang.reflect.Field;

public class UserRepository {

    public void create(Preferences preferences) throws IllegalAccessException {
        MongoDatabase connection = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = connection.getCollection("users");
        Document convert = convert(preferences);
        collection.insertOne(convert);
    }

    public Preferences findByUserId(long userId) {
        MongoDatabase connection = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = connection.getCollection("users");
        Document user = collection.find(Filters.eq("userId", userId)).first();
        return user != null ? convert(user) : null;
    }

    private Preferences convert(Document document) {
        Preferences preferences = new Preferences();
        preferences.setUserId(document.getLong("userId"));
        preferences.setEmail(document.getString("email"));
        preferences.setPassword(document.getString("password"));
        preferences.setFirstName(document.getString("firstName"));
        preferences.setLastName(document.getString("lastName"));
        preferences.setProtocol(Protocol.valueOf(document.getString("protocol")));
        preferences.setVendor(Vendor.valueOf(document.getString("vendor")));
        preferences.setSetupState(SetupState.valueOf(document.getString("setupState")));
        return preferences;
    }

    private Document convert(Preferences preferences) throws IllegalAccessException {
        Document doc = new Document();
        Field[] declaredFields = Preferences.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object fieldValue = declaredField.get(preferences);
            String value = fieldValue != null ? fieldValue.toString() : "";
            doc.append(declaredField.getName(), value);
        }
        return doc;
    }
}
