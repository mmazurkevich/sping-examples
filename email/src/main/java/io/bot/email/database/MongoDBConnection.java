package io.bot.email.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static MongoDatabase database;
    private static final String DATABSE_URL = "mongodb://127.0.0.1:27017";
    private static final String DATABSE_NAME = "email_bot";

    public static MongoDatabase getConnection() {
        if (database != null) {
            return database;
        } else {
            MongoClientURI connectionString = new MongoClientURI(DATABSE_URL);
            MongoClient mongoClient = new MongoClient(connectionString);
            database = mongoClient.getDatabase(DATABSE_NAME);
            return database;
        }
    }
}
