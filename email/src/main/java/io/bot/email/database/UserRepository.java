package io.bot.email.database;

import com.mongodb.BasicDBObject;
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

    public void create(Preferences preferences) {
        MongoDatabase connection = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = connection.getCollection("users");
        Document convert = convert(preferences);
        collection.insertOne(convert);
    }

    public void update(Preferences preferences) {
        MongoDatabase connection = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = connection.getCollection("users");
        Document convert = convertForUpdate(preferences);
        collection.updateOne(Filters.eq("userId", String.valueOf(preferences.getUserId())),
                new BasicDBObject("$set", new BasicDBObject(convert)));
    }

    public Preferences findByUserId(long userId) {
        MongoDatabase connection = MongoDBConnection.getConnection();
        MongoCollection<Document> collection = connection.getCollection("users");
        Document user = collection.find(Filters.eq("userId", String.valueOf(userId))).first();
        return user != null ? convert(user) : null;
    }

    private Preferences convert(Document document) {
        Preferences preferences = new Preferences();
        preferences.setUserId(Long.valueOf(document.getString("userId")));
        preferences.setEmail(document.getString("email"));
        preferences.setPassword(document.getString("password"));
        preferences.setFirstName(document.getString("firstName"));
        preferences.setLastName(document.getString("lastName"));
        if (!document.getString("protocol").isEmpty())
            preferences.setProtocol(Protocol.valueOf(document.getString("protocol")));
        if (!document.getString("vendor").isEmpty())
            preferences.setVendor(Vendor.valueOf(document.getString("vendor")));
        if (!document.getString("setupState").isEmpty())
            preferences.setSetupState(SetupState.valueOf(document.getString("setupState")));
        return preferences;
    }

    private Document convertForUpdate(Preferences preferences) {
        Document doc = new Document();
        Field[] declaredFields = Preferences.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.getName().equals("userId")) {
                Object fieldValue = null;
                try {
                    fieldValue = declaredField.get(preferences);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String value = fieldValue != null ? fieldValue.toString() : "";
                doc.append(declaredField.getName(), value);
            }
        }
        return doc;
    }

    private Document convert(Preferences preferences) {
        Document doc = new Document();
        Field[] declaredFields = Preferences.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object fieldValue = null;
            try {
                fieldValue = declaredField.get(preferences);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String value = fieldValue != null ? fieldValue.toString() : "";
            doc.append(declaredField.getName(), value);
        }
        return doc;
    }
}
