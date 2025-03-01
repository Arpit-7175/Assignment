package com.networknt.petstore.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.networknt.config.Config;
import com.networknt.server.StartupHookProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoStartupHookProvider implements StartupHookProvider {

    public static MongoDatabase db;
    static String CONFIG_NAME = "mongo";

    static void initDataSource() {
        MongoConfig config = (MongoConfig) Config.getInstance().getJsonObjectConfig(CONFIG_NAME, MongoConfig.class);
        MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();
        clientOptions.minConnectionsPerHost(10); // minPoolSize
        clientOptions.connectionsPerHost(50); // maxPoolSize
        MongoClient mongoClient = new MongoClient(new ServerAddress(config.getHost()), clientOptions.build());

        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        db = mongoClient.getDatabase(config.getName()).withCodecRegistry(codecRegistry);
    }

    public void onStartup() {
        System.out.println("MongoStartupHookProvider is called");
        initDataSource();
        System.out.println("MongoStartupHookProvider db = " + db);
    }
}