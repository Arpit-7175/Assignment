package com.networknt.petstore.handler.userHandler;

import com.mongodb.client.model.Filters;
import com.networknt.petstore.db.MongoStartupHookProvider;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class UsersUserIdDeleteHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String userId = exchange.getQueryParameters().get("userId").getFirst();

        User user =
                MongoStartupHookProvider.db
                        .getCollection("users", User.class)
                        .find(Filters.eq("_id", userId))
                        .first();
        if(user==null){
            exchange.setStatusCode(404);
            exchange.getResponseSender().send("No user with id:"+userId+ "exists");
        }
        MongoStartupHookProvider.db
                .getCollection("users", User.class)
                .findOneAndDelete(Filters.eq("_id", userId));

        // TODO: Fix with the latest method.
        exchange.setResponseCode(204);
        exchange.endExchange();
    }
}
