package com.networknt.petstore.handler.userHandler;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.petstore.db.MongoStartupHookProvider;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.util.Map;

public class UsersUserIdPutHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String userId = exchange.getQueryParameters().get("userId").getFirst();

        Map<String, String> body =
                (Map<String, String>) exchange.getAttachment(BodyHandler.REQUEST_BODY);

        User user= Config.getInstance().getMapper().convertValue(body, User.class);
        User oldUser=MongoStartupHookProvider.db
                .getCollection("users", User.class)
                .find(Filters.eq("_id", userId))
                .first();
        if(oldUser==null){
            exchange.setStatusCode(404);
            exchange.getResponseSender().send("No user with id:"+userId+ "exists");
        }
        MongoStartupHookProvider.db
                .getCollection("users", User.class)
                .updateOne(Filters.eq("_id", userId), new BasicDBObject("$set", user));

        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.setStatusCode(200);
        exchange.getResponseSender().send("User updated");
    }
}
