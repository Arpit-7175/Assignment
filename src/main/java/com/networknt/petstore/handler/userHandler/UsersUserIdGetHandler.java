package com.networknt.petstore.handler.userHandler;

import com.mongodb.client.model.Filters;
import com.networknt.config.Config;
import com.networknt.petstore.db.MongoStartupHookProvider;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsersUserIdGetHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String userId = exchange.getQueryParameters().get("userId").getFirst();

        User user =
                MongoStartupHookProvider.db
                        .getCollection("users", User.class)
                        .find(Filters.eq("_id", userId))
                        .first();

        // NOTE: Sending JSON String
        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        if(user==null){
            exchange.setStatusCode(404);
            exchange.getResponseSender().send("No user with id:"+userId+ "exists");
        }
        String result = Config.getInstance().getMapper().writeValueAsString(user);
        exchange.getResponseSender().send(result);
    }
}
