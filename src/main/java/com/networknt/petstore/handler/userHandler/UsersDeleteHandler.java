package com.networknt.petstore.handler.userHandler;

import com.networknt.petstore.db.MongoStartupHookProvider;
import com.mongodb.BasicDBObject;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class UsersDeleteHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        // NOTE: Delete *
        BasicDBObject blank = new BasicDBObject();
        MongoStartupHookProvider.db.getCollection("users", User.class).deleteMany(blank);

        // TODO: Fix with the latest method.
        exchange.setResponseCode(204);
        exchange.endExchange();
    }
}