package io.bot.email.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static MongoDatabase database;
    private static final String DATABSE_URL = System.getenv("MONGODB_URI");

    public static MongoDatabase getConnection() {
        if (database != null) {
            return database;
        } else {
            MongoClientURI connectionString = new MongoClientURI(DATABSE_URL);
            MongoClient mongoClient = new MongoClient(connectionString);
            database = mongoClient.getDatabase(connectionString.getDatabase());
            return database;
        }
    }
}
