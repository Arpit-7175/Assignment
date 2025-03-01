package com.networknt.petstore.handler.userHandler;

import com.mongodb.MongoCommandException;
import com.networknt.petstore.db.MongoStartupHookProvider;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class UsersLoadGetHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        // Static Content Loading
        final Logger log = LoggerFactory.getLogger(UsersLoadGetHandler.class);

        List<User> users = new ArrayList<>();
        users.add(new User(UUID.randomUUID().toString(), "Arjun SK", "arjunsk@gmail.com"));
        users.add(new User(UUID.randomUUID().toString(), "Admin", "admin@arjunsk.com"));

        // Create Collection only once
        try {
            MongoStartupHookProvider.db.createCollection("users");
        } catch (MongoCommandException ex) {
            log.warn("Collection already exist");
        }
        MongoStartupHookProvider.db.getCollection("users", User.class).insertMany(users);
        exchange.endExchange();
    }
}
